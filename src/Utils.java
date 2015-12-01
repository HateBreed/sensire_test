import java.io.StringWriter;

import org.json.simple.JSONObject;


public interface Utils {
	public abstract boolean writeJSONString(JSONObject obj);
	public abstract StringWriter getWriter();
}
