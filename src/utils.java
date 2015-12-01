import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;

import org.json.simple.JSONObject;


public class utils implements Utils {
	
	private static Utils instance = null;
	StringWriter out = null;
	
	/**
	 * For singleton
	 */
	private utils() {
		out = new StringWriter();
	}
	
	/*
	 * Singleton
	 * @returns Utils instance
	 */
	public static Utils getInstance() {
		if(instance == null) instance = new utils();
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
		if(StringToInt(in).intValue() == -1) return false;
		return true;
	}
	
	@Override
	public BigInteger StringToInt(String code) {
		BigInteger intcode = null;
		
		// Basic check for string
		if(code == null || code.length() == 0) intcode = new BigInteger("-1");
		try {
			// Try to create new Big Integer
			intcode = new BigInteger(code);
		} catch (NumberFormatException e) {
			intcode = new BigInteger("-1");
		}
		return intcode;
	}
	
	

}
