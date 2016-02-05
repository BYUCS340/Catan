package shared.networking.transport;

import java.util.ArrayList;
import java.util.List;

public class NetGame 
{
	String title;
	int id;
	List<NetPlayer> netPlayers;
	
	
	/**
	 * Default constructor, sets title to null, id to -1, and 
	 * instantiates netPlayers to new arraylist
	 */
	public NetGame(){
		title = null;
		id = -1;
		netPlayers = new ArrayList<NetPlayer>();
	}


	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}


	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}


	/**
	 * @return the netPlayers
	 */
	public List<NetPlayer> getNetPlayers()
	{
		return netPlayers;
	}


	/**
	 * @param netPlayers the netPlayers to set
	 */
	public void setNetPlayers(List<NetPlayer> netPlayers)
	{
		this.netPlayers = netPlayers;
	}
	
	

}
