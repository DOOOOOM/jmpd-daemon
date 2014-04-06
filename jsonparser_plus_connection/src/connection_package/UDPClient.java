package connection_package;
import java.io.*;

import container_class_package.JParser;
import container_class_package.TestDatabase;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class UDPClient {
	final static int messageLength = 1024;
	static DatagramSocket clientSocket;
	InetAddress IPAddress;
	DataOutputStream outToServer;
	DataInputStream inFromServer;
	JParser jsonParser;
	
	public static void main(String[] args){
		final int port = 5006;
		try {
			final UDPClient client = new UDPClient();
			clientSocket = new DatagramSocket(port);
			System.out.println("Now listening on "+String.valueOf(port));
			//start function to receive responses from server.
			Thread threadListener = new Thread( new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						client.listener(port);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
			threadListener.start();
			client.sendMessage("playlist", "gospel");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void listener(int port) throws Exception{
		byte[] receiveData = new byte[messageLength];
		while(true){
			//Receiving 
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			//Waits for an incoming datagram.
			clientSocket.receive(receivePacket);
			System.out.println("package received from server");
			//Handle connection request	& Init the thread to start processing request
            Thread thread = new Thread(this.new daemonRequest(clientSocket,receivePacket));
            //start thread
            thread.start();			
		}		
	}
	
	public void sendMessage(String cmd,String arg) throws Exception{
		int port = 5005;
		byte[] envelope;
		ByteArrayOutputStream outGoing = new ByteArrayOutputStream();
		//DatagramSocket socket = new DatagramSocket(5007);
		InetAddress IPAddress = InetAddress.getByName("localhost");
		JsonGenerator jsonGen = Json.createGenerator(outGoing);
		jsonGen.writeStartObject()
			.write(cmd,arg)
		.writeEnd();
		jsonGen.close();
		//convert ByteStream .. to byte array to send packet
		envelope = outGoing.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(envelope,envelope.length,IPAddress,port);
		clientSocket.send(sendPacket);
	}
		
	public void sendMessageWithArgList(String cmd, List<String> result) throws Exception{		
		int port = 5005;
		byte[] envelope;
		int PORT = Configure();
		ByteArrayOutputStream outGoing = new ByteArrayOutputStream();
		JsonGenerator jsonGen = Json.createGenerator(outGoing);
		DatagramSocket socket = new DatagramSocket(5007);
		InetAddress IPAddress = InetAddress.getByName("localhost");
		Integer count = new Integer(0);
        JsonGenerator jarray = jsonGen.writeStartObject().writeStartArray(cmd).writeStartObject();		
	     for(String eachItem : result){
	    	 count++;
	    	 jarray.write(cmd+"_"+count.toString(),eachItem);		        	 
	     }
	    jarray.writeEnd().writeEnd().writeEnd();
		jsonGen.close();
		//convert ByteStream .. to byte array to send packet
		envelope = outGoing.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(envelope,envelope.length,IPAddress,port);
		clientSocket.send(sendPacket);
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
	
	public void getPlaylist() throws Exception{
		System.out.println("about to send");
		jsonParser.sendMessage("playlist", "soul");
		if(clientSocket.isClosed()){
			System.out.println("clientSocket is already closed at this point");
		}
		Map <String,Object> result = jsonParser.jsonParser();
		//System.out.println(result.toString());
	}
	

}
