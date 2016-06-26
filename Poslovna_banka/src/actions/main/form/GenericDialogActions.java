package actions.main.form;

import gui.standard.form.GenericDialog;

import java.awt.Component;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
	
	/**
	 * Get all values from text fields.
	 * @return LinkedHashMap with key text field name and value text field text.
	 */
	public LinkedHashMap<String, String> getAllValuesFromFields()
	{
		LinkedHashMap<String, String> retValue = new LinkedHashMap<String, String>();
		for(Component component : dialog.getInfoPanel().getComponents())
		{
			if(component instanceof JTextField && component != null)
			{
				if(((JTextField) component).getText().trim().equals(""))
				{
					retValue.put(((JTextField) component).getName(),null);
				}else
				{
					retValue.put(((JTextField) component).getName(), ((JTextField) component).getText());
				}
				
			}
			else if(component instanceof JRadioButton && component != null)
			{
				if(((JRadioButton) component).isSelected())
				{
					if(((JRadioButton) component).getText().equalsIgnoreCase("da"))
						retValue.put(((JRadioButton) component).getName(), "true");
					else
						retValue.put(((JRadioButton) component).getName(), "false");
				}
			}
		}
		
		return retValue;
		
	}
	
	public void sync() 
	{
		int index = dialog.getTable().getSelectedRow();
		if (index < 0)
		{
			clearAllTextFields();
			return;
		}
		ArrayList<String> valueList = new ArrayList<>();
		
		for(int i = 0; i < dialog.getTable().getModel().getColumnCount(); i++)
		{
			valueList.add((String) dialog.getTable().getModel().getValueAt(index, i));	
		}
		
		fillAllTextFields(valueList);
		dialog.getInfoPanel().getTextFields().get(0).setEditable(false);
	}

}
