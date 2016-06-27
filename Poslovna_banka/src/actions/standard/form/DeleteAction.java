package actions.standard.form;

import exceptionHandler.SqlExceptionHandler;
import gui.standard.form.GenericDialog;
import gui.tablemodel.TableModel;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class DeleteAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public DeleteAction(JDialog standardForm) 
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/remove.gif")));
		putValue(SHORT_DESCRIPTION, "Brisanje");
		this.standardForm=standardForm;	
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(standardForm instanceof GenericDialog)
		{
			JTable table = ((GenericDialog) standardForm).getTable();
			int index = table.getSelectedRow();

			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Morate selektovati red koji zelite da obrisete!", "Greska",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if((JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da obrisete selektovani red? ",
				"", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION))
			{
				int newIndex = index;
				if (index == table.getModel().getRowCount() - 1)
					newIndex--;
				try 
				 {
					TableModel tableModel = (TableModel) table.getModel();
					tableModel.deleteRow(index);
					if (table.getModel().getRowCount() > 0)
					{
						table.setRowSelectionInterval(newIndex, newIndex);

					}
				} 
				catch (SQLException ex) 
				{
					String tableCode = ((GenericDialog) standardForm).getDatabaseTableModel().getCode();
					JOptionPane.showMessageDialog(null, SqlExceptionHandler.getHandledMessage(tableCode, ex.getMessage()), "Greska", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
