import org.json.simple.JSONObject;


public class AddressImpl implements Address {

	private String iStreet = "";
	private String iCity = "";
	private String iPostalCode = "";
	
	/**
	 * Private constructor, never used
	 */
	@SuppressWarnings("unused")
	private AddressImpl() {
		iStreet = "";
		iCity = "";
		iPostalCode = "";
	}
	
	public AddressImpl(String street, String city, String postalcode) {
		iStreet = street;
		iCity = city;
		if(postalcode != null && postalcode.length() == 5 && UtilsImpl.getInstance().isNumeric(postalcode)) iPostalCode = postalcode;
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
		if(code != null && code.length() == 5 && UtilsImpl.getInstance().isNumeric(code)) iPostalCode = code;
	}

	@Override
	public String getPostalCodeString() {
		return iPostalCode;
	}

	@Override
	public int getPostalCodeInt() {
		// Postal code never exceeds the limit of Integer so it can be transformed to int
		return UtilsImpl.getInstance().StringToInt(iPostalCode).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSON() {
		StringStorage st = StringStorageImpl.getInstance();
		JSONObject obj = new JSONObject();
		
		// Add street, city and postal code to JSONObject
		obj.put(st.getAddressStrings()[st.getStreetPosition()], getStreet());
		obj.put(st.getAddressStrings()[st.getCityPosition()], getCity());
		obj.put(st.getAddressStrings()[st.getPostalCodePosition()], getPostalCodeString());
		
		if(UtilsImpl.getInstance().writeJSONString(obj)) return obj;
		return null;
	}

	@Override
	public String getJSONString() {
		JSONObject obj = getJSON();
		if(obj != null) return obj.toJSONString();
		else return "";
	}

}
