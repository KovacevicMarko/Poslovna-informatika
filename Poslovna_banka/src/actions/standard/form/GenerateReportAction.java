package actions.standard.form;

import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

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
		int index = ((GenericDialog)standardForm).getTable().getSelectedRow();
		if(index < 0)
		{
			JOptionPane.showMessageDialog(null, "Morate selektovati red u tabeli.", "Greska", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String id = (String) ((GenericDialog)standardForm).getTable().getModel().getValueAt(index, 0);
		JOptionPane.showMessageDialog(null, id, "Greska", JOptionPane.ERROR_MESSAGE);

			

		
	}

}
