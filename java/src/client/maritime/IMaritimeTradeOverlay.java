package client.maritime;

import client.base.*;
import shared.definitions.*;

/**
 * Interface for the maritime trade overlay, which lets the user make a maritime
 * trade
 */
public interface IMaritimeTradeOverlay extends IOverlayView
{
	
	/**
	 * Resets the view to its initial state.
	 */
	void reset();
	
	/**
	 * Hides the "get" resources and undo components.
	 */
	void hideGetOptions();
	
	/**
	 * Hides the "give" resources and undo components.
	 */
	void hideGiveOptions();
	
	/**
	 * Selects the resource the user has selected to receive. Specifically, this
	 * method does the following: 1. Hides all the "get" resources except the
	 * selected one 2. Displays and disables the selected "get" resource 3.
	 * Displays the "get" undo components using the specified amount
	 * 
	 * @param selectedResource
	 *            The "get" resource selected by the user
	 * @param amount
	 *            The amount of the specified "get" resource the user will
	 *            receive (e.g., 1)
	 */
	void selectGetOption(ResourceType selectedResource, int amount);
	
	/**
	 * Selects the resource the user has selected to give. Specifically, this
	 * method does the following: 1. Hides all the "give" resources except the
	 * selected one 2. Displays and disables the selected "give" resource 3.
	 * Displays the "give" undo components using the specified amount
	 * 
	 * @param selectedResource
	 *            The "give" resource selected by the user
	 * @param amount
	 *            The amount of the specified "give" resource the user will give
	 *            (e.g., 3)
	 */
	void selectGiveOption(ResourceType selectedResource, int amount);
	
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
	
	/**
	 * Shows all the "get" resources, enabling the ones the player can receive,
	 * and disabling the ones he can't.
	 * 
	 * @param enabledResources
	 *            Array of "get" resources that should be enabled.
	 */
	void showGetOptions(ResourceType[] enabledResources);
	
	/**
	 * Shows all the "give" resources, enabling the ones the player can give,
	 * and disabling the ones he can't.
	 * 
	 * @param enabledResources
	 *            Array of "give" resources that should be enabled.
	 */
	void showGiveOptions(ResourceType[] enabledResources);
	
}

