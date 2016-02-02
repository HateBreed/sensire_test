import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;


public class JsonSenderImpl implements JSONSender {
	private URL url = null;
	private HttpURLConnection httpc = null;
	
	private DataOutputStream output = null;
	private BufferedReader reader = null;

	public JsonSenderImpl() {
	}
	
	@Override
	public boolean initialize(String address) throws MalformedURLException, IOException{
		if(address != null && address.length() > 8) {
			url = new URL(address);
			httpc = (HttpURLConnection)url.openConnection();
			return true;
		}
		return false;
	}

	@Override
	public boolean sendHTTPPost(JSONObject obj) throws IOException {
		
		if(obj != null) {
			String encodedJSON = URLEncoder.encode(obj.toString(),"UTF-8");
			httpc.setRequestMethod("POST");
			httpc.setRequestProperty("content-type", "application/json");
			httpc.setRequestProperty("Content-Length", "" + Integer.toString(encodedJSON.getBytes().length));
			httpc.setUseCaches(false);
			httpc.setDoInput(true);
			httpc.setDoOutput(true);
			
			output = new DataOutputStream(httpc.getOutputStream());
			output.writeBytes(encodedJSON);
			output.flush();
			output.close();
			output = null;
			
			return true;
		}
		return false;
	}

	@Override
	public String readReply() throws IOException {
		reader = new BufferedReader(new InputStreamReader(httpc.getInputStream()));
		String line = "";
		StringBuffer response = new StringBuffer();
		while((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		reader = null;
		return response.toString();
	}

	@Override
	public String getResponse() throws IOException {
		String response = "HTTP " + httpc.getResponseCode() + " " + httpc.getResponseMessage();
		return response;
	}

	@Override
	public void close() throws IOException {
		if(output != null) output.close();
		if(reader != null) reader.close();
		if(httpc != null) httpc.disconnect();
	}

}
