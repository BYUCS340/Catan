package server.persistence;

import java.util.Map;

/**
 * 
 * @author Parker Ridd
 *
 */
public interface IGameDAO 
{
	/**
	 * 
	 * @param gameID
	 * @param blob
	 * @return
	 */
	boolean AddGame(int gameID, String blob);
	
	/**
	 * Updates an existing game
	 * @param gameID
	 * @param blob
	 * @return
	 */
	boolean UpdateGame(int gameID, String blob);
	
	/**
	 * Deletes a single game
	 * @param gameID
	 * @return
	 */
	boolean DeleteGame(int gameID);

	/**
	 * Deletes all games
	 * @return
     */
	boolean DeleteAllGames();
	
	/**
	 * Gets the current persisted checkpoint for the given gameID
	 * @param gameID
	 * @return
	 */
	String GetCheckpoint(int gameID);
	
	/**
	 * Returns all games
	 * @return a map of Game ID to blobs
	 */
	Map<Integer, String> GetAllGames();
	
}
