
public class stringStorage implements StringStorage {
	
	private static String mainIdentifier = "customers";
	private static String[] mainItems = {"name", "address", "phoneNumber"};
	private static int NAME_POS = 0;
	private static int ADDRESS_POS = 1;
	private static int PHONE_POS = 2;
	
	private static String[] addressItems = { "street", "city", "postalCode"};
	private static int STREET_POS = 0;
	private static int CITY_POS = 1;
	private static int CODE_POS = 2;
	
	private static String[] numberItems = {"type", "number"};
	private static int TYPE_POS = 0;
	private static int NUMBER_POS = 1;
	
	private static StringStorage storage = null;
	
	private stringStorage() {
		
	}
	
	public static StringStorage getInstance() {
		if(storage == null) storage = new stringStorage();
		return storage;
	}

	@Override
	public String getIdentifierString() {
		return mainIdentifier;
	}

	@Override
	public String[] getMainStrings() {
		return mainItems;
	}

	@Override
	public int getNamePosition() {
		return NAME_POS;
	}

	@Override
	public int getAddressPosition() {
		return ADDRESS_POS;
	}

	@Override
	public int getPhoneNumberPosition() {
		return PHONE_POS;
	}

	@Override
	public String[] getAddressStrings() {
		return addressItems;
	}

	@Override
	public int getStreetPosition() {
		return STREET_POS;
		
	}

	@Override
	public int getCityPosition() {
		return CITY_POS;
	}

	@Override
	public int getPostalCodePosition() {
		return CODE_POS;
	}

	@Override
	public String[] getPhoneNumberStrings() {
		return numberItems;
	}

	@Override
	public int getTypePosition() {
		return TYPE_POS;
	}

	@Override
	public int getNumberPosition() {
		return NUMBER_POS;
	}
}
