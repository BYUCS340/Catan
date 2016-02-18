package shared.model;

import shared.definitions.CatanColor;
/**
 * A player in the game. Can be an AI player or a human player
 * @author matthewcarlson, garrettegan
 *
 */
public class Player
{
	public String name;
	public CatanColor color;
	public Bank playerBank;
	private int playerIndex;
	private int playerID;
	private boolean isRobot = false;
	
	/**
	 * Creates a Player
	 * @param name the name of the player
	 * @param index the player's index
	 * @param playerColor the color of the player's game pieces
	 * @param isHuman whether the player is a human or computer player
	 */
	public Player(String name, int index, CatanColor playerColor, boolean isHuman)
	{
		this.isRobot = !isHuman;
		this.name = name;
		this.playerBank = new Bank();
		this.playerIndex = index;
		this.color = playerColor;
		this.playerID = -1;
	}
	public Player(String name, int index, CatanColor playerColor, boolean isHuman, int ID)
	{
		this(name, index, playerColor, isHuman);
		this.playerID = ID;
	}
	
	/**
	 * A simple function to determine if the player is controlled by AI
	 * @return true if is a robot
	 */
	public boolean isARobot()
	{
		return isRobot;
	}
	
	/**
	 * Returns the playerIndex so it can't be changed 
	 * @return 0 to 3
	 */
	public int playerIndex()
	{
		return playerIndex;
	}
	
	/**
	 * Returns the player ID
	 * @return
	 */
	public int playerID()
	{
		return playerID;
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		return name + ":"+ color.name()+" "+playerID+"-"+playerIndex;
		
	}
}
