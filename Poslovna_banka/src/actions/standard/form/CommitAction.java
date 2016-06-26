package actions.standard.form;

import gui.standard.form.GenericDialog;
import gui.tablemodel.TableModel;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import actions.main.form.GenericDialogActions;
import util.EnumActiveMode;
import databaseModel.DatabaseColumnModel;

public class CommitAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public CommitAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/commit.gif")));
		putValue(SHORT_DESCRIPTION, "Commit");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(standardForm instanceof GenericDialog)
		{
			GenericDialogActions action = new GenericDialogActions((GenericDialog)standardForm);
			if(((GenericDialog)standardForm).getMode() == EnumActiveMode.DODAVANJE)
			{
				addNewRowToTable(action);
				action.clearAllTextFields();
				((GenericDialog) standardForm).setMode(EnumActiveMode.DODAVANJE);
			}
			else if(((GenericDialog)standardForm).getMode() == EnumActiveMode.IZMENA)
			{
				updateRowInTalbe(action);
			}else
			{
				String query = action.makeSearchQuery();
				TableModel tableModel = (TableModel)((GenericDialog) standardForm).getTable().getModel();
				try 
				{
					tableModel.fillSearchData(query);
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	private void addNewRowToTable(GenericDialogActions action)
	{	
		try
		{	
			TableModel tableModel = (TableModel)((GenericDialog) standardForm).getTable().getModel();
			int index = tableModel.insertRow(action.getAllValuesFromFields());
			((GenericDialog) standardForm).getTable().setRowSelectionInterval(index, index);		
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	private void updateRowInTalbe(GenericDialogActions action)
	{
		try
		{	
			TableModel tableModel = (TableModel)((GenericDialog) standardForm).getTable().getModel();
			int index = ((GenericDialog) standardForm).getTable().getSelectedRow();
			tableModel.updateRow(index, action.getAllValuesFromFields());
			((GenericDialog) standardForm).getTable().setRowSelectionInterval(index, index);
			((GenericDialog) standardForm).refresh();
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "GRESKA", JOptionPane.ERROR_MESSAGE);	
		}
	}
}

