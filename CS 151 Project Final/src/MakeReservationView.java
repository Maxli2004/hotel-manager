import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

/**
 * 
 * @author Shinjo
 *
 */
public class MakeReservationView extends JPanel implements ChangeListener {
	final int CHEAP_ROOM = 1;
	final int EXP_ROOM = 2;
	final int ALL_ROOM = 3;
	
	int roomType;
	Date tempStart = null; 
	Date tempEnd = null; 
	DefaultListModel<String> smodel = new DefaultListModel<String>();
	JList list = new JList(smodel);
	static GregorianCalendar startDate = new GregorianCalendar();
	static GregorianCalendar endDate = new GregorianCalendar();
	GregorianCalendar currentDate = new GregorianCalendar();
	Model model;
	Person person;
	JFrame mFrame;
	final JTextField start = new JTextField(8);
	final JTextField end = new JTextField(8);
/**
* Displays a panel that has 2 text fields for the start day and end date input as DD/MM/YYYY and 2 buttons to choose price
* when 2 valid dates are input displays rooms available for those dates.
* cheep button action listener on click sets variable roomType to 1 which denotes a cheap room.
* expensive button action listener on click sets variable roomType to 2 which denotes a expensive room.
* start document change listener when values are typed into the field attempts to format them as a GregorianCalendar Object and set them as the startDate.
* end document change listener when are values are typed into the field attempts to format them as a GregorianCalendar Object and set them as the endDate.
* confirm button action listener when pressed the selected room is reserved and a popup is displayed.
* return button action listener when pressed returns to the GuestView.
* This is the context program of Strategy pattern, which calls the model.getReceipt() to print simple or comprehensive receipt.
* 
* @param JFrame mFrame the main frame so that changes to the displayed panel can be made
* @param Model model the model so that available rooms can be viewed and reservations be made
* @param Person person the identity of the person making the reservations so that the reservations can be identitified at a later date
*/

	public MakeReservationView(final JFrame mFrame, final Model model, final Person person) {
		this.model = model;
		this.person = person;
		this.mFrame = mFrame;
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		
		LineBorder roundedLineBorder = new LineBorder(Color.BLACK, 2, false);
		TitledBorder roundedTitledBorder = new TitledBorder(roundedLineBorder, "Make Reservation");
		roundedTitledBorder.setTitleColor(new Color(0, 162, 232));
		this.setBorder(roundedTitledBorder);
		this.setBackground(Color.WHITE);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane jp = new JScrollPane(list);
		jp.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(0, 162, 232)));
		this.add(jp, BorderLayout.EAST);
		JPanel inputs = new JPanel();
		JPanel rightf = new JPanel();
		rightf.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		JButton confirm = new JButton("Confirm");
		JButton transaction = new JButton("Transaction Done");
		transaction.setBackground(Color.WHITE);
		JLabel hold = new JLabel("Start Date:");
		JLabel hold1 = new JLabel("End Date:");

		JPanel starting = new JPanel();
		JPanel ending = new JPanel();
		ending.add(hold1);
		ending.add(end);
		ending.setBackground(Color.WHITE);
		JButton cheap = new JButton("100$");
		JButton expensive = new JButton("200$");
		cheap.setBackground(Color.WHITE);
		expensive.setBackground(Color.WHITE);
		starting.add(hold);
		starting.add(start);
		starting.setBackground(Color.WHITE);
		inputs.add(starting, BorderLayout.NORTH);
		inputs.add(ending, BorderLayout.CENTER);
		inputs.setBackground(Color.WHITE);
		inputs.add(cheap);
		inputs.add(expensive);

		JPanel buttons = new JPanel();
		confirm.setBackground(Color.WHITE);
		buttons.add(confirm);
		buttons.add(transaction);

		buttons.setBackground(Color.WHITE);
		this.add(inputs, BorderLayout.NORTH);
		this.add(buttons, BorderLayout.SOUTH);
		this.add(jp, BorderLayout.CENTER);

		cheap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				roomType = CHEAP_ROOM;
				startDate = stringToGregorian(start.getText());
				endDate = stringToGregorian(end.getText());
				if (isValidDate(start.getText(), end.getText()) && DayLimit(startDate, endDate)) {	
					smodel.clear();
					populateList();
				}
			}
		});
		
		expensive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startDate = stringToGregorian(start.getText());
				endDate = stringToGregorian(end.getText());
				roomType = EXP_ROOM;
				if (isValidDate(start.getText(), end.getText()) && DayLimit(startDate, endDate)) {
					smodel.clear();
					populateList();
				}
			}
		});

		start.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent arg0) {
				smodel.clear();
				setStartDay();
			}
			public void insertUpdate(DocumentEvent arg0) {
				smodel.clear();
				setStartDay();
			}
			public void removeUpdate(DocumentEvent arg0) {
				smodel.clear();
				setStartDay();
			}
		});

		end.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent arg0) {
				smodel.clear();
				setEndDay();
			}
			public void insertUpdate(DocumentEvent arg0) {
				smodel.clear();
				setEndDay();
			}
			public void removeUpdate(DocumentEvent arg0) {
				smodel.clear();
				setEndDay();
			}
		});

		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String roomNumber = "";
				try {
					roomNumber = list.getSelectedValue().toString().split(" ")[2];
				}
				catch (Exception e) {
					
				}
				if (!list.isSelectionEmpty()) {
					smodel.remove(list.getSelectedIndex());
					model.makeReservation(Integer.parseInt(roomNumber),
							(Guest) person, true, startDate, endDate);
					String[] selection = {"No, complete transaction", "Yes, continue"};
					int returnValues = JOptionPane.showOptionDialog(mFrame,
							"Would you like to continue?", "Message",
							JOptionPane.YES_NO_OPTION, 0, null, selection,
							selection[1]);
					if (returnValues == 0) {
						String[] selections = { "Simple Receipt",
								"Comprehensive Receipt" };
						int returnValue = JOptionPane.showOptionDialog(mFrame,
								"Please choose the receipt type", "Message",
								JOptionPane.YES_NO_OPTION, 0, null, selections,
								selections[1]);
						if (returnValue == 0) {
							ReceiptFormatter rf = new SimpleReceiptFormatter();
							String message = model.getReceipt(rf);
							JOptionPane.showMessageDialog(mFrame, message,"Simple Receipt", JOptionPane.PLAIN_MESSAGE);
							model.save();
						} else if (returnValue == 1) {	
							ReceiptFormatter rf = new ComprehensiveReceiptFormatter();
							String message = model.getReceipt(rf);
							JOptionPane.showMessageDialog(mFrame, message, "Comprehensive Receipt",JOptionPane.PLAIN_MESSAGE);
							model.save();
						}
						mFrame.setContentPane(new GuestView(mFrame, model, person));
						mFrame.revalidate();
					}
				}
				else {
					JOptionPane.showMessageDialog(mFrame, "Please select a room!", "Reminder",JOptionPane.PLAIN_MESSAGE);
				}

			}
		});

		transaction.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String[] selections = { "Simple Receipt",
						"Comprehensive Receipt" };
				int returnValue = JOptionPane.showOptionDialog(mFrame,
						"Please choose the receipt type", "Message",
						JOptionPane.YES_NO_OPTION, 0, null, selections,
						selections[1]);
				if (returnValue == 0) {
					// Simple
					ReceiptFormatter rf = new SimpleReceiptFormatter();
					String message = model.getReceipt(rf);
					JOptionPane.showMessageDialog(mFrame, message, "Simple Receipt", JOptionPane.PLAIN_MESSAGE);
					model.save();
				} else if (returnValue == 1) {
					ReceiptFormatter rf = new ComprehensiveReceiptFormatter();
					String message = model.getReceipt(rf);
					JOptionPane.showMessageDialog(mFrame, message, "Comprehensive Receipt", JOptionPane.PLAIN_MESSAGE);
					model.save(); 
				}
				mFrame.setContentPane(new GuestView(mFrame, model, person));
				mFrame.revalidate();
			}

		});
 
	}
	
	/**
	 * Responsible for setting the end date of the reservation and catching exceptions
	 */
	public void setEndDay(){
		try {
			roomType = ALL_ROOM;
			tempEnd = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(end.getText());
			endDate.setTime(tempEnd);
			if (tempStart != null && endDate.after(startDate) 
					&& (startDate.after(currentDate) || startDate.equals(currentDate)) 
					&& DayLimit(startDate, endDate)) {
				populateList();
			}
		} 
		catch (Exception e) {

	}
}
	
	/**
	 * Responsible for setting the start date of the reservation and catching exceptions
	 */
	public void setStartDay(){	
		try {
			roomType = ALL_ROOM;
			tempStart = new SimpleDateFormat("MM/dd/yyyy",
					Locale.ENGLISH).parse(start.getText());
			startDate.setTime(tempStart);
			if ( tempEnd != null
					&& tempStart.before(tempEnd)
					&& (startDate.after(currentDate) || startDate.equals(currentDate))
					&& DayLimit(startDate, endDate)) {
				populateList();
			}
		} catch (Exception e) {

		}
	}
	
	/**
	 * Checks the length of the reservation being made if longer then 60 days returns false
	 * otherwise it returns true 
	 *
	 * @param GregorianCalendar start the start date of the reservation
	 * @param GregorianCalendar end the end date of the reservation
	 * @return boolean true if the reservation is 60 days or less and false if its longer
	 */
	public boolean DayLimit(GregorianCalendar start, GregorianCalendar end) {
		GregorianCalendar temp = (GregorianCalendar) start.clone();
		int iter = 0;
		while (!temp.equals(end)) {
			temp.add(Calendar.DAY_OF_MONTH, 1);
			iter++;
		}
		if (iter > 60) {
			JOptionPane.showMessageDialog(mFrame,
					"Sorry, you cannot make a reservation longer than 60 days");

			return false;
		}
		return true;

	}

	/**
	 * Checks if the given start and end dates are valid 
	 * @param String sd the start date as a string
	 * @param String ed the end date as a string
	 * @return boolean true if both dates are valid and false if they arent
	 */
	public boolean isValidDate(String sd, String ed) {
		try {
			Date tmpStartDate = new SimpleDateFormat("MM/dd/yyyy",
					Locale.ENGLISH).parse(sd);
			Date tmpEndDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
					.parse(ed);
			GregorianCalendar gregStartDate = new GregorianCalendar();
			GregorianCalendar gregEndDate = new GregorianCalendar();
			gregStartDate.setTime(tmpStartDate);
			gregEndDate.setTime(tmpEndDate);
            if (tmpStartDate != null && tmpEndDate != null && tmpStartDate.before(tmpEndDate) && (gregStartDate.after(currentDate) || gregStartDate.equals(currentDate)))
				return true;	
            else if (tmpStartDate.after(tmpEndDate)) {
            	JOptionPane.showMessageDialog(mFrame,
    					"Sorry, your end date should not be prior to start date.");
            	return false;
            }
            else if (gregStartDate.before(currentDate)) {
            	JOptionPane.showMessageDialog(mFrame,
    					"Sorry, your start date should not be prior to today. \nPlease enter another date.");
            	return false;
            } 	
		} 
		catch (ParseException e) {
			JOptionPane.showMessageDialog(mFrame, "Sorry, the date format you entered is not correct. \nPlease enter you date in the format of 'MM/DD/YYYY'.");
		}
		return false;
	}

	/**
	 * Converts a string to a GregorianCalendar object
	 * @param String s string to be formatted
	 * @return GregorianCalendar the string converted to GregorianCalendar
	 */
	public GregorianCalendar stringToGregorian(String s) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		GregorianCalendar cal = new GregorianCalendar();
		try {
			date = df.parse(s);
			cal.setTime(date);
		} 
		catch (ParseException e) {
		
		}
		return cal;
	}
	
	/**
	 * Responsible for populating the JList to display the available rooms for the given days
	 */
	public void populateList(){
		ArrayList<String> strList = model.getAvailableRooms(startDate, endDate, roomType);
		for (String s: strList) 
			smodel.addElement(s);
	}
	
	/**
	 * Called by the model to populate the JList with available rooms
	 */
	public void stateChanged(ChangeEvent arg0) {
		smodel.clear();
		populateList();
	}
}

