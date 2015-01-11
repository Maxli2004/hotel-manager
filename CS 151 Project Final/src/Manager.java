/**
 * Manager is a subclass of Person with manager status as true.
 * @author Max	
 * @see Person
 *
 */
public class Manager extends Person{
	/**
	 * Manager constructor
	 * @param name User ID in String
	 * @param password Password in String
	 */
	public Manager(String name, String password) {
		super(name, password, true);
	}

}


