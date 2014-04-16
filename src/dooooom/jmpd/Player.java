package dooooom.jmpd;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

public class Player extends Application {
    public enum Command {
        NULL, TOGGLE, PAUSE, PLAY, STOP, PREV,
        NEXT, ADD, ADDTOPLAYLIST, REM,
        REMPLAYLIST, DEL, ACK
    }

    private ArrayList<String> playQueueFiles = new ArrayList<String>();
    private ArrayList<MediaPlayer> playQueue = new ArrayList<MediaPlayer>();
    private boolean loopingRepeat = false;
    private int currentPlayer;

    @Override
    public void start(Stage arg0) {
        PlayerControl control = new PlayerControl();
        Thread controllerThread = new Thread(control);
        controllerThread.start();
//        addSongs();
        setPlayQueue();
//        playQueue.get(0).play();
    }
    public void setPlayQueue() {
        for(String path : playQueueFiles) {
            System.out.println(path);
            MediaPlayer p = new MediaPlayer(new Media(path));
            playQueue.add(p);
        }
        for(int i = 0; i < playQueue.size(); i++) {
            if(i < playQueue.size()-1) {
                final int t = i + 1;
                playQueue.get(i).setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        currentPlayer = t;
                        playQueue.get(t).play();
                    }
                });
            } else if(loopingRepeat) {
                playQueue.get(playQueue.size()-1).setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        playQueue.clear();
                        setPlayQueue();
                        currentPlayer = 0;
                        playQueue.get(0).play();
                    }
                });
            }
        }
    }
    //Testing only
    public void addSongs() {
//        playQueueFiles.add("file:/home/zap/alarm03.mp3");
        ArrayList<String> pq = new ArrayList<String>();
//        pq.add("file:/home/zap/music/Sleigh%20Bells/Treats/01%20Tell%20'Em.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/01 Tell 'Em.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/02 Kids.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/03 Riot Rhythm.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/04 Infinity Guitars.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/05 Run the Heart.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/06 Rachel.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/07 Rill Rill.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/08 Crown On the Ground.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/09 Straight A's.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/10 A-B Machines.mp3");
        pq.add("file:/home/zap/music/Sleigh Bells/Treats/11 Treats.mp3");

        for(String s : pq) {
            String str = s.replace(" ", "%20");
            playQueueFiles.add(str);
        }
//        playQueueFiles.add("file:/home/zap/alarm02.mp3");
//        playQueueFiles.add("file:/home/zap/alarm03.mp3");
//        playQueueFiles.add("file:/home/zap/interlude.mp3");
//        playQueueFiles.add("file:/home/zap/meow.mp3");
//        playQueueFiles.add("file:/home/zap/death.mp3");
        setPlayQueue();
    } //end Testing

    public void add(ArrayList<String> newSongs) {
        for(String path : newSongs) {
            final MediaPlayer p = new MediaPlayer(new Media(path.replace(" ", "%20")));
            playQueue.add(p);
        }
        playQueueFiles.addAll(newSongs);
    }

    //TODO: remove main and launch from Server as a thread
    public static void main(String[] args) {
        launch(args);
    }

    public class PlayerControl implements Runnable {
        public void run() {
            Scanner in = new Scanner(System.in);
            try {
                while(true){
                    String input = in.nextLine();
                    System.out.println(input);
                    if(input.equals("t")) {
                        toggle();
                    } else if (input.equals("n")) {
                        next();
                    } else if (input.equals("p")) {
                        prev();
                    } else if (input.equals("s")) {
                        stop();
                    } else if (input.equals("a")) {
                        addSongs();
                    }
                }
            } catch (NoSuchElementException e) {
                in.close();
            }
        }
    }

    public void toggle() {
        try {
            if(!playQueue.isEmpty()) {
                if (playQueue.get(currentPlayer).getStatus() == MediaPlayer.Status.PLAYING) {
                    playQueue.get(currentPlayer).pause();
                } else {
                    playQueue.get(currentPlayer).play();
                }
            }
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }
    public void pause() {
        try {
            if(!playQueue.isEmpty())
                playQueue.get(currentPlayer).pause();
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }
    public void play() {
        try {
            if(!playQueue.isEmpty())
                playQueue.get(currentPlayer).play();
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }
    public void stop() {
        try {
            if(!playQueue.isEmpty())
                playQueue.get(currentPlayer).stop();
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }
    public void next() {
        try {
            if(!playQueue.isEmpty()) {
                playQueue.get(currentPlayer).stop();
                currentPlayer = getNextTrackIndex(currentPlayer);
                if(!((currentPlayer == 0)   //Exclude the corner case where we're at the
                        && !loopingRepeat)) //   end of the play queue and not repeating
                    playQueue.get(currentPlayer).play();
            }
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }
    public int getNextTrackIndex(int current) {
        int nextTrackIndex = 0;
        if(current != playQueue.size()-1)
            nextTrackIndex = current + 1;
        return nextTrackIndex;
    }
    public void prev() {
        try {
            if(!playQueue.isEmpty()) {
                playQueue.get(currentPlayer).stop();
                currentPlayer = getPrevTrackIndex(currentPlayer);
                playQueue.get(currentPlayer).play();
            }
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }
    public int getPrevTrackIndex(int current) {
        int nextTrackIndex = playQueue.size() - 1;
        if(current != 0)
            nextTrackIndex = current - 1;
        return nextTrackIndex;
    }
    public class UDPServer implements Runnable{
        final static int messageLength = 1024;

        public void run() {
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
            private Map<String,Object> _requestContainer = new HashMap<String,Object>();
            //        TestDatabase tdb = new TestDatabase();
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
                                toggle();
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
//                            List<String> result = tdb.findPlayList((String)entry.getValue());
                                //System.out.println("Server: message to be sent "+result.toString());
                                //jsonParser.sendMessage("playlist", "soul");
                                System.out.println("Server sent response");
//                            jsonParser.sendMessageWithArgList(Command.ADDTOPLAYLIST, result);
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
//                                jsonParser.sendMessage(Command.ACK, "unknownCommand");

                        }
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            }
        }

        private int Configure() {
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

}
