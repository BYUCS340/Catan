package shared.data;

import java.io.Serializable;
import java.util.*;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo implements Serializable
{
	private static final long serialVersionUID = 8471016022451788524L;
	private int id;
	private String title;
	private List<PlayerInfo> players;
	
	public GameInfo()
	{
		setId(-1);
		setTitle("");
		players = new ArrayList<PlayerInfo>();
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void addPlayer(PlayerInfo newPlayer)
	{
		players.add(newPlayer);
	}
	
	public void setPlayers(List<PlayerInfo> newPlayers)
	{
		players = newPlayers;
	}
	
	public void setPlayers(PlayerInfo[] newPlayers)
	{
		players.clear();
		for (int i=0; i< newPlayers.length; i++)
		{
			players.add(newPlayers[i]);
		}
	}
	
	public List<PlayerInfo> getPlayers()
	{
		return Collections.unmodifiableList(players);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameInfo [id=" + id + ", title=" + title + ", players=" + players + "]";
	}
	
	
}

