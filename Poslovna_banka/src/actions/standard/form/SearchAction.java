package actions.standard.form;

import gui.standard.form.GenericDialog;
import gui.tablemodel.TableModel;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import util.EnumActiveMode;
import actions.main.form.GenericDialogActions;

public class SearchAction extends AbstractAction 
{

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public SearchAction(JDialog standardForm) 
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/search.gif")));
		putValue(SHORT_DESCRIPTION, "Pretraga");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(standardForm instanceof GenericDialog)
		{
			GenericDialogActions action = new GenericDialogActions((GenericDialog) standardForm);
			((GenericDialog) standardForm).setMode(EnumActiveMode.PRETRAGA);
			action.clearAllTextFields();
			((GenericDialog) standardForm).getInfoPanel().getTextFields().get(0).requestFocus();
			((GenericDialog) standardForm).getInfoPanel().getTextFields().get(0).setEditable(true);
		}
	}
}
