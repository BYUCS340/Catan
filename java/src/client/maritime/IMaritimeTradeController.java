package client.maritime;

import client.base.*;
import shared.definitions.*;

/**
 * Interface for the maritime trade controller
 */
public interface IMaritimeTradeController extends IController
{
	
	/**
	 * Called by the maritime trade view when the user clicks the maritime trade
	 * button.
	 */
	void startTrade();
	
	/**
	 * Make the specified trade with the bank.
	 */
	void makeTrade();
	
	/**
	 * Called by the maritime trade overlay when the user cancels a trade.
	 */
	void cancelTrade();
	
	/**
	 * Called when the user selects the resource to get.
	 * 
	 * @param resource
	 *            The selected "get" resource
	 */
	void setGetResource(ResourceType resource);
	
	/**
	 * Called when the user selects the resource to give.
	 * 
	 * @param resource
	 *            The selected "give" resource
	 */
	void setGiveResource(ResourceType resource);
	
	/**
	 * Called when the player "undoes" their get selection.
	 */
	void unsetGetValue();
	
	/**
	 * Called when the player "undoes" their give selection.
	 */
	void unsetGiveValue();
	
}

