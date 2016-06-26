package actions.main.form;

import gui.main.form.MainFrame;
import gui.standard.form.GenericDialog;
import gui.standard.form.concrete.RacunDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import net.sourceforge.jtds.jdbc.ColInfo;
import database.DBConnection;
import databaseModel.DatabaseColumnModel;
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
		
		if(databaseTableModel.getCode().equals("RACUNI"))
		{
			GenericDialog dialogRacuni = new RacunDialog(MainFrame.getInstance(),databaseTableModel);
			dialogRacuni.setVisible(true);
		}
		else
		{
			GenericDialog dialog = new GenericDialog(MainFrame.getInstance(), databaseTableModel);
			dialog.setVisible(true);
		}
		
		
	}
	
}
