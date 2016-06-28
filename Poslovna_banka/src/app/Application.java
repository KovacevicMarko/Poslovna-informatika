package app;

import gui.dialogs.LoginDialog;
import gui.main.form.MainFrame;

import javax.swing.UIManager;

public class Application 
{
	
	public static void main (String[] args)
	{
		UIManager.put("OptionPane.yesButtonText", "Da");
		UIManager.put("OptionPane.noButtonText", "Ne");
		UIManager.put("OptionPane.cancelButtonText", "Otkaži");
		
		MainFrame frame = MainFrame.getInstance();
		frame.setVisible(true);
		
		//LoginDialog dialog = new LoginDialog(frame);
	//	dialog.setVisible(true);
	}

}
