
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * This is the underlying model for JTable (Calendar).
 * @author yitong
 *
 */

public class TableModel extends DefaultTableModel {
	
	private String[] columnName = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	private GregorianCalendar calendar;
	private ArrayList<TableModelListener> listeners;
	int i = 0, j = 0, k = 0;
	private ArrayList<String> dates; 
	/**
	 * Construct TableModel with GregorianCalendar
	 * @param c: an instance of GregorianCalendar
	 */
	public TableModel(GregorianCalendar c) { 
		calendar = c;
		setTableModel(c);
		listeners = new ArrayList<TableModelListener>();
	}
	
	/**
	 * mutator to change the underlying model of TableModel class
	 * @param c: an instance of GregorianCalendar
	 */
	public void setTableModel(GregorianCalendar c) {
		dates = new ArrayList<String>();

		c.set(Calendar.DAY_OF_MONTH, 1); 
		int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		// date belongs to last month fill with empty string
		for (i = 0; i < firstDayOfWeek; i++) {
			dates.add("");
		}
		// fill in date of current month
		for (j = 0; j < daysInMonth; j++) {
			dates.add(Integer.toString(j+1)); 
		}
		// 5th week if exist
		if ((i+j) % 7 != 0) {
			for (k = 0; k < 7-(i+j) % 7; k++)	{
				dates.add("");
			}
		}
		
	}
	
	@Override
	/**
	 * override the table column with 7
	 */
	public int getColumnCount() {
		return 7;
	}

	
	@Override
	/**
	 * get the row count of the JTable
	 */
	public int getRowCount() {
		return (i+j+k)/7;
	}

	@Override
	/**
	 * return the column number
	 */
	public String getColumnName(int column) {
		return columnName[column];
	}
	
	@Override	
	/**
	 * return the value at specific row and column
	 */
	public Object getValueAt(int row, int column) {   // this part should be overwritten to the actual object used in the reservation system
		return dates.get(row*7+column);
	}
	
	@Override
	/**
	 * enforce the JTable cell is not editable.
	 */
	public boolean isCellEditable(int rowIndex, int mColIndex) {
        return false;
      }
	
	/**
	 * mutator to set the year of the GregorianCalendar object.
	 * @param y: int
	 */
	public void setYear(int y) {
		calendar.set(y, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));	
		for (TableModelListener l : listeners) 
			l.tableChanged(new TableModelEvent(this));
	}
	
	/**
	 * mutator to set the month of the GregorianCalender object
	 * @param m
	 */
	public void setMonth(int m) {
		calendar.set(calendar.get(Calendar.YEAR), m, calendar.get(Calendar.DAY_OF_MONTH));	
		for (TableModelListener l : listeners) 
			l.tableChanged(new TableModelEvent(this));
	}

	/**
	 * allow TableModelListener to be attached to the model
	 * @param l: TableModelListener
	 */
	public void attach(TableModelListener l) {
		listeners.add(l);
	}
	
	/**
	 * return underlying GregorianCalendar object of the tableModel
	 * @return
	 */
	public GregorianCalendar getModel() {
		return calendar;
	}

}

