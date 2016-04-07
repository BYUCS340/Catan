package server.persistence;

import java.util.List;

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
	 * @throws PersistenceException
	 */
	void AddGame(int gameID, String blob) throws PersistenceException;
	
	/**
	 * Updates an existing game
	 * @param gameID
	 * @param blob
	 * @return
	 * @throws PersistenceException
	 */
	void UpdateGame(int gameID, String blob) throws PersistenceException;
	
	
	/**
	 * Returns all games
	 * @return a map of Game ID to blobs
	 * @throws PersistenceException
	 */
	List<String> GetAllGames() throws PersistenceException;
	
}
