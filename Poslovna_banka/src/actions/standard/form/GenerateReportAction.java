package actions.standard.form;

import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import database.DBConnection;

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
		
		
		
		
		if(((GenericDialog)standardForm).getDatabaseTableModel().getCode().equalsIgnoreCase("banka")){
			
			try {
			      String status = (String) ((GenericDialog)standardForm).getTable().getModel().getValueAt(index, 0);  
			      Map params = new HashMap(1);
				  params.put("banka", status );
				  System.out.println(getClass().getResource("/jasper/SpisakRacunaZaBanku.jasper"));
				  JasperPrint jp = JasperFillManager.fillReport(
				  getClass().getResource("/jasper/SpisakRacunaZaBanku.jasper").openStream(),
				  params, DBConnection.getDatabaseWrapper().getConnection());
				  JasperViewer.viewReport(jp, false);

				} catch (Exception ex) {
				  ex.printStackTrace();
				}
			
			
			
		}else if(((GenericDialog)standardForm).getDatabaseTableModel().getCode().equalsIgnoreCase("klijent")){
				if(((GenericDialog)standardForm).getToolbar().getOdTxt().getText().length() ==0)
				{
					JOptionPane.showMessageDialog(null, "Morate uneti datum.", "Greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(((GenericDialog)standardForm).getToolbar().getDoTxt().getText().length() ==0)
				{
					JOptionPane.showMessageDialog(null, "Morate uneti datum.", "Greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
			      String status = (String) ((GenericDialog)standardForm).getTable().getModel().getValueAt(index, 0);
			      String odTad=((GenericDialog)standardForm).getToolbar().getOdTxt().getText();
			      String doTad=((GenericDialog)standardForm).getToolbar().getDoTxt().getText();
			      Map params = new HashMap(1);
				  params.put("klijent", status );
				  params.put("od", odTad );
				  params.put("do", doTad );
				  System.out.println(getClass().getResource("/jasper/IzvodKlijenataZaInterval.jasper"));
				  JasperPrint jp = JasperFillManager.fillReport(
				  getClass().getResource("/jasper/IzvodKlijenataZaInterval.jasper").openStream(),
				  params, DBConnection.getDatabaseWrapper().getConnection());
				  JasperViewer.viewReport(jp, false);
	
				} catch (Exception ex) {
				  ex.printStackTrace();
				}
		}
				
	}

}
