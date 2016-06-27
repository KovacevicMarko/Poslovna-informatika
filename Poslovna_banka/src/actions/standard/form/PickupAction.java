package actions.standard.form;

import gui.standard.form.GenericDialog;
import gui.tablemodel.TableModel;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTextField;

import util.EnumActiveMode;

import com.sun.jmx.snmp.Timestamp;

import databaseModel.DatabaseColumnModel;

public class PickupAction extends AbstractAction 
{

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public PickupAction(JDialog standardForm) 
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/zoom-pickup.gif")));
		putValue(SHORT_DESCRIPTION, "Zoom pickup");
		this.standardForm = standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(standardForm instanceof GenericDialog) 
		{
			GenericDialog genericDialog = ((GenericDialog)standardForm);
			TableModel model = (TableModel) genericDialog.getTable().getModel();
			Vector<DatabaseColumnModel> columnModels = genericDialog.getInfoPanel().getColumnModels();
			JTextField textField = genericDialog.getField();
			Vector vector = model.getDataVector();
			String code = genericDialog.getCode();
			int i =0;
			for(DatabaseColumnModel columnModel : columnModels)
			{
				if(columnModel.getCode().equalsIgnoreCase(code))
				{
					Object obj = ((Vector)vector.get(genericDialog.getTable().getSelectedRow())).get(i);
					if(obj instanceof String) 
					{
						textField.setText((String)obj);
					}
				}
				i++;
			}	
			((GenericDialog) standardForm).setMode(GenericDialog.getMode());
			standardForm.dispose();
		}
	}
}
