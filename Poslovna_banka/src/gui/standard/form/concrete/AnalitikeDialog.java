package gui.standard.form.concrete;

import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JTextField;

import actions.standard.form.ImportNalogAction;
import databaseModel.DatabaseTableModel;
import gui.standard.form.GenericDialog;

public class AnalitikeDialog extends GenericDialog {
	
	public AnalitikeDialog(Window parent, DatabaseTableModel databaseTableModel, JTextField field, String code) {
		super(parent,databaseTableModel,field,code);
		setModal(false);
	}

	public AnalitikeDialog(Window parent, DatabaseTableModel databaseTableModel) 
	{
		super(parent,databaseTableModel);
		setModal(false);
	}

	public AnalitikeDialog() 
	{
		super();
		setModal(false);
		
	}
	
	@Override
	protected void init(DatabaseTableModel tableModel) {
		super.init(tableModel);
		
		JButton b = new JButton(new ImportNalogAction(this));
		//b.setIcon(new ImageIcon(getClass().getResource("/img/export.png")));
	
		
		toolbar.addSeparator();
		toolbar.add(b);
		
	}
	
	
}
