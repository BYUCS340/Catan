package client.resources;

import client.base.*;

/**
 * Interface for the resource bar view
 */
public interface IResourceBarView extends IView
{
	
	/**
	 * Sets the enable/disable state for the specified resource bar element
	 * 
	 * @param element
	 *            The resource bar element being enabled or disabled
	 * @param enabled
	 *            The new enable/disable state for the specified element
	 */
	void setElementEnabled(ResourceBarElement element, boolean enabled);
	
	/**
	 * Sets the amount for the specified resource bar element
	 * 
	 * @param element
	 *            The resource bar element whose amount is being set
	 * @param amount
	 *            The new amount for the specified element
	 */
	void setElementAmount(ResourceBarElement element, int amount);
}

