package shared.networking.parameter;

import java.io.Serializable;

import shared.locations.EdgeLocation;

public class PBuildRoad implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5960849814196160677L;
	EdgeLocation edgeLocation;
	boolean free;
	/**
	 * @return the edgeLocation
	 */
	public EdgeLocation getEdgeLocation() {
		return edgeLocation;
	}
	/**
	 * @param edgeLocation the edgeLocation to set
	 */
	public void setEdgeLocation(EdgeLocation edgeLocation) {
		this.edgeLocation = edgeLocation;
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
