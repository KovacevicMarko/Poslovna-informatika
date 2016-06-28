package exceptionHandler;


/**
 * 
 *	
 *	Prepoznaje i razvrstava <i>SQLException</i> poruke. 
 *  Poseduje metodu koja vraca korisniku korigovanu poruku.
 *	
 *	
 *  @author Vladan Desnica
 */



public class SqlExceptionHandler 
{
	private static String primaryKey="Violation of PRIMARY KEY";
	
	private static String deleteReferncedFK = "The DELETE statement conflicted";
	
	private static String insertReferncedFK = "The INSERT statement conflicted";
	
	private static String updateReferncedFK = "The UPDATE statement conflicted";
	
	private static String dateInvalidFormat = "Conversion failed when converting";
	
	private static String nullKey = "Cannot insert the value NULL into column";
	
	/**
	 * 
	 *	
	 *	Parsira <i>SQLException</i> poruku i vraca korigovanu poruku korisniku.
	 *	
	 *  @param tableCode - SQLException poruka koja se parsira
	 *  @return poruku za korisnika
	 */
	public static String getHandledMessage(String tableCode, String message){
		String retVal = "";
		String value = "dsdsdsd";
		String tableName = "";
		
		if(message != null){
			if(message.startsWith(primaryKey))
			{	
				try
				{
					value = message.split("\\(")[1];
					value = value.split("\\)")[0];
					retVal = tableCode + " " + "sa oznakom: " + " " + value + " " + "vec postoji u bazi podataka.";	
				}
				catch(Exception e)
				{
					retVal = "Ne mozete da menjate primarni kljuc.";
				}
				
			}
			
			else if(message.startsWith(deleteReferncedFK))
			{
				tableName = message.split("dbo.")[1].split("\"")[0];
				retVal = "Ne moze se obrisati slog: " + tableCode + ", jer je referenciran u tabeli: " + tableName + ".";
			}
			else if(message.startsWith(insertReferncedFK))
			{
				tableName = message.split("dbo.")[1].split("\"")[0];	
				retVal = "Ne moze se dodati slog: " + tableCode + ", jer ne postoji u referencirajucoj tabeli: " + tableName + ".";
			}
			else if(message.startsWith(nullKey))
			{	
				String column = message.split("\'")[1];
				retVal = "Unesite vrednost u obavezno polje: " + column + " u tabeli " + tableCode + ".";
			}	
			else if(message.startsWith(updateReferncedFK))
			{
				tableName = message.split("dbo.")[1].split("\"")[0];
				retVal = "Ne moze se azurirati slog: " + tableCode + ", jer ne postoji oznaka u tabeli " + tableName + ".";
			}
			else if(message.startsWith(dateInvalidFormat))
			{
				retVal = "Datum nije unesen u validnom formatu. Format je yyyy/MM/dd";
			}
			else
			{
				System.out.println(message);
				retVal = "Neispravno uneti podaci. Proverite da li su sva obavezna polja uneta u odgovarajucem formatu.";
			}
		}
		else
		{
			retVal = "Neispravno uneti podaci. Proverite da li su sva obavezna polja uneta u odgovarajucem formatu.";
		}
		
		return retVal;
	}

}
