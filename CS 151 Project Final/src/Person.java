import java.io.Serializable;

/**
 * Person stores the UserID, Password, and manager status 
 * @author Max
 * 
 */
public class Person implements Serializable{
	
	private String name;
	private String password;
	private boolean isManager;
	
	
	/**
	 * Person constructor
	 * @param name User ID in String
	 * @param password Password in String
	 * @param manager Boolean to determine manager status. If manager - true, else false
	 */
	public Person(String name, String password, boolean manager) {
		this.name = name;
		this.password = password;		
		isManager = manager;
	}
	
	/**
	 * 
	 * @return boolean true if manager, false if guest
	 */
	public boolean getManager(){
		return isManager;
	}
	
	/** get user ID
	 * 
	 * @return returns the name
	 */
	public String getName(){
		return name;
	}
	
	/** get user Password
	 * 
	 * @return returns the password
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * compares person
	 * @param person person to be compared
	 * @return true if same, false is different
	 */
    public boolean same(Person person){
    	if(this.name.equals(person.name) && this.password.equals(person.password)){
    		return true;
    	}
    	else {
    		return false;
    	}
    }

}

