package gui.standard.form.concrete;

import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import actions.standard.form.GenAndExpNalogAction;
import databaseModel.DatabaseTableModel;
import gui.standard.form.GenericDialog;

public class RacunDialog extends GenericDialog {
	
	public RacunDialog(Window parent, DatabaseTableModel databaseTableModel, JTextField field, String code) {
		super(parent,databaseTableModel,field,code);
	}

	public RacunDialog(Window parent, DatabaseTableModel databaseTableModel) 
	{
		super(parent,databaseTableModel);
	}

	public RacunDialog() 
	{
		super();
	}
	
	@Override
	protected void init(DatabaseTableModel tableModel) {
		super.init(tableModel);
		
		JButton b = new JButton();
		b.setIcon(new ImageIcon(getClass().getResource("/img/export.png")));
		b.addActionListener(new GenAndExpNalogAction(this)); 
		
		toolbar.addSeparator();
		toolbar.add(b);
		
	}

}
