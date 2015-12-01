import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;


public class utils implements Utils {
	
	private static Utils instance = null;
	StringWriter out = null;
	
	private utils() {
		out = new StringWriter();
	}
	
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
	
	

}
