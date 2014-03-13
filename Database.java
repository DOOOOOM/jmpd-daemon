/**
*  Database.java
*  The Database Class would handle the conversion of 
*  the Database.json file content into class objects
*/

public class Database extends doooom.jmpd.parser{
    /*
	* Main class constructor
	* pre-condition: Database.json file must exist
	* post-condition: Database.json path is instantiated
	* Exception: Incorrect file path
	*/
    public Database(String DatabasedotJsonFilePath);
	
	/*
	* Creates Songs Database Object
	*/
	private class DatabaseSong ();
	
	/*
	* Creates Artist Database Object
	*/
	private class DatabaseArtist ();
	
	/*
	* Creates Album Database Object
	*/
	private class DatabaseAlbum ();
	
}