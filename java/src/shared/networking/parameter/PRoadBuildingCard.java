package shared.networking.parameter;

import java.io.Serializable;

import shared.locations.EdgeLocation;

public class PRoadBuildingCard implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2284077430240273139L;
	private EdgeLocation location1;
	private EdgeLocation location2;
	/**
	 * @return the location1
	 */
	public EdgeLocation getLocation1() {
		return location1;
	}
	/**
	 * @param location1 the location1 to set
	 */
	public void setLocation1(EdgeLocation location1) {
		this.location1 = location1;
	}
	/**
	 * @return the location2
	 */
	public EdgeLocation getLocation2() {
		return location2;
	}
	/**
	 * @param location2 the location2 to set
	 */
	public void setLocation2(EdgeLocation location2) {
		this.location2 = location2;
	}
	
	
}
