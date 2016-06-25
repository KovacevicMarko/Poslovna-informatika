package gui.main.form;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import actions.main.form.MenuBarAction;
import database.DBConnection;
import databaseModel.DatabaseColumnModel;
import databaseModel.DatabaseTableModel;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static MainFrame instance;
	private JMenuBar menuBar;

	private MainFrame()
	{

		setSize(new Dimension(1000,700));
		setContentPane(new JLabel(new ImageIcon(getClass().getResource("/img/bank.jpg"))));
		setLocationRelativeTo(null);
		setTitle("Poslovna banka");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//setExtendedState(MAXIMIZED_BOTH);
		setUpMenu();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(MainFrame.getInstance(),
						"Da li ste sigurni da zelite da zatvorite aplikaciju?", "Zatvaranje aplikacije",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					/*
					 * Zatvori konekciju
					 */
					DBConnection.getDatabaseWrapper().close();
					System.exit(0);
				}
			}
		});
		
		setJMenuBar(menuBar);

	}

	public static MainFrame getInstance(){
		if (instance==null)
			instance=new MainFrame();
		return instance;

	}

	/**
	 * Menu bar initialization.
	 */
	private void setUpMenu()
	{
		menuBar = new JMenuBar();

		JMenu orgSemaMenu = new JMenu("Organizaciona šema");
		orgSemaMenu.setMnemonic(KeyEvent.VK_O);
		addTablesToMenu(orgSemaMenu);
		menuBar.add(orgSemaMenu);
	}

	
	/**
	 * Add all tables from database to menu.
	 * @param orgMenu 
	 */
	private void addTablesToMenu(JMenu orgMenu)
	{
		
		Vector<String> tableCodes = DBConnection.getDatabaseWrapper().getTableCodes();
		DatabaseTableModel databaseTableModel;
		JMenuItem menuItem;
		for(int i = 0; i < tableCodes.size(); i++) 
		{
			databaseTableModel = new DatabaseTableModel();
			databaseTableModel.setCode(tableCodes.get(i));
			databaseTableModel.setLabel(tableCodes.get(i));

			Vector<DatabaseColumnModel> databaseColumnModel = DBConnection.getDatabaseWrapper().getColumnModelByTableCode(tableCodes.get(i));
			Vector<String> nextTables = DBConnection.getDatabaseWrapper().getExportedTables(tableCodes.get(i));
			HashMap<String,String> foreignTables = DBConnection.getDatabaseWrapper().getImportedTables(tableCodes.get(i));
			
			for(int j = 0; j < nextTables.size(); j++) {
				databaseTableModel.addNextTable(nextTables.get(j));
			}
			
			for(int j = 0; j < databaseColumnModel.size(); j++) 
			{
				String key = tableCodes.get(i) + "." + databaseColumnModel.get(j).getCode();
				
				databaseColumnModel.get(j).setLabel(key);
				databaseColumnModel.get(j).setPrimary_key(DBConnection.getDatabaseWrapper().isPrimaryKey(tableCodes.get(i),databaseColumnModel.get(j).getCode()));
				databaseColumnModel.get(j).setForeign_key(DBConnection.getDatabaseWrapper().isForeignKey(tableCodes.get(i),databaseColumnModel.get(j).getCode()));
				if(foreignTables.containsKey(databaseColumnModel.get(j).getCode())) 
				{
					databaseColumnModel.get(j).setTableParent(foreignTables.get(databaseColumnModel.get(j).getCode()));
					databaseColumnModel.get(j).setCodeInParent(databaseColumnModel.get(j).getCode());
				} 
				else 
				{				
					databaseColumnModel.get(j).setTableParent(null);
					databaseColumnModel.get(j).setCodeInParent(null);
				}
			}
			databaseTableModel.setcolumnsModel(databaseColumnModel);
			menuItem = new JMenuItem(databaseTableModel.getLabel());
			menuItem.addActionListener(new MenuBarAction(databaseTableModel));
			orgMenu.add(menuItem);

		}
	}

}