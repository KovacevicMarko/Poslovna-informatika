package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import actions.main.form.GenericDialogActions;
import gui.dialogs.NalogDialog;
import gui.standard.form.concrete.RacunDialog;
import gui.tablemodel.Table;
import modelFromXsd.NalogZaPlacanje;

public class ExportNalogAction extends AbstractAction {
	

	private JDialog standardForm;
	
	public ExportNalogAction(JDialog standardForm)
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/export.png")));
		putValue(SHORT_DESCRIPTION, "Exportuj nalog");
		this.standardForm = standardForm;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		RacunDialog thisDialog = (RacunDialog) standardForm;
		Table thisTable = thisDialog.getTable();
		int selectedRow =  thisTable.getSelectedRow();
		GenericDialogActions helperMethods = new GenericDialogActions(thisDialog);
		
		boolean racunVazi =  thisTable.getValueAt(selectedRow, 5).toString().equalsIgnoreCase("da")? true : false;
		
		NalogZaPlacanje nalog = new NalogZaPlacanje();
		
		if(racunVazi){

			String racunId = thisTable.getValueAt(selectedRow, 0).toString();
			String valutaId = thisTable.getValueAt(selectedRow, 2).toString();
			String klijentId = thisTable.getValueAt(selectedRow, 3).toString();			
			nalog.setDuznik(klijentId);
			nalog.setOznakaValute(valutaId);
			nalog.setRacunDuznika(racunId);			
			//XmlManager.generateDocument(nalog);
			
			NalogDialog nDialog = new NalogDialog(nalog,false);
			nDialog.setVisible(true);
		}
		
	}

	
	
}
