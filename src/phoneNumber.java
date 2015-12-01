import org.json.simple.JSONObject;


public class phoneNumber implements PhoneNumber {
	
	private String iType = "";
	private String iNumber = "";
	
	@SuppressWarnings("unused")
	private phoneNumber() {
		iType = "";
		iNumber = "";
	}
	
	public phoneNumber(String type, String number) {
		iType = type;
		if(number != null && number.length() > 0 && utils.getInstance().isNumeric(number)) iNumber = number;
	}

	@Override
	public String getType() {
		return iType;
	}

	@Override
	public String getNumber() {
		return iNumber;
	}

	@Override
	public void setType(String type) {
		if(type != null && type.length() > 0) iType = type;
	}

	@Override
	public void setNumber(String number) {
		if(number != null && number.length() > 0 && utils.getInstance().isNumeric(number)) iNumber = number;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSON() {
		StringStorage st = stringStorage.getInstance();
		JSONObject obj = new JSONObject();
		
		// Add type and number to JSONObject
		obj.put(st.getPhoneNumberStrings()[st.getTypePosition()], getType());
		obj.put(st.getPhoneNumberStrings()[st.getNumberPosition()],getNumber());
		
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
