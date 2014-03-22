package dooooom.jmpd.daemon.player;

import java.util.List;

/**
 * Created by zap on 3/22/14.
 */
public class Database {
    private List<Artist> artists;

    private class Artist {
        private String name;
        private List<Album> albums;

        public void setName(String artistName) {
            name = artistName;
        }
        public void addAlbums(List<Album> multipleAlbums) {
            albums.addAll(multipleAlbums);
        }
        public void addAlbum(Album a) {
            albums.add(a);
        }
    }
    private class Album {
        private String name;
        private List<Song> songs;

        public void setName(String albumName) {
            name = albumName;
        }
        public void addSongs(List<Song> multipleSongs) {
            songs.addAll(multipleSongs);
        }
        public void addSong(Song s) {
            songs.add(s);
        }
    }
    private class Song {
        private int id;
        private String filepath;
        private String title;
        private String artistName;
        private String albumName;
        private int year;
        private int trackNumber;
        private int tracksTotal;
        private int songLength;

        public void setId(int uniqueIdentifier) {
            id = uniqueIdentifier;
        }
        public void setFilepath(String path) {
            filepath = path;
        }
    }
}
