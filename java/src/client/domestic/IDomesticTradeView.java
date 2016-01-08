package client.domestic;

import client.base.*;

/**
 * Interface of the domestic trade view, which contains the "Domestic Trade"
 * button
 */
public interface IDomesticTradeView extends IView
{
	
	/**
	 * Enables or disables the domestic trade button.
	 * 
	 * @param value
	 *            Whether or not the domestic trade button is enabled
	 */
	void enableDomesticTrade(boolean value);
}

