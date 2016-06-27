package exceptionHandler;

import java.sql.SQLException;

import application.App;

/**
 * 
 *	
 *	Prepoznaje i razvrstava <i>SQLException</i> poruke. 
 *  Poseduje metodu koja vraca korisniku korigovanu poruku.
 *	
 *	
 *  @author Vladan Desnica
 */



public class SqlExceptionHandler {

	private String message;
	
	public static String primaryKey="Violation of PRIMARY KEY";
	
	public static String deleteReferncedFK = "The DELETE statement conflicted";
	
	public static String insertReferncedFK = "The INSERT statement conflicted";
	
	public static String updateReferncedFK = "The UPDATE statement conflicted";
	
	
	public static String nullKey = "Cannot insert the value NULL into column";
	
	public SqlExceptionHandler(String message) {
		// TODO Auto-generated constructor stub
		
		this.message = message;
		
		
		//System.out.println("PORUKA JE + " + message);
	}
	
	/**
	 * 
	 *	
	 *	Parsira <i>SQLException</i> poruku i vraca korigovanu poruku korisniku.
	 *	
	 *  @param tableCode - SQLException poruka koja se parsira
	 *  @return poruku za korisnika
	 */
	
	
	
	public String getHandledMessage(String tableCode){
		String retVal = "";
		String value = "dsdsdsd";
		String tableName = "";
		
		if(message != null){
			if(message.startsWith(primaryKey)){
				
				value = message.split("\\(")[1];
				value = value.split("\\)")[0];
				
				tableName = App.getInstance().getResourceBundle().getString(tableCode);
				
				retVal = tableName + " " + App.getInstance().getResourceBundle().getString("SAOZNAKOM") 
						+ " " + value + " " + App.getInstance().getResourceBundle().getString("VECPOSTOJI");
				
			}
			
			else if(message.startsWith(deleteReferncedFK)){
				
			
				String table2 = message.split("dbo.")[1].split("\"")[0];
				
				tableName = App.getInstance().getResourceBundle().getString(table2);
				
				retVal = App.getInstance().getResourceBundle().getString("NEMOGUCEOBRISATIRED") + App.getInstance().getResourceBundle().getString(tableCode) + 
						 App.getInstance().getResourceBundle().getString("GUBISINFO") + tableName+"."; 
				
				
				
				
			}
			
			else if(message.startsWith(insertReferncedFK)){
				
				
				String table2 = message.split("dbo.")[1].split("\"")[0];
				
				tableName = App.getInstance().getResourceBundle().getString(table2);
				
				retVal = App.getInstance().getResourceBundle().getString("NEMOGUCEDODATIRED") + " " + App.getInstance().getResourceBundle().getString(tableCode) + 
						 App.getInstance().getResourceBundle().getString("NEMAOZNAKA") + " " + tableName+"."; 
			}
			
			else if(message.startsWith(nullKey)){
				
				String column = message.split("\'")[1];
				
				retVal = App.getInstance().getResourceBundle().getString("UNESIVREDNOST")+ " " + column + App.getInstance().getResourceBundle().getString("UTABELI")+ " " + App.getInstance().getResourceBundle().getString(tableCode)+".";
			}
			
			else if(message.startsWith(updateReferncedFK)){
				
				
				String table2 = message.split("dbo.")[1].split("\"")[0];
				
				tableName = App.getInstance().getResourceBundle().getString(table2);
				
				retVal = App.getInstance().getResourceBundle().getString("NEMOGUCEAZURIRATIRED")+ " " + App.getInstance().getResourceBundle().getString(tableCode) + 
						App.getInstance().getResourceBundle().getString("NEMAOZNAKA")+ " " + tableName+"."; 
			}
			else
			{
				retVal = App.getInstance().getResourceBundle().getString("NEISPRAVNOUNETIPODACI");
			}
		}else{
			retVal = App.getInstance().getResourceBundle().getString("NEISPRAVNOUNETIPODACI");
		}
		
		return retVal;
	}
	
	
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
