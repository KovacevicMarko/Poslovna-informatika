package util;

public class DatabaseModelHandler 
{
	
	public static String ConvertTableLabel(String label)
	{
		String retVal = label.toLowerCase();
		
		if(retVal.contains("."))
			retVal = retVal.replace(".", "  ");
		
		if(retVal.contains("_"))
			retVal = retVal.replace("_", " ");
		
		retVal = retVal.substring(0, 1).toUpperCase() + retVal.substring(1);
		
		return retVal;
	}
	
	public static String ConvertColumnLabel(String label)
	{
		String retVal = label.toLowerCase();
		
		
		if(retVal.contains("."))
		{
			try
			{
				retVal = retVal.split("\\.")[1];
			}
			catch(Exception e)
			{
				retVal = retVal.split("\\.")[0];
			}
			
		}
			
		
		if(retVal.contains("_"))
			retVal = retVal.replace("_", " ");
		
		return retVal;
	}

}
