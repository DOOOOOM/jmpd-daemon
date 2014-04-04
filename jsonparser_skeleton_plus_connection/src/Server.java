import java.io.*;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) throws Exception {
       // int PORT = Configure();
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
        Socket socket;

        public daemonRequest(Socket socket) throws Exception {
            this.socket = socket;
        }
        
        public void jsonParser ()throws Exception{
        	InputStreamReader inFromClient = new InputStreamReader(this.socket.getInputStream());
        	JsonParser jParser = Json.createParser(inFromClient);
        	while(jParser.hasNext()){
        		JsonParser.Event event = jParser.next();
        		switch(event){
        		case START_OBJECT:
        			System.out.println(event.toString());
        			break;
        		case KEY_NAME:
        			System.out.print(event.toString()+ " "+jParser.getString()+" ");
        			break;
        		case VALUE_STRING:
        			System.out.println(event.toString()+ " "+jParser.getString());
        			break;
        		case VALUE_NUMBER:
        			System.out.println(event.toString()+ " "+jParser.getString());
        			break;
        		case START_ARRAY:
        			while(event.toString() != "END_ARRAY"){
        				if (event.toString() == "KEY_NAME"){
        				    System.out.print("\t"+event.toString()+ " "+jParser.getString());
        				}else if (event.toString() == "VALUE_STRING"){
        					System.out.println(" "+event.toString()+ " "+jParser.getString());        					
        				}else if (event.toString() == "VALUE_NUMBER"){
        					System.out.println("\t"+event.toString()+ " "+jParser.getString()); 
        				}else{
        					if (event.toString() != "START_OBJECT"){
        						System.out.println(event.toString());
        					}else if (event.toString() != "END_OBJECT"){
        						System.out.println(event.toString());
        					}else if (event.toString() != "START_ARRAY"){
        						System.out.println(" "+event.toString());
        					}else if (event.toString() != "END_ARRAY"){
        						System.out.println(event.toString());
        					}else{
        						System.out.println(event.toString());
        					}        					
        				}
        				event = jParser.next();      				
        			}
        			break;
        		
        		}
        		
        	}
        	
        }
        
        public void run() {
            try {
                jsonParser();
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
