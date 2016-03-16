package shared.networking.parameter;

import java.io.Serializable;

import shared.model.map.Coordinate;

public class PSoldierCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7427874189043352063L;
	private int victimIndex;
	private Coordinate location;
	
	public PSoldierCard()
	{
		
	}
	
	/**
	 * @param victimIndex
	 * @param location
	 */
	public PSoldierCard(int victimIndex, Coordinate location)
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
	public Coordinate getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Coordinate location) {
		this.location = location;
	}
	
}
