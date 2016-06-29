package util;

import java.util.Calendar;

import actions.main.form.CliringAction;

public class CliringThread extends Thread 
{
	private CliringAction cliringAction;
	
	private String[] timeToSendCliring = new String[]{"22:10", "22:00"};
	
	public CliringThread()
	{
		cliringAction = new CliringAction();
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			
			if(CheckDate())
			{
				cliringAction.executeClearing();
				try 
				{
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try 
			{
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean CheckDate()
	{
		Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		int minute = rightNow.get(Calendar.MINUTE);
		
		for(String s: timeToSendCliring)
		{
			if(s.split(":")[0].equals(Integer.toString(hour)) && s.split(":")[1].equals(Integer.toString(minute)))
			{
				return true;
			}
		}
		return false;
		
	}

}
