package gui.standard.form;

import gui.main.form.MainFrame;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import actions.main.form.ZoomAction;
import databaseModel.DatabaseColumnModel;
import databaseModel.DatabaseTableModel;

public class InfoPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	private ArrayList<JButton> zoomButtons = new ArrayList<JButton>();
	private ArrayList<ButtonGroup> radioButtons = new ArrayList<ButtonGroup>();
	private Vector<DatabaseColumnModel> columnModels = new Vector<DatabaseColumnModel>();

	
	private static int FIELDLENGTH = 8;
	
	public InfoPanel(DatabaseTableModel tables, GenericDialog dialog)
	{
		this.setColumnModels(tables.getcolumnsModel());
		this.setLayout(new MigLayout("gapx 15px"));				
		for(DatabaseColumnModel column : tables.getcolumnsModel())
		{
			String labelText = column.isNullable()? column.getLabel() : column.getLabel() + "*" ;
			JLabel labelName = new JLabel(labelText);
			this.add(labelName);
			JTextField textField;
			
			if(isDateField(column))
			{
				textField = new JTextField(FIELDLENGTH);
				textField.setName(column.getCode());
				textField.setToolTipText("Validan format je yyyy/MM/dd");
				textFields.add(textField);
								
				if (column.getTableParent() != null)
				{
					String parent = column.getTableParent();
					JButton zoomBtn = new JButton("...");
					for(DatabaseTableModel tableModel : MainFrame.getInstance().getTableModels())
					{
						if(tableModel.getCode().contains(parent))
						{
							zoomBtn.addActionListener(new ZoomAction(tableModel, textField, column, dialog));
						}
					}
					
					zoomButtons.add(zoomBtn);
					this.add(textField);
					this.add(zoomBtn,"wrap, w 25!, h 22!");
				}
				
				else {
					
					this.add(textField,"wrap");
				}
	
			}
			else if(column.getType().equalsIgnoreCase("bit"))
			{
				ButtonGroup btnGroup = new ButtonGroup();
				JRadioButton rdbBtnYes = new JRadioButton();
				rdbBtnYes.setText("Da");
				JRadioButton rdbBtnNo = new JRadioButton();
				rdbBtnNo.setText("Ne");
				rdbBtnYes.setName(column.getCode());
				rdbBtnNo.setName(column.getCode());
				btnGroup.add(rdbBtnYes);
				btnGroup.add(rdbBtnNo);
				radioButtons.add(btnGroup);
				this.add(rdbBtnYes);
				this.add(rdbBtnNo, "wrap");
			}
			else
			{
				textField = new JTextField(FIELDLENGTH);
				textField.setName(column.getCode());
				if(column.getType().equalsIgnoreCase("char"))
				{
					textField.setToolTipText("Polje mora imati :" + column.getLength() + " karaktera.");
				}
				else
				{
					textField.setToolTipText("Maksimalna duzina je :" + column.getLength()+ " karaktera.");
				}
				
				textFields.add(textField);
				
				if (column.getTableParent() != null)
				{
					String parent = column.getTableParent();
					JButton zoomBtn = new JButton("...");
					for(DatabaseTableModel tableModel : MainFrame.getInstance().getTableModels())
					{
						if(tableModel.getCode().contains(parent))
						{
							zoomBtn.addActionListener(new ZoomAction(tableModel, textField, column, dialog));
						}
					}
					
					zoomButtons.add(zoomBtn);
					this.add(textField);
					this.add(zoomBtn,"wrap, w 25!, h 22!");
				}
				
				else {
					
					this.add(textField,"wrap");
				}
			}
				
		}
	}
	
	public JTextField getField(String code) 
	{
		for(int i = 0; i < textFields.size(); i++) 
		{
			if(textFields.get(i).getName().equals(code)) 
			{
				return textFields.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Check if columns type is date..
	 * @param column
	 * @return
	 */
	private boolean isDateField(DatabaseColumnModel column)
	{
		if(column.getType().equalsIgnoreCase("datetime") || column.getType().equalsIgnoreCase("date"))
		{
			return true;
		}
		else
		{
			return false;
		}	
	}

	public ArrayList<JTextField> getTextFields() {
		return textFields;
	}

	public void setTextFields(ArrayList<JTextField> textFields) {
		this.textFields = textFields;
	}

	public ArrayList<JButton> getZoomButtons() {
		return zoomButtons;
	}

	public void setZoomButtons(ArrayList<JButton> zoomButtons) {
		this.zoomButtons = zoomButtons;
	}

	public ArrayList<ButtonGroup> getRadioButtons() {
		return radioButtons;
	}

	public void setRadioButtons(ArrayList<ButtonGroup> radioButtons) {
		this.radioButtons = radioButtons;
	}

	public Vector<DatabaseColumnModel> getColumnModels() {
		return columnModels;
	}

	public void setColumnModels(Vector<DatabaseColumnModel> columnModels) {
		this.columnModels = columnModels;
	}
}
