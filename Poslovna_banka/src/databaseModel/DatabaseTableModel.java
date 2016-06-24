package databaseModel;

import java.util.Vector;

/**
 * Table model from database.
 * @author marko
 *
 */
public class DatabaseTableModel 
{
	private String code;
	private String label;
	private Vector<String> nextTables = new Vector<String>();
	private Vector<DatabaseColumnModel> columnsModel;
	
	
	/**
	 * Get column model for table code.
	 * @param code
	 * @return
	 */
	public DatabaseColumnModel getColumnModel(String code) 
	{
		for(int i = 0; i < columnsModel.size(); i++) 
		{
			if(columnsModel.get(i).getCode().equals(code)) 
			{
				return columnsModel.get(i);
			}
		}
		return null;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Vector<String> getNextTables() {
		return nextTables;
	}
	public void setNextTables(Vector<String> nextTables) {
		this.nextTables = nextTables;
	}
	public Vector<DatabaseColumnModel> getcolumnsModel() {
		return columnsModel;
	}
	public void setcolumnsModel(Vector<DatabaseColumnModel> columnsModel) {
		this.columnsModel = columnsModel;
	}
	
	public void addNextTable(String tableCode) {
		if(nextTables == null) {
			nextTables = new Vector<String>();
		}
		nextTables.add(tableCode);
	}
}
