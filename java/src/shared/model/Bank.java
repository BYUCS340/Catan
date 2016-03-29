package shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import shared.definitions.DevCardType;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;

/**
 * The bank class holds resource cards, game pieces, and dev cards
 * @author matthewcarlson, garrettegan, parker ridd
 *
 */
public class Bank implements Serializable
{
	private static final long serialVersionUID = -4491817117883319092L;

	private int[] resources;
	private int[] devCards;
	private int[] pieces;
	
	private int numberResourceTypes = 5;
	private int numberDevCardTypes  = 5;
	private int numberPieceTypes    = 3; 
	
	private int solidersInPlay = 0;
	
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
	
	public void resetToBankDefaults()
	{
		try
		{
			this.giveDevCard(DevCardType.MONOPOLY, 5);
			this.giveDevCard(DevCardType.MONUMENT, 5);
			this.giveDevCard(DevCardType.ROAD_BUILD, 5);
			this.giveDevCard(DevCardType.SOLDIER, 5);
			this.giveDevCard(DevCardType.YEAR_OF_PLENTY, 5);
			
			int resourcesEach = 95/5;
			this.giveResource(ResourceType.WOOD, resourcesEach);
			this.giveResource(ResourceType.SHEEP, resourcesEach);
			this.giveResource(ResourceType.WHEAT, resourcesEach);
			this.giveResource(ResourceType.ORE, resourcesEach);
			this.giveResource(ResourceType.BRICK, resourcesEach);
			
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
	 * Returns the number of soliders you've played
	 * @return
	 */
	public int getNumberSolidersRecruited()
	{
		return this.solidersInPlay;
	}
	
	/**
	 * Adds one to the number of soliders you've played
	 */
	public void recruitSolider()
	{
		solidersInPlay++;
	}
	/**
	 * Gets a dev card of the specific type (taking it from the bank)
	 * @param type
	 * @throws ModelException
	 */
	public void getDevCard(DevCardType type) throws ModelException
	{
		if (this.getDevCardCount(type) < 1) throw new ModelException();
		devCards[type.ordinal()]--;
	}
	
	/**
	 * Attempts to get a piece (road, settlement, city). Local since it you can only get pieces out of bank by buying them
	 * @param type it can be anything but the robber
	 * @throws if there don't have the resource or if type robber
	 */
	public void getPiece(PieceType type) throws ModelException
	{
		if (type == PieceType.ROBBER) throw new ModelException();
		int resourceIndex = type.ordinal();
		if (this.pieces[resourceIndex] >= 1)
		{
			this.pieces[resourceIndex]--;
		}
		else
		{
			throw new ModelException ("Not enough resources to buy "+type);
		}
	}
	
	/**
	 * This gets a dev card which by default are shuffled when given. 
	 * @throws if there don't have the resource
	 * @return the type of the card you received or UNKNOWN if there are no more cards left
	 */
	public DevCardType getDevCard() throws ModelException
	{
		//TODO rewrite 
		
		
		//Make sure we have at least one dev card
		if (this.getDevCardCount() == 0) throw new ModelException("No Dev Cards");
		
		//implemented this http://stackoverflow.com/questions/6737283/weighted-randomness-in-java
		
		
		// Compute the total weight of all items together
		int totalWeight = 0;
		for (Integer i : this.devCards)
		{
		    totalWeight += i;
		}
		// Now choose a random item
		int randomIndex = -1;
		double random = Math.random() * totalWeight;
		for (int i = 0; i < this.devCards.length; i++)
		{
		    random -= this.devCards[i];
		    if (random <= 0 && this.devCards[i]> 0)
		    {
		        randomIndex = i;
		        break;
		    }
		}
		
		if (randomIndex == -1)
			throw new ModelException("No Dev Card was found");
		
		//take one from the array
		this.devCards[randomIndex] --;
		
		//Get the first one on the stack
		DevCardType isThisYourCard = DevCardType.fromInt(randomIndex);
		
			
		return isThisYourCard;
		
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
		if (amount < 0 ) throw new ModelException("Negative Amount");
		this.resources[type.ordinal()] += amount;
	}
	
	/**
	 * Gives a random resource from this player to the bank
	 * @return the resourcetype taken; null if this player doesn't have any
	 * resources
	 */
	public ResourceType takeRandomResource() throws ModelException
	{
		if(this.getResourceCount() == 0)
		{
			return null;
		}
		
		//create a list of the resources
		List<ResourceType> resourcesCanTake = new ArrayList<>();
		
		for (int i=0; i < this.getResourceCount(ResourceType.WOOD);  i++) resourcesCanTake.add(ResourceType.WOOD);
		for (int i=0; i < this.getResourceCount(ResourceType.SHEEP); i++) resourcesCanTake.add(ResourceType.SHEEP);
		for (int i=0; i < this.getResourceCount(ResourceType.WHEAT); i++) resourcesCanTake.add(ResourceType.WHEAT);
		for (int i=0; i < this.getResourceCount(ResourceType.BRICK); i++) resourcesCanTake.add(ResourceType.BRICK);
		for (int i=0; i < this.getResourceCount(ResourceType.ORE);   i++) resourcesCanTake.add(ResourceType.ORE);
		
		if (resourcesCanTake.isEmpty()) return null;
		
		int resourceIndex = (int) (Math.random() * resourcesCanTake.size() );
		
		this.getResource(resourcesCanTake.get(resourceIndex));
		
		return resourcesCanTake.get(resourceIndex);		
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
		if (amount < 0 ) throw new ModelException("Trying to add "+amount+" to piece type "+type);
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
	 * 
	 * @param type
	 * @param amount
	 */
	private void giveDevCard(DevCardType type, int amount)
	{
		this.devCards[type.ordinal()]+= amount;
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
			throw new ModelException("You can't afford a city");
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
	
	//TEMP DEV CARD STUFF  ==========================================
	//This is needed in order to get the counts correct on the view
	//without allowing these new cards to be played.
	private int[] newDevCards = new int[numberDevCardTypes];
	public int getNewDevCardCount(DevCardType type)
	{
		return this.newDevCards[type.ordinal()];
	}
	public void giveNewDevCard(DevCardType type)
	{
		this.newDevCards[type.ordinal()]++;
	}
	public void newToOldDevs()
	{
		for(int i = 0; i < this.numberDevCardTypes; i++)
		{
			this.devCards[i] = this.devCards[i] + this.newDevCards[i];
			this.newDevCards[i] = 0;
		}
	}
	//TEMP DEV CARD STUFF  ==========================================
	
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bank [" + (resources != null ? "resources=" + Arrays.toString(resources) + ", " : "")
				+ (devCards != null ? "devCards=" + Arrays.toString(devCards) + ", " : "")
				+ (pieces != null ? "pieces=" + Arrays.toString(pieces) + ", " : "")
				+ (newDevCards != null ? "newDevCards=" + Arrays.toString(newDevCards) : "") + "]";
	}
	
	public String resourcesToString()
	{
		return "resources=" + Arrays.toString(resources);
	}

	/**
	 * Gives the bank the resources to buy this piece 
	 * @param piece
	 */
	public void giveResourcesFor(PieceType piece) 
	{
		try 
		{
			switch(piece)
			{
			case ROAD:
				this.giveResource(ResourceType.BRICK, 1);
				this.giveResource(ResourceType.WOOD, 1);
				break;
			case CITY:
				this.giveResource(ResourceType.ORE, 3);
				this.giveResource(ResourceType.WHEAT, 2);
				break;
			case SETTLEMENT:		
				this.giveResource(ResourceType.BRICK, 1);
				this.giveResource(ResourceType.WOOD, 1);
				this.giveResource(ResourceType.SHEEP, 1);
				this.giveResource(ResourceType.WHEAT, 1);
				break;
	
			default:
				break;
			}
		} 
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Gives the bank the resources for a dev card
	 */
	public void giveResourcesForDevCard() 
	{
		try
		{
			this.giveResource(ResourceType.ORE, 1);
			this.giveResource(ResourceType.SHEEP, 1);
			this.giveResource(ResourceType.WHEAT, 1);
		}
		catch (ModelException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
