import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * This program is Hotel reservation system. There are total 20 rooms in this hotel. Among them, room 1-10 are economic rooms costing $100,
 * room 11-20 are luxurious rooms costing $200. There are two different types of users: Guest and Manager. 
 * If the user is guest, after log in, the application shows the hotel reservation views. Guest can either make reservation or view/cancel reservation;
 * If the user is manager, after log in, the application shows the manager view, where manager can select days to check the reserved rooms and
 * available rooms.
 * @author yitong, Shinjo, Max
 * Team Name: Ninja
 *
 */
public class MainMethod {
   public static void main(String[] args){
	   JFrame mFrame = new JFrame();
	   mFrame.setSize(600, 600);
	   final Model model = new Model();
	   model.load(); 
	   
	   LoginView login = new LoginView(mFrame, model);
	   mFrame.setBackground(Color.WHITE);
	   mFrame.add(login);
	   mFrame.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent e) {
			   model.save();
			   }
		   });
	   mFrame.setVisible(true);
	   mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }  
}


