package gui.standard.form;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TablePane extends JScrollPane 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2018883172112749430L;
	private JTable tblGrid = new JTable();

	public TablePane(JTable table) {

		this.tblGrid = table;
		this.setViewportView(tblGrid);

		
	}

}