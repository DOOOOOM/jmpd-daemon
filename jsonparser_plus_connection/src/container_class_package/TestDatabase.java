package container_class_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDatabase {
	Map <String,Object> plylContainer = new HashMap<String,Object>();
	String [] playListNames = {"workout","gospel","soul"};
	String [] tracks = {"By the sea","The Blues","Gone for good","I hate typing","This is a test db","DOOOOOM","orthogonality"};
	
	public Map <String,Object> getPlayList(){
		for(String plyNm : playListNames){
			List <String> trk = new ArrayList<String>();
			for(String tr : tracks){
				trk.add(tr);							
			}
			plylContainer.put(plyNm, trk);			
		}
		return plylContainer;
	}
	
	public List<String> findPlayList(String plyList){
		List <String> result = new ArrayList<String>();
		Map <String,Object> playlist = getPlayList();
		result = (List<String>) playlist.get(plyList);
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
