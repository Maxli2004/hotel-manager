
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Shinjo
 *
 */
public class GuestView extends JPanel {
	JFrame mFrame;
	Model model;
	Person person;
	
	/**
	 * Creates a panel that is responsible for displaying the next 2 panels for the user
	 * attaches both potential objects to the model so that they can be notified of changes.
	 * <p>
	 * ViewCancel button that changes the panel to ViewCancelReservation panel.
	 * <p>
	 * MakeReservation button action listener that changes the panel to MakeReservationView panel.
	 * @param JFrame mFrame the main frame so that GuestView can modify the main frame
	 * @param Model model a reference to the model so that it can pass it on to other objects
	 * @param Person person a reference to the person logged in so that reservations can be identified
	 */
	public GuestView(final JFrame mFrame, final Model model, final Person person) {
		this.mFrame = mFrame;
		this.model = model;
		this.person = person;
		LineBorder roundedLineBorder = new LineBorder(Color.BLACK, 2, false);  
		TitledBorder roundedTitledBorder = new TitledBorder(roundedLineBorder, "Choose window");
		roundedTitledBorder.setTitleColor(new Color(0, 162, 232));
	    this.setBorder(roundedTitledBorder);
	    this.setBackground(Color.WHITE); 
	    this.setLayout(new FlowLayout());
	    JButton ViewCancel = new JButton("View/Cancel Reservations");
	    JButton MakeReservation = new JButton("Make Reservation");
	    JPanel buttons= new JPanel();
	    ViewCancel.setBackground(Color.WHITE);
	    MakeReservation.setBackground(Color.WHITE);
		final MakeReservationView mview = new MakeReservationView(mFrame, model, person);
		final ViewCancelReservation vview = new ViewCancelReservation(mFrame, model, person);
		model.attach(mview);
		model.attach(vview);
	    buttons.add(ViewCancel);
	    buttons.add(MakeReservation);
	     
	    buttons.setBackground(Color.WHITE);
	    this.add(buttons);
	    buttons.setBorder(BorderFactory.createEmptyBorder(180, 0, 0,0));
	    
	    /**
	     * attache actionListener to ViewCancel button, which will set the ViewVancel reservation panel to the frame.
	     */
	    ViewCancel.addActionListener(new ActionListener(){  	
			public void actionPerformed(ActionEvent e) {
				mFrame.setContentPane(vview);
				mFrame.revalidate();
			}
	     });
	    
	    /**
	     * attache actionListener to makeReservation button, which will set the makeReservation panel to the frame.
	     */
	     MakeReservation.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					model.load();
					mFrame.setContentPane(mview);
					mFrame.revalidate();
				}
		     });
	}
}

