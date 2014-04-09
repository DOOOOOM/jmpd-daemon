package dooooom.jmpd.data.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import dooooom.jmpd.data.Track;
import dooooom.jmpd.data.TrackList;

public class TrackListGenerator {
	private static String getRandomString(int length) {
		Random r = new Random();
		
		String s = "";
		
		for(int i = 0; i < length; i++) {
			int n = r.nextInt(26) + 97;
			s = s.concat(Character.toString((char) n));
		}
		
		return s;
	}
	
	public static TrackList randomTracks(int nArtists) {
		TrackList tracks = new TrackList();
		
		final int albumsPerArtist = 3;
		final int tracksPerAlbum = 8;

		for(int i = 0; i < nArtists; i++) {
			String artist = getRandomString(4);
			
			for (int j = 0; j < albumsPerArtist; j++) {
				String album = getRandomString(6);
				
				for (int k = 0; k < tracksPerAlbum; k++) {
					Track track = new Track();
					track.put("id", Integer.toString(i * albumsPerArtist * tracksPerAlbum + j * tracksPerAlbum + k));
					track.put("artist", artist);
					track.put("album", album);
					track.put("title", getRandomString(10));
					tracks.add(track);
				}
			}
		}
		
		return tracks;
	}
}
