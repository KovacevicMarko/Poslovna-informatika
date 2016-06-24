package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;


public class ZoomFormAction extends AbstractAction 
{

	private static final long serialVersionUID = 1L;
	private static final String modelName = "Zoom";
	private JDialog standardForm;
	
	public ZoomFormAction(JDialog standardForm) 
	{
		putValue(SHORT_DESCRIPTION, modelName);
		putValue(NAME, "...");
		this.standardForm = standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{

	}
	
}
