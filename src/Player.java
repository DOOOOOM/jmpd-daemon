package dooooom.jmpd.daemon.player;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Player extends Application {
    private static ArrayList<String> playQueueFiles = new ArrayList<String>();
    private static ArrayList<MediaPlayer> playQueue = new ArrayList<MediaPlayer>();
    private static boolean loopingRepeat = true;

    @Override
    public void start(Stage arg0) {
        addSongs();
        setPlayQueue();
        playQueue.get(0).play();
    }
    public void setPlayQueue() {
        for(String path : playQueueFiles) {
            try {
                final MediaPlayer p = new MediaPlayer(new Media(path));
                playQueue.add(p);
            } catch (MediaException e) {

            }
        }
        for(int i = 0; i < playQueue.size(); i++) {
            if(i < playQueue.size()-1) {
                final int t = i + 1;
                playQueue.get(i).setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        playQueue.get(t).play();
                    }
                });
            } else if(loopingRepeat) {
                playQueue.get(i).setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        playQueue.get(0).play();
                    }
                });
            }
        }
    }
    public void addSongs() {
        playQueueFiles.add("file:///home/zap/alarm03.mp3");
        playQueueFiles.add("file:///home/zap/alarm01.mp3");
        playQueueFiles.add("file:///home/zap/alarm02.mp3");
//        playQueueFiles.add("file:/home/zap/alarm03.mp3");
//        playQueueFiles.add("file:/home/zap/interlude.mp3");
//        playQueueFiles.add("file:/home/zap/death.mp3");
//        playQueueFiles.add("file:/home/zap/meow.mp3");
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

//        public void toggle() {
//            try {
//                if(mp.getStatus() == MediaPlayer.Status.PLAYING) {
//                    pause();
//                } else {
//                    play();
//                }
//            } catch (MediaException e) {
//                System.out.println("Invalid media type.");
//            }
//        }
//        public void pause() {
//            try {
//                mp.pause();
//            } catch (MediaException e) {
//                System.out.println("Invalid media type.");
//            }
//        }
//        public  void play() {
//            try {
//                mp.play();
//            } catch (MediaException e) {
//                System.out.println("Invalid media type.");
//            }
//        }
}
