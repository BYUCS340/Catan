package client.domestic;

import client.base.*;
import shared.definitions.*;

/**
 * Interface for the accept trade overlay, which allows the user to accept or
 * reject a proposed trade
 */
public interface IAcceptTradeOverlay extends IOverlayView
{
	
	/**
	 * Adds a resource that the player will receive to the view.
	 * 
	 * @param resource
	 *            The resource being received
	 * @param amount
	 *            The amount of the resource being received
	 */
	void addGetResource(ResourceType resource, int amount);
	
	/**
	 * Adds a resource that the player will give to the view.
	 * 
	 * @param resource
	 *            The resource being given
	 * @param amount
	 *            The amount of the resource being given
	 */
	void addGiveResource(ResourceType resource, int amount);
	
	/**
	 * Enables or disables the accept button
	 * 
	 * @param enable
	 *            Whether or not the accept button is enabled
	 */
	void setAcceptEnabled(boolean enable);
	
	/**
	 * Sets the name of the player offering the trade.
	 * 
	 * @param name
	 *            The offering player's name
	 */
	void setPlayerName(String name);
	
	/**
	 * Resets the overlay back to the original settings.
	 */
	void reset();
}

