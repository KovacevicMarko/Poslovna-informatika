package gui.main.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import actions.standard.form.DatumAction;
import actions.standard.form.DeleteAction;
import actions.standard.form.ExportIzvod;
import actions.standard.form.FirstAction;
import actions.standard.form.GenerateReportAction;
import actions.standard.form.HelpAction;
import actions.standard.form.LastAction;
import actions.standard.form.NextAction;
import actions.standard.form.NextFormAction;
import actions.standard.form.PickupAction;
import actions.standard.form.PreviousAction;
import actions.standard.form.RefreshAction;
import actions.standard.form.RevokeBillAction;
import actions.standard.form.SearchAction;
import database.DBConnection;
import databaseModel.DatabaseColumnModel;
import databaseModel.DatabaseTableModel;
import gui.standard.form.GenericDialog;
import util.DatabaseModelHandler;

public class ToolBar extends JToolBar
{
	private JButton pickButton;
	private JLabel odLbl;
	private JLabel doLbl;
	private JTextField odTxt;
	private JTextField doTxt;
	private JDialog dialog;
	
	private static String[] readOnlyTables = new String[] {"DNEVNO_STANJE_RACUNA", "ANALITIKA_IZVODA", "UKIDANJE", "KLIRING", "RTGS", "STAVKA_KLIRINGA"};
	
	public ToolBar(JDialog dialog, boolean reportForBank, boolean reportForClient) 
	{
		this.dialog = dialog;
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
		this.add(setButtonEnabledToFalse(button));

		if(((GenericDialog)dialog).getDatabaseTableModel().getCode().equalsIgnoreCase("racuni"))
		{
			button = new JButton(new RevokeBillAction(dialog));
		}
		else
		{
			button = new JButton(new DeleteAction(dialog));
		}
		this.add(setButtonEnabledToFalse(button));
		this.addSeparator();

		final JButton nextFormButton = new JButton(new ImageIcon(getClass().getResource("/img/nextForm.gif")));
		nextFormButton.setToolTipText("Sledeća forma");
		
		final JPopupMenu menu = new JPopupMenu("Menu");
		String actualTable = ((GenericDialog)dialog).getDatabaseTableModel().getCode();		
		boolean hasNextTable = false;
		
		for(DatabaseTableModel tableModel : MainFrame.getInstance().getTableModels())
		{
			HashMap<String, String> foreignTables = DBConnection.getDatabaseWrapper().getImportedTables(tableModel.getCode());
			Vector<DatabaseColumnModel> columnModels = DBConnection.getDatabaseWrapper().getColumnModelByTableCode(tableModel.getCode());
			
			for(DatabaseColumnModel columnModel : columnModels)
			{
				boolean primaryKey = DBConnection.getDatabaseWrapper().isPrimaryKey(actualTable, columnModel.getCode());
				boolean foreignKey = DBConnection.getDatabaseWrapper().isForeignKey(actualTable, columnModel.getCode());
				
				if(primaryKey && foreignKey)
				{
					if(foreignTables.containsKey(columnModel.getCode()))
					{
						String tableName = tableModel.getCode();
						if(tableName.equals(((GenericDialog)dialog).getDatabaseTableModel().getCode()))
						{
							continue;
						}
						JMenuItem tab = new JMenuItem(DatabaseModelHandler.ConvertTableLabel(tableName));
						tab.addActionListener(new NextFormAction(dialog, tableName));
						menu.add(tab);
						hasNextTable = true;
					}
				}
			}
		}

		if(hasNextTable)
		{
			nextFormButton.addActionListener( new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					menu.show(nextFormButton, nextFormButton.getWidth()/2, nextFormButton.getHeight()/2);
				}
			} 
			);
		}
		else
		{
			nextFormButton.setEnabled(false);
		}
		
		this.add(nextFormButton);
		
		if(reportForBank)
		{
			button = new JButton(new GenerateReportAction(dialog));
			this.add(button);
			
		}
		if(reportForClient)
		{

			button = new JButton(new DatumAction(dialog));
			this.add(button);
			
			
		}
		
		if(((GenericDialog)dialog).getDatabaseTableModel().getCode().equalsIgnoreCase("PRAVNA_LICA")
			|| ((GenericDialog)dialog).getDatabaseTableModel().getCode().equalsIgnoreCase("FIZICKA_LICA") ){
				 			
				 JButton exportButton = new JButton(new ExportIzvod(dialog));
				 this.addSeparator();
				 this.add(exportButton);
		}
		

		this.setFloatable(false);
	}
	
	private JButton setButtonEnabledToFalse(JButton button)
	{
		String tableCode = ((GenericDialog)dialog).getDatabaseTableModel().getCode();
		for(String readOnlyTable : readOnlyTables)
		{
			if(tableCode.equalsIgnoreCase(readOnlyTable))
			{
				button.setEnabled(false);
			}
		}
		return button;
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
