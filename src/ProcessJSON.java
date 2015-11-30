import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public interface ProcessJSON {
	/**
	 * Initialize JSON processing "engine"
	 */
	public abstract void Initialize();
	
	/**
	 * Decode JSON from given file and add each Customer into the database
	 * @param JSONPath path to JSON file
	 * @return true when JSON was verified, decoded and added to database
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 * @throws JSONExcepttion When JSON does not contain all required string keys
	 */
	public abstract boolean DecodeJSON(String JSONPath) throws FileNotFoundException, IOException, ParseException, JSONException;
	
	/**
	 * Encode JSON from the database content
	 * @return JSONObject representation of the database
	 * @throws IOException
	 */
	public abstract JSONObject EncodeJSON() throws IOException;
	
	/**
	 * Verify that the JSONObejct contains all required keys
	 * @param obj JSONObject to verify
	 * @return true when object contains all keys
	 */
	public abstract boolean verifyJSON(JSONObject obj);
}
