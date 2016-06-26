package gui.standard.form;

import gui.main.form.StatusBar;
import gui.main.form.ToolBar;
import gui.tablemodel.Table;
import gui.tablemodel.TableModel;

import java.awt.Window;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import util.EnumActiveMode;
import actions.main.form.GenericDialogActions;
import actions.standard.form.CommitAction;
import actions.standard.form.RollbackAction;
import databaseModel.DatabaseTableModel;

public class GenericDialog extends JDialog 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InfoPanel infoPanel;
	private StatusBar statusBar;
	private Table table;
	private JTextField field;
	private String code;
	
	private static int mode;
	
	private GenericDialog parentDialog;
	private TableModel tableModel;
	protected ToolBar toolbar;
	private DatabaseTableModel databaseTableModel;

	public static int getMode() 
	{
		return mode;
	}
	
	public void setMode(int mode) 
	{
		this.mode = mode;
		statusBar.init();
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

	protected void init(DatabaseTableModel databaseTableModel) 
	{
		setSize(800, 400);
		setLayout(new MigLayout("fill"));
		
		if (databaseTableModel.getCode().equalsIgnoreCase("banka")) 
		{
			this.toolbar = new ToolBar(this,true,false);
		}else if(databaseTableModel.getCode().equalsIgnoreCase("klijent"))
		{
			this.toolbar = new ToolBar(this,false,true);
		}else
		{
			this.toolbar = new ToolBar(this,false,false);
		}
		//this.toolbar = databaseTableModel.getCode().equalsIgnoreCase("banka") ? new ToolBar(this,true) : new ToolBar(this,false);
		this.add(this.toolbar,"dock north");
		this.table = new Table(this.getdatabaseTableModel());
		this.add(new TablePane(this.table),"grow, wrap");
		this.infoPanel = new InfoPanel(databaseTableModel, this);
		
		//set text for labels in StatusBar
		statusBar = new StatusBar();
		setMode(EnumActiveMode.IZMENA);
		statusBar.getLabelName().setText(databaseTableModel.getLabel());
		
		//add new panel for textfields
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		bottomPanel.add(infoPanel);
		bottomPanel.add(panelWithButtons(),"dock east");
		
		add(bottomPanel, "grow, wrap");
		add(statusBar, "dock south");
		final GenericDialogActions actions = new GenericDialogActions(this);
		this.table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting() && getMode() == EnumActiveMode.IZMENA)
							return;
						actions.sync();
					}
				}
			);
	}

	public void refresh() throws SQLException
	{

		TableModel tableModel = (TableModel)table.getModel();
		table.setModel(tableModel);

		try {
			tableModel.fillData();
			table.setRowSelectionInterval(0, 0);
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "GRESKA", JOptionPane.ERROR_MESSAGE);			
		} 
		catch (Exception ecx)
		{
			
		}


	}
	
	/**
	 * Create panel with buttons for commit and rollback.
	 * @return
	 */
	public JPanel panelWithButtons()
	{
		JPanel panel = new JPanel();
		JButton btnCommit = new JButton(new ImageIcon(getClass().getResource("/img/commit.gif")));
		btnCommit.setToolTipText("Potvrdi");
		btnCommit.addActionListener(new CommitAction((JDialog) this));
		
		JButton btnRollback = new JButton(new ImageIcon(getClass().getResource("/img/remove.gif")));
		btnRollback.setToolTipText("Poništi");
		btnRollback.addActionListener(new RollbackAction((JDialog) this));
		
		panel.setLayout(new MigLayout("wrap"));
		panel.add(btnCommit);
		panel.add(btnRollback);
		return panel;
	}
	
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
	
	public InfoPanel getInfoPanel() {
		return infoPanel;
	}

	public void setInfoPanel(InfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(StatusBar statusBar) {
		this.statusBar = statusBar;
	}

	public GenericDialog getParentDialog() {
		return parentDialog;
	}

	public void setParentDialog(GenericDialog parentDialog) {
		this.parentDialog = parentDialog;
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public ToolBar getToolbar() {
		return toolbar;
	}

	public void setToolbar(ToolBar toolbar) {
		this.toolbar = toolbar;
	}

	public DatabaseTableModel getDatabaseTableModel() {
		return databaseTableModel;
	}

	public void setDatabaseTableModel(DatabaseTableModel databaseTableModel) {
		this.databaseTableModel = databaseTableModel;
	}
}
