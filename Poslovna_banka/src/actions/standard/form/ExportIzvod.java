package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class ExportIzvod extends AbstractAction{
	
	private JDialog standardForm;
	
	public ExportIzvod(JDialog dialog) {
		// TODO Auto-generated constructor stub
		standardForm = dialog;
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/export.png")));
		putValue(SHORT_DESCRIPTION, "Export izvoda");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
