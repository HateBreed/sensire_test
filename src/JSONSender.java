
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONObject;


public interface JSONSender {
	/**
	 * Initialize sender with address
	 * @param address Address whereto send the JSON as HTTP POST
	 * @return true if success
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public abstract boolean initialize(String address) throws MalformedURLException, IOException;
	
	/**
	 * Close connection including output and input streams
	 * @throws IOException
	 */
	public abstract void close() throws IOException;
	
	/**
	 * Send the given JSONObject as HTTP Post to initialized address
	 * @param obj JSONOBject to be sent
	 * @return true when ok
	 * @throws IOException
	 */
	public abstract boolean sendHTTPPost(JSONObject obj) throws IOException;
	
	/**
	 * Read the server reply from the connection
	 * @return String containing the reply or a 0 length string if no reply is read
	 * @throws IOException
	 */
	public abstract String readReply() throws IOException;
	
	/**
	 * Get the response code and message from connection
	 * @return String containing the HTTP reply and the message
	 * @throws IOException
	 */
	public abstract String getResponse() throws IOException;
}
