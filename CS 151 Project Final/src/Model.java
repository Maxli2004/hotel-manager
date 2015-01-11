
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Model for the MVC strategy.
 * Stores the list of rooms and users with accessors and mutators.
 * @author Max, Shinjo, Yitong
 * 
 */
public class Model implements Serializable {
	
	final String FILE_NAME = "List.data";
	final int NO_ROOMS = 20;
	final int CHEAP_ROOM = 1;
	final int EXP_ROOM = 2;
	
	private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();
	private ArrayList<Room> rooms = new ArrayList<Room>();	
	private ArrayList<Person> users = new ArrayList<Person>();	
	private transient ArrayList<Room> currentTransactionRooms = new ArrayList<Room>();	
	private transient String currentUserName;
	
	
	/** 
	 * Check's availability of room and makes reservation
	 * @param roomNumber room number to reserve
	 * @param guest Guest who checked out the room
	 * @param startDay beginning day of stay
	 * @param endDay end day of stay
	 */
	public void makeReservation(int roomNumber, Guest guest, boolean quality, GregorianCalendar startDay, GregorianCalendar endDay) {
			if (checkAvailableRoom(roomNumber, startDay, endDay)) {
				Room temp = new Room(roomNumber, guest, startDay, endDay);
				rooms.add(temp);
				currentTransactionRooms.add(temp);
			} 
			for (ChangeListener l : listeners) {
				l.stateChanged(new ChangeEvent(this));
			}	
	}
	
	/**
	 * Helper method for MakeReservation that checks for available rooms 
	 * @param roomNumber room number to reserve
	 * @param startDay beginning day of stay
	 * @param endDay end day of stay
	 * @return true if room is available, false if not
	 */
	private boolean checkAvailableRoom(int roomNumber, GregorianCalendar startDay, GregorianCalendar endDay) {
		Date startD = startDay.getTime();
		Date endD = endDay.getTime();
		if ((endD.getTime() - startD.getTime())/(1000 * 60 * 60 * 24) > 60) {
			return false;
		}
			
		else {
			for(int i = 0; i < rooms.size(); i++) {
			Room temp = rooms.get(i);
			if (temp.getRoom() == roomNumber && !temp.checkAvailable(startDay, endDay)) {
				return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Delete reservation of the room
	 * @param roomNumber room number to delete
	 * @param start GregorianCalendar start date
	 */
	public void deleteReservation(int roomNumber, GregorianCalendar start) {
		for(int i = 0; i < rooms.size(); i++) {
			Room temp;
			temp = rooms.get(i);
			if (roomNumber == temp.getRoom() && temp.getStartDay().equals(start.getTime())) {
				rooms.remove(i);
				
			}
		}
		for (int j = 0; j < currentTransactionRooms.size(); j++) {
			Room temp = currentTransactionRooms.get(j);
			if (roomNumber == temp.getRoom() && temp.getStartDay().equals(start.getTime())) {
				currentTransactionRooms.remove(j);
			}
		}
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}		
	}
	/**
	 * Verify account matching name and password
	 * @param name String of User ID
	 * @param password String of password
	 * @return Person that matches the information
	 */
	public Person verifyAccountInformation(String name, String password, boolean isManager) {
		for(int i = 0; i < users.size(); i++) {
			Person temp = users.get(i);
			if(temp.getName().equals(name) && temp.getPassword().equals(password) && isManager == temp.getManager()) {
					currentUserName = name;
					return temp;
			}
		} 
		return null;
		
	}
	
	/** Create a new guest account
	 * @param name guest ID
	 * @param password password to access the account
	 */
	public boolean createAccountInformation(String name, String password, boolean manager) {
		boolean checker = true;
		for(int i = 0; i<users.size(); i++) {
			if(users.get(i).getName().equals(name)){
				checker = false;
			}
		}
		if(checker){
			if(manager){
				users.add(new Manager(name, password));
			} else {
				Guest newUser = new Guest(name, password);
				users.add(newUser);
			}
			save();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This is the context which uses the Strategy pattern to get the receipt, either simple or comprehensive.
	 * @param r ReceiptFormatter
	 * @return format of receipt
	 */
	public String getReceipt(ReceiptFormatter r){
		return r.format(this);
	}
	
	/**
	 * gets all the reserved rooms on a day
	 * @param day GregorianCalendar day 
	 * @return Arraylist of Room of the taken rooms
	 */
	public ArrayList<Room> takenRooms(GregorianCalendar day) {
		ArrayList<Room> takenRooms = new ArrayList<Room>();
		for(int i = 0; i < rooms.size(); i++) {
			if(!rooms.get(i).checkAvailable(day, day)) {
				takenRooms.add(rooms.get(i));
			}
		}
		return takenRooms;
	}
	
	/**
	 * gets all the available rooms on a day
	 * @param day GregorianCalendar day
	 * @return ArrayList of integer of the available rooms
	 */
	public ArrayList<Integer> availableRooms(GregorianCalendar day) {
		int totalRooms = 20; // rooms 1-20
		
		ArrayList<Room> takenList;
		takenList = takenRooms(day);
		
		ArrayList<Integer> openRooms = new ArrayList<Integer>();
		for(int i = 0; i < totalRooms; i++) {
			openRooms.add(i+1);
		}
		for(int i = 0; i < takenList.size(); i++) {
			boolean next = true;
			for(int j = 0; j < openRooms.size() && next==true; j++) {
				if(takenList.get(i).getRoom() == openRooms.get(j)) {
					openRooms.remove(j);
					next = false;
				}
			}
		}
		return openRooms;		
	}
	
	/**
	 * prints all the available rooms
	 * @param day GregorianClanedar day
	 * @return all rooms in string
	 */
	public String showAvailableRooms(GregorianCalendar day) {
		ArrayList<Integer> openRooms = availableRooms(day);
		String result = "Available Rooms: \n";
		if (openRooms.isEmpty()) 
			return "No Available Rooms";
		else {
			for(Integer in: openRooms) {
				if(in < 10) {
					result += "Room number: " + String.valueOf(in) + " Price: " + "100" + "\n";
					}
				else {
					result += "Room number: " + String.valueOf(in) + " Price: " + "200" + "\n";
				}
			}
		}	
		return result;
	}
	
	/**
	 * prints all the taken rooms
	 * @param day GregorianCalendar dat
	 * @return all rooms in strings
	 */
	public String showTakenRooms(GregorianCalendar day) {
		ArrayList<Room> taken = (ArrayList<Room>) takenRooms(day);
		String message = "Taken Rooms \n";
		if (taken.isEmpty())
			return "No Room Reserved in this day. \n";
		else
			for(Room r: taken) {
				message += r.toString() + "\n\n";
			}
		return message;	
	}
	
	/**
	 * Saves the users and reservation to a data file.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
			out.writeObject(rooms);
			out.writeObject(users);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * loads rooms and user from a data file & resets currentTransactionRooms
	 */
	public void load() {	
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
			rooms = (ArrayList<Room>) in.readObject();
			users = (ArrayList<Person>) in.readObject();
			currentTransactionRooms.clear();
			in.close();
		} 
		catch (Exception e) {
		} 

	}
	
	/**
	 * find all rooms belonging to a guest
	 * @param person Guest user
	 * @return ArrayList of rooms belonging to the Guest
	 */
	public ArrayList<Room> findRoomBelongingTo(Guest person) {
		ArrayList<Room> res = new ArrayList<Room>();
		for(Room cur : rooms){
			if(cur.getGuest().same(person)){
				int cost = 100;
				if(cur.getQuality()){
					cost = 200;
				}
				res.add(cur);
			}
		}
		return res;
	}
	
	/**
	 * gets rooms
	 * @return ArrayList of all the rooms
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	/**
	 * gets users
	 * @return ArrayList of all the users
	 */
	public ArrayList<Person> getUsers() {
		return users;
	}
	
	/**
	 * gets current rooms used in transaction
	 * @return ArrayList of rooms in use
	 */
	public ArrayList<Room> getCurrentTransactionRooms() {
		return currentTransactionRooms;
	}
	
	/**
	 * return reserved rooms by guests
	 * @param name String name of guest
	 * @return ArrayList of rooms checked out by the user
	 */
	public ArrayList<Room> getGuestReservedRooms(String name) {
		ArrayList<Room> res = new ArrayList<Room>();
		for(Room cur : rooms){
			if(cur.getGuest().getName().equals(name)){
				res.add(cur);
			}
		}
		return res;
	}

	/**
	 * gets current user name
	 * @return String of user name
	 */
	public String getCurrentUserName() {
		return currentUserName;
	}
	
	/**
	 * attach change listener
	 * @param l ChangeListener
	 */
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
	
	/**
	 * returns all the available rooms
	 * @param startDate GregorianCalendar startDate
	 * @param endDate GregorianClanedar endDate
	 * @param roomType cheap room or expensive room
	 * @return ArrayList of all the available rooms
	 */
	public ArrayList<String> getAvailableRooms(GregorianCalendar start, GregorianCalendar end, int roomType) {
		ArrayList<Integer> availableRoomsBetween = new ArrayList<Integer>();
		//all 20 rooms
		for (int i = 1; i <= NO_ROOMS; i++) {
				availableRoomsBetween.add(i);
		}
		//remove the occupied room from the 20 rooms
		for (Room r : rooms) {
			if (!r.checkAvailable(start, end))
				availableRoomsBetween.remove(new Integer(r.getRoom()));
		}
		//filter out the rooms according to roomType and out put string
		return filterRoomByType(availableRoomsBetween, roomType);
	}
	
	/**
	 * filter out the rooms according to roomType and out put string
	 * @param openRooms all the rooms available
	 * @param roomType the roomType you want
	 * @return an ArrayList of String containing only the rooms of the specified type
	 */
	public ArrayList<String> filterRoomByType(ArrayList<Integer> openRooms, int roomType) {
		ArrayList<String> res = new ArrayList<String>();
		for (int i : openRooms) {
			if (roomType == CHEAP_ROOM) {
				if (i <= 10)
					res.add("Dirty Room " + String.valueOf(i));
			}
			else if (roomType == EXP_ROOM) {
				if (i > 10)
					res.add("Expensive Room " + String.valueOf(i));
			}
			else {
				if (i <= 10)
					res.add("Dirty Room " + String.valueOf(i));
				else
					res.add("Expensive Room " + String.valueOf(i));
			}	
		}
		return res;
	}
}
