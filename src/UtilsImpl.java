import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;

import org.json.simple.JSONObject;


public class UtilsImpl implements Utils {
	
	private static Utils instance = null;
	StringWriter out = null;
	
	/**
	 * For singleton
	 */
	private UtilsImpl() {
		out = new StringWriter();
	}
	
	/*
	 * Singleton
	 * @returns Utils instance
	 */
	public static Utils getInstance() {
		if(instance == null) instance = new UtilsImpl();
		return instance;
	}

	@Override
	public boolean writeJSONString(JSONObject obj) {
		
		try { 
			obj.writeJSONString(out);
		} catch (IOException ie) {
			return false;
		}
		return true;
	}

	@Override
	public StringWriter getWriter() {
		return out;
	}

	@Override
	public boolean isNumeric(String in) {
		if(StringToInt(in) == null) return false;
		return true;
	}
	
	@Override
	public BigInteger StringToInt(String str) {
		BigInteger strint = null;
		
		// Basic check for string
		if(str == null || str.length() == 0) return strint;
		try {
			// Try to create new Big Integer
			strint = new BigInteger(str);
		} catch (NumberFormatException e) {
			
		}
		return strint;
	}
}
