import org.json.simple.JSONObject;


public class address implements Address {

	private String iStreet = "";
	private String iCity = "";
	private String iPostalCode = "";
	
	/**
	 * Private constructor, never used
	 */
	@SuppressWarnings("unused")
	private address() {
		iStreet = "";
		iCity = "";
		iPostalCode = "";
	}
	
	public address(String street, String city, String postalcode) {
		iStreet = street;
		iCity = city;
		iPostalCode = postalcode;
	}
	
	/**
	 * Transform the string format postal code to integer
	 * @param code postal code
	 * @return Integer representation of the postalcode
	 */
	private Integer StringCodeToInt(String code) {
		Integer postalcode = 0;
		if(code == null || code.length() == 0) return null;
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
		if(street != null && street.length() > 0) iStreet = street;
	}

	@Override
	public void setCity(String city) {
		if(city != null && city.length() > 0) iCity = city;
	}

	@Override
	public void setPostalCode(String code) {
		// Allow 5 digit codes only
		if(code != null && code.length() == 5) iPostalCode = code;
	}

	@Override
	public String getPostalCodeString() {
		return iPostalCode;
	}

	@Override
	public int getPostalCodeInt() {
		return StringCodeToInt(iPostalCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		
		// Add street, city and postal code to JSONObject
		obj.put("street", getStreet());
		obj.put("city", getCity());
		obj.put("postalCode", getPostalCodeString());
		
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
