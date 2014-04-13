package dooooom.jmpd.data;

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
	
	public boolean trackIDExists(Track t) {
		if(this.search("id", t.get("id")).size() > 0)
			return true;
		else
			return false;
	}
}
