
/**
 * Simple format receipt shows user id, name and reserved rooms and corresponding total amount dues only made through this transaction
 * This is concrete Strategy which implements ReceiptFormatter interface.
 * @author yitong
 *
 */
public class SimpleReceiptFormatter implements ReceiptFormatter{
    /**
     * Generate a string which contains user id, name, reserved rooms and corresponding total amount through the current transaction
     * @param pass in an instance of Model
     * @return String which contains all the simple receipt information 
     */
	public String format(Model m) {
		String message = new String();
		message += "User Name: " + m.getCurrentUserName() + "\n";
		int totalPrice = 0;
		for (Room r : m.getCurrentTransactionRooms()) {
			message += "Room Number: " + r.getRoom() + " From " + r.getStartDate() + " to " + r.getEndDate() + "\n";
			totalPrice += r.getPrice();
		}
		message += "Total Price: " + totalPrice;
		return message;
	}
}


