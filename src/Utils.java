import java.io.StringWriter;
import java.math.BigInteger;

import org.json.simple.JSONObject;


public interface Utils {
	
	/**
	 * Ẅrite string representation of given JSONObject to it 
	 * @param obj JSONObject to use
	 * @return true when written
	 */
	public abstract boolean writeJSONString(JSONObject obj);
	
	/**
	 * Get the instance of StringWriter
	 * @return StringWriter
	 */
	public abstract StringWriter getWriter();
	
	/**
	 * Check if the String contains numbers only
	 * @param in String to check
	 * @return true when contains integers only
	 */
	public abstract boolean isNumeric(String in);
	
	/**
	 * Transform the string format number to integer (BigInteger, hence the size of phone numbers)
	 * @param number to be transformed
	 * @return BigInteger representation of the given string 
	 */
	public BigInteger StringToInt(String number);
}
