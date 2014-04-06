package connection_package;
import java.io.*;

import container_class_package.JParser;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client{	
	Socket clientSocket;
	DataOutputStream outToServer;
	DataInputStream inFromServer;
	JParser jsonParser;
	
	public Client() throws Exception{
		clientSocket = new Socket(InetAddress.getByName("localhost"),5005);
		jsonParser = new JParser(clientSocket);
	}
	
	public void getPlaylist() throws Exception{
		System.out.println("about to send");
		jsonParser.sendMessage("playlist", "soul");
		if(clientSocket.isClosed()){
			System.out.println("clientSocket is already closed at this point");
		}
		Map <String,Object> result = jsonParser.jsonParser();
		//System.out.println(result.toString());
		jsonParser.closeSocket();
	}
	public static void main(String[] args)throws Exception {
		Client client = new Client();
		client.getPlaylist();
	}

}
