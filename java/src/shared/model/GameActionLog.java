package shared.model;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.CatanColor;

/**
 * This keeps track of all the actions in the game
 * @author matthewcarlson
 *
 */
public class GameActionLog 
{
	private class GameAction
	{
		String action;
		int playerID;
	}
	
	private List<CatanColor> playerColors;
	private List<String> playerNames;
	private List<GameAction> actions;
	
	public GameActionLog()
	{
		playerColors = new ArrayList<>();
		playerNames  = new ArrayList<>();
		actions      = new ArrayList<>();
	}
	
	/**
	 * Sets a player's name and color
	 * @param playerID the number of the player
	 * @param name the name
	 * @param color
	 * @throws ModelException if bad playerID
	 */
	public void setPlayer(int playerID, String name, CatanColor color) throws ModelException
	{
		if (playerID < 0 || playerID > 3) 
			throw new ModelException();
		playerColors.set(playerID, color);
		playerNames.set(playerID, name);
	}
	
	/**
	 * Logs an action the game Action log
	 * @param playerID
	 * @param action
	 */
	public void logAction(int playerID, String action)
	{
		
	}
	
	

}
