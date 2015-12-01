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
		if(postalcode != null && postalcode.length() == 5 && utils.getInstance().isNumeric(postalcode)) iPostalCode = postalcode;
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
		if(code != null && code.length() == 5 && utils.getInstance().isNumeric(code)) iPostalCode = code;
	}

	@Override
	public String getPostalCodeString() {
		return iPostalCode;
	}

	@Override
	public int getPostalCodeInt() {
		// Postal code never exceeds the limit of Integer so it can be transformed to int
		return utils.getInstance().StringToInt(iPostalCode).intValue();
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
