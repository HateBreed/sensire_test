
public interface PhoneNumber extends Component {
	
	/**
	 * Get phone number type
	 * @return String number type, either "work" or "home"
	 */
	public abstract String getType();
	
	/**
	 * Get the phone number
	 * @return String representation of number
	 */
	public abstract String getNumber();
	
	/**
	 * Set the phone number type
	 * @param type PhoneNumber type, either "work" or "home"
	 */
	public abstract void setType(String type);
	
	/**
	 * Set the phone number
	 * @param number new number
	 */
	public abstract void setNumber(String number);
}
