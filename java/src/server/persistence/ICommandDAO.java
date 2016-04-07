package server.persistence;

import java.util.List;

public interface ICommandDAO 
{
	
	/**
	 * gets command blobs for a game ID
	 * @param gameID
	 * @return
	 * @throws PersistenceException
	 */
	List<String> GetCommandsFor(int gameID) throws PersistenceException;
	
	/**
	 * Adds a command to the DAO
	 * @param gameID
	 * @param blob
	 * @return
	 * @throws PersistenceException
	 */
	void AddCommand(int gameID, String blob) throws PersistenceException;
	
	/**
	 * Deletes all commands for a specific games
	 * @param gameID
	 * @return
	 * @throws PersistenceException
	 */
	void DeleteCommandFor(int gameID) throws PersistenceException;
	
	/**
	 * Gets the total number of commands for
	 * @param gameID
	 * @return
	 * @throws PersistenceException
	 */
	int GetCommandCountFor(int gameID) throws PersistenceException;
	
	
	
}
