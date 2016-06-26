package actions.main.form;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import gui.standard.form.GenericDialog;

public class GenericDialogActions 
{
	private GenericDialog dialog;
	
	public GenericDialogActions(GenericDialog dialog)
	{
		this.dialog = dialog;
	}
	
	/**
	 * Clear all text fields.
	 */
	public void clearAllTextFields()
	{
		for(JTextField txtField : dialog.getInfoPanel().getTextFields())
		{
			txtField.setText("");
		}
	}

	/**
	 * Fill all text fields.
	 */
	public void fillAllTextFields(ArrayList<String> valueLists)
	{
		int i = 0;
		for(JTextField txtField : dialog.getInfoPanel().getTextFields())
		{
			try
			{
				txtField.setText(valueLists.get(i));
				i++;
			}
			catch(Exception e)
			{
				txtField.setText("");
			}
			
		}
	}
	
		
		
	
	

}
