package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import javax.swing.table.DefaultTableModel;

import util.SortUtils;
import database.DBConnection;
import databaseModel.DatabaseTableModel;

public class TableModel extends DefaultTableModel 
{

	private static final long serialVersionUID = -4186581278768828397L;
	private String tableName;
	private DatabaseTableModel databaseTableModel;
	private String query;
	
	public TableModel(Object[] colNames, int rowCount) 
	{
		super(colNames, rowCount);
	}

	public TableModel(DatabaseTableModel databaseTableModel) 
	{
		if (databaseTableModel == null && databaseTableModel.getcolumnsModel() == null) 
		{
			return;
		}
		
		for (int i = 0; i < databaseTableModel.getcolumnsModel().size(); i++) 
		{
			this.addColumn(databaseTableModel.getcolumnsModel().get(i).getLabel());
		}

		this.databaseTableModel = databaseTableModel;
		this.tableName = databaseTableModel.getCode();
		this.query = "SELECT * FROM " + tableName;
	}


	@Override
    public boolean isCellEditable(int row, int column) 
	{
       return false;
    }
	
	public DatabaseTableModel getdatabaseTableModel() 
	{
		return databaseTableModel;
	}

	public void setdatabaseTableModel(DatabaseTableModel databaseTableModel) 
	{
		this.databaseTableModel = databaseTableModel;
	}

	// otvaranje upita
	public void open() throws SQLException
	{
		fillData(tableName);
	}

	private void fillData(String tableName) throws SQLException 
	{
		String[] colValues = new String[databaseTableModel.getcolumnsModel().size()];
		setRowCount(0);

		Statement stmt = DBConnection.getDatabaseWrapper().getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(query);

		while (rset.next()) 
		{
			for (int i = 0; i < databaseTableModel.getcolumnsModel().size(); i++) {
				colValues[i] = rset.getString(databaseTableModel.getcolumnsModel().get(i).getCode());
			}
			addRow(prepareRow(colValues));
		}

		rset.close();
		stmt.close();
		fireTableDataChanged();

	}

	private String[] prepareRow(String[] colValues) 
	{
		String[] row = new String[colValues.length];
		for (int i = 0; i < colValues.length; i++) 
		{
			row[i] = colValues[i];
		}
		return row;
	}

	public void deleteRow(int index) throws SQLException 
	{
		checkRow(index);
		// assumption: first column in list of column is always primary key or
		// semantic unique identifier!
		String query = "DELETE FROM " + databaseTableModel.getCode() + " WHERE "
				+ databaseTableModel.getcolumnsModel().get(0).getCode() + " = ?";
		PreparedStatement stmt = DBConnection.getDatabaseWrapper().getConnection().prepareStatement(query);
		String _id = (String) getValueAt(index, 0);
		// Deleting from the database
		stmt.setString(1,_id);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getDatabaseWrapper().getConnection().commit();
		// Delete from the table model only if deletion from the database was
		// successful.
		if (rowsAffected > 0) 
		{
			removeRow(index);
			fireTableDataChanged();
		}

	}

	public int insertRow(LinkedHashMap<String, String> data) throws SQLException 
	{
		int retVal = 0;
		final String TYPE = "INSERT";
		String query = makeInsertQuery(data, databaseTableModel.getCode(), TYPE);
		PreparedStatement stmt = DBConnection.getDatabaseWrapper().getConnection().prepareStatement(query);

		int i = 1;
		for (String key : data.keySet()) 
		{
			stmt.setString(i, data.get(key));
			i++;
		}

		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// inserting into database
		DBConnection.getDatabaseWrapper().getConnection().commit();
		if (rowsAffected > 0) 
		{
			retVal = sortedInsert(data);
			fireTableDataChanged();
		}
		return retVal;
	}

	private String makeInsertQuery(LinkedHashMap<String, String> data, String tableName, String type) 
	{
		if (type.equals("INSERT")) 
		{
			String query = "INSERT INTO " + tableName + " ( ";
			for (String key : data.keySet()) {
				query += key + ", ";
			}
			query = query.substring(0, query.length() - 2);
			query += " ) VALUES ( ";
			for (int i = 0; i < data.keySet().size(); i++) {
				query += "?, ";
			}
			query = query.substring(0, query.length() - 2);
			query += " );";
			return query;
		} 
		else 
		{
			String query = "UPDATE " + tableName + " SET ";
			boolean isFirst = true;
			String _id = "";
			for (String key : data.keySet())
			{
				if (isFirst) 
				{
					_id = key;
					isFirst = false;
					continue;
				}
				query += key + " = ?, ";
			}
			query = query.substring(0, query.length() - 2);
			query += " WHERE " + _id + " = ?";

			return query;
		}
	}

	private int sortedInsert(LinkedHashMap<String, String> data) 
	{
		LinkedHashMap<String, String> dataCopy = new LinkedHashMap<>();
		dataCopy.putAll(data);
		String _id = dataCopy.keySet().iterator().next();
		int left = 0;
		int right = getRowCount() - 1;
		int mid = (left + right) / 2;
		while (left <= right) 
		{
			mid = (left + right) / 2;
			String _aID = (String) getValueAt(mid, 0);
			if (SortUtils.getLatCyrCollator().compare(_id, _aID) > 0)
				left = mid + 1;
			else if (SortUtils.getLatCyrCollator().compare(_id, _aID) < 0)
				right = mid - 1;
			break;
		}
		String[] colValues = new String[data.size()];
		int i = 0;
		for (String key : data.keySet())
		{
			colValues[i] = data.get(key);
			i++;
		}
		insertRow(left, prepareRow(colValues));
		return left;
	}

	public void updateRow(int index, LinkedHashMap<String, String> data) throws SQLException
	{
		checkRow(index);
		final String TYPE = "UPDATE";
		String query = makeInsertQuery(data, databaseTableModel.getCode(), TYPE);
		PreparedStatement stmt = DBConnection.getDatabaseWrapper().getConnection().prepareStatement(query);

		boolean isFirst = true;
		int i = 1;
		String _id = "";
		for (String key : data.keySet())
		{
			if (isFirst) 
			{
				_id = data.get(key);
				isFirst = false;
				continue;
			}
			stmt.setString(i, data.get(key));
			i++;
		}
		stmt.setString(i, _id);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getDatabaseWrapper().getConnection().commit();
		// check if update successfuly passed
		if (rowsAffected > 0)
			fireTableDataChanged();
	}

	private void checkRow(int index) throws SQLException
	{
		DBConnection.getDatabaseWrapper().getConnection().setTransactionIsolation(DBConnection.getDatabaseWrapper().getConnection().TRANSACTION_REPEATABLE_READ);
		// assumption: first column in list of column is always primary key or
		// semantic unique identifier!
		String sql = query + " WHERE " + databaseTableModel.getcolumnsModel().get(0).getCode() + " = ?";
		PreparedStatement selectStmt = DBConnection.getDatabaseWrapper().getConnection().prepareStatement(sql);
		String _id = (String) getValueAt(index, 0);
		selectStmt.setString(1, _id);

		ResultSet rset = selectStmt.executeQuery();
		String[] newValues = new String[databaseTableModel.getcolumnsModel().size()];
		Boolean exists = false;

		while (rset.next()) 
		{
			for (int i = 0; i < databaseTableModel.getcolumnsModel().size(); i++) 
			{
				newValues[i] = rset.getString(databaseTableModel.getcolumnsModel().get(i).getCode());
			}
			exists = true;
		}
		if (!exists) 
		{
			removeRow(index);
			fireTableDataChanged();
		} 
		else if (!identicalValues(newValues, index)) 
		{
			for (int i = 0; i < newValues.length; i++) 
			{
				setValueAt(newValues[i], index, i);
			}
		}
		rset.close();
		selectStmt.close();
		DBConnection.getDatabaseWrapper().getConnection().setTransactionIsolation(DBConnection.getDatabaseWrapper().getConnection().TRANSACTION_READ_COMMITTED);
		DBConnection.getDatabaseWrapper().getConnection().commit();

	}

	private Boolean identicalValues(String[] newValues, int index) 
	{
		boolean retVal = true;
		for (int i = 0; i < newValues.length; i++) 
		{
			if ((SortUtils.getLatCyrCollator().compare(newValues[i], ((String) getValueAt(index, i)).trim()) != 0))
			{
				retVal = false;
				break;
			}
		}
		return retVal;
	}

	
	
}
