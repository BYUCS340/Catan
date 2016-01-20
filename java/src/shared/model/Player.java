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
	private boolean isRobot = false;
	
	public Player(String name, int index, CatanColor playerColor, boolean isHuman){
		this.isRobot = !isHuman;
		this.name = name;
		this.playerBank = new Bank();
		this.playerIndex = index;
		this.color = playerColor;
	}
	
	public boolean buildRoad()
	{
		return false;
	}
	public boolean buildSettlement()
	{
		return false;
	}
	public boolean buildCity()
	{
		return false;
	}
	
	/**
	 * A simple function to determine if the player is controlled by AI
	 * @return true if is a robot
	 */
	public boolean isARobot(){
		return isRobot;
	}
	
	/**
	 * Returns the playerIndex so it' can't be changed 
	 * @return 0 to 3
	 */
	public int playerIndex(){
		return playerIndex;
		
	}
}
