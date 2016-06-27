package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import actions.main.form.GenericDialogActions;
import gui.dialogs.NalogDialog;
import gui.standard.form.GenericDialog;
import gui.standard.form.concrete.RacunDialog;
import gui.tablemodel.Table;
import modelFromXsd.NalogZaPlacanje;
import modelFromXsd.ObjectFactory;
import xml.XmlManager;

public class GenAndExpNalogAction extends AbstractAction {
	
	private JDialog standardForm;
	
	public GenAndExpNalogAction(JDialog standardForm) 
	{
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/export.png")));
		putValue(SHORT_DESCRIPTION, "Generate Nalog");
		this.standardForm=standardForm;	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		RacunDialog thisDialog = (RacunDialog) standardForm;
		Table thisTable = thisDialog.getTable();
		int selectedRow =  thisTable.getSelectedRow();
		GenericDialogActions helperMethods = new GenericDialogActions(thisDialog);
		
		boolean racunVazi =  thisTable.getValueAt(selectedRow, 5).equals("1")? true : false;
		
		NalogZaPlacanje nalog = new NalogZaPlacanje();
		
		if(racunVazi){
			
			String racunId = thisTable.getValueAt(selectedRow, 0).toString();
			String valutaId = thisTable.getValueAt(selectedRow, 1).toString();
			String klijentId = thisTable.getValueAt(selectedRow, 2).toString();
			String pibBanke = thisTable.getValueAt(selectedRow, 3).toString();
			
			nalog.setDuznik(klijentId);
			nalog.setOznakaValute(valutaId);
			nalog.setRacunDuznika(racunId);
			nalog.setId("1");
			
			//XmlManager.generateDocument(nalog);
			
			
			
			NalogDialog nDialog = new NalogDialog(nalog);
			nDialog.setVisible(true);
		}
		
		
		
	}
}
