package actions.standard.form;

import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTextField;

import databaseModel.DatabaseColumnModel;

public class CommitAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public CommitAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/commit.gif")));
		putValue(SHORT_DESCRIPTION, "Commit");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(standardForm instanceof GenericDialog)
		{
			ArrayList<JTextField> txtFields=((GenericDialog)standardForm).getInfoPanel().getTextFields();
			Vector<DatabaseColumnModel> columnModels = ((GenericDialog)standardForm).getInfoPanel().getColumnModels();
			
			
		}
		
	}
}

