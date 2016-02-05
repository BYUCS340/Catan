package shared.networking.transport;

public class NetRoad
{
	int ownerID;
	NetDirectionalLocation netEdgeLocation;
	
	/**
	 * Default constructor, sets ownerID to -1 and
	 * instantiates a default NetEdgeLocation
	 */
	public NetRoad()
	{
		ownerID = -1;
		netEdgeLocation = new NetDirectionalLocation();
	}

	/**
	 * @return the ownerID
	 */
	public int getOwnerID()
	{
		return ownerID;
	}

	/**
	 * @param ownerID the ownerID to set
	 */
	public void setOwnerID(int ownerID)
	{
		this.ownerID = ownerID;
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
