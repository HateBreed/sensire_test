import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.MalformedURLException;

import org.json.simple.*;
import org.json.simple.parser.ParseException;

public class main {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String file = "";
		String address = "http://dev.sensire.fi:40019/TestTask/Jussi/Laakkonen";
		if(args.length == 0) file = "data/customers.json";
		else file = args[0];
		
		// Initialize JSON processing engine
		ProcessJSON processer = new ProcessJSONImpl();
		processer.Initialize();
		
		try {
			// Read JSON from file to database and when successful, sort
			if(processer.DecodeJSON(file)) {
				System.out.println("Read from \"" + file + "\":");
				DataBaseImpl.getInstance().print();
				DataBaseImpl.getInstance().sort();
				System.out.println("\nDatabase sorted, contains:");
				DataBaseImpl.getInstance().print();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + file + "\" not found");
			return;
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			return;
		} catch (ParseException e) {
			System.out.println("Cannot parse JSON, reason:" + e.getLocalizedMessage());
			return;
		} catch (JSONException e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		
		try {
			// Create JSON representation of the database
			JSONObject json = processer.EncodeJSON();
			
			if(json != null && address != null) {
				JSONSender sender = new JsonSenderImpl();
				
				// Initialize with address
				if(!sender.initialize(address)) return;
				
				// Send the JSON
				if(!sender.sendHTTPPost(json)) return;
				
				// Try to read server reply
				String reply = sender.readReply();
				
				// No reply, read response code
				if(reply.length() == 0) {
					reply = sender.getResponse();
				}
				
				// Print either, reply or response
				System.out.println("\n" + reply);
				
				// Close
				sender.close();
			}
		} catch (MalformedURLException e) {
			System.out.println("URL is invalid: " + e.getLocalizedMessage());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		
	}
}
