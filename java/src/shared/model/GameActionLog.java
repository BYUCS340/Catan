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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GameActionLog))
			return false;
		GameActionLog other = (GameActionLog) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		return true;
	}
	
	
	

}
