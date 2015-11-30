import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;


public class phoneNumber implements PhoneNumber {
	
	private String iType = "";
	private String iNumber;
	
	private phoneNumber() {
		iType = "";
		iNumber = "";
	}
	
	public phoneNumber(String type, String number) {
		iType = type;
		iNumber = number;
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
		if(type != null && type.length() > 0) {
			iType = type;
		}
	}

	@Override
	public void setNumber(String number) {
		if(number != null && number.length() > 0) iNumber = number;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", getType());
		obj.put("number",getNumber());
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
