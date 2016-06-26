package actions.standard.form;

import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import util.EnumActiveMode;
import actions.main.form.GenericDialogActions;

public class RefreshAction extends AbstractAction 
{

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public RefreshAction(JDialog standardForm) 
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/refresh.gif")));
		putValue(SHORT_DESCRIPTION, "Refresh");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		GenericDialogActions action = new GenericDialogActions((GenericDialog) standardForm);
		if(standardForm instanceof JDialog)
		{
			try {
				action.refresh();	
				((GenericDialog) standardForm).setMode(EnumActiveMode.IZMENA);
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
	}
}
