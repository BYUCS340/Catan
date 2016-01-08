package client.domestic;

import client.base.*;
import client.data.*;
import shared.definitions.*;

/**
 * Implementation of the domestic trade overlay, which allows the user to
 * propose a domestic trade
 */
public interface IDomesticTradeOverlay extends IOverlayView
{
	
	/**
	 * Resets the view to its initial state.
	 */
	void reset();
	
	/**
	 * Sets the opponents the local player may trade with. This should be called
	 * on initializing the controller, and only called once.
	 * 
	 * @param value
	 *            Information about the players that can be traded with
	 */
	void setPlayers(PlayerInfo[] value);
	
	/**
	 * Enables or disables the ability to select a player to trade with.
	 * 
	 * @param enable
	 *            Whether or not player selection is currently allowed
	 */
	void setPlayerSelectionEnabled(boolean enable);
	
	/**
	 * Sets the amount displayed for a resource.
	 * 
	 * @param resource
	 *            The resource for which the amount is being set
	 * @param amount
	 *            The string to be displayed as the resource's amount (can be
	 *            empty)
	 */
	void setResourceAmount(ResourceType resource, String amount);
	
	/**
	 * Enables or disables the ability to increase and decrease a resource's
	 * amount.
	 * 
	 * @param resource
	 *            The resource for which amount changing is being enabled or
	 *            disabled
	 * @param canIncrease
	 *            Whether or not the resource's amount may be increased
	 * @param canDecrease
	 *            Whether or not the resource's amount may be decreased
	 */
	void
			setResourceAmountChangeEnabled(ResourceType resource,
										   boolean canIncrease,
										   boolean canDecrease);
	
	/**
	 * Enables or disables the ability to select resources to trade.
	 * 
	 * @param enable
	 *            Whether or not the user can select resources to trade
	 */
	void setResourceSelectionEnabled(boolean enable);
	
	/**
	 * Sets the message on the button indicating the state of the trade.
	 * 
	 * @param message
	 *            The new state message
	 */
	void setStateMessage(String message);
	
	/**
	 * Enables or disables the trade button.
	 * 
	 * @param enable
	 *            Whether or not the user may currently execute the trade
	 */
	void setTradeEnabled(boolean enable);
	
	/**
	 * Enables or disables the cancel button.
	 * 
	 * @param enabled
	 *            Whether or not the user may cancel the trade operation
	 */
	void setCancelEnabled(boolean enabled);
	
}

