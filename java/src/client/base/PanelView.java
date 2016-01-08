package client.base;

import javax.swing.*;

/**
 * Base class for JPanel-based views
 */
@SuppressWarnings("serial")
public class PanelView extends JPanel implements IView
{
	
	private IController controller;
	
	public IController getController()
	{
		return controller;
	}
	
	public void setController(IController controller)
	{
		this.controller = controller;
	}
	
}

