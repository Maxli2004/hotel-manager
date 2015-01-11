
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Shinjo Melosh
 *
 */
public class LoginView extends JPanel {
	int West;
	int North;
	JLabel User = new JLabel("UserName:", JLabel.TRAILING);
	JLabel Pass = new JLabel("Password:", JLabel.TRAILING);
	
	CheckboxGroup cbg = new CheckboxGroup();
    Checkbox isManager = new Checkbox("Manager", cbg, false);
    Checkbox isGuest = new Checkbox("Guest", cbg, true);
 
	final JTextField UserName = new JTextField(20);
	final JPasswordField Password = new JPasswordField(20);
	JButton Enter = new JButton("Log in");
	JButton Create = new JButton("Create Account");
	JButton Cancel = new JButton("Cancel");
	JButton CreateAccount = new JButton("Create Account");
	JPanel buttons = new JPanel();
	JPanel LoginPanel = new JPanel();

	/**
	 *Creates a login panel that is displayed within the frame, responsible for handling 
	 *the front end side of account creation and login verification.
	 * 
	 *@param mFrame a reference to the frame so the panel can be changed
	 *@param model a reference to the model so accounts can be created and verified
	 *
	 */
	public LoginView(final JFrame mFrame, final Model model) {
		West = mFrame.getWidth() / 4 - 15;
		North = mFrame.getHeight() / 3;
		LoginPanel = this;
	    this.setLayout(new BorderLayout());
		this.add(setLogin(mFrame, model), BorderLayout.CENTER);		
	}

	/** 
	 * Displays a panel that contains a user name and 2 password fields so that users can create an account
	 * and two buttons one to return to the login screen and one to create an account.
	 * <p>
	 * cancel1 button action listener that returns to login panel.
	 * <p>
	 * ca button action listener that passes user name and password to the model to create an account.
	 * @param JFrame mFrame allows the method to modify the main frame to display create account panel
	 * @param Model model the model so that createAccountInformation can be called and an account can be created
	 * @return the panel that is displayed in the main frame
	 */
	public JPanel setCreate(final JFrame mFrame, final Model model) {
		SpringLayout layout = new SpringLayout();
		JPanel CreateAccount = new JPanel();
		CreateAccount.setLayout(layout);
		final JLabel Pass1 = new JLabel("Verify Password:", JLabel.TRAILING);
		final JPasswordField Password1 = new JPasswordField(20);
		JButton ca = new JButton("Create Account");
		JButton cancel1 = new JButton("Cancel");
		User.add(UserName);
		Pass.add(Password);
		Pass1.add(Password1);
		buttons.setLayout(new FlowLayout());
		buttons.add(cancel1);
		buttons.add(ca);
		cancel1.setBackground(Color.WHITE);
		ca.setBackground(Color.WHITE);
		CreateAccount.add(User);
		User.setLabelFor(UserName);
		CreateAccount.add(UserName);
		layout.putConstraint(SpringLayout.WEST, User, West - 30, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, User, North, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, UserName, West + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, UserName, North, SpringLayout.NORTH, mFrame.getContentPane());

		CreateAccount.add(Pass);
		Pass.setLabelFor(Password);
		CreateAccount.add(Password);
		layout.putConstraint(SpringLayout.WEST, Pass, West - 30, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, Pass, North + 30, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, Password, West + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, Password, North + 30, SpringLayout.NORTH, mFrame.getContentPane());
		
		CreateAccount.add(Pass1);
		Pass1.setLabelFor(Password1);
		CreateAccount.add(Password1);
		layout.putConstraint(SpringLayout.WEST, Pass1, West - 65, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, Pass1, North + 60, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, Password1, West + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, Password1, North + 60, SpringLayout.NORTH, mFrame.getContentPane());
		
		CreateAccount.add(buttons);
		layout.putConstraint(SpringLayout.WEST, buttons, mFrame.getWidth() / 4 + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, buttons, North + 110, SpringLayout.NORTH, mFrame.getContentPane());
		
		CreateAccount.add(isManager);
		// add by yitong
		CreateAccount.add(isGuest);
		//layout.putConstraint(SpringLayout.WEST, isManager, pad, e2, c2);
		layout.putConstraint(SpringLayout.WEST, isManager, West + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, isManager, North + 90, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, isGuest, West + 150, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, isGuest, North + 90, SpringLayout.NORTH, mFrame.getContentPane());
		//Style
		 LineBorder roundedLineBorder = new LineBorder(Color.BLACK, 2, false);  
		 TitledBorder roundedTitledBorder = new TitledBorder(roundedLineBorder, "Create account");
		 roundedTitledBorder.setTitleColor(new Color(0, 162, 232));
		 CreateAccount.setBorder(roundedTitledBorder);
		 CreateAccount.setBackground(Color.WHITE); 
		 User.setForeground(Color.BLACK);
	     Pass.setForeground(Color.BLACK);
		       
		cancel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttons.removeAll();
				LoginPanel.removeAll();
				LoginPanel.add(setLogin(mFrame, model));
				mFrame.setContentPane(LoginPanel);
				mFrame.revalidate();
			}
		});

		ca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!String.valueOf(Password.getPassword()).equals(String.valueOf(Password1.getPassword()))){
					User.setForeground(Color.BLACK);
					Pass.setForeground(Color.RED);
					Pass1.setForeground(Color.RED);
				} else if(model.createAccountInformation(UserName.getText(), String.valueOf(Password.getPassword()), isManager.getState())){
					buttons.removeAll();
					LoginPanel.removeAll();
					LoginPanel.add(setLogin(mFrame, model));
					mFrame.setContentPane(LoginPanel);
					mFrame.revalidate();
				} else {
					User.setForeground(Color.RED);
					Pass.setForeground(Color.BLACK);
					Pass1.setForeground(Color.BLACK);
				}
			}
		});
		return CreateAccount;
	}
	
	/**
	 * Displays a panel in the main frame that is responsible for handling the front end of logging in
	 * contains a username and password textfield and a login button that when pressed calls 
	 * verifyAccountInformation in the model to see if the given account exists or if the password is incorrect.
	 * <p>
	 * Enter button action listener that passes the user name and password to the model.
	 * <p>
	 * Create button action listener that changes the panel to the create account panel.
	 * @param JFrame mFrame the main frame so that the login panel can be displayed
	 * @param Model model the model so that verifyAccountInformation can be called
	 * @return JPanel the panel that is displayed in the main frame.
	 */
	public JPanel setLogin(final JFrame mFrame, final Model model) {
		final JPanel CreateLogin = new JPanel();
		SpringLayout layout = new SpringLayout();
		JPanel card2 = new JPanel();
		card2.setLayout(new CardLayout());
		CreateLogin.setLayout(layout);
		User.add(UserName);
		Pass.add(Password);
		buttons.setLayout(new FlowLayout());
		Enter.setBackground(Color.WHITE);
		Create.setBackground(Color.WHITE);
		buttons.add(Enter);
		buttons.add(Create);
		CreateLogin.add(User);
		User.setLabelFor(UserName);
		CreateLogin.add(UserName);
		CreateLogin.add(Pass);
		Pass.setLabelFor(Password);
		CreateLogin.add(Password);
		CreateLogin.add(buttons);
		buttons.setBackground(Color.WHITE);
		layout.putConstraint(SpringLayout.WEST, User, West - 30, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, User, North, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, UserName, West + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, UserName, North, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, Pass, West - 30, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, Pass, North + 30, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, Password, West + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, Password, North + 30, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, buttons, mFrame.getWidth() / 4 + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, buttons, North + 80, SpringLayout.NORTH, mFrame.getContentPane());
		
		CreateLogin.add(isManager);
		CreateLogin.add(isGuest);
		layout.putConstraint(SpringLayout.WEST, isManager, West + 45, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, isManager, North + 60, SpringLayout.NORTH, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, isGuest, West + 150, SpringLayout.WEST, mFrame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, isGuest, North + 60, SpringLayout.NORTH, mFrame.getContentPane());
		//style
		 LineBorder roundedLineBorder = new LineBorder(Color.BLACK, 2, false);  
		 TitledBorder roundedTitledBorder = new TitledBorder(roundedLineBorder, "Hotel log in");
		 roundedTitledBorder.setTitleColor(new Color(0, 162, 232));
		 CreateLogin.setBorder(roundedTitledBorder);
		 CreateLogin.setBackground(Color.WHITE); 
		 User.setForeground(Color.BLACK);
	     Pass.setForeground(Color.BLACK);
		 
			//Action listeners
		 Enter.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 Person login = model.verifyAccountInformation(UserName.getText(), String.valueOf(Password.getPassword()), isManager.getState());
				 if (login != null) {
					 if(!login.getManager()){
						 mFrame.setContentPane(new GuestView(mFrame, model, login));
						 mFrame.revalidate();
						 } 
					 else {
						 mFrame.setContentPane(new ManagerView(mFrame, model));
					     mFrame.revalidate();
					     }
					 } 
				 else {
					 User.setForeground(Color.RED);
					 Pass.setForeground(Color.RED);
					 }
				 }
			});
		 
		 Create.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 buttons.removeAll();
				 LoginPanel.removeAll();
				 LoginPanel.add(setCreate(mFrame, model));
				 mFrame.setContentPane(LoginPanel);
				 mFrame.revalidate();
				 }
			 });
		 return CreateLogin;
		 }
	}
