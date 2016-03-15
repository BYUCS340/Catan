package shared.networking.parameter;

import java.io.Serializable;

import shared.model.map.Coordinate;

public class PBuildSettlement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 778817440625963270L;
	private Coordinate location;
	private boolean free;
	
	public PBuildSettlement()
	{
		
	}
	
	/**
	 * @param location
	 * @param free
	 */
	public PBuildSettlement(Coordinate location, boolean free)
	{
		super();
		this.location = location;
		this.free = free;
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
	/**
	 * @return the free
	 */
	public boolean isFree() {
		return free;
	}
	/**
	 * @param free the free to set
	 */
	public void setFree(boolean free) {
		this.free = free;
	}
}
