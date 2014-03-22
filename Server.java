package dooooom.jmpd.daemon.player;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) throws Exception {
        int PORT = Configure();
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
    private final class daemonRequest implements Runnable {
        Socket socket;

        public daemonRequest(Socket socket) throws Exception {
            this.socket = socket;
        }
        public void printer() throws Exception {
            BufferedReader is = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter os = new PrintWriter(this.socket.getOutputStream(), true);
            while(socket != null) {
                String requestLine = is.readLine();
                os.println(requestLine);
            }
            is.close();
            os.close();
        }
        public void run() {
            try {
                printer();
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
