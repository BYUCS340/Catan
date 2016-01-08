package client.domestic;

import client.base.*;
import shared.definitions.*;

/**
 * Interface for the domestic trade controller
 */
public interface IDomesticTradeController extends IController
{
	
	/**
	 * Called by the domestic trade view when the user clicks the domestic trade
	 * button.
	 */
	void startTrade();
	
	/**
	 * Called by the domestic trade overlay when the user decreases the amount
	 * of a resource.
	 * 
	 * @param resource
	 *            The resource whose amount is being decreased
	 */
	void decreaseResourceAmount(ResourceType resource);
	
	/**
	 * Called by the domestic trade overlay when the user increases the amount
	 * of a resource.
	 * 
	 * @param resource
	 *            The resource whose amount is being increased
	 */
	void increaseResourceAmount(ResourceType resource);
	
	/**
	 * Called by the domestic trade overlay when the user clicks the trade
	 * button.
	 */
	void sendTradeOffer();
	
	/**
	 * Called by the domestic trade overlay when the user selects a player to
	 * trade with.
	 * 
	 * @param playerIndex
	 *            The index [0, 3] of the selected trading partner, or -1 if
	 *            "None" was selected
	 */
	void setPlayerToTradeWith(int playerIndex);
	
	/**
	 * Called by the domestic trade overlay when the user selects a resource to
	 * be received.
	 * 
	 * @param resource
	 *            The resource to be received
	 */
	void setResourceToReceive(ResourceType resource);
	
	/**
	 * Called by the domestic trade overlay when the user selects a resource to
	 * be sent.
	 * 
	 * @param resource
	 *            The resource to be sent
	 */
	void setResourceToSend(ResourceType resource);
	
	/**
	 * Called by the domestic trade overlay when user selects "none" for a
	 * resource.
	 * 
	 * @param resource
	 *            The resource for which "none" was selected
	 */
	void unsetResource(ResourceType resource);
	
	/**
	 * Called by the domestic trade overlay when the user cancels a trade.
	 */
	void cancelTrade();
	
	/**
	 * Called by the accept trade overlay when the user either accepts or
	 * rejects a trade.
	 * 
	 * @param willAccept
	 *            Whether or not the user accepted the trade
	 */
	void acceptTrade(boolean willAccept);
}

