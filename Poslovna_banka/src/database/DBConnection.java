package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import databaseModel.DatabaseColumnModel;

public class DBConnection {

	private static Connection conn;
	private static DBConnection dbConnection;
	private static DatabaseMetaData dbMetadata;

	public static DBConnection getDatabaseWrapper() 
	{
		if(dbConnection == null)
		{
			try {
				open();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return new DBConnection();
		}else
		{
			return dbConnection;
		}
	}
	
	
	public Connection getConnection()
	{	
		if (conn == null)
			try {
				open();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return conn;
	}

	public static void open() throws ClassNotFoundException, SQLException 
	{
		if (conn != null)
			return;
		ResourceBundle bundle =
				PropertyResourceBundle.getBundle("DBConnection"); //ime fajla
		String driver = bundle.getString("driver"); //Ime parametara
		String url = bundle.getString("url");
		String username = bundle.getString("username");  
		String password = bundle.getString("password");
		Class.forName(driver); //Registrovanje drajvera
		conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		dbMetadata = conn.getMetaData();
	}

	public void close() 
	{
		try {
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Get all table codes.
	 * @return vector with table codes.
	 */
	public Vector<String> getTableCodes() 
	{
		String query = "SELECT * from INFORMATION_SCHEMA.TABLES;";
		Vector<String> result = new Vector<String>();
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			ResultSet rst = stmt.getResultSet();
			while(rst.next()) 
			{
				result.add(rst.getString(3));
			}
			stmt.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Vector<DatabaseColumnModel> getColumnModelByTableCode(String tableCode) {
		
		Vector<DatabaseColumnModel> result = new Vector<DatabaseColumnModel>();
		String query = "SELECT * from INFORMATION_SCHEMA.COLUMNS;";
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			ResultSet rst = stmt.getResultSet();
			while(rst.next()) 
			{
				if(rst.getString(3).equalsIgnoreCase(tableCode)) 
				{
					DatabaseColumnModel desc = new DatabaseColumnModel();
					desc.setCode(rst.getString(4));
					String yes_no = rst.getString(7);
					boolean nullable = false;
					if(yes_no.equalsIgnoreCase("YES")) 
					{
						nullable = true;
					}
					desc.setNullable(nullable);
					desc.setType(rst.getString(8));
					if(rst.getString(9) != null)
					{
						desc.setLength(new Integer(rst.getString(9)));
					}
					result.add(desc);
				}
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	public boolean isPrimaryKey(String tableCode, String columnCode) 
	{
		
		String query = "SELECT * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE;";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			ResultSet rst = stmt.getResultSet();
			while(rst.next()) 
			{
				if(rst.getString(6).equalsIgnoreCase(tableCode) 
						&& rst.getString(7).equalsIgnoreCase(columnCode)
						&& rst.getString(3).startsWith("PK")) 
				{
					return true;		
				}
					
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	public String PrimaryKeyColumnname(String tableCode, String columnCode)
	{
		
		String query = "SELECT * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE;";
		Statement stmt;
		try
		{
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			ResultSet rst = stmt.getResultSet();
			while(rst.next()) 
			{
				if(rst.getString(6).equalsIgnoreCase(tableCode) 
						&& rst.getString(7).equalsIgnoreCase(columnCode)
						&& rst.getString(3).startsWith("PK"))
				{
					return columnCode;		
				}
					
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	public boolean isForeignKey(String tableCode, String columnCode) 
	{
		
		String query = "SELECT * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE;";
		Statement stmt;
		try
		{
			stmt = conn.createStatement();
			stmt.executeQuery(query);
			ResultSet rst = stmt.getResultSet();
			while(rst.next()) {
				if(rst.getString(6).equalsIgnoreCase(tableCode) 
						&& rst.getString(7).equalsIgnoreCase(columnCode)
						&& rst.getString(3).startsWith("PK")) 
				{
					return true;		
				}
					
			}
		} 
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
		
	}
		
	public HashMap<String,String> getImportedTables(String tableName) 
	{
		
		String catalog = null;
		try {
			catalog = DBConnection.getDatabaseWrapper().getConnection().getCatalog();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String   schemaPattern    = null;//"dbo";
		String   tableNamePattern = tableName;
	
		HashMap<String,String> res = new HashMap<String,String>();
		Vector<String> columns = new Vector<String>();
		ResultSet result;
		try {
			result = dbMetadata.getImportedKeys(catalog, schemaPattern, tableNamePattern);
			while(result.next()) 
			{
			    String columnName = result.getString(7);
			    columns.add(columnName);
			    res.put(result.getString(4), result.getString(3));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	public Vector<String> getExportedTables(String tableName) 
	{
		
		String   catalog          = null;
		String   schemaPattern    = "dbo";
		String   tableNamePattern = tableName;
	
		Vector<String> columns = new Vector<String>();
		ResultSet result;
		try {
			result = dbMetadata.getExportedKeys(catalog, schemaPattern, tableNamePattern);
			while(result.next()) 
			{
			    String columnName = result.getString(7);
			    if(!columns.contains(columnName))
			    {
			    	columns.add(columnName);
			    }
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return columns;
		
	}
	
}
