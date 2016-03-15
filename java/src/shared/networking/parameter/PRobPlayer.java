package shared.networking.parameter;

import java.io.Serializable;

import shared.model.map.Coordinate;

public class PRobPlayer implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4306868465316221465L;
	private int victimIndex;
	private Coordinate location;
	
	public PRobPlayer()
	{
		
	}
	
	/**
	 * @param victimIndex
	 * @param location
	 */
	public PRobPlayer(int victimIndex, Coordinate location)
	{
		super();
		this.victimIndex = victimIndex;
		this.location = location;
	}
	/**
	 * @return the victimIndex
	 */
	public int getVictimIndex() {
		return victimIndex;
	}
	/**
	 * @param victimIndex the victimIndex to set
	 */
	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
	/**
	 * @return the location
	 */
	public Coordinate getLocation()
	{
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Coordinate location)
	{
		this.location = location;
	}

	
	
}
