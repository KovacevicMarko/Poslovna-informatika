package actions.main.form;

import gui.main.form.MainFrame;
import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextField;

import databaseModel.DatabaseColumnModel;
import databaseModel.DatabaseTableModel;

public class ZoomAction implements ActionListener
{
	private DatabaseTableModel tableModel;
	private JTextField txtField;
	private DatabaseColumnModel columnModel;
	private JDialog parentDialog;

	public ZoomAction(DatabaseTableModel description, JTextField textField, DatabaseColumnModel cdesc, JDialog parentDialog) 
	{
		this.tableModel = description;
		this.txtField = textField;
		this.columnModel = cdesc;
		this.parentDialog = parentDialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		GenericDialog dialog = new GenericDialog(MainFrame.getInstance(), tableModel, txtField, columnModel.getCodeInParent());
		dialog.setMode(GenericDialog.getMode());
		dialog.setVisible(true);
	}
}
