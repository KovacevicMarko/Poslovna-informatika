package util;

import java.util.Date;

import javax.swing.JLabel;

public class TimeThread extends Thread {

	private JLabel label;
	
	public TimeThread(JLabel label){
		this.label = label;
	}
	
	@Override
	public void run() {
		while(true){
			label.setText(new Date().toString());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
