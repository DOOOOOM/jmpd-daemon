package dooooom.jmpd;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPServer implements Runnable{
    public enum Command {
        NULL, TOGGLE, PAUSE, PLAY, STOP, PREV,
        NEXT, ADD, ADDTOPLAYLIST, REM,
        REMPLAYLIST, DEL, ACK
    }

	final static int messageLength = 1024;
    public JParser jsonParser;
    private Map <String,Object> _requestContainer = new HashMap<String,Object>();
    int PORT = Configure();
    byte[] receiveData = new byte[messageLength];
    //TestDatabase tdb = new TestDatabase();
    DatagramSocket socket;

    public UDPServer() throws Exception {
        socket = new DatagramSocket(PORT);
    }

    public void run() {
        Player controller = new Player();

        while(true){
            //Receiving
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            //Waits for an incoming datagram.
            try {
                socket.receive(receivePacket);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                jsonParser = new JParser(this.socket,receivePacket);
                _requestContainer = jsonParser.jsonParser();
                System.out.println(_requestContainer.toString());
                for(Map.Entry<String, Object> entry : _requestContainer.entrySet()){
                    switch(Command.valueOf(entry.getKey())){
                        case TOGGLE:
                            controller.toggle();
                            break;
                        case PAUSE:
                            break;
                        case PLAY:
                            break;
                        case STOP:
                            break;
                        case PREV:
                            controller.prev();
                            break;
                        case NEXT:
                            controller.next();
                            break;
                        case ADD:
//                            controller.add();
                            break;
                        case ADDTOPLAYLIST:
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
