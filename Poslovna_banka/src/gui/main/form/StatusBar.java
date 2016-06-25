package gui.main.form;

import gui.standard.form.GenericDialog;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import util.EnumActiveMode;
import util.TimeThread;

public class StatusBar extends JPanel
{
	
	private JLabel labelWelcome = new JLabel("Dobrodosli");
	private JLabel labelName = new JLabel("");
	private JLabel labelActualState = new JLabel("");
	private JLabel labelDateTime = new JLabel("");
	
	public StatusBar() 
	{
        setLayout(new GridLayout(1,4,1,1));
                
        labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        labelWelcome.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(labelWelcome);
        
        labelName.setHorizontalAlignment(SwingConstants.CENTER);
        labelName.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(labelName);
        
        labelActualState.setHorizontalAlignment(SwingConstants.CENTER);
        labelActualState.setBorder(BorderFactory.createLoweredBevelBorder());
        add(labelActualState);
            
        labelDateTime.setHorizontalAlignment(SwingConstants.CENTER);
        labelDateTime.setBorder(BorderFactory.createLoweredBevelBorder());
        add(labelDateTime);
        
        Thread t = new TimeThread(labelDateTime);
		t.start();
      
	}
	
	/**
	 * Set actual state on state label.
	 */
	public void init()
	{
		
		if(GenericDialog.getMode() == EnumActiveMode.DODAVANJE)
		{
			labelActualState.setText("Rezim dodavanja");
		}
		else if(GenericDialog.getMode() == EnumActiveMode.PRETRAGA)
		{
			labelActualState.setText("Rezim pretrage");
		}
		else
		{
			labelActualState.setText("Rezim izmene");
		}
	}
	
	public JLabel getLabelName() {
		return labelName;
	}

	public void setLabelName(JLabel labelName) {
		this.labelName = labelName;
	}

	public JLabel getLabelActualState() {
		return labelActualState;
	}

	public void setLabelActualState(JLabel labelActualState) {
		this.labelActualState = labelActualState;
	}

	public JLabel getLabelDateTime() {
		return labelDateTime;
	}

	public void setLabelDateTime(JLabel labelDateTime) {
		this.labelDateTime = labelDateTime;
	}

}
