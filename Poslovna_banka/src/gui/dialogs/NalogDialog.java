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
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import database.DBQueryManager;
import modelFromXsd.NalogZaPlacanje;
import xml.XmlManager;

public class NalogDialog extends JDialog {

	JLabel duznikLbl = new JLabel("Duznik:");
	JTextField duznikTxt = new JTextField();

	JLabel svrhaLbl = new JLabel("Svrha placanja:");
	JTextField svrhaTxt = new JTextField();

	JLabel primalacLbl = new JLabel("Primalac:");
	JTextField primalacTxt = new JTextField();

	JLabel dValuteLbl = new JLabel("Datum valute:");
	JTextField dValuteTxt = new JTextField();

	JLabel mestoPrijemaLbl = new JLabel("Mesto:");
	JTextField mestoPrijemaTxt = new JTextField();

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
	
	JLabel sifraPlacanjaLbl = new JLabel("Sifra placanja:");
	JTextField sifraPlacanjaTxt = new JTextField();
	
	JLabel hitnoLbl = new JLabel("Hitno?");
	JCheckBox hitnoCbx = new JCheckBox();

	private NalogZaPlacanje nalog;
	private boolean isUkidanje;

	public NalogDialog(NalogZaPlacanje nalog,boolean isUkidanje) {
		this.nalog = nalog;
		this.isUkidanje = isUkidanje;
		this.setSize(200, 400);
		setTitle("Nalog za prenos");
		setLocationRelativeTo(this.getParent());
		setResizable(false);
		initGUI();
	}

	private void initGUI() {

		JPanel panel = new JPanel();
		if(isUkidanje){
			panel.setLayout(new GridLayout(12, 2, 10, 10));
		}
		else{
			panel.setLayout(new GridLayout(14, 2, 10, 10));
		}
		initPanel(panel, nalog);

		JPanel buttonPanel = new JPanel();
		initButtonPanel(buttonPanel);
		
		this.add(panel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.pack();
	}

	private void initPanel(JPanel panel, NalogZaPlacanje nalog) {

		duznikTxt.setText(nalog.getDuznik());
		duznikTxt.setEditable(false);
		panel.add(duznikLbl);
		panel.add(duznikTxt);

		if (isUkidanje) {
			svrhaTxt.setText("Ukidanje racuna");
			svrhaTxt.setEditable(false);
		}
		
		panel.add(svrhaLbl);
		panel.add(svrhaTxt);

		panel.add(primalacLbl);
		panel.add(primalacTxt);

		panel.add(mestoPrijemaLbl);
		panel.add(mestoPrijemaTxt);

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
		
		if(!isUkidanje){
			panel.add(iznosLbl);
			panel.add(iznosTxt);
		}

		oznakaValuteTxt.setText(nalog.getOznakaValute());
		oznakaValuteTxt.setEditable(false);
		panel.add(oznakaValuteLbl);
		panel.add(oznakaValuteTxt);
		
		panel.add(sifraPlacanjaLbl);
		panel.add(sifraPlacanjaTxt);
		
		if(!isUkidanje){
			panel.add(hitnoLbl);
			panel.add(hitnoCbx);
		}
	}
	
	private void initButtonPanel(JPanel buttonPanel){
		JButton btnOk = new JButton("Ok");
		JButton btnCancel = new JButton("Cancel");
		
		btnOk.addActionListener(new OkAction(this));
		btnCancel.addActionListener(new CancelAction(this));
		
		buttonPanel.add(btnOk, BorderLayout.WEST);
		buttonPanel.add(btnCancel, BorderLayout.EAST);
	}
	
	
	public class OkAction extends AbstractAction {
		private JDialog dialog;

		public OkAction(JDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//Populate nalog object from form and export to xml doc.
			
			try {

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

				Date date1 = new Date();
				GregorianCalendar c1 = new GregorianCalendar();
				c.setTime(date1);
				try {
					nalog.setDatumPrijema(DatatypeFactory.newInstance().newXMLGregorianCalendar(c1));

				} catch (DatatypeConfigurationException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				nalog.setMestoPrijema(mestoPrijemaTxt.getText());
				nalog.setModelZaduzenja(new BigInteger(modelZaduzenjaTxt.getText()));
				nalog.setPozivNaBrojZaduzenja(brojZaduzenjaTxt.getText());
				nalog.setRacunPrimaoca(racunPrimaocaTxt.getText());
				nalog.setModelOdobrenja(new BigInteger(modelOdobrenjeTxt.getText()));
				nalog.setPozivNaBrojOdobrenja(brojOdobrenjaTxt.getText());
				nalog.setSifraPlacanja(sifraPlacanjaTxt.getText());
				// TODO napraviti checkBox za ovo
				if(isUkidanje){
					nalog.setIznos(new BigDecimal(1.00));
					nalog.setHitno(false);
				}
				else{
					nalog.setIznos(new BigDecimal(iznosTxt.getText()));
					nalog.setHitno(hitnoCbx.isSelected());
				}

				boolean proslo = XmlManager.generateDocumentNalog(nalog, dialog);
				if (proslo) {
					dialog.dispose();
					JOptionPane.showMessageDialog(null, "Uspesno generisan nalog za prenos");
					DBQueryManager.importNalog(nalog, isUkidanje);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Niste popunili potrebna polja!");
			}
		}
	}
	
	public class CancelAction extends AbstractAction {
		private JDialog dialog;

		public CancelAction(JDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
	}	
}
