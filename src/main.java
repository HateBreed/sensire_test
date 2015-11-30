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
		ProcessJSON processer = new processJSON();
		processer.Initialize();
		
		try {
			// Read JSON from file to database and when successful, sort
			if(processer.DecodeJSON(file)) {
				System.out.println("Read from \"" + file + "\":");
				db.getInstance().print();
				db.getInstance().sort();
				System.out.println("\nDatabase sorted, contains:");
				db.getInstance().print();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		
		JSONObject json = null;
		try {
			// Create JSON representation of the database
			json = processer.EncodeJSON();
			
			if(json != null && address != null) {
				JSONSender sender = new jsonSender();
				
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
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
