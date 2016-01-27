package shared.networking.transport;

public abstract class NetVertexObject
{
	int owner;
	NetEdgeLocation netEdgeLocation;
	
	/**
	 * Default constructor, sets owner to -1 and
	 * instantiates a default NetEdgeLocation
	 */
	public NetVertexObject()
	{
		
	}
	
	/**
	 * @return the owner
	 */
	public int getOwner()
	{
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(int owner)
	{
		this.owner = owner;
	}
	/**
	 * @return the netEdgeLocation
	 */
	public NetEdgeLocation getNetEdgeLocation()
	{
		return netEdgeLocation;
	}
	/**
	 * @param netEdgeLocation the netEdgeLocation to set
	 */
	public void setNetEdgeLocation(NetEdgeLocation netEdgeLocation)
	{
		this.netEdgeLocation = netEdgeLocation;
	}

}
