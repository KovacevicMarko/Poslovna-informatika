package gui.tablemodel;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import databaseModel.DatabaseTableModel;

public class Table extends JTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1979301279110278583L;
	private TableModel model;

	public Table(DatabaseTableModel databaseTableModel) 
	{
		
		String[] colNames = new String[databaseTableModel.getcolumnsModel().size()];
		for (int i = 0; i < databaseTableModel.getcolumnsModel().size(); i++) 
		{
			colNames[i] = databaseTableModel.getcolumnsModel().get(i).getCode();
		}
		this.model = new TableModel(databaseTableModel);
		this.setModel(this.model);
		try 
		{
			this.model.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setRowSelectionAllowed(true);
		this.setColumnSelectionAllowed(false);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
			{
				if (e.getValueIsAdjusting())
					return;

			}
		});

		this.setFillsViewportHeight(true);
		this.getTableHeader().setReorderingAllowed(false);

	}

	public void addInTable(Vector rowData) {
		model.addRow(rowData);
	}

}
