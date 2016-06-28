package gui.standard.form.concrete;

import gui.standard.form.GenericDialog;

import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import databaseModel.DatabaseTableModel;

public class RacunDialog extends GenericDialog {
	
	public RacunDialog(Window parent, DatabaseTableModel databaseTableModel, JTextField field, String code) {
		super(parent,databaseTableModel,field,code);
		setModal(false);
	}

	public RacunDialog(Window parent, DatabaseTableModel databaseTableModel) 
	{
		super(parent,databaseTableModel);
		setModal(false);
	}

	public RacunDialog() 
	{
		super();
		setModal(false);
		
	}
	
	@Override
	protected void init(DatabaseTableModel tableModel) {
		super.init(tableModel);
	}

}
