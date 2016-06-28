package actions.standard.form;

import gui.dialogs.DatumDijalog;
import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DatumAction extends AbstractAction{
	private DatumDijalog dialog;
	private JDialog parent;

	public DatumAction(JDialog parent) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/report.jpg")));
		putValue(SHORT_DESCRIPTION, "Generisi izvestaj");
		this.parent=parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(((GenericDialog)parent).getTable().getSelectedRow() < 0)
		{
			JOptionPane.showMessageDialog(null, "Morate selektovati red.");
			return;
		}
		DatumDijalog d=new DatumDijalog(((GenericDialog)parent).getTable().getSelectedRow(), parent);
		d.setModal(true);
		d.setVisible(true);
		
		
	}

}
