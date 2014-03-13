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
	* pre-condition: well formated json database file
	* post-condition: string name of key returned
	*/
	public String getkey();
	
	/*
	* Retrieves json obj value
	* pre-condition: well formated json database file
	* post-condition: string name of value returned
	*/
	public String getValue(String key);
	
	/*
	* Read Input file
	* pre-condition: Database.json file must exist
	* post-condition: parsed json file
	*/
	public Void readFile();
	
	/*
	* Insert data into database.json
	* pre-condition: Database.json file must exist
	* post-condition: json file is updated
	*/
	public Void insertEntry(JSONObject entry);
	
	/*
	* Verify json format is valid before adding to 
	* main database.json
	* pre-condition: Database.json file must exist
	* post-condition: parameter is verify to be in json format.
	*/
	Boolean isJson(String entry);
}