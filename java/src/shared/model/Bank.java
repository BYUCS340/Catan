package shared.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import shared.definitions.*;

/**
 * The bank class holds resource cards, game pieces, and dev cards
 * @author matthewcarlson, garrettegan
 *
 */
public class Bank
{
	private int[] resources;
	private int[] devCards;
	private int[] pieces;
	
	private int numberResourceTypes = 5;
	private int numberDevCardTypes  = 5;
	private int numberPieceTypes    = 3; 
	
	/**
	 * Creates a bank
	 */
	public Bank()
	{
		super();
		this.resources = new int[numberResourceTypes];
		this.devCards = new int[numberDevCardTypes];
		this.pieces = new int[numberPieceTypes];
	}
	
	/**
	 * Resets the bank to the defaults of a player
	 */
	public void resetToPlayerDefaults()
	{
		try
		{
			this.givePiece(PieceType.SETTLEMENT, 5);
			this.givePiece(PieceType.CITY, 4);
			this.givePiece(PieceType.ROAD, 15);
		}
		catch (Exception e)
		{
			System.err.println("An Error has occured in the bank that should not have occurred");
		}
	}
	
	/**
	 * Attempts to get a resource from the bank
	 * @param type the type requested
	 * @throws if there don't have the resource
	 * @return true or false depending on whether it was successful or not
	 */
	public void getResource(ResourceType type) throws ModelException
	{
		this.getResource(type,1);
	}
	
	/**
	 * Gets a resource of the type requested at the amount requested
	 * @param type
	 * @param amount 
	 * @throws ModelException if not enough of type
	 */
	public void getResource(ResourceType type, int amount) throws ModelException
	{
		int resourceIndex = type.ordinal();

		if (amount > 0 && this.resources[resourceIndex] >= amount)
		{
			this.resources[resourceIndex]-= amount;
		}
		else
		{
			throw new ModelException ();
		}
	}
	
	/**
	 * Attempts to get a piece (road, settlement, city). Local since it you can only get pieces out of bank by buying them
	 * @param type it can be anything but the robber
	 * @throws if there don't have the resource or if type robber
	 */
	private void getPiece(PieceType type) throws ModelException
	{
		if (type == PieceType.ROBBER) throw new ModelException();
		int resourceIndex = type.ordinal();
		if (this.pieces[resourceIndex] >= 1)
		{
			this.pieces[resourceIndex]--;
		}
		else
		{
			throw new ModelException ();
		}
	}
	
	/**
	 * This gets a dev card which by default are shuffled when given. 
	 * @throws if there don't have the resource
	 * @return the type of the card you received or UNKNOWN if there are no more cards left
	 */
	public DevCardType getDevCard() throws ModelException
	{
		//Make sure we have at least one dev card
		if (this.getDevCardCount() == 0) throw new ModelException();
		
		//Create a list of cards with at least one card
		List<DevCardType> availableCards = new ArrayList<DevCardType>();
		for (int i = 0; i <= this.numberDevCardTypes; i++)
		{
			if (this.devCards[i] > 0)
				availableCards.add(DevCardType.fromInt(i));
		}
		//Shuffle that up
		Collections.shuffle(availableCards);
		
		
		//Get the first one on the stack
		DevCardType isThisYourCard = availableCards.get(0);
		
		
		//Check to make sure just in case
		if (isThisYourCard == null)
		{
			throw new ModelException();
		}
		else
		{
			//take one from the array
			this.devCards[isThisYourCard.ordinal()] --;
			return isThisYourCard;
		}
		//WTF David Blane! (You'll thank me later)
		// https://youtu.be/AYxu_MQSTTY
		// https://youtu.be/wTqsV3q7rRU
		
	}
	
	/**
	 * Give a single resource of type
	 * @param type
	 * @throws ModelException
	 */
	public void giveResource(ResourceType type) throws ModelException
	{
		this.giveResource(type,1);
	}
	
	/**
	 * Gives the bank amount of type resource of type
	 * @param type the resource given
	 * @param the amount to give
	 * @throws ModelException 
	 */
	public void giveResource(ResourceType type, int amount) throws ModelException
	{
		if (amount <= 0 ) throw new ModelException();
		this.resources[type.ordinal()] += amount;
	}
	
	/**
	 * Gives the bank one piece
	 * @param type
	 * @throws ModelException if type robber passed in
	 */
	public void givePiece(PieceType type) throws ModelException
	{
		this.givePiece(type,1);
	}
	
	/**
	 * 
	 * @param type
	 * @param amount
	 * @throws ModelException if type robber passed in or if try to give negative amount
	 */
	public void givePiece(PieceType type, int amount) throws ModelException
	{
		if (type == PieceType.ROBBER) throw new ModelException();
		if (amount < 0 ) throw new ModelException();
		this.pieces[type.ordinal()] += amount;
	}
	
	/**
	 * Gives the bank one dev card- it will be shuffled into pile
	 * @param type
	 */
	public void giveDevCard(DevCardType type)
	{
		this.devCards[type.ordinal()]++;
	}
	
	/**
	 * Gets the current number of all held resources
	 * @return the number of resources
	 */
	public int getResourceCount()
	{
		int total = 0;
		for (int i = 0; i < this.numberResourceTypes; i++)
		{
			total += this.resources[i];
		}
		return total;
	}
	
	/**
	 * returns the number of resources for a specified type
	 * @param type
	 * @return
	 */
	public int getResourceCount(ResourceType type)
	{
		return this.resources[type.ordinal()];
	}
	
	/**
	 * Gets the number of pieces available for a type held
	 * @param type
	 * @return
	 */
	public int getPieceCount(PieceType type) throws ModelException
	{
		if (type == PieceType.ROBBER) throw new ModelException();
		return this.pieces[type.ordinal()];
	}
	
	/**
	 * Gets the number of dev cards of a type held
	 * @param type
	 * @return
	 */
	public int getDevCardCount(DevCardType type)
	{
		return this.devCards[type.ordinal()];
	}
	
	/**
	 * A little bit of a simple method but eh.
	 * @return the number of armys in the bank
	 */
	public int getArmyCount()
	{
		return this.getDevCardCount(DevCardType.SOLDIER);
	}
	
	/**
	 * Gets the total number of dev cards held
	 * @return
	 */
	public int getDevCardCount()
	{
		int total = 0;
		for (int i=0; i< this.numberDevCardTypes; i++)
		{
			total += this.devCards[i];
		}
		return total;
	}
	
	
	
	
	/**
	 * Checks if has enough cards to build a road (one wood, one brick, one road)
	 * @return true if allowed, false otherwise
	 */
	public boolean canBuildRoad()
	{
		int woodIndex = ResourceType.WOOD.ordinal();
		int brickIndex = ResourceType.BRICK.ordinal();
		int roadIndex = PieceType.ROAD.ordinal();
		
		if (this.resources[woodIndex] > 0 && this.resources[brickIndex] > 0
				&& this.pieces[roadIndex] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Removes resource cards, if allowed, needed to build a road (one wood, one brick, one road)
	 * @throws if they don't have the resource
	 */
	public void buildRoad() throws ModelException
	{
		if (canBuildRoad())
		{
			int woodIndex = ResourceType.WOOD.ordinal();
			int brickIndex = ResourceType.BRICK.ordinal();
			int roadIndex = PieceType.ROAD.ordinal();
			
			this.resources[woodIndex]--;
			this.resources[brickIndex]--;
			this.pieces[roadIndex]--;
		}
		else
		{
			throw new ModelException();
		}
	}
	
	/**
	 * Checks if has enough cards to build a settlement (one wood, one brick, one sheep, one wheat, one settlement)
	 * @return true if allowed, false otherwise
	 */
	public boolean canBuildSettlement()
	{
		int woodIndex = ResourceType.WOOD.ordinal();
		int brickIndex = ResourceType.BRICK.ordinal();
		int sheepIndex = ResourceType.SHEEP.ordinal();
		int wheatIndex = ResourceType.WHEAT.ordinal();
		int settlementIndex = PieceType.SETTLEMENT.ordinal();
		
		if (this.resources[woodIndex] > 0 && this.resources[brickIndex] > 0
				&& this.resources[sheepIndex] > 0 && this.resources[wheatIndex] > 0
				&& this.pieces[settlementIndex] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Removes resource cards, if allowed, needed to build a settlement (one wood, one brick, one sheep, one wheat, one settlement)
	 * @return true if successful, false otherwise
	 * @throws if they don't have the resource
	 */
	public void buildSettlement() throws ModelException
	{
		if (canBuildSettlement())
		{
			int woodIndex = ResourceType.WOOD.ordinal();
			int brickIndex = ResourceType.BRICK.ordinal();
			int sheepIndex = ResourceType.SHEEP.ordinal();
			int wheatIndex = ResourceType.WHEAT.ordinal();
			int settlementIndex = PieceType.SETTLEMENT.ordinal();
			
			this.resources[woodIndex]--;
			this.resources[brickIndex]--;
			this.resources[sheepIndex]--;
			this.resources[wheatIndex]--;
			this.pieces[settlementIndex]--;
		}
		else
		{
			throw new ModelException();
		}
	}
	
	/**
	 * Checks if has enough cards to build a city (two wheat, three ore, one city)
	 * @return true if allowed, false otherwise
	 */
	public boolean canBuildCity()
	{
		int wheatIndex = ResourceType.WHEAT.ordinal();
		int oreIndex = ResourceType.ORE.ordinal();
		int cityIndex = PieceType.CITY.ordinal();
		
		if (this.resources[wheatIndex] >= 2 && this.resources[oreIndex] >= 3
				&& this.pieces[cityIndex] >= 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Removes resource cards, if allowed, needed to build a city (two wheat, three ore, one city)
	 * @throws if they don't have the resource
	 */
	public boolean buildCity() throws ModelException
	{
		//Requires a wheat, an ore, 
		if (canBuildCity())
		{
			int wheatIndex = ResourceType.WHEAT.ordinal();
			int oreIndex = ResourceType.ORE.ordinal();
			int cityIndex = PieceType.CITY.ordinal();
			int settlementIndex = PieceType.SETTLEMENT.ordinal();
			
			this.resources[wheatIndex] -= 2;
			this.resources[oreIndex] -= 3;
			this.pieces[cityIndex]--;
			//give back the settlement
			this.pieces[settlementIndex]++;
			return true;
		}
		else
		{
			throw new ModelException();
		}
	}
	
	/**
	 * Checks if has enough cards to buy a development card (one wheat, one sheep, one ore)
	 * @return true if allowed, false otherwise
	 */
	public boolean canBuyDevCard()
	{
		int wheatIndex = ResourceType.WHEAT.ordinal();
		int sheepIndex = ResourceType.SHEEP.ordinal();
		int oreIndex = ResourceType.ORE.ordinal();
		
		if (this.resources[wheatIndex] > 0 && this.resources[sheepIndex] > 0
				&& this.resources[oreIndex] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Removes resource cards, if allowed, needed to buy a development card (one wheat, one sheep, one ore)
	 * @throws if they don't have the resource
	 */
	public void buyDevCard() throws ModelException
	{
		//Requires 1 Wheat, 1 Sheep, 1 Ore
		if (canBuyDevCard())
		{
			int wheatIndex = ResourceType.WHEAT.ordinal();
			int sheepIndex = ResourceType.SHEEP.ordinal();
			int oreIndex = ResourceType.ORE.ordinal();
			
			this.resources[wheatIndex]--;
			this.resources[sheepIndex]--;
			this.resources[oreIndex]--;
		}
		else
		{
			throw new ModelException();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(devCards);
		result = prime * result + Arrays.hashCode(pieces);
		result = prime * result + Arrays.hashCode(resources);
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Bank))
			return false;
		Bank other = (Bank) obj;
		if (!Arrays.equals(devCards, other.devCards))
			return false;
		if (!Arrays.equals(pieces, other.pieces))
			return false;
		if (!Arrays.equals(resources, other.resources))
			return false;
		return true;
	}
}
