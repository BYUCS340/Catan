package client.discard;

import client.base.*;
import shared.definitions.*;

/**
 * Discard view interface
 */
public interface IDiscardView extends IOverlayView
{
	
	/**
	 * Used to enable or disable the discard button.
	 * 
	 * @param enabled
	 *            Whether or not the discard button should be enabled
	 */
	void setDiscardButtonEnabled(boolean enabled);
	
	/**
	 * Sets the discard amount displayed for the specified resource.
	 * 
	 * @param resource
	 *            The resource for which the discard amount is being set
	 * @param amount
	 *            The new discard amount
	 */
	void setResourceDiscardAmount(ResourceType resource, int amount);
	
	/**
	 * Sets the maximum amount displayed for the specified resource.
	 * 
	 * @param resource
	 *            The resource for which the maximum amount is being set
	 * @param maxAmount
	 *            The new maximum amount
	 */
	void setResourceMaxAmount(ResourceType resource, int maxAmount);
	
	/**
	 * Used to specify whether or not the discard amount of the specified
	 * resource can be increased and decreased. (The buttons for increasing or
	 * decreasing the discard amount are only visible when the corresponding
	 * operations are allowed.)
	 * 
	 * @param resource
	 *            The resource for which amount changes are being enabled or
	 *            disabled
	 * @param increase
	 *            Whether or not the amount for the specified resource can be
	 *            increased
	 * @param decrease
	 *            Whether or not the amount for the specified resource can be
	 *            decreased
	 */
	void setResourceAmountChangeEnabled(ResourceType resource,
										boolean increase, boolean decrease);
	
	/**
	 * Sets the state message, which indicates how many cards a player has set
	 * to discard, and how many remain to set.
	 * 
	 * @param message
	 *            The new state message (e.g., "0/6")
	 */
	void setStateMessage(String message);
}

