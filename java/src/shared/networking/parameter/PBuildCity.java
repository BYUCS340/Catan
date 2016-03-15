package shared.networking.parameter;

import java.io.Serializable;

import shared.model.map.Coordinate;

public class PBuildCity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9091624117293202149L;
	private Coordinate location;

	/**
	 * @return the vertexLocation
	 */
	public Coordinate getLocation() {
		return location;
	}

	/**
	 * @param location the vertexLocation to set
	 */
	public void setLocation(Coordinate location) {
		this.location = location;
	}
	
}
