package gui.dialogs;

import gui.dialogs.NalogDialog.OkAction;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
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

import actions.standard.form.GenerateReportAction;
import xml.XmlManager;
import modelFromXsd.NalogZaPlacanje;
import databaseModel.DatabaseTableModel;

public class DatumDijalog extends JDialog{

	private JLabel odLbl=new JLabel("Od datuma: ");
	private JLabel doLbl= new JLabel("Do datuma: ");
	private JTextField odTxt = new JTextField();
	private JTextField doTxt=new JTextField();
	private int index;
	private JDialog standardForm;
	
	public DatumDijalog(int index, JDialog standardForm){
		setLocationRelativeTo(this.getParent());
		this.index=index;
		this.standardForm = standardForm;
		initGUI();

	}
	
	
	private void initGUI(){
		JPanel panel = new JPanel();
		
        SpringLayout layout = new SpringLayout();
		
		panel.setLayout(new GridLayout(2, 2, 10, 10));
		
		initPanel(panel,layout);
		
		this.add(panel,BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		JButton btnOk = new JButton(new GenerateReportAction(this, index, standardForm));
		//JButton btnCancel = new JButton("Cancel");
		
		buttonPanel.add(btnOk,BorderLayout.WEST);
		//buttonPanel.add(btnCancel,BorderLayout.EAST);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	public void initPanel(JPanel panel,SpringLayout layout)
	{

		panel.add(odLbl);
		panel.add(odTxt);
		layout.putConstraint(BorderLayout.WEST, odLbl, 5, BorderLayout.WEST, odTxt);
		
		panel.add(doLbl);
		panel.add(doTxt);
		layout.putConstraint(BorderLayout.WEST, doLbl, 5, BorderLayout.WEST, doTxt);
		
		
	}


	public JTextField getOdTxt() {
		return odTxt;
	}


	public void setOdTxt(JTextField odTxt) {
		this.odTxt = odTxt;
	}


	public JTextField getDoTxt() {
		return doTxt;
	}


	public void setDoTxt(JTextField doTxt) {
		this.doTxt = doTxt;
	}
	
}
