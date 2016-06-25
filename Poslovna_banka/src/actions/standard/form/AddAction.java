package actions.standard.form;

import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import util.EnumActiveMode;

public class AddAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public AddAction(JDialog standardForm) 
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/add.gif")));
		putValue(SHORT_DESCRIPTION, "Dodavanje");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(standardForm instanceof GenericDialog)
		{
			((GenericDialog) standardForm).setMode(EnumActiveMode.DODAVANJE);
		}
			
	}
	
}
