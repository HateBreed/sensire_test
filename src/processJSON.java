import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class processJSON implements ProcessJSON {
	
	private static String mainIdentifier = "customers";
	private static String[] mainItems = {"name", "address", "phoneNumber"};
	private static int NAME_POS = 0;
	private static int ADDRESS_POS = 1;
	private static int PHONE_POS = 2;
	
	private static String[] addressItems = { "street", "city", "postalCode"};
	private static int STREET_POS = 0;
	private static int CITY_POS = 1;
	private static int CODE_POS = 2;
	
	private static String[] numberItems = {"type", "number"};
	private static int TYPE_POS = 0;
	private static int NUMBER_POS = 1;
	
	private JSONParser parser = null;
	private StringWriter out = null;
	
	
	@Override
	public void Initialize() {
		parser = new JSONParser();
		out = new StringWriter();
		
	}
	@Override
	public boolean DecodeJSON(String JSONPath) throws FileNotFoundException, 
		IOException, ParseException, JSONException {
		
		// Read and parse JSON
		FileReader reader = new FileReader(JSONPath);
		JSONObject jsonobj = (JSONObject)parser.parse(reader);
		
		// First check that it has the correct identifier
		if(jsonobj.containsKey(mainIdentifier)) {
			
			JSONArray jsonarray = (JSONArray)jsonobj.get(mainIdentifier);
			
			Iterator<JSONObject> iter = jsonarray.iterator();
			
			while(iter.hasNext()) {
				JSONObject entry = iter.next();
				if(verifyJSON(entry)) {
					
					Customer cust = new customer(entry.get(mainItems[NAME_POS]).toString());
					
					JSONObject address = (JSONObject)entry.get(mainItems[ADDRESS_POS]);
					cust.setAddress(new address(address.get(addressItems[STREET_POS]).toString(), 
							address.get(addressItems[CITY_POS]).toString(),
							address.get(addressItems[CODE_POS]).toString()));
					
					JSONArray numbers = (JSONArray)entry.get(mainItems[PHONE_POS]);
					Iterator<JSONObject> numbiter = numbers.iterator();
					while(numbiter.hasNext()) {
						JSONObject numbobj = numbiter.next();
						cust.addNumber(new phoneNumber(numbobj.get(numberItems[TYPE_POS]).toString(),
								numbobj.get(numberItems[NUMBER_POS]).toString()));
					}
					if(!db.getInstance().add(cust)) return false;
				}
				else throw new JSONException("JSON in file \"" + JSONPath + "\" is invalid");
			}
		}
		else throw new JSONException("JSON in file \"" + JSONPath + "\" is invalid");
		return true;
		
	}
	
	@Override
	public JSONObject EncodeJSON() throws IOException {
		
		JSONObject json = new JSONObject();
		JSONArray items = new JSONArray();
		
		// Put the "customer" string into the beginning of new JSONObject
		json.put(mainIdentifier, items);
		
		// Go through the customer list and add each individually
		Iterator<Customer> iter = db.getInstance().getDB().iterator();
		while(iter.hasNext()) {
			// Each Customer, Address and PhoneNumber creates JSONObjects from themselves
			items.add(iter.next().getJSON());
		}
		
		try { 
			json.writeJSONString(out);
		} catch (IOException ie) {
			throw ie;
		}
		return json;
	}
	
	@Override
	public boolean verifyJSON(JSONObject obj) {

		for(int pos = 0 ; pos < mainItems.length; pos++) {
		
			if(obj.containsKey(mainItems[pos])) {
				
				// Address items
				if(pos == ADDRESS_POS) {
					if(!jsonVerifyObject(obj, mainItems[pos], addressItems)) return false;
				}
				
				// Phone number items
				if(pos == PHONE_POS) {
					if(!jsonVerifyObject(obj, mainItems[pos], numberItems)) return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Verify a single JSONObject content
	 * @param obj main JSONOBject to be verified
	 * @param objname The name of the inner JSONObject/JSONArray to be retrieved
	 * @param list The strings the inner object should contain
	 * @return true when all strings are found within object or array
	 */
	private boolean jsonVerifyObject(JSONObject obj, String objname, String[] list) {
		
		// If the obj contains a plain JSONObject
		if(obj.get(objname).getClass().getSimpleName().toString().compareTo(JSONObject.class.getSimpleName().toString())==0) {
			// Check that it has the keys given in the list
			if(!jsonContainsKeys((JSONObject)obj.get(objname), list)) return false;
		}
		// If the obj contains a JSONArray
		else if(obj.get(objname).getClass().getSimpleName().toString().compareTo(JSONArray.class.getSimpleName().toString())==0) {
		JSONArray number = (JSONArray)obj.get(objname);
		
			// Iterate through array and check each JSONObject for the keys given in list
			Iterator<JSONObject> phoneIter = number.iterator();
			while(phoneIter.hasNext()) {
				if(!jsonContainsKeys((JSONObject)phoneIter.next(), list)) return false;
			}
		}
		return true;
	}
	
	/**
	 * Check that this particular JSONObject contains all keys in list
	 * @param obj to be verified
	 * @param list items to be searched 
	 * @return true when all were found
	 */
	private boolean jsonContainsKeys(JSONObject obj, String[] list) {
		// Go through the items in JSONObject fo check whether it has all keys given in list
		for(int i = 0; i < list.length; i++) {
			if(!obj.containsKey(list[i])) return false;
		}
		return true;
	}

}