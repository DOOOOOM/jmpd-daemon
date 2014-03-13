/**
*  Database.java
*  The Database Class would handle the conversion of 
*  the Database.json file content into class objects
*/

public interface Database {
    /*
	* Main class constructor
	* pre-condition: Database.json file must exist
	* post-condition: Database.json path is instantiated
	* Exception: Incorrect file path
	*/
    public Database(String DatabasedotJsonFilePath);
	
	/*
	* Creates Songs Database Object
	* pre-condition: Database.json file exist
	* post-condition: SongDatabase instance
	*/
	private class DatabaseSong ();
	
	/*
	* Creates Artist Database Object
	* pre-condition: Database.json file exist
	* post-condition: ArtistDatabase instance
	*/
	private class DatabaseArtist ();
	
	/*
	* Creates Album Database Object
	* pre-condition: Database.json file exist
	* post-condition: AlbumDatabase instance
	*/
	private class DatabaseAlbum ();
}