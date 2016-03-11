package server.model;

import java.util.Map;

/**
 * This keeps traack of the different games in the game
 * @author matthewcarlson
 *
 */
public class GameTable 
{
	private Map<Integer, ServerGameManager> games;
	/**
	 * Creates a new game on the server 
	 * @return the id of the new game created -1 if unable to create
	 */
	public int CreateGame(String name)
	{
		return -1;
	}
	
	/**
	 * Gets a game object
	 * @param id the 
	 * @return
	 * @throws GameException if the game is not found
	 */
	public ServerGameManager GetGame (int id) throws GameException
	{
		throw new GameException("Game "+id+" not found");
		
	}
	
	/**
	 * Sets the game to the index
	 * @param sgm
	 * @param id
	 */
	private void SetGame(ServerGameManager sgm, int id)
	{
		games.put(id, sgm);
	}
	
	/**
	 * Loads a game
	 * @param filePath 
	 * @return
	 */
	public boolean LoadGame(String filePath)
	{
		return false;
	}
	
	/**
	 * Saves the game at the id
	 * @param id
	 * @param filePath the file destination to write to
	 * @return true if succedded
	 */
	public boolean SaveGame(int id, String filePath)
	{
		return false;
	}
	
	
	
}
