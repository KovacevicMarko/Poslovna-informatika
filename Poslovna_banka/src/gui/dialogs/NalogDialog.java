package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import modelFromXsd.NalogZaPlacanje;
import xml.XmlManager;

public class NalogDialog extends JDialog{
	
	JLabel duznikLbl = new JLabel("Duznik:");
	JTextField duznikTxt = new JTextField();
	
	
	JLabel svrhaLbl = new JLabel("Svrha placanja:");
	JTextField svrhaTxt = new JTextField();
	
	
	JLabel primalacLbl = new JLabel("Primalac:");
	JTextField primalacTxt = new JTextField();
	
	
	JLabel dValuteLbl = new JLabel("Datum valute:");
	JTextField dValuteTxt = new JTextField();
	
	
	JLabel racunDuznikLbl = new JLabel("Racun duznika:");
	JTextField racunDuznikTxt = new JTextField();
	
	
	JLabel modelZaduzenjaLbl = new JLabel("Model zaduzenja:");
	JTextField modelZaduzenjaTxt = new JTextField();
	
	
	JLabel brojZaduzenjaLbl = new JLabel("Poziv na broj zaduzenja:");
	JTextField brojZaduzenjaTxt = new JTextField();
	
	
	JLabel racunPrimaocaLbl = new JLabel("Racun primaoca:");
	JTextField racunPrimaocaTxt = new JTextField();
	
	
	JLabel modelOdobrenjeLbl = new JLabel("Model odobrenja:");
	JTextField modelOdobrenjeTxt = new JTextField();
	
	
	JLabel brojOdobrenjaLbl = new JLabel("Poziv na broj odobrenja:");
	JTextField brojOdobrenjaTxt = new JTextField();
	
	
	JLabel iznosLbl = new JLabel("Iznos:");
	JTextField iznosTxt = new JTextField();
	
	
	JLabel oznakaValuteLbl = new JLabel("Oznaka valute:");
	JTextField oznakaValuteTxt = new JTextField();
	
	
	private NalogZaPlacanje nalog;
	
	public NalogDialog(NalogZaPlacanje nalog)
	{
		this.nalog = nalog;
		this.setSize(200,400);
		setTitle("Nalog za prenos");
		setLocationRelativeTo(this.getParent());
		setResizable(false);
		initGUI();
	}
	
	private void initGUI(){
		
		JPanel panel = new JPanel();
	
        SpringLayout layout = new SpringLayout();
		
		panel.setLayout(new GridLayout(11, 2, 10, 10));
		
		initPanel(panel, nalog,layout);
		
		this.add(panel,BorderLayout.NORTH);
		
		
		JPanel buttonPanel = new JPanel();
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new OkAction());
		JButton btnCancel = new JButton("Cancel");
		
		buttonPanel.add(btnOk,BorderLayout.WEST);
		buttonPanel.add(btnCancel,BorderLayout.EAST);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	public void initPanel(JPanel panel,NalogZaPlacanje nalog,SpringLayout layout)
	{
		
		duznikTxt.setText(nalog.getDuznik());
		duznikTxt.setEditable(false);
		panel.add(duznikLbl);
		panel.add(duznikTxt);
		layout.putConstraint(BorderLayout.WEST, duznikLbl, 5, BorderLayout.WEST, duznikTxt);
		
		panel.add(svrhaLbl);
		panel.add(svrhaTxt);
		
		
		panel.add(primalacLbl);
		panel.add(primalacTxt);
		
		racunDuznikTxt.setText(nalog.getRacunDuznika());
		racunDuznikTxt.setEditable(false);
		panel.add(racunDuznikLbl);
		panel.add(racunDuznikTxt);
		
		modelZaduzenjaTxt.setColumns(2);
		panel.add(modelZaduzenjaLbl);
		panel.add(modelZaduzenjaTxt);
		
		panel.add(brojZaduzenjaLbl);
		panel.add(brojZaduzenjaTxt);
		
		panel.add(racunPrimaocaLbl);
		panel.add(racunPrimaocaTxt);
	
		modelZaduzenjaTxt.setColumns(2);
		panel.add(modelOdobrenjeLbl);
		panel.add(modelOdobrenjeTxt);
		
		panel.add(brojOdobrenjaLbl);
		panel.add(brojOdobrenjaTxt);
		
		panel.add(iznosLbl);
		panel.add(iznosTxt);
		
		modelZaduzenjaTxt.setColumns(3);
		panel.add(oznakaValuteLbl);
		panel.add(oznakaValuteTxt);
		
	}
	
	public class OkAction extends AbstractAction
	{
		public OkAction()
		{
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			nalog.setSvrhaPlacanja(svrhaTxt.getText());
			nalog.setPrimalac(primalacTxt.getText());
			
			Date date = new Date();
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(date);
			try {
				nalog.setDatumValute(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
				
			} catch (DatatypeConfigurationException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			nalog.setModelZaduzenja(new BigInteger(modelZaduzenjaTxt.getText()));
			nalog.setPozivNaBrojZaduzenja(brojZaduzenjaTxt.getText());
			nalog.setRacunPrimaoca(racunPrimaocaTxt.getText());
			nalog.setModelOdobrenja(new BigInteger(modelOdobrenjeTxt.getText()));
			nalog.setPozivNaBrojOdobrenja(brojOdobrenjaTxt.getText());
			nalog.setIznos(new BigDecimal(iznosTxt.getText()));
			nalog.setOznakaValute(oznakaValuteTxt.getText());
			//TODO napraviti checkBox za ovo
			nalog.setHitno(false);
			
			XmlManager.generateDocument(nalog);
		}
	}
}
