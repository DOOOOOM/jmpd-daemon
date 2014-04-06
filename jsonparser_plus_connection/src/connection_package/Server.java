package connection_package;
import java.io.*;
import java.net.*;
import java.util.*;

import container_class_package.JParser;
import container_class_package.TestDatabase;
public class Server {

    public static void main(String[] args) throws Exception {
        int PORT = Configure();
        Server daemon = new Server();
        ServerSocket listeningSocket = new ServerSocket(5005);         
        while(true){
            Socket connectionMade = listeningSocket.accept();
            //Handle connection request	& Init the thread to start processing request
            Thread thread = new Thread(daemon.new daemonRequest(connectionMade));
            //start thread
            thread.start();
        }
    }
    private final class daemonRequest implements Runnable {
    	public JParser jsonParser;
    	private Map <String,Object> _requestContainer = new HashMap<String,Object>();
    	TestDatabase tdb = new TestDatabase();
        Socket socket;

        public daemonRequest(Socket socket) throws Exception {
            this.socket = socket;
            jsonParser = new JParser(this.socket);
        }
        
        public void run() {
        	try {
            	_requestContainer = jsonParser.jsonParser();
            	System.out.println(_requestContainer.toString());
            	for(Map.Entry<String, Object> entry : _requestContainer.entrySet()){
            		switch(entry.getKey()){
            		case "playlist":
            			List<String> result = tdb.findPlayList((String)entry.getValue());
            			//System.out.println("Server: message to be sent "+result.toString());
            			jsonParser.sendMessage("playlist", "soul");
            			System.out.println("Server sent response");
            			//jsonParser.sendMessageWithArgList("playlist", result);
            			jsonParser.closeSocket();
            			System.out.println("Server closed socket");
            			break;
            		default:
            			jsonParser.sendMessage("error", "unknownCommand");      			
                	
                	}            		            		 
            	}
            	
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
    }
    private static int Configure() {
        int port = 5005;
        Properties props = new Properties();
        InputStream in = null;

        try {
            in = new FileInputStream("jmpd.properties");
            if(in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null)
                   in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(props.getProperty("Port") != null) {
            port = Integer.parseInt(props.getProperty("Port"));
        }
        System.out.println(port);
        return port;
    }
}
