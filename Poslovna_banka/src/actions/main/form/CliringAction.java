package actions.main.form;

import javax.swing.JOptionPane;

import database.DBQueryManager;

public class CliringAction 
{
	public CliringAction()
	{
		
	}

	public void executeClearing()
	{
		//TODO pozvati proceduru
		DBQueryManager.callKliring();
		JOptionPane.showMessageDialog(null, "Kliring izvrsen!");
	}
}
