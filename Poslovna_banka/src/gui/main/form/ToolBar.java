package gui.main.form;

import gui.standard.form.GenericDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import actions.standard.form.GenerateReportAction;
import actions.standard.form.HelpAction;
import actions.standard.form.LastAction;
import actions.standard.form.NextAction;
import actions.standard.form.NextFormAction;
import actions.standard.form.PickupAction;
import actions.standard.form.PreviousAction;
import actions.standard.form.RefreshAction;
import actions.standard.form.SearchAction;
import database.DBConnection;
import databaseModel.DatabaseColumnModel;
import databaseModel.DatabaseTableModel;

public class ToolBar extends JToolBar
{
	private JButton pickButton;
	
	public ToolBar(JDialog dialog, boolean reportForBank) 
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

		final JButton nextFormButton = new JButton(new ImageIcon(getClass().getResource("/img/nextForm.gif")));
		nextFormButton.setToolTipText("Sledeća forma");
		
		final JPopupMenu menu = new JPopupMenu("Menu");
		String actualTable = ((GenericDialog)dialog).getDatabaseTableModel().getCode();		
		int popUpMeni = 0;
		
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
						String tableName = tableModel.getLabel();
						JMenuItem tab = new JMenuItem(tableName);
						tab.addActionListener(new NextFormAction(dialog, tableName));
						menu.add(tab);
						popUpMeni++;
					}
				}
			}
		}

		if(popUpMeni>0)
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

		this.setFloatable(false);
	}
	
	public void disablePick() {
		this.pickButton.setEnabled(false);
	}

}
