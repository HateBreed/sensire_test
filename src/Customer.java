import java.util.ArrayList;

import org.json.simple.JSONObject;


public interface Customer {
	public abstract String getName();
	public abstract void setName(String name);
	public abstract Address getAddress();
	public abstract void setAddress(Address address);
	public abstract ArrayList<PhoneNumber> getNumbers();
	public abstract PhoneNumber getNumber(String type);
	public abstract void addNumber(PhoneNumber number);
	public abstract JSONObject getJSON();
	public abstract String getJSONString();
}
