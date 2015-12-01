import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	//private StringWriter out = null;
	
	
	@Override
	public void Initialize() {
		parser = new JSONParser();
		//out = new StringWriter();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean DecodeJSON(String JSONPath) throws FileNotFoundException, 
		IOException, ParseException, JSONException {
		
		// Read and parse JSON
		FileReader reader = new FileReader(JSONPath);
		JSONObject jsonobj = (JSONObject)parser.parse(reader);
		StringStorage st = stringStorage.getInstance();

		// First check that it has the correct identifier and contains a JSONArray
		if(jsonobj.containsKey(st.getIdentifierString()) && 
				jsonobj.get(st.getIdentifierString()).getClass().getSimpleName().toString().compareTo(
						JSONArray.class.getSimpleName().toString())==0) {
			
			JSONArray jsonarray = (JSONArray)jsonobj.get(st.getIdentifierString());
			
			// Iterate through array
			Iterator<JSONObject> iter = jsonarray.iterator();
			
			while(iter.hasNext()) {
				JSONObject entry = iter.next();
				
				// Verify that the main JSONObject contains at least one of each required field
				if(verifyJSON(entry)) {
					
					// Get customer name
					Customer cust = new customer(entry.get(st.getMainStrings()[st.getNamePosition()]).toString());
					
					// Get address and add it to customer object
					JSONObject address = (JSONObject)entry.get(st.getMainStrings()[st.getAddressPosition()]);
					cust.setAddress(new address(address.get(st.getAddressStrings()[st.getStreetPosition()]).toString(), 
							address.get(st.getAddressStrings()[st.getCityPosition()]).toString(),
							address.get(st.getAddressStrings()[st.getPostalCodePosition()]).toString()));
					
					// Get phone numbers as JSONArray and iterate through it
					JSONArray numbers = (JSONArray)entry.get(st.getMainStrings()[st.getPhoneNumberPosition()]);
					Iterator<JSONObject> numbiter = numbers.iterator();
					while(numbiter.hasNext()) {
						// Get number and add it to customer object
						JSONObject numbobj = numbiter.next();
						cust.addNumber(new phoneNumber(numbobj.get(st.getPhoneNumberStrings()[st.getTypePosition()]).toString(),
								numbobj.get(st.getPhoneNumberStrings()[st.getNumberPosition()]).toString()));
					}
					// If customer cannot added, return
					if(!db.getInstance().add(cust)) return false;
				}
				// Invalid JSON
				else throw new JSONException("JSON in file \"" + JSONPath + "\" is invalid");
			}
		}
		else throw new JSONException("JSON in file \"" + JSONPath + "\" is invalid, does not contain " + mainIdentifier + 
				"or does not contain " + JSONArray.class.getSimpleName().toString());
		return true;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject EncodeJSON() throws IOException {
		
		JSONObject json = new JSONObject();
		JSONArray items = new JSONArray();
		
		// Put the "customers" string into the beginning of new JSONObject
		json.put(stringStorage.getInstance().getIdentifierString(), items);
		
		// Go through the customer list and add each individually
		Iterator<Customer> iter = db.getInstance().getDB().iterator();
		while(iter.hasNext()) {
			// Each Customer, Address and PhoneNumber creates JSONObjects from themselves
			items.add(iter.next().getJSON());
		}
		
		if(utils.getInstance().writeJSONString(json)) return json;
		else return null;
	}
	
	@Override
	public boolean verifyJSON(JSONObject obj) {

		StringStorage st = stringStorage.getInstance();
		for(int pos = 0 ; pos < st.getMainStrings().length; pos++) {
		
			if(obj.containsKey(st.getMainStrings()[pos])) {
				
				// Address items
				if(pos == st.getAddressPosition()) {
					if(!jsonVerifyObject(obj, st.getMainStrings()[pos], st.getAddressStrings())) return false;
				}
				
				// Phone number items
				if(pos == st.getPhoneNumberPosition()) {
					if(!jsonVerifyObject(obj, st.getMainStrings()[pos], st.getPhoneNumberStrings())) return false;
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
	@SuppressWarnings("unchecked")
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
