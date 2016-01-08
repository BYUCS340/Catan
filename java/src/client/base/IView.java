package client.base;

/**
 * Base interface for views
 */
public interface IView
{
	
	/**
	 * Controller setter
	 * 
	 * @param controller
	 *            The view's controller
	 */
	void setController(IController controller);
	
	/**
	 * Controller getter
	 * 
	 * @return The view's controller
	 */
	IController getController();
}

