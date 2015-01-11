
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Shinjo
 *
 */

public class ViewCancelReservation extends JPanel implements ChangeListener {
	JFrame mFrame;
	Model model;
	DefaultListModel<String> smodel = new DefaultListModel<String>();
	ArrayList<Room> res ;
    Person person;
    
    /**
     * Displays the reservations belonging to the person that is logged in
     * @param JFrame mFrame the main frame so that the panel can be changed
     * @param Model model the model so that the reservation list can be populated and reservations can be deleted
     * @param Person person the identity of the person who made the reservation so that the reservations can be retrieved
     */
	public ViewCancelReservation(final JFrame mFrame, final Model model, final Person person) {
		this.mFrame = mFrame;
		this.model = model;
		this.person = person;
		this.setLayout(new BorderLayout());		
		
		this.setBackground(Color.WHITE);
		LineBorder roundedLineBorder = new LineBorder(Color.BLACK, 2, false);
		TitledBorder roundedTitledBorder = new TitledBorder(roundedLineBorder, "View/Cancel Reservation");
		roundedTitledBorder.setTitleColor(new Color(0, 162, 232));
		this.setBorder(roundedTitledBorder);
	
		final JList list = new JList(smodel);		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSize(mFrame.getWidth() / 4, 100);
	    JScrollPane jp = new JScrollPane(list);
	    jp.setBorder(BorderFactory.createMatteBorder(5, 5,5, 5, Color.DARK_GRAY));
	    JButton Remove = new JButton("Remove");
	    JButton Return = new JButton("Return");
		this.add(jp, BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(Remove);
		buttons.add(Return);
		buttons.setBackground(Color.WHITE);
	    this.add(buttons, BorderLayout.EAST);
		
	    loadReservations();	    
		 
		Remove.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent arg0) {
	    		Room r = null;
	    		try {
	    			r =res.get(res.size() - (list.getSelectedIndex() + 1));
	    			GregorianCalendar start = new GregorianCalendar();
	    			start.setTime(r.getStartDay());
	    			model.deleteReservation(r.getRoom(), start);
	    			model.save();
	    		}
	    		catch (Exception e) {
	    			JOptionPane.showMessageDialog(mFrame, "Please select a room!", "Reminder", JOptionPane.PLAIN_MESSAGE);	
	    		}		
			}
	    });
		
	    Return.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				mFrame.setContentPane(new GuestView(mFrame, model, person));
				mFrame.revalidate();	
			}
	    });	  
	}
	
	/**
	 * loads reservations that belong to user from the model and adds them to the JList
	 */
	public void loadReservations(){
		res = model.findRoomBelongingTo((Guest) person);
	    for(Room reservation: res){
		   smodel.add(0,reservation.reservationTitle());
	   }
	}
	
	/**
	 * Listens for notifications from the model
	 */
	public void stateChanged(ChangeEvent arg0) {
		smodel.clear();
		loadReservations();
	}
}
