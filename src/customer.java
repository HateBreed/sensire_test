import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class customer implements Customer {
	private String iName = "";
	private Address iAddress = null;
	private ArrayList<PhoneNumber> iPhoneNumbers = null;
	
	private customer() {
		iName = "";
		iPhoneNumbers = new ArrayList<PhoneNumber>();
	}
	
	public customer(String name) {
		iName = name;
		iPhoneNumbers = new ArrayList<PhoneNumber>();
	}

	@Override
	public String getName() {
		return iName;
	}

	@Override
	public void setName(String name) {
		if(name.length() > 0) iName = name;
		
	}

	@Override
	public Address getAddress() {
		return iAddress;
	}

	@Override
	public PhoneNumber getNumber(String type) {
		Iterator<PhoneNumber> i = iPhoneNumbers.iterator();
		while(i.hasNext()) {
			PhoneNumber number = i.next();
			if(number.getType().equals(type)) return number;
		}
		return null;
	}

	@Override
	public ArrayList<PhoneNumber> getNumbers() {
		return iPhoneNumbers;
	}

	@Override
	public void setAddress(Address address) {
		if(address != null) iAddress = address;
	}

	@Override
	public void addNumber(PhoneNumber number) {
		if(number != null) {
			// Should not ever happen
			if(iPhoneNumbers == null) iPhoneNumbers = new ArrayList<PhoneNumber>();
			Iterator<PhoneNumber> i = iPhoneNumbers.iterator();
			while(i.hasNext()) {
				if(number.getType().equals(i.next().getType())) return;
			}
			iPhoneNumbers.add(number);
		}
	}

	@Override
	public JSONObject getJSON() {
		StringWriter out = new StringWriter();
		JSONObject obj = new JSONObject();
		
		obj.put("name", getName());
		obj.put("address",this.getAddress().getJSON());
		
		JSONArray numbers = new JSONArray();
		
		Iterator<PhoneNumber> iter = this.getNumbers().iterator();
		while(iter.hasNext()) numbers.add(iter.next().getJSON());
		
		obj.put("phoneNumber",numbers);
		
		try { 
			obj.writeJSONString(out);
		} catch (IOException ie) {
			return null;
		}
		return obj;
	}

	@Override
	public String getJSONString() {
		JSONObject obj = getJSON();
		if(obj != null) return obj.toJSONString();
		else return "";
	}

}
