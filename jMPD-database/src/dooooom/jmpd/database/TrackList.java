package dooooom.jmpd.database;

import java.util.ArrayList;
import java.util.HashMap;

public class TrackList extends ArrayList<Track> {
	
	
	public TrackList search(String key, String value) {
		TrackList result = new TrackList();
		
		for(Track tr : this) {
			if(tr.get(key).equals(value))
				result.add(tr);
		}
		
		return result;
	}
}
