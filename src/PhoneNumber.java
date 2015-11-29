import org.json.simple.JSONObject;


public interface PhoneNumber {
	public abstract String getType();
	public abstract String getNumber();
	public abstract void setType(String type);
	public abstract void setNumber(String number);
	public abstract JSONObject getJSON();
	public abstract String getJSONString();
}
