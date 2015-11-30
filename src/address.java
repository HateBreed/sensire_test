import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;


public class address implements Address {

	private String iStreet = "";
	private String iCity = "";
	private Integer iPostalCode = 0;
	
	/**
	 * Private constructor, never used
	 */
	private address() {
		iStreet = "";
		iCity = "";
		iPostalCode = 0;
	}
	
	public address(String street, String city, String postalcode) {
		iStreet = street;
		iCity = city;
		iPostalCode = StringCodeToInt(postalcode);
	}
	
	/**
	 * Transform the string format postal code to integer
	 * @param code postal code
	 * @return Integer representation of the postalcode
	 */
	private Integer StringCodeToInt(String code) {
		Integer postalcode = 0;
		try {
			postalcode = Integer.parseInt(code);
		} catch (NumberFormatException e) {
			postalcode = -1;
		}
		return postalcode;
	}
	
	
	@Override
	public String getStreet() {
		return iStreet;
	}

	@Override
	public String getCity() {
		return iCity;
	}


	@Override
	public void setStreet(String street) {
		if(street.length() > 0) iStreet = street;
	}

	@Override
	public void setCity(String city) {
		if(city.length() > 0) iCity = city;
	}

	@Override
	public void setPostalCode(String code) {
		if(code.length() > 0) iPostalCode = StringCodeToInt(code);
	}

	@Override
	public String getPostalCodeString() {
		return iPostalCode.toString();
	}

	@Override
	public int getPostalCodeInt() {
		return iPostalCode;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("street", getStreet());
		obj.put("city", getCity());
		obj.put("postalCode", getPostalCodeString());
		StringWriter out = new StringWriter();
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
