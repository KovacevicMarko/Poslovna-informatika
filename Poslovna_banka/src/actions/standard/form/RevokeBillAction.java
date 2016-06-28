package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class RevokeBillAction extends AbstractAction
{
	private JDialog standardForm;
	
	public RevokeBillAction(JDialog standardForm)
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/remove.gif")));
		putValue(SHORT_DESCRIPTION, "Ukinite racun");
		this.standardForm = standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	}

}
