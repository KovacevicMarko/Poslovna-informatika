package actions.main.form;

import gui.main.form.MainFrame;
import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import databaseModel.DatabaseTableModel;

public class MenuBarAction implements ActionListener
{
	private DatabaseTableModel databaseTableModel;
	
	public MenuBarAction(DatabaseTableModel model)
	{
		this.databaseTableModel = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//Create new dialog with specified table model.
		GenericDialog dialog = new GenericDialog(MainFrame.getInstance(), databaseTableModel);
		dialog.setVisible(true);
	}

}
