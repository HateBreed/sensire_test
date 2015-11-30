
public interface Address extends Component {
	/**
	 * Get the street address
	 * @return String containing street address
	 */
	public abstract String getStreet();
	
	/**
	 * Get the city
	 * @return String City name
	 */
	public abstract String getCity();
	
	/**
	 * Get postal code as string
	 * @return String representation of postal code
	 */
	public abstract String getPostalCodeString();
	
	/**
	 * Get postal code as integer
	 * @return int representation of postal code
	 */
	public abstract int getPostalCodeInt();
	
	/**
	 * Set the street address
	 * @param street Street address
	 */
	public abstract void setStreet(String street);
	
	/**
	 * Set the city name
	 * @param city City name
	 */
	public abstract void setCity(String city);
	
	/** 
	 * Set the postal code
	 * @param code Postal code
	 */
	public abstract void setPostalCode(String code);

}
