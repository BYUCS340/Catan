package shared.networking.transport;

public abstract class NetVertexObject
{
	int owner;
	NetDirectionalLocation netDirectionalLocation;
	
	/**
	 * Default constructor, sets owner to -1 and
	 * instantiates a default NetEdgeLocation
	 */
	public NetVertexObject()
	{
		owner = -1;
		netDirectionalLocation = new NetDirectionalLocation();
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
	public NetDirectionalLocation getNetDirectionalLocation()
	{
		return netDirectionalLocation;
	}
	/**
	 * @param netDirectionalLocation the netEdgeLocation to set
	 */
	public void setNetEdgeLocation(NetDirectionalLocation netDirectionalLocation)
	{
		this.netDirectionalLocation = netDirectionalLocation;
	}

}
