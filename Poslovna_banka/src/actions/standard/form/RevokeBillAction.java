package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import actions.main.form.GenericDialogActions;
import database.DBQueryManager;
import gui.dialogs.NalogDialog;
import gui.standard.form.GenericDialog;
import gui.standard.form.concrete.RacunDialog;
import gui.tablemodel.Table;
import modelFromXsd.NalogZaPlacanje;

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
		RacunDialog thisDialog = (RacunDialog) standardForm;
		Table thisTable = thisDialog.getTable();
		int selectedRow =  thisTable.getSelectedRow();
		GenericDialogActions helperMethods = new GenericDialogActions(thisDialog);
		
		boolean racunVazi =  thisTable.getValueAt(selectedRow, 5).equals("1")? true : false;
		
		NalogZaPlacanje nalog = new NalogZaPlacanje();
		
		if(true){

			String racunId = thisTable.getValueAt(selectedRow, 0).toString();
			String valutaId = thisTable.getValueAt(selectedRow, 2).toString();
			String klijentId = thisTable.getValueAt(selectedRow, 3).toString();
			String pibBanke = thisTable.getValueAt(selectedRow, 1).toString();
			
			nalog.setDuznik(klijentId);
			nalog.setOznakaValute(valutaId);
			nalog.setRacunDuznika(racunId);			
			//XmlManager.generateDocument(nalog);
			
			NalogDialog nDialog = new NalogDialog(nalog);
			nDialog.setVisible(true);
		}
		
	}

}
