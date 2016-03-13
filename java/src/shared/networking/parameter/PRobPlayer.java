package shared.networking.parameter;

import java.io.Serializable;

import shared.locations.HexLocation;

public class PRobPlayer implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4306868465316221465L;
	private int victimIndex;
	private HexLocation location;
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
	public HexLocation getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	
	
}
