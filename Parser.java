/**
* Parser.java
* The Parser.java would handle the parsing of the
* Database.json
*/

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class public Parser{
    /*
	* Main Constructor function handles the opening
	* and instantiation of the json object
	* pre-condition: Database.json file must exist
    * post-condition: Database.json path is instantiated
    * Exception: IOError
	*/
	public Parser(String DatabasedotJsonFilePath);
	
	/*
	* Retrieves json obj key
	*/
	public String getkey();
	
	/*
	* Retrieves json obj value
	*/
	public String getValue(String key);
	
	/*
	* Read Input file
	*/
	public Void readFile();
	
	/*
	* Insert data into database.json
	*/
	public Void insertEntry(JSONObject entry);
	
	/*
	* Verify json format is valid before adding to 
	* main database.json
	*/
	Boolean isJson(String entry);
}