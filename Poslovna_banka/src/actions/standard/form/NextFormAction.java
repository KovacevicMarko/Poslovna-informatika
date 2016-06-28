package actions.standard.form;

import gui.main.form.MainFrame;
import gui.standard.form.GenericDialog;
import gui.tablemodel.TableModel;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import database.DBConnection;
import databaseModel.DatabaseColumnModel;
import databaseModel.DatabaseTableModel;


public class NextFormAction extends AbstractAction 
{

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	private String tableName;
	
	public NextFormAction(JDialog standardForm, String tableName) 
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/nextform.gif")));
		putValue(SHORT_DESCRIPTION, "Sledeća forma");
		this.standardForm  = standardForm;
		this.tableName = tableName;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for(DatabaseTableModel tableModel : gui.main.form.MainFrame.getInstance().getTableModels())
		{
			if(tableModel.getCode().equalsIgnoreCase(tableName))
			{
				int selectedRow = ((GenericDialog)standardForm).getTable().getSelectedRow();
				if(selectedRow > -1)
				{
					GenericDialog genDialog = new GenericDialog(MainFrame.getInstance(), tableModel);
					String tableCode = ((GenericDialog) standardForm).getDatabaseTableModel().getCode();
					Vector<DatabaseColumnModel> columnModels = DBConnection.getDatabaseWrapper().getColumnModelByTableCode(tableCode);

					int i = 0;
					for(DatabaseColumnModel column : columnModels)
					{
						
						boolean primaryKey = DBConnection.getDatabaseWrapper().isPrimaryKey(tableCode, column.getCode());
						boolean foreignKey = DBConnection.getDatabaseWrapper().isForeignKey(tableCode, column.getCode());
						
						if(primaryKey && foreignKey)
						{
							String columnName = DBConnection.getDatabaseWrapper().PrimaryKeyColumnname(tableCode, column.getCode());
							String key = (String) ((GenericDialog) standardForm).getTable().getValueAt(selectedRow, i);

							genDialog = filterNextDialogByKey(key, genDialog, columnName);
							genDialog.setVisible(true);
							break;
						}
						i++;
					}
				}
				else
				{
					GenericDialog genDialog = null;
					genDialog = new GenericDialog(MainFrame.getInstance(), tableModel);
					genDialog.setVisible(true);
					break;
				}
			}
			
		}

	}
	
	/**
	 * Filter Child dialog by selected row in parent.
	 * @param key
	 * @param dialog
	 * @param columnName
	 * @return
	 */
	private GenericDialog filterNextDialogByKey(String key, GenericDialog dialog, String columnName)
	{
		GenericDialog retDialog = dialog;
		for(int i=0; i<((GenericDialog)retDialog).getTable().getRowCount(); i++)
		{
			Vector<DatabaseColumnModel> columns = DBConnection.getDatabaseWrapper().getColumnModelByTableCode(retDialog.getDatabaseTableModel().getCode());
			int columnNumber = 0;
			for(DatabaseColumnModel column : columns)
			{
				String value = (String) retDialog.getTable().getValueAt(i, columnNumber);
				if(column.getCode().contains(columnName))
				{
					if(value!=null && !value.equalsIgnoreCase(key))
					{
						TableModel dtm = (TableModel) retDialog.getTable().getModel();
						dtm.removeRow(i);
						i--;
						break;
					}
				}
				columnNumber++;
			}
		}
		
		return retDialog;
	}
}
