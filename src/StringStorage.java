
public interface StringStorage {
	
	/**
	 * Get main identifier of JSON ("customers")
	 * @return main identifier string
	 */
	public abstract String getIdentifierString();
	
	/**
	 * Get list of main item strings
	 * @return array of strings
	 */
	public abstract String[] getMainStrings();
	
	/**
	 * Get position of name in getMainStrings() return
	 * @return int value of position
	 */
	public abstract int getNamePosition();
	
	/**
	 * Get position of address in getMainStrings() return
	 * @return int value of position
	 */
	public abstract int getAddressPosition();
	
	/**
	 * Get position of phone number in getMainStrings() return
	 * @return int value of position
	 */
	public abstract int getPhoneNumberPosition();
	
	/**
	 * Get list of address items
	 * @return array of strings
	 */
	public abstract String[] getAddressStrings();
	
	/**
	 * Get position of street in getAddressStrings() return
	 * @return int value of position
	 */
	public abstract int getStreetPosition();
	
	/**
	 * Get position of city in getAddressStrings() return
	 * @return int value of position
	 */
	public abstract int getCityPosition();
	
	/**
	 * Get position of postal code in getAddressStrings() return
	 * @return int value of position
	 */
	public abstract int getPostalCodePosition();
	
	/**
	 * Get list of phone number items
	 * @return array of strings
	 */
	public abstract String[] getPhoneNumberStrings();
	
	/**
	 * Get position of type in getPhoneNumberStrings() return
	 * @return int value of position
	 */
	public abstract int getTypePosition();
	/**
	 * Get position of number in getPhoneNumberStrings() return
	 * @return int value of position
	 */
	public abstract int getNumberPosition();
}
