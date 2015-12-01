
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class customer implements Customer {
	private String iName = "";
	private Address iAddress = null;
	private ArrayList<PhoneNumber> iPhoneNumbers = null;
	
	@SuppressWarnings("unused")
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
		if(name != null && name.length() > 0) iName = name;
		
	}

	@Override
	public Address getAddress() {
		return iAddress;
	}

	@Override
	public PhoneNumber getNumber(String type) {
		if(type == null || type.length() == 0) return null;
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

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		
		// Add customer name to JSON
		obj.put("name", getName());
		
		// Add address to JSON by calling Address.getJSON()
		obj.put("address",this.getAddress().getJSON());
		
		// Add phone numbers as array
		JSONArray numbers = new JSONArray();
		obj.put("phoneNumber",numbers);
		
		// Go through phone numbers of customer and add each as separate JSONObject into array
		Iterator<PhoneNumber> iter = this.getNumbers().iterator();
		while(iter.hasNext()) numbers.add(iter.next().getJSON());
		
		if(utils.getInstance().writeJSONString(obj)) return obj;
		return null;
	}

	@Override
	public String getJSONString() {
		JSONObject obj = getJSON();
		if(obj != null) return obj.toJSONString();
		else return "";
	}

}
