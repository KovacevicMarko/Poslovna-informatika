package actions.standard.form;

import gui.standard.form.GenericDialog;
import gui.tablemodel.TableModel;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import actions.main.form.GenericDialogActions;
import util.EnumActiveMode;
import databaseModel.DatabaseColumnModel;
import exceptionHandler.SqlExceptionHandler;

public class CommitAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	private GenericDialogActions action;
	
	public CommitAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/commit.gif")));
		putValue(SHORT_DESCRIPTION, "Commit");
		this.standardForm=standardForm;
		this.action = new GenericDialogActions((GenericDialog)standardForm);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(standardForm instanceof GenericDialog)
		{
			 
			if(GenericDialog.getMode() == EnumActiveMode.DODAVANJE)
			{
				if(isFizickoOrPravno(((GenericDialog)standardForm).getDatabaseTableModel().getCode()))
				{
					addNewClientRowToTable();
				}
				else
				{
					addNewRowToTable();
				}
				action.clearAllTextFields();
				((GenericDialog) standardForm).setMode(EnumActiveMode.DODAVANJE);
			}
			else if(GenericDialog.getMode() == EnumActiveMode.IZMENA)
			{
				if(((GenericDialog)standardForm).getTable().getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Morate selektovati jedan red prilikom izmene!", "Greska", JOptionPane.ERROR_MESSAGE);
				}
					
				updateRowInTable();
			}
			else
			{
				String query = action.makeSearchQuery();
				TableModel tableModel = (TableModel)((GenericDialog) standardForm).getTable().getModel();
				try 
				{
					tableModel.fillSearchData(query);
				} catch (SQLException e1) 
				{
					String tableCode = ((GenericDialog) standardForm).getDatabaseTableModel().getCode();
					JOptionPane.showMessageDialog(null, SqlExceptionHandler.getHandledMessage(tableCode, e1.getMessage()), "Greska", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}
	
	private void addNewRowToTable()
	{	
		try
		{	
			TableModel tableModel = (TableModel)((GenericDialog) standardForm).getTable().getModel();
			int index = tableModel.insertRow(action.getAllValuesFromFields());
			((GenericDialog) standardForm).getTable().setRowSelectionInterval(index, index);		
		}
		catch(SQLException ex)
		{
			String tableCode = ((GenericDialog) standardForm).getDatabaseTableModel().getCode();
			JOptionPane.showMessageDialog(null, SqlExceptionHandler.getHandledMessage(tableCode, ex.getMessage()), "Greska", JOptionPane.ERROR_MESSAGE);	
		}
	}
	/**
	 * Add new row in ClientTable
	 */
	private void addNewClientRowToTable()
	{
		try
		{	
			TableModel tableModel = (TableModel)((GenericDialog) standardForm).getTable().getModel();
			LinkedHashMap<String, String> mapForClient = new LinkedHashMap<String, String>();
			
			for (Map.Entry<String, String> entry : action.getAllValuesFromFields().entrySet())
			{
				//Add to client
				if(entry.getKey().toLowerCase().contains("kl"))
				{
					mapForClient.put(entry.getKey(), entry.getValue());
				}
	    
			}
			tableModel.insertInClientRow(mapForClient);
			int index = tableModel.insertRow(action.getAllValuesFromFields());
			
			((GenericDialog) standardForm).getTable().setRowSelectionInterval(index, index);		
		}
		catch(SQLException ex)
		{
			String tableCode = ((GenericDialog) standardForm).getDatabaseTableModel().getCode();
			JOptionPane.showMessageDialog(null, SqlExceptionHandler.getHandledMessage(tableCode, ex.getMessage()), "Greska", JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	private void updateRowInTable()
	{
		try
		{	
			TableModel tableModel = (TableModel)((GenericDialog) standardForm).getTable().getModel();
			int index = ((GenericDialog) standardForm).getTable().getSelectedRow();
			if(index > -1)
			{
				tableModel.updateRow(index, action.getAllValuesFromFields());
				((GenericDialog) standardForm).getTable().setRowSelectionInterval(index, index);
			}
			((GenericDialog) standardForm).refresh();
		}
		catch(SQLException ex)
		{
			String tableCode = ((GenericDialog) standardForm).getDatabaseTableModel().getCode();
			JOptionPane.showMessageDialog(null, SqlExceptionHandler.getHandledMessage(tableCode, ex.getMessage()), "Greska", JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	private boolean isFizickoOrPravno(String tableCode)
	{
		if(tableCode.equalsIgnoreCase("FIZICKA_LICA") || tableCode.equalsIgnoreCase("PRAVNA_LICA"))
			return true;
		return false;
	}
}

