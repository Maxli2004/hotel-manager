
/**
 * Comprehensive receipt shows user id, name and all valid reservations made by this user with corresponding total amount due.	
 * This is concrete Strategy which implements ReceiptFormatter interface.
 * @author yitong
 *
 */
public class ComprehensiveReceiptFormatter implements ReceiptFormatter{
    /**
     * Generate a string which contains user id, name, all valid reserved rooms and corresponding total amount due.
     * @param pass in an instance of Model
     * @return String which contains all the comprehensive receipt information 
     */
	public String format(Model m) {
		String message = new String();
		message += "User Name: " + m.getCurrentUserName() + "\n";
		int totalPrice = 0;
		for (Room r : m.getGuestReservedRooms(m.getCurrentUserName())) {
			message += "Room Number: " + r.getRoom() + " From " + r.getStartDate() + " to " + r.getEndDate() + "\n";
			totalPrice += r.getPrice();
		}
		message += "Total Price: " + totalPrice;
		return message;
	}

}


