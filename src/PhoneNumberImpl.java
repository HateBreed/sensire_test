import org.json.simple.JSONObject;


public class PhoneNumberImpl implements PhoneNumber {
	
	private String iType = "";
	private String iNumber = "";
	
	@SuppressWarnings("unused")
	private PhoneNumberImpl() {
		iType = "";
		iNumber = "";
	}
	
	public PhoneNumberImpl(String type, String number) {
		iType = type;
		if(number != null && number.length() > 0 && UtilsImpl.getInstance().isNumeric(number)) iNumber = number;
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
		if(number != null && number.length() > 0 && UtilsImpl.getInstance().isNumeric(number)) iNumber = number;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getJSON() {
		StringStorage st = StringStorageImpl.getInstance();
		JSONObject obj = new JSONObject();
		
		// Add type and number to JSONObject
		obj.put(st.getPhoneNumberStrings()[st.getTypePosition()], getType());
		obj.put(st.getPhoneNumberStrings()[st.getNumberPosition()],getNumber());
		
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
