/**
 * Guest is a subclass of Person with manager status set as false;
 * @author Max
 * @see Person
 */
public class Guest extends Person{
	
	/**
	 * Guest constructor
	 * @param name UserID in String
	 * @param password Password in Strings
	 */
	public Guest(String name, String password) {
		super(name, password, false);
	}
	
}

