package databaseModel;

/**
 * Column model from database.
 * @author marko
 *
 */
public class DatabaseColumnModel 
{
	private String code;
	private String label;
	private String type;
	private int length; 
	private boolean primary_key;
	private boolean foreign_key;
	private boolean nullable;
	private String tableParent;
	private String codeInParent;
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public boolean isPrimary_key() {
		return primary_key;
	}
	public void setPrimary_key(boolean primary_key) {
		this.primary_key = primary_key;
	}
	public boolean isForeign_key() {
		return foreign_key;
	}
	public void setForeign_key(boolean foreign_key) {
		this.foreign_key = foreign_key;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public String getTableParent() {
		return tableParent;
	}
	public void setTableParent(String tableParent) {
		this.tableParent = tableParent;
	}
	public String getCodeInParent() {
		return codeInParent;
	}
	public void setCodeInParent(String codeInParent) {
		this.codeInParent = codeInParent;
	}
}
