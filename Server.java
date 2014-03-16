package connection;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	public static void main(String[] args)throws Exception {
		final int PORT = 5005;
		Server daemon = new Server();
		ServerSocket listeningSocket = new ServerSocket(PORT);
		while(true){
			Socket connectionMade = listeningSocket.accept();
			//Handle connection request	& Init the thread to start processing request
			Thread thread = new Thread(daemon.new daemonRequest(connectionMade));			
			//start thread
			thread.start();
			
		}		

	}
	private final class daemonRequest implements Runnable{
		Socket socket;
		
		public daemonRequest(Socket socket)throws Exception{
			this.socket = socket;
		}
	
		public void printer()throws Exception{
			BufferedReader is = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			DataOutputStream os = new DataOutputStream(this.socket.getOutputStream());
			String requestLine = is.readLine();
			System.out.println(requestLine);
		}
		public void run(){
			try{
				printer();
			}catch(Exception e){
				System.out.println(e);
			}
			
		}
	}

}
