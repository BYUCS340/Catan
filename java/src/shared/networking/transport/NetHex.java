package shared.networking.transport;

import shared.definitions.ResourceType;

public class NetHex
{
	NetHexLocation netHexLocation;
	ResourceType resourceType;
	int numberChit;
	
	
	/**
	 * Default constructor, instantiates a default NetHexLocation and 
	 * a null resourceType. The number chit is set to -1
	 */
	public NetHex()
	{
		netHexLocation = new NetHexLocation();
		resourceType = ResourceType.BRICK;
		numberChit = -1;
	}


	/**
	 * @return the netHexLocation
	 */
	public NetHexLocation getNetHexLocation()
	{
		return netHexLocation;
	}


	/**
	 * @param netHexLocation the netHexLocation to set
	 */
	public void setNetHexLocation(NetHexLocation netHexLocation)
	{
		this.netHexLocation = netHexLocation;
	}


	/**
	 * @return the resourceType
	 */
	public ResourceType getResourceType()
	{
		return resourceType;
	}


	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(ResourceType resourceType)
	{
		this.resourceType = resourceType;
	}


	/**
	 * @return the numberChit
	 */
	public int getNumberChit()
	{
		return numberChit;
	}


	/**
	 * @param numberChit the numberChit to set
	 */
	public void setNumberChit(int numberChit)
	{
		this.numberChit = numberChit;
	}
	
	public String toString(){
		if (resourceType == null)
			return "N/A "+ numberChit+ "@" + netHexLocation;
		else
			return resourceType.toString() + " "+ numberChit+ "@" + netHexLocation;
	}

		
	
	
}
