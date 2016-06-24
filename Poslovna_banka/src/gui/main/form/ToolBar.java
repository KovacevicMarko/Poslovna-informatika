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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import actions.standard.form.AddAction;
import actions.standard.form.DeleteAction;
import actions.standard.form.FirstAction;
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
	
	public ToolBar(JDialog dialog) 
	{

		JButton button;

		button = new JButton(new ImageIcon(getClass().getResource("/img/search.gif")));
		button.setToolTipText("Pretraga");
		button.addActionListener(new SearchAction(dialog));
		this.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("/img/refresh.gif")));
		button.setToolTipText("Refresh");
		button.addActionListener(new RefreshAction(dialog));
		this.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("/img/zoom-pickup.gif")));
		button.setToolTipText("Zoom pickup");
		button.addActionListener(new PickupAction(dialog));
		this.pickButton = button;
		this.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("/img/help.gif")));
		button.setToolTipText("Pomoć");
		button.addActionListener(new HelpAction(dialog));
		this.add(button);
		this.addSeparator();

		button = new JButton(new ImageIcon(getClass().getResource("/img/first.gif")));
		button.setToolTipText("Početak");
		button.addActionListener(new FirstAction(dialog));
		this.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("/img/prev.gif")));
		button.setToolTipText("Prethodni");
		button.addActionListener(new PreviousAction(dialog));
		this.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("/img/next.gif")));
		button.setToolTipText("Sledeći");
		button.addActionListener(new NextAction(dialog));
		this.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("/img/last.gif")));
		button.setToolTipText("Poslednji");
		button.addActionListener(new LastAction(dialog));
		this.add(button);
		this.addSeparator();

		button = new JButton(new ImageIcon(getClass().getResource("/img/add.gif")));
		button.setToolTipText("Dodavanje");
		button.addActionListener(new AddAction(dialog));
		this.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("/img/remove.gif")));
		button.setToolTipText("Brisanje");
		button.addActionListener(new DeleteAction(dialog));
		this.add(button);
		this.addSeparator();

		final JButton button1 = new JButton(new ImageIcon(getClass().getResource("/img/nextForm.gif")));
		button1.setToolTipText("Sledeća forma");

		final JPopupMenu menu = new JPopupMenu("Menu");
		

		this.add(button);
		this.add(button1);
		this.setFloatable(false);
	}
	
	public void disablePick() {
		this.pickButton.setEnabled(false);
	}

}
