import java.util.ArrayList;


public interface DataBase {
	/**
	 * Get the database
	 * @return ArrayList<Customer> containing all customers, or null if empty 
	 */
	public abstract ArrayList<Customer> getDB();
	
	/**
	 * Add new customer to database
	 * @param c Customer object
	 * @return true when added and c is not null
	 */
	public abstract boolean add(Customer c);
	
	/**
	 * Sort the database alphabetically
	 */
	public abstract void sort();
	
	/**
	 * Clear the database
	 */
	public abstract void clear();
	
	/**
	 * Print database to stdout
	 */
	public abstract void print();
}
