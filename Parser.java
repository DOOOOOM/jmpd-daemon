/**
* Parser.java
* The Parser.java would handle the parsing of the
* Database.json
*/

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/*
* pre-condition: Database.json file must exist
* post-condition: Database.json path is instantiated
* Exception: IOError
*/
class public Parser{
    /*
	* Main Constructor function handles the opening
	* and instantiation of the json object
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
}