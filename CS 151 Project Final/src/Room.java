
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Room class that stores information about reservations
 * @author Max
 * 
 */
public class Room implements Serializable {

	private Date startDay;
	private Date endDay;
	private int roomNumber;
	private String startDayS;
	private String endDayS;
	private boolean quality; 
	private Guest guest;

	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * Room constructor
	 * @param roomNumber room number in int
	 * @param guest Guest that reserved the room 
	 * @param sDay beginning day of stay in GregorianCalendar
	 * @param eDay end day of stay in Gregorian Calendar
	 */
	public Room(int roomNumber, Guest guest, GregorianCalendar sDay,
			GregorianCalendar eDay) {
		this.roomNumber = roomNumber;
		this.guest = guest;
		if (roomNumber > 10) {
			quality = true;
		} else {
			quality = false;
		}

		startDay = sDay.getTime();
		endDay = eDay.getTime();
		startDayS = (Integer.toString(sDay.get(Calendar.MONTH) + 1)) + "/"
				+ Integer.toString(sDay.get(Calendar.DAY_OF_MONTH)) + "/"
				+ Integer.toString(sDay.get(Calendar.YEAR));
		endDayS = (Integer.toString(eDay.get(Calendar.MONTH) + 1)) + "/"
				+ Integer.toString(eDay.get(Calendar.DAY_OF_MONTH)) + "/"
				+ Integer.toString(eDay.get(Calendar.YEAR));
	}

	/**
	 * 
	 * @return room number
	 */
	public int getRoom() {
		return roomNumber;
	}

	/**
	 * 
	 * @return quality to determine if the room is 100$ or 200$
	 */
	public boolean getQuality() {
		return quality;
	}

	/**
	 * 
	 * @return boolean true
	 */
	public boolean equals() {
		boolean equal = true;

		return equal;
	}

	/**
	 * 
	 * @return returns price of the room depending on quality
	 */
	public int getPrice() {
		if (quality)
			return 200;
		return 100;
	}

	/**
	 * 
	 * @return Guest for the room
	 */
	public Guest getGuest() {
		return guest;
	}

	/**
	 * 
	 * @return startDay converted to String
	 */
	public String getStartDate() {
		return df.format(startDay);
	}

	/**
	 * 
	 * @return endDay converted to String
	 */
	public String getEndDate() {
		return df.format(endDay);
	}

	/**
	 * 
	 * @return return startDay in Date format
	 */
	public Date getStartDay() {
		return startDay;
	}

	/**
	 * 
	 * @return endDay in Date format
	 */
	public Date getEndDay() {
		return endDay;
	}
	
	/**
	 * 
	 * @return String containing Room number, length of stay, and cost
	 */
	public String reservationTitle() {
		int cost = 100;
		if (quality) {
			cost = 200;
		}
		return "Room " + roomNumber +  " Stay from "
				+ getStartDate() + " Until " + getEndDate() + " Cost $" + cost;
	}

	/**
	 * @return String containing room number, guest name, price, and length of stay
	 */
	public String toString() {
		int price;
		if (quality) {
			price = 200;
		} else {
			price = 100;
		}
		return ("Room number: " + Integer.toString(roomNumber) + "\n"
				+ " Guest: " + guest.getName() + "\n" + " Price: "
				+ Integer.toString(price) + "\n" + " Stay from: " + startDayS
				+ "\n" + " Until: " + endDayS);
	}

	/**
	 * Checks the variables to see if the room is open
	 * 
	 * @param day GregorianCalendar start day of stay 
	 * @param end GregorianCalendar end day of stay
	 * @return boolean: true if available, false if not available
	 */
	public boolean checkAvailable(GregorianCalendar start, GregorianCalendar end) {
		Date tempStart = start.getTime();
		Date tempEnd = end.getTime();
		return tempStart.after(this.endDay) || tempEnd.before(this.startDay);
	}
}
