package actions.main.form;

import gui.main.form.MainFrame;
import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer.Form;

import javax.swing.JTextField;

import databaseModel.DatabaseColumnModel;
import databaseModel.DatabaseTableModel;

public class ZoomAction implements ActionListener
{
	private DatabaseTableModel tableModel;
	private JTextField txtField;
	private DatabaseColumnModel columnModel;

	public ZoomAction(DatabaseTableModel description, JTextField textField, DatabaseColumnModel cdesc) {
		this.tableModel = description;
		this.txtField = textField;
		this.columnModel = cdesc;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		GenericDialog dialog = new GenericDialog(MainFrame.getInstance(), tableModel, txtField, columnModel.getCodeInParent());
		dialog.setVisible(true);
	}
}
