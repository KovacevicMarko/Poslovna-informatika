package actions.main.form;

import gui.standard.form.GenericDialog;
import gui.tablemodel.TableModel;

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import util.Column;

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
	
	/**
	 * Go to last element in table.
	 */
	public void goLast() 
	{
		int rowCount = dialog.getTable().getModel().getRowCount();
		if (rowCount > 0)
			dialog.getTable().setRowSelectionInterval(rowCount - 1, rowCount - 1);
	}

	/**
	 * Go to first element in table.
	 */
	public void goFirst() 
	{
		int rowCount = dialog.getTable().getModel().getRowCount();
		if (rowCount > 0)
			dialog.getTable().setRowSelectionInterval(0, 0);
	}

	/**
	 * Go to next element in table.
	 */
	public void goNext() 
	{
		int idx = dialog.getTable().getSelectedRow();
		int rowCount = dialog.getTable().getModel().getRowCount();
		if (idx == rowCount - 1)
			dialog.getTable().setRowSelectionInterval(0, 0);
		else
			dialog.getTable().setRowSelectionInterval(idx + 1, idx + 1);
	}

	/**
	 * Go to previous element in table.
	 */
	public void goPrevious() 
	{
		int idx = dialog.getTable().getSelectedRow();
		int rowCount = dialog.getTable().getModel().getRowCount();
		if (idx == 0)
			dialog.getTable().setRowSelectionInterval(rowCount - 1, rowCount - 1);
		else
			dialog.getTable().setRowSelectionInterval(idx - 1, idx - 1);
	}
	
	/**
	 * Refersh table and clear all fields.
	 * @throws SQLException
	 */
	public void refresh() throws SQLException
	{

		TableModel tableModel = (TableModel)dialog.getTable().getModel();
		dialog.getTable().setModel(tableModel);

		try 
		{
			tableModel.fillData();
			sync();
			dialog.getTable().setRowSelectionInterval(0, 0);
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "GRESKA", JOptionPane.ERROR_MESSAGE);			
		} 

	}
	
	private LinkedHashMap<String, Object> getAllValuesFromFieldsForSearch()
	{
		LinkedHashMap<String, Object> retValue = new LinkedHashMap<String, Object>();
		for(Component component : dialog.getInfoPanel().getComponents())
		{
			if(component instanceof JTextField && component != null)
			{
				if(!((JTextField) component).getText().trim().equals(""))
				{
					if(isNumeric(((JTextField) component).getText()))
					{
						retValue.put(((JTextField) component).getName(), Integer.parseInt(((JTextField) component).getText()));
					}
					else
					{
						retValue.put(((JTextField) component).getName(), ((JTextField) component).getText());
					}
				}
			}
			else if(component instanceof JRadioButton && component != null)
			{
				if(((JRadioButton) component).isSelected())
				{
					if(((JRadioButton) component).getText().equalsIgnoreCase("da"))
						retValue.put(((JRadioButton) component).getName(), true);
					else
						retValue.put(((JRadioButton) component).getName(), false);
				}
			}
		}
		
		return retValue;
	}
	
	public String makeSearchQuery()
	{
		LinkedHashMap<String, Object> retValue = getAllValuesFromFieldsForSearch();
		String whereClause = "";
		StringBuffer retVal = new StringBuffer();
		
		for (Map.Entry<String, Object> entry : retValue.entrySet()) {
		    if(retVal.length() > 0)
		    	retVal.append(" AND");
		    
		    retVal.append(' ');
		    retVal.append(dialog.getdatabaseTableModel().getCode());
		    retVal.append('.');
		    retVal.append(entry.getKey());
		    
		    if(entry.getValue() instanceof String)
		    {
		    	retVal.append(" LIKE ");
				retVal.append("'");
				retVal.append(entry.getValue());
				retVal.append("%'");
		    }
		    else if(entry.getValue() instanceof Integer)
		    {
		    	retVal.append(" = ");
				retVal.append("'");
				retVal.append(entry.getValue());
				retVal.append("'");
		    }

		    
		    
		}

				/*
					retVal.append('=');
					if (c.getValue() instanceof Number) {
						retVal.append(c.getValue().toString());
					} else if (c.getValue() instanceof Date) {
						retVal.append("'");
						retVal.append(sdf.format((Date) c.getValue()));
						retVal.append("'");
					} else if (c.getValue() instanceof BigDecimal) {
						retVal.append(c.getValue());
					}
				}
			}
		}
		*/
		String query = "SELECT * FROM " + dialog.getdatabaseTableModel().getCode() + " WHERE " + retVal.toString();
		
		return query;
	}
	
	private boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}

}
