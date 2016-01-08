package client.devcards;

import client.base.*;
import shared.definitions.*;

/**
 * "Play dev card" view interface
 */
public interface IPlayDevCardView extends IOverlayView
{
	
	/**
	 * Resets the view to its initial state.
	 */
	void reset();
	
	/**
	 * Sets the enable/disable state for the specified card type.
	 * 
	 * @param cardType
	 *            The card type being enabled or disabled
	 * @param enabled
	 *            Whether or not to enable the card type
	 */
	void setCardEnabled(DevCardType cardType, boolean enabled);
	
	/**
	 * Sets the amount for the specified card type.
	 * 
	 * @param cardType
	 *            The card type the amount is being set for
	 * @param amount
	 *            The new card amount
	 */
	void setCardAmount(DevCardType cardType, int amount);
}

