package actions.standard.form;

import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import actions.main.form.GenericDialogActions;

public class NextAction extends AbstractAction
{

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public NextAction(JDialog standardForm)
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/next.gif")));
		putValue(SHORT_DESCRIPTION, "Sledeci");
		this.standardForm=standardForm;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		GenericDialogActions action = new GenericDialogActions((GenericDialog) standardForm);
		action.goNext();
	}
}
