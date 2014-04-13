package dooooom.jmpd;

import java.io.*;
import java.net.*;
import java.util.*;

import container_class_package.JParser;
import container_class_package.TestDatabase;

public class UDPServer {
	final static int messageLength = 1024;

    public enum Command {
        NULL, TOGGLE, PAUSE, PLAY, STOP, PREV,
        NEXT, ADD, ADDTOPLAYLIST, REM,
        REMPLAYLIST, DEL, ACK
    }

	public static void main(String[] args) {
		int PORT = Configure();
		byte[] receiveData = new byte[messageLength];
		try {
			UDPServer daemon = new UDPServer();
			DatagramSocket socket = new DatagramSocket(PORT);
			while(true){
				//Receiving 
				DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
				//Waits for an incoming datagram.
				socket.receive(receivePacket);
				//Handle connection request	& Init the thread to start processing request
	            Thread thread = new Thread(daemon.new daemonRequest(socket,receivePacket));
	            //start thread
	            thread.start();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private final class daemonRequest implements Runnable {
    	public JParser jsonParser;
    	private Map <String,Object> _requestContainer = new HashMap<String,Object>();
    	TestDatabase tdb = new TestDatabase();
    	DatagramSocket socket;

        public daemonRequest(DatagramSocket socket,DatagramPacket packet) throws Exception {
            this.socket = socket;
            jsonParser = new JParser(this.socket,packet);
        }
        
        public void run() {
        	try {
            	_requestContainer = jsonParser.jsonParser();
            	System.out.println(_requestContainer.toString());
            	for(Map.Entry<String, Object> entry : _requestContainer.entrySet()){
            		switch(Command.valueOf(entry.getKey())){
                    case TOGGLE:
                        break;
                    case PAUSE:
                        break;
                    case PLAY:
                        break;
                    case STOP:
                        break;
                    case PREV:
                        break;
                    case NEXT:
                        break;
                    case ADD:
                        break;
            		case ADDTOPLAYLIST:
            			List<String> result = tdb.findPlayList((String)entry.getValue());
            			//System.out.println("Server: message to be sent "+result.toString());
            			//jsonParser.sendMessage("playlist", "soul");
            			System.out.println("Server sent response");
            			jsonParser.sendMessageWithArgList(Command.ADDTOPLAYLIST, result);
            			break;
                    case REM:
                        break;
                    case REMPLAYLIST:
                        break;
                    case DEL:
                        break;
                    case ACK:
                        break;
                    case NULL:
                        break;
            		default:
            			jsonParser.sendMessage(Command.ACK, "unknownCommand");
                	
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
//            e.printStackTrace();
            System.out.println("No user configuration. Using default values");
        } finally {
            try {
                if(in != null)
                   in.close();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        if(props.getProperty("Port") != null) {
            port = Integer.parseInt(props.getProperty("Port"));
        }
        System.out.println(port);
        return port;
    }

}
