import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


public class db implements DataBase {
	
	private ArrayList<Customer> database = null;
	private static DataBase instance = null;
	
	/**
	 * Private constructor, calls initDb();
	 * @see #initDb()
	 */
	private db() {
		initDb();
	}
	
	/**
	 * Database singleton
	 * @return DataBase object
	 */
	public static DataBase getInstance() {
		if(instance == null) instance = new db();
		return instance;
	}
	
	/**
	 * Initialize database
	 */
	private void initDb() {
		 database = new ArrayList<Customer>();
	}

	/**
	 * Returns the database object
	 */
	@Override
	public ArrayList<Customer> getDB() {
		if(database == null) initDb();
		return database;
	}

	/**
	 * Add new customer object to database
	 * @param c A customer object containing Address and PhoneNumber objects
	 * @return true when adding is ok, false if cannot add or c == null
	 * @see Customer
	 * @see Address
	 * @see PhoneNumber
	 */
	@Override
	public boolean add(Customer c) {
		if(database == null) initDb();
		if(c != null) return database.add(c);
		return false;
	}

	/**
	 * Clear database
	 */
	@Override
	public void clear() {
		database.clear();
	}
	
	
	/**
	 * Simple sorting of an ArrayList with Collections and Comparator
	 * @see DataBase#sort()
	 */
	@Override
	public void sort() {
		Collections.sort(database, new Comparator<Customer>() {
			@Override
			public int compare(Customer i1, Customer i2) {
				return i1.getName().compareTo(i2.getName());
			}
		});
	}

	
	/*
	 * Print the database to stdout
	 * @see DataBase#print()
	 */
	@Override
	public void print() {
		Iterator<Customer> iter = getInstance().getDB().iterator();
		
		while(iter.hasNext()) {
			Customer c = iter.next();
			System.out.println("Name:" + c.getName());
			System.out.println("Address: " + c.getAddress().getStreet() + "," 
					+ c.getAddress().getCity() + "," 
					+ c.getAddress().getPostalCodeString());
			Iterator<PhoneNumber> numbers = c.getNumbers().iterator();
			
			while(numbers.hasNext()) {
				PhoneNumber n = numbers.next();
				System.out.println("Number: " + n.getType() + " = " + n.getNumber());
			}
		}
	}
}
