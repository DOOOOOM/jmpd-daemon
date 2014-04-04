import java.io.*;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.net.*;

public class Client {
	Socket clientSocket;
	DataOutputStream outToServer;
	DataInputStream inFromServer;

	public Client() throws Exception{
		clientSocket = new Socket(InetAddress.getByName("localhost"),5005);
	}
	public void getMembersNames() throws Exception{
		//FileWriter writer = new FileWriter("C:/Users/oazogu/Desktop/jp.txt");
		OutputStreamWriter outTo = new OutputStreamWriter(clientSocket.getOutputStream());
		JsonGenerator jsonGen = Json.createGenerator(outTo);
		jsonGen.writeStartObject()
			.write("TEAM","DOOOOOM")
			.write("PROJECT","jMPD & jMPC")
			    .writeStartArray("Roles")
			       .writeStartObject()
			         .write("Cordinator","Zach")
			         .write("Lead Developer","Essenam")
			         .write("Developer","Obi")
			       .writeEnd()
			    .writeEnd()
		.writeEnd();
		jsonGen.close();
	}
	
	public void getMembersName_toFile() throws Exception{
		FileWriter writer = new FileWriter("Project.json");
		//OutputStreamWriter outTo = new OutputStreamWriter(clientSocket.getOutputStream());
		JsonGenerator jsonGen = Json.createGenerator(writer);
		jsonGen.writeStartObject()
			.write("TEAM","DOOOOOM")
			.write("PROJECT","jMPD & jMPC")
			    .writeStartArray("Roles")
			       .writeStartObject()
			         .write("Cordinator","Zach")
			         .write("Lead Developer","Essenam")
			         .write("Developer","Obi")
			       .writeEnd()
			    .writeEnd()
		.writeEnd();
		jsonGen.close();
	}
	
	public void sendMsg(String msg)throws Exception{
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(msg);
	}
	public void receiveMsg(String response)throws Exception{
		BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		System.out.println(is.readLine());
		
	}
	public void closeConnection()throws Exception{
		clientSocket.close();
	}
	public static void main(String[] args)throws Exception {
		Client client = new Client();
		client.getMembersNames();
		client.getMembersName_toFile();
		//client.sendMsg("DOOOOOM ready!!");
		client.closeConnection();
		

	}

}
