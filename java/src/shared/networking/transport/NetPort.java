package shared.networking.transport;

import shared.definitions.Direction;
import shared.definitions.ResourceType;

public class NetPort {
	int ratio;
	ResourceType resource;
	NetHexLocation netHexLocation;
	Direction direction;
	
	/**
	 * @return the ratio
	 */
	public int getRatio() {
		return ratio;
	}
	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	/**
	 * @return the resource
	 */
	public ResourceType getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceType resource) {
		this.resource = resource;
	}
	/**
	 * @return the netHexLocation
	 */
	public NetHexLocation getNetHexLocation() {
		return netHexLocation;
	}
	/**
	 * @param netHexLocation the netHexLocation to set
	 */
	public void setNetHexLocation(NetHexLocation netHexLocation) {
		this.netHexLocation = netHexLocation;
	}
	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public String toString()
	{
		return "NETPORT: "+this.resource+" at ratio "+ratio+" @"+this.netHexLocation+"->"+this.direction;
	}

	
	
}
