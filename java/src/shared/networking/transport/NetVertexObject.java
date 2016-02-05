package shared.networking.transport;

public abstract class NetVertexObject
{
	int owner;
	NetDirectionalLocation netEdgeLocation;
	
	/**
	 * Default constructor, sets owner to -1 and
	 * instantiates a default NetEdgeLocation
	 */
	public NetVertexObject()
	{
		owner = -1;
		netEdgeLocation = new NetDirectionalLocation();
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
	public NetDirectionalLocation getNetEdgeLocation()
	{
		return netEdgeLocation;
	}
	/**
	 * @param netEdgeLocation the netEdgeLocation to set
	 */
	public void setNetEdgeLocation(NetDirectionalLocation netEdgeLocation)
	{
		this.netEdgeLocation = netEdgeLocation;
	}

}
