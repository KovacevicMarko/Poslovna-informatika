package gui.main.form;

import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import actions.standard.form.AddAction;
import actions.standard.form.DeleteAction;
import actions.standard.form.FirstAction;
import actions.standard.form.GenerateReportAction;
import actions.standard.form.HelpAction;
import actions.standard.form.LastAction;
import actions.standard.form.NextAction;
import actions.standard.form.NextFormAction;
import actions.standard.form.PickupAction;
import actions.standard.form.PreviousAction;
import actions.standard.form.RefreshAction;
import actions.standard.form.SearchAction;

public class ToolBar extends JToolBar
{
	private JButton pickButton;
	private JLabel odLbl;
	private JLabel doLbl;
	private JTextField odTxt;
	private JTextField doTxt;
	public ToolBar(JDialog dialog, boolean reportForBank, boolean reportForClient) 
	{
		JButton button;
		
		
		button = new JButton(new SearchAction(dialog));
		this.add(button);

		button = new JButton(new RefreshAction(dialog));
		this.add(button);

		button = new JButton(new PickupAction(dialog));
		this.pickButton = button;
		this.add(button);

		button = new JButton(new HelpAction(dialog));
		this.add(button);
		this.addSeparator();

		button = new JButton(new FirstAction(dialog));
		this.add(button);
		
		button = new JButton(new PreviousAction(dialog));
		this.add(button);

		button = new JButton(new NextAction(dialog));
		this.add(button);

		button = new JButton(new LastAction(dialog));
		this.add(button);
		this.addSeparator();

		button = new JButton(new AddAction(dialog));
		this.add(button);

		button = new JButton(new DeleteAction(dialog));
		this.add(button);
		this.addSeparator();

		button = new JButton(new NextFormAction(dialog));
		this.add(button);
		
		if(reportForBank)
		{
			button = new JButton(new GenerateReportAction(dialog));
			this.add(button);
		}
		
		if(reportForClient)
		{
			button = new JButton(new GenerateReportAction(dialog));
			odLbl=new JLabel("Od:");
			doLbl=new JLabel("Do:");
			odTxt=new JTextField();
			doTxt=new JTextField();
			this.add(button);
			this.add(odLbl);
			this.add(odTxt);
			this.add(doLbl);
			this.add(doTxt);
			
		}
		
		

		this.setFloatable(false);
	}
	
	public void disablePick() {
		this.pickButton.setEnabled(false);
	}

	public JLabel getOdLbl() {
		return odLbl;
	}

	public void setOdLbl(JLabel odLbl) {
		this.odLbl = odLbl;
	}

	public JLabel getDoLbl() {
		return doLbl;
	}

	public void setDoLbl(JLabel doLbl) {
		this.doLbl = doLbl;
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
