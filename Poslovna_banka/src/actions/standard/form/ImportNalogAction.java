package actions.standard.form;

import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import database.DBQueryManager;
import modelFromXsd.NalogZaPlacanje;
import xml.XMLFileFilter;
import xml.XmlManager;

public class ImportNalogAction extends AbstractAction {
	
	private JDialog standardForm;
	
	public ImportNalogAction(JDialog standardForm){
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/import.png")));
		putValue(SHORT_DESCRIPTION, "Import Nalog");
		this.standardForm=standardForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Importuj nalog...");
		fc.setFileFilter(new XMLFileFilter());
        fc.setAcceptAllFileFilterUsed(false);
        fc.setCurrentDirectory(new File("./data/nalozi"));
        NalogZaPlacanje nalog = null;
		
		if(fc.showOpenDialog(standardForm) == JFileChooser.APPROVE_OPTION) {

		   nalog = XmlManager.generateBeanNalog(fc.getSelectedFile());
		   if(nalog!=null){
			   
			   
			   DBQueryManager.importNalog(nalog);
			   
			   
		   }
		   
		   
			
		} else {
			fc.setVisible(false);
			return;
		}
		
		
	}
	
}
