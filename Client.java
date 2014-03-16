package connection;
import java.io.*;
import java.net.*;

public class Client {
	Socket clientSocket;
	DataOutputStream outToServer;

	public Client() throws Exception{
		clientSocket = new Socket(InetAddress.getByName("localhost"),5005);
	}
	public void sendMsg(String msg)throws Exception{
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(msg);
	}
	public void closeConnection()throws Exception{
		clientSocket.close();
	}
	public static void main(String[] args)throws Exception {
		Client client = new Client();
		client.sendMsg("DOOOOOM ready!!");
		client.closeConnection();
		

	}

}
