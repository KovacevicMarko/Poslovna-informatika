package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class GenerateReportAction extends AbstractAction 
{
	private JDialog standardForm;
	
	public GenerateReportAction(JDialog standardForm)
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/report.jpg")));
		putValue(SHORT_DESCRIPTION, "Generisi izvestaj");
		this.standardForm=standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
