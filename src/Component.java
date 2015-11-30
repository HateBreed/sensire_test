import org.json.simple.JSONObject;


public interface Component {
	
	/**
	 * Create JSON representation of the implementing class
	 * @return JSONObject representation of the class
	 */
	public abstract JSONObject getJSON();
	
	/**
	 * Create JSON representation in string format of the implementing class
	 * @return String representation of JSONObject
	 */
	public abstract String getJSONString();
}
