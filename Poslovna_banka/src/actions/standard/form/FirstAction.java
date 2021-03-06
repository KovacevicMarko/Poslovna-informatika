package actions.standard.form;

import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import actions.main.form.GenericDialogActions;

public class FirstAction extends AbstractAction 
{
	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public FirstAction(JDialog standardForm) 
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/first.gif")));
		putValue(SHORT_DESCRIPTION, "Pocetak");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		GenericDialogActions action = new GenericDialogActions((GenericDialog) standardForm);
		action.goFirst();
	}
}
