package dooooom.jmpd;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Application {
    private ArrayList<String> playQueueFiles = new ArrayList<String>();
    private ArrayList<MediaPlayer> playQueue = new ArrayList<MediaPlayer>();
    private boolean loopingRepeat = true;
    private int currentPlayer;

    @Override
    public void start(Stage arg0) {
        PlayerControl control = new PlayerControl();
        Thread controllerThread = new Thread(control);
        controllerThread.start();
        addSongs();
        setPlayQueue();
        playQueue.get(0).play();
    }
    public void setPlayQueue() {
        for(String path : playQueueFiles) {
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

    public void addSongs() {
//        playQueueFiles.add("file:/home/zap/alarm03.mp3");
//        playQueueFiles.add("file:/home/zap/alarm01.mp3");
//        playQueueFiles.add("file:/home/zap/alarm02.mp3");
//        playQueueFiles.add("file:/home/zap/alarm03.mp3");
        playQueueFiles.add("file:/home/zap/interlude.mp3");
        playQueueFiles.add("file:/home/zap/meow.mp3");
        playQueueFiles.add("file:/home/zap/death.mp3");
    }
    public void add(ArrayList<String> newSongs) {
        playQueueFiles.addAll(newSongs);
        for(String path : newSongs) {
            final MediaPlayer p = new MediaPlayer(new Media(path));
            playQueue.add(p);
        }
    }

    //TODO: remove main and launch from Server as a thread
    public static void main(String[] args) {
        launch(args);
    }

//        private void getMedia() {
//            System.out.println(path);
//        }
    public class PlayerControl implements Runnable {
        public void run() {
            Scanner in = new Scanner(System.in);
            while(true){
                String input = in.nextLine();
                System.out.println(input);
                if(input.equals("t")) {
                    toggle();
                }
            }
        }
    }

        public void toggle() {
            try {
                if(playQueue.get(currentPlayer).getStatus() == MediaPlayer.Status.PLAYING) {
                    playQueue.get(currentPlayer).pause();
                } else {
                    playQueue.get(currentPlayer).play();
                }
            } catch (MediaException e) {
                System.out.println("Invalid media type.");
            }
        }
        public void pause() {
            try {
                playQueue.get(currentPlayer).pause();
            } catch (MediaException e) {
                System.out.println("Invalid media type.");
            }
        }
        public  void play() {
            try {
                playQueue.get(currentPlayer).play();
            } catch (MediaException e) {
                System.out.println("Invalid media type.");
            }
        }
}
