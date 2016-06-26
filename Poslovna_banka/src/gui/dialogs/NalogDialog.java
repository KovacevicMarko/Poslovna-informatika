package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import modelFromXsd.NalogZaPlacanje;

public class NalogDialog extends JDialog{
	
	private NalogZaPlacanje nalog;
	
	public NalogDialog(NalogZaPlacanje nalog)
	{
		this.nalog = nalog;
		initGUI();
	}
	
	private void initGUI(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(10, 2));
		
		JLabel svrhaLbl = new JLabel("Svrha uplate");
		JTextArea svrhaTxt = new JTextArea();
		panel.add(svrhaLbl);
		panel.add(svrhaTxt);
		
		JLabel primalacLbl = new JLabel("Primalac");
		JTextArea primalacTxt = new JTextArea();
		panel.add(primalacLbl);
		panel.add(primalacTxt);
		
		this.add(panel,BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		JButton btnOk = new JButton("Ok");
		JButton btnCancel = new JButton("Cancel");
		
		buttonPanel.add(btnOk);
		buttonPanel.add(btnCancel);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
}
