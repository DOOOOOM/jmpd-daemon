package dooooom.jmpd;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.omg.CORBA.UNKNOWN;

import javax.print.attribute.standard.MediaPrintableArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
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
        setPlayQueue();
    }

    public void setPlayQueue() {
        for (int i = 0; i < playQueue.size(); i++) {
            if (i < playQueue.size() - 1) {
                final int t = i + 1;
                playQueue.get(i).setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        currentPlayer = t;
                        playQueue.get(t).play();
                    }
                });
            } else if (loopingRepeat) {
                playQueue.get(playQueue.size() - 1).setOnEndOfMedia(new Runnable() {
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
        playQueueFiles.clear();
        ArrayList<String> pq = new ArrayList<String>();
        pq.addAll(returnPathNames());
        Collections.sort(pq);
        add(pq);
        setPlayQueue();
    }

    public ArrayList<String> returnPathNames()
    /**
     * This method provided with the default music directory for the system,
     * will return an array of Strings, each corresponding to the pathName
     * of the file in question. Directories will not be returned
     * @ Pre: the musicFolderPath is a valid String
     * @ Pre: the musicFolderPath is a valid directory path
     * @ Post: None of the paths returned by the method are folders
     *
     */
    {
        String s = System.getProperty("file.separator");
        File musicFolder = new File("/home/zap/music/Sleigh Bells/Reign of Terror/");

        File[] abstractPaths = musicFolder.listFiles();

        ArrayList<String> arrayToReturn = new ArrayList<String>();

        for(File f :abstractPaths) {
            if(f.isFile())
                arrayToReturn.add("file:" + f.getAbsolutePath());
        }

        ArrayList<String> list = new ArrayList<String>();

        for (String str : arrayToReturn)  // flush null values
        {
            if (str != null && s.length() > 0) {
                if ((str.substring(str.length() - 4, str.length()).equalsIgnoreCase(".mp3")))
                    list.add(str);
            }
        }
        arrayToReturn = list;

        return arrayToReturn;
    } //end Testing

    public void add(ArrayList<String> newSongs) {
        for (String s : newSongs) {
            String path = s.replace(" ", "%20");
            System.out.println(path);
            final MediaPlayer p = new MediaPlayer(new Media(path));
            playQueue.add(p);
        }
        playQueueFiles.addAll(newSongs);
    }

    public void toggle() {
        try {
            if (!playQueue.isEmpty()) {
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
            if (!playQueue.isEmpty())
                playQueue.get(currentPlayer).pause();
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }

    public void play() {
        try {
            if (!playQueue.isEmpty())
                playQueue.get(currentPlayer).play();
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }

    public void stop() {
        try {
            if (!playQueue.isEmpty())
                playQueue.get(currentPlayer).stop();
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }

    public void next() {
        try {
            if (!playQueue.isEmpty()) {
                playQueue.get(currentPlayer).stop();
                currentPlayer = getNextTrackIndex(currentPlayer);
                if (!((currentPlayer == 0)   //Exclude the corner case where we're at the
                        && !loopingRepeat)) //   end of the play queue and not repeating
                    playQueue.get(currentPlayer).play();
            }
        } catch (MediaException e) {
            System.out.println("Invalid media type.");
        }
    }

    public int getNextTrackIndex(int current) {
        int nextTrackIndex = 0;
        if (current != playQueue.size() - 1)
            nextTrackIndex = current + 1;
        return nextTrackIndex;
    }

    public void prev() {
        try {
            if (!playQueue.isEmpty()) {
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
        if (current != 0)
            nextTrackIndex = current - 1;
        return nextTrackIndex;
    }

    public MediaPlayer getCurrent() {
        if(playQueue.isEmpty())
            return null;
        else
            return playQueue.get(currentPlayer);
    }

    public double getTime() {
        if(!playQueue.isEmpty())
            return getCurrent().getCurrentTime().toSeconds();
        else
            return 0.0;
    }

    //TODO: remove main and launch from Server as a thread
    public static void main(String[] args) {
        launch(args);
    }

    public class PlayerControl implements Runnable {
        public void run() {
            Scanner in = new Scanner(System.in);
            try {
                while (true) {
                    String input = in.nextLine();
                    System.out.println(input);
                    if (input.equals("t")) {
                        toggle();
                    } else if (input.equals("n")) {
                        next();
                    } else if (input.equals("p")) {
                        prev();
                    } else if (input.equals("s")) {
                        stop();
                    } else if (input.equals("a")) {
                        addSongs();
                    } else if (input.equals("c")) {
                        System.out.println(getTime());
                    }
                }
            } catch (NoSuchElementException e) {
                in.close();
            }
        }
    }

}
