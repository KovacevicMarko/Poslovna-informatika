package gui.standard.form;

import gui.main.form.MainFrame;
import gui.main.form.ToolBar;

import java.awt.Window;
import java.lang.Thread.State;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.Context;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Table;
import model.TableModel;
import net.miginfocom.swing.MigLayout;
import database.DBConnection;
import databaseModel.DatabaseColumnModel;
import databaseModel.DatabaseTableModel;

public class GenericDialog extends JDialog 
{
	//private DataPanel dataPanel;
	private Table table;
	private JTextField field;
	private String code;
	
	private static int mode;
	
	private GenericDialog parentDialog;
	private TableModel tableModel;
	private ToolBar toolbar;
	private DatabaseTableModel databaseTableModel;

	public static int getMode() 
	{
		return mode;
	}
	
	public void setMode(int mode) 
	{
		this.mode = mode;
	}

	public GenericDialog(Window parent, DatabaseTableModel databaseTableModel, JTextField field, String code) {
		super(parent,databaseTableModel.getLabel());
		this.setModal(true);
		this.databaseTableModel = databaseTableModel;
		this.field = field;
		this.code = code;
		this.init(databaseTableModel);
		this.setLocationRelativeTo(parent);

	}

	public GenericDialog(Window parent, DatabaseTableModel databaseTableModel) 
	{
		super(parent,databaseTableModel.getLabel());
		this.setModal(true);
		this.databaseTableModel = databaseTableModel;
		this.init(databaseTableModel);
		this.toolbar.disablePick();
		this.setLocationRelativeTo(parent);
	}

	public GenericDialog() 
	{
		super();
	}

	private void init(DatabaseTableModel databaseTableModel) 
	{

		int width = 500 + (databaseTableModel.getcolumnsModel().size()-2)*50;

		setSize(width, 400);
		setLayout(new MigLayout("fill"));
		this.toolbar = new ToolBar(this);
		this.add(this.toolbar,"dock north");
		this.table = new Table(this.getdatabaseTableModel());

		this.add(new TablePane(this.table),"grow, wrap");

		//this.dataPanel=new DataPanel(databaseTableModel,this);
		//statusBar = new StatusBar();

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		//bottomPanel.add(dataPanel);
		//bottomPanel.add(new ButtonsPanel(this),"dock east");

		add(bottomPanel, "grow, wrap");


	}

	public void nextFilter(String sifra,String column) throws SQLException{

		int br_redova= this.table.getRowCount();
		


		for(int i=0;i<br_redova;i++){
			Vector<DatabaseColumnModel> cdatabaseTableModel = DBConnection.getDatabaseWrapper().getColumnModelByTableCode(databaseTableModel.getCode());
			for(int j = 0; j < cdatabaseTableModel.size(); j++) {
				String provera = (String) this.table.getValueAt(i, j);
				System.out.print("  PRV:"+provera);
				
				boolean strani_kluc=false;

				if(cdatabaseTableModel.get(j).getCode().contains(column))
				strani_kluc=true;


				if(strani_kluc){
					
					if(provera!=null){
						if(!provera.contains(sifra)){
							System.out.print("remove ");
							TableModel dtm = (TableModel) this.table.getModel();
							dtm.removeRow(i);
							i--;
							br_redova--;
							break;
						}
					}	
					
				}

				int rowCount = this.table.getRowCount();

				if(rowCount>0){
					this.table.setRowSelectionInterval(0,0);
				}

			}
		}
	}
	//GETTERS AND SETTERS
	public DatabaseTableModel getdatabaseTableModel() {
		return databaseTableModel;
	}

	public void setdatabaseTableModel(DatabaseTableModel databaseTableModel) {
		this.databaseTableModel = databaseTableModel;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public JTextField getField() {
		return field;
	}

	public void setField(JTextField field) {
		this.field = field;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void refresh(int index) throws SQLException
	{

		TableModel tableModel = (TableModel)table.getModel();
		table.setModel(tableModel);

		try {
			tableModel.open();
			table.setRowSelectionInterval(index, index);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "GRESKA", JOptionPane.ERROR_MESSAGE);			
		} 


	}
}
