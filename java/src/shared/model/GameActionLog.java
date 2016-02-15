package shared.model;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.CatanColor;

/**
 * This keeps track of all the actions in the game
 * @author matthewcarlson, parker ridd
 *
 */
public class GameActionLog 
{
	private class GameAction
	{
		private String action;
		private int playerIndex;
		
		public GameAction(int playerIndex, String action)
		{
			this.action = action;
			this.playerIndex = playerIndex;
		}
		
		public String getAction()
		{
			return action;
		}
		
		public int getPlayerIndex()
		{
			return playerIndex;
		}
	}
	
	private List<GameAction> actions;
	
	public GameActionLog()
	{
		actions      = new ArrayList<>();
	}
	

	/**
	 * Logs an action the game Action log
	 * @param playerID
	 * @param action
	 */
	public void logAction(int playerID, String action)
	{
		actions.add(new GameAction(playerID, action));
	}
	
	/**
	 * Get the size of the log
	 * @return size
	 */
	public int size()
	{
		return actions.size();
	}
	
	/**
	 * Gets the player index at the specified log entry
	 * @param i the index of the log entry
	 * @return the playerIndex
	 */
	public int getPlayerIndex(int i)
	{
		return actions.get(i).getPlayerIndex();
	}
	
	
	/**
	 * Get the action text at the specified log entry
	 * @param i the index of the log entry
	 * @return the action text as a string
	 */
	public String getAction(int i)
	{
		return actions.get(i).getAction();
	}
	

}
