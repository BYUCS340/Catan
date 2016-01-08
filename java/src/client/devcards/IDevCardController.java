package client.devcards;

import client.base.*;
import shared.definitions.*;

/**
 * "Dev card" controller interface
 */
public interface IDevCardController extends IController
{
	
	/**
	 * This method displays the "buy dev card" view.
	 */
	void startBuyCard();
	
	/**
	 * This method is called when the user cancels out of buying a development
	 * card.
	 */
	void cancelBuyCard();
	
	/**
	 * This method is called when the user buys a development card.
	 */
	void buyCard();
	
	/**
	 * This method displays the "play dev card" view.
	 */
	void startPlayCard();
	
	/**
	 * This method is called when the user cancels out of playing a development
	 * card.
	 */
	void cancelPlayCard();
	
	/**
	 * This method is called when the user plays a monopoly development card.
	 * 
	 * @param resource
	 *            The resource to take from other players
	 */
	void playMonopolyCard(ResourceType resource);
	
	/**
	 * This method is called when the user plays a monument development card.
	 */
	void playMonumentCard();
	
	/**
	 * This method is called when the user plays a road build development card.
	 */
	void playRoadBuildCard();
	
	/**
	 * This method is called when the user plays a soldier development card.
	 */
	void playSoldierCard();
	
	/**
	 * This method is called when the user plays a year of plenty development
	 * card.
	 * 
	 * @param resource1
	 *            The first resource to gain
	 * @param resource2
	 *            The second resource to gain
	 */
	void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2);
}

