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
	
	public static TrackList randomTracks(int n, int artistlen) {
		TrackList tracks = new TrackList();

		for(int i = 0; i < n; i++) {
			Track track = new Track();
			
			track.put("id", Integer.toString(i));
			track.put("artist", getRandomString(artistlen));
			track.put("album", getRandomString(artistlen+1));
			track.put("title", getRandomString(artistlen+4));
			
			tracks.add(track);
		}
		
		return tracks;
	}
}
