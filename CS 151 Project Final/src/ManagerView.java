
import java.awt.BorderLayout;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

/**
 * Display the manager view when manager logs in. Manager can view the reserved rooms and available rooms by clicking on a specific day.
 * @author yitong
 *
 */
public class ManagerView extends JPanel{
	private Model mainModel;
	/**
	 * Construct a manager view with left panel (JTable Calendar), middle panel (JTextArea) and right panel (JButton: load, save and quit). And actionListeners
	 * are attached to the JTable cell and buttons.
	 * @param mFrame: a instance of JFrame
	 * @param m: an instance of Model
	 */
	public ManagerView(JFrame mFrame, Model m) {
		mFrame.setSize(1000, 500);
		mainModel = m;
		GregorianCalendar now = new GregorianCalendar();
		int currentYear = now.get(Calendar.YEAR);
		final TableModel tableModel = new TableModel(now);
		final DefaultTableModel model = tableModel; 
		
		final JTable table = new JTable(model);	
		table.setSize(420, 420);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);	
		
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		
		final String[] monthStrings = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		final JComboBox<String> monthList = new JComboBox<String>(monthStrings);
		monthList.setSelectedIndex(now.get(Calendar.MONTH));  

		SpinnerModel spinner = new SpinnerNumberModel(currentYear, currentYear - 100, currentYear + 100, 1);
		final JSpinner yearList = new JSpinner(spinner);
		JSpinner.NumberEditor e = new JSpinner.NumberEditor(yearList, "####");
		yearList.setEditor(e);
		
		
		JPanel comboPanel = new JPanel();
		comboPanel.add(monthList);
		comboPanel.add(yearList);
		final JPanel leftPanel = new JPanel();

		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(comboPanel, BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(table), BorderLayout.SOUTH);

		/**
		 * attach ActionListener to monthList JComboBox, which will change the tableModel by setMonth.
		 */
		monthList.addActionListener(new ActionListener() {	 
			public void actionPerformed(ActionEvent e) {
				String monthName = (String)monthList.getSelectedItem();
				int month = Arrays.asList(monthStrings).indexOf(monthName);
				tableModel.setMonth(month);  
			}	
		});
		
		/**
		 * attach ActionListener to yearList JSpinner, which will change the tableModel by setYear.
		 */
		yearList.addChangeListener(new ChangeListener() {	 
			public void stateChanged(ChangeEvent e) {
				int year = (Integer)yearList.getValue();
				tableModel.setYear(year);			
			}	
		});

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
			
		JLabel label = new JLabel("Room Information");
		final JTextArea textArea = new JTextArea(25, 25);
		textArea.setCaretPosition(0);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		textArea.setEditable(false);
		middlePanel.add(label, BorderLayout.NORTH);
		middlePanel.add(scroll, BorderLayout.SOUTH);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		JButton savequitButton = new JButton("Save and Quit");
		JButton loadButton = new JButton("Load");
		rightPanel.add(loadButton, BorderLayout.NORTH);
		rightPanel.add(savequitButton, BorderLayout.SOUTH);
		
		/**
		 * Attach actionListener to "Save and Quit" button which will save the data structure and exit the program.
		 */
		savequitButton.addActionListener(new ActionListener() {			 
			public void actionPerformed(ActionEvent arg0) {
				mainModel.save();
				System.exit(0);
			}
			
		});
		
		/**
		 * Attach actionListener to "Load" button which will load the data structure to the model.
		 */
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainModel.load();
				
			}
			
		});
		
		/**
		 * Attach TableModelListener to the tableModel, when there is any change in the tableModel, the table will repaint itself.
		 */
		tableModel.attach(new TableModelListener() { 
			public void tableChanged(TableModelEvent e) {
				tableModel.setTableModel(tableModel.getModel());
				table.repaint();	
			} 
			
		});

		/**
		 * Attach MouseListener to the JTable, when a specific day is clicked on, will create a GregorianCalendar object and pass to Model to get
		 * the reserved rooms and available rooms information.
		 */
		table.addMouseListener(new MouseAdapter() { 
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();	
				int year = (Integer) yearList.getModel().getValue();
				int month = monthList.getSelectedIndex();
				int day = Integer.parseInt(((String) tableModel.getValueAt(row, column)));
				GregorianCalendar date = new GregorianCalendar(year, month, day);
				textArea.setText(mainModel.showTakenRooms(date) + "\n" + mainModel.showAvailableRooms(date));
				textArea.repaint();
			}		
		});
		this.add(leftPanel);
		this.add(middlePanel);
		this.add(rightPanel);
	}
}

