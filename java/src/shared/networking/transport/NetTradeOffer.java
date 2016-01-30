package shared.networking.transport;

public class NetTradeOffer
{
	
	int sender;
	int receiver;
	NetResourceList netResourceList;
	/**
	 * @return the sender
	 */
	public int getSender()
	{
		return sender;
	}
	/**
	 * @param sender the ID of the player who sent the trade offer
	 */
	public void setSender(int sender)
	{
		this.sender = sender;
	}
	/**
	 * @return the receiver
	 */
	public int getReceiver()
	{
		return receiver;
	}
	/**
	 * @param receiver the ID of the player who received the trade offer
	 */
	public void setReceiver(int receiver)
	{
		this.receiver = receiver;
	}
	/**
	 * @return the netResourceList
	 */
	public NetResourceList getNetResourceList()
	{
		return netResourceList;
	}
	/**
	 * @param netResourceList the netResourceList to set
	 */
	public void setNetResourceList(NetResourceList netResourceList)
	{
		this.netResourceList = netResourceList;
	}
	
	
}
