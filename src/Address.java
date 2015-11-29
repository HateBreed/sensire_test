import org.json.simple.JSONObject;


public interface Address {
	public abstract String getStreet();
	public abstract String getCity();
	public abstract String getPostalCodeString();
	public abstract int getPostalCodeInt();
	public abstract void setStreet(String street);
	public abstract void setCity(String city);
	public abstract void setPostalCode(String code);
	public abstract JSONObject getJSON();
	public abstract String getJSONString();
}
