import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URLConnection;

public class main {

	private static String[] mainItems = {"name", "address", "phoneNumber"};
	private static String[] addressItems = { "street", "city", "postalCode"};
	private static String[] numberItems = {"type", "number"};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Customer> iCustomers = new ArrayList<Customer>();
		
		JSONParser parser = new JSONParser();
		
		try {
			FileReader reader = new FileReader("data/customers.json");
			JSONObject jsonobj = (JSONObject)parser.parse(reader);
			System.out.println(jsonobj.toString());
			
			if(jsonobj.containsKey("customers")) {
				JSONArray jsonarray = (JSONArray)jsonobj.get("customers");
				
				Iterator<JSONObject> iter = jsonarray.iterator();
				
				while(iter.hasNext()) {
					JSONObject entry = iter.next();
					if(verifyJSON(entry)) {
						
						Customer cust = new customer(entry.get("name").toString());
						
						JSONObject address = (JSONObject)entry.get("address");
						cust.setAddress(new address(address.get("street").toString(), 
								address.get("city").toString(),
								address.get("postalCode").toString()));
						
						JSONArray numbers = (JSONArray)entry.get("phoneNumber");
						Iterator<JSONObject> numbiter = numbers.iterator();
						while(numbiter.hasNext()) {
							JSONObject numbobj = numbiter.next();
							cust.addNumber(new phoneNumber(numbobj.get("type").toString(),
									numbobj.get("number").toString()));
						}
						iCustomers.add(cust);
					}
					else System.out.println("Invalid JSON");
				}
			}			
		} catch (FileNotFoundException fe) {
			System.err.println("File not found" + fe.getLocalizedMessage());
			
		} catch (IOException ie) {
			System.err.println("IOException" + ie.getLocalizedMessage());
			
		} catch (ParseException pe) {
			System.err.println("ParseException" + pe.getLocalizedMessage());
			
		}
		
		sort(iCustomers);
		
//		Iterator<Customer> iter = iCustomers.iterator();
//		while(iter.hasNext()) {
//			Customer c = iter.next();
//			System.out.println("Name:" + c.getName());
//			System.out.println("Address: " + c.getAddress().getStreet() + "," 
//					+ c.getAddress().getCity() + "," 
//					+ c.getAddress().getPostalCodeString());
//			Iterator<PhoneNumber> numbers = c.getNumbers().iterator();
//			while(numbers.hasNext()) {
//				PhoneNumber n = numbers.next();
//				System.out.println("Number: " + n.getType() + " = " + n.getNumber());
//			}
//		}
		
		JSONObject json = new JSONObject();
		JSONArray items = new JSONArray();
		StringWriter out = new StringWriter();
		
		json.put("customers", items);
		
		Iterator<Customer> iter = iCustomers.iterator();
		while(iter.hasNext()) {
			items.add(iter.next().getJSON());
		}
		
		try { 
			json.writeJSONString(out);
		} catch (IOException ie) {
			
		}
		URL url = null;
		HttpURLConnection httpc = null;
		try {
			url = new URL("http://dev.sensire.fi:40019/TestTask/Jussi/Laakkonen");
			String encodedJSON = URLEncoder.encode(json.toString(),"UTF-8");
			httpc = (HttpURLConnection)url.openConnection();
			httpc.setRequestMethod("POST");
			httpc.setRequestProperty("content-type", "application/json");
			httpc.setRequestProperty("Content-Length", "" + Integer.toString(encodedJSON.getBytes().length));
			httpc.setUseCaches(false);
			httpc.setDoInput(true);
			httpc.setDoOutput(true);
			
			DataOutputStream output = new DataOutputStream(httpc.getOutputStream());
			output.writeBytes(encodedJSON);
			output.flush();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpc.getInputStream()));
			String line = "";
			StringBuffer response = new StringBuffer();
			while((line = reader.readLine()) != null) {
				response.append(line);
			}
			output.close();
			reader.close();
			if(response.length() == 0) {
				System.out.println("HTTP " + httpc.getResponseCode() + " " + httpc.getResponseMessage());
			}
			else {
				System.out.println("Server responded with a message: " + response);
			}
			
		} catch (IOException ie) {
			System.err.println("IoException:" + ie.getLocalizedMessage());
		} finally {
			if(httpc != null) httpc.disconnect();
		}
		
		
		System.out.println(json.toJSONString());
	}
	
	public static void sort(ArrayList<Customer> list) {
		Collections.sort(list, new Comparator<Customer>() {
			@Override
			public int compare(Customer i1, Customer i2) {
				return i1.getName().compareTo(i2.getName());
			}
		});
	}
	
	public static boolean verifyJSON(JSONObject obj) {
		
		for(int pos = 0 ; pos < mainItems.length; pos++) {
			
			if(obj.containsKey(mainItems[pos])) {
				
				switch(mainItems[pos]) {
				case "address":
					if(!jsonVerifyObject(obj, mainItems[pos], addressItems)) return false;
					break;
				case "phoneNumber":
					if(!jsonVerifyObject(obj, mainItems[pos], numberItems)) return false;
					break;
				}
			}
		}
		return true;
	}
	
	private static boolean jsonVerifyObject(JSONObject obj, String objname, String[] list) {
		if(obj.get(objname).getClass().getSimpleName().toString().compareTo(JSONObject.class.getSimpleName().toString())==0) {
			if(!jsonContainsKeys((JSONObject)obj.get(objname), list)) return false;
		}
		else if(obj.get(objname).getClass().getSimpleName().toString().compareTo(JSONArray.class.getSimpleName().toString())==0) {
		JSONArray number = (JSONArray)obj.get(objname);
			Iterator<JSONObject> phoneIter = number.iterator();
			while(phoneIter.hasNext()) {
				if(!jsonContainsKeys((JSONObject)phoneIter.next(), list)) return false;
			}
		}
		return true;
	}
	
	private static boolean jsonContainsKeys(JSONObject obj, String[] list) {
		for(int i = 0; i < list.length; i++) {
			if(!obj.containsKey(list[i])) return false;
		}
		return true;
	}
}
