import java.util.ArrayList;

public interface Customer extends Component {
	/**
	 * Get name of customer
	 * @return String name
	 */
	public abstract String getName();
	
	/**
	 * Set name for customer
	 * @param name new name
	 */
	public abstract void setName(String name);
	
	/**
	 * Get address of customer
	 * @return Address object
	 * @see Address
	 */
	public abstract Address getAddress();
	
	/**
	 * Set address for customer
	 * @param address Address
	 * @see Address
	 */
	public abstract void setAddress(Address address);
	
	/**
	 * Get all numbers belonging to customer
	 * @return ArrayList<PhoneNumber> a list of number objects
	 * @see PhoneNumber
	 */
	public abstract ArrayList<PhoneNumber> getNumbers();
	
	/**
	 * Get a number with type
	 * @param type Phone number type to be searched, either "work" or "home"
	 * @return PhoneNumber object
	 * @see PhoneNumber
	 */
	public abstract PhoneNumber getNumber(String type);
	
	/**
	 * Add a new number to customer
	 * @param number new PhoneNumber object to be added
	 * @see PhoneNumber
	 */
	public abstract void addNumber(PhoneNumber number);
}
