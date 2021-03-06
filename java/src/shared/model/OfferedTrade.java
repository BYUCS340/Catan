package shared.model;

import java.io.Serializable;

import shared.definitions.ResourceType;

/**
 * The is the object the packages a trade
 * @author matthewcarlson
 *
 */
public class OfferedTrade implements Serializable
{
	private static final long serialVersionUID = -8757581898891413179L;

	private int fromPlayerID;
	private int toPlayerID;
	
	private int offeredWood;
	private int offeredWheat;
	private int offeredSheep;
	private int offeredOre;
	private int offeredBrick;
	
	private int wantedWood;
	private int wantedWheat;
	private int wantedSheep;
	private int wantedOre;
	private int wantedBrick;
	
	
	public void setOfferedResourceAmount(ResourceType type, int amount){
		switch(type){
		case WOOD:
			offeredWood = amount;
			break;
		case WHEAT:
			offeredWheat = amount;
			break;
		case SHEEP:
			offeredSheep = amount;
			break;
		case ORE:
			offeredOre = amount;
			break;
		case BRICK:
			offeredBrick = amount;
			break;
		}
	}
	
	public void setWantedResourceAmount(ResourceType type, int amount){
		switch(type){
		case WOOD:
			wantedWood = amount;
			break;
		case WHEAT:
			wantedWheat = amount;
			break;
		case SHEEP:
			wantedSheep = amount;
			break;
		case ORE:
			wantedOre = amount;
			break;
		case BRICK:
			wantedBrick = amount;
			break;
		}
	}
	
	public int getOfferedResourceAmount(ResourceType type){
		switch(type){
		case WOOD:
			return offeredWood;
		case WHEAT:
			return offeredWheat;
		case SHEEP:
			return offeredSheep;
		case ORE:
			return offeredOre;
		case BRICK:
			return offeredBrick;
		default:
			return -1;
		}
	}
	
	public int getWantedResourceAmount(ResourceType type){
		switch(type){
		case WOOD:
			return wantedWood;
		case WHEAT:
			return wantedWheat;
		case SHEEP:
			return wantedSheep;
		case ORE:
			return wantedOre;
		case BRICK:
			return wantedBrick;
		default:
			return -1;
		}
	}

	/**
	 * @return the fromPlayerID
	 */
	public int getFromPlayerID()
	{
		return fromPlayerID;
	}

	/**
	 * @param fromPlayerID the fromPlayerID to set
	 */
	public void setFromPlayerID(int fromPlayerID)
	{
		this.fromPlayerID = fromPlayerID;
	}

	/**
	 * @return the toPlayerID
	 */
	public int getToPlayerID()
	{
		return toPlayerID;
	}

	/**
	 * @param toPlayerID the toPlayerID to set
	 */
	public void setToPlayerID(int toPlayerID)
	{
		this.toPlayerID = toPlayerID;
	}
}
