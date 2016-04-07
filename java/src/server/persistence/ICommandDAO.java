package server.persistence;

import java.util.List;

public interface ICommandDAO 
{
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
	void DeleteCommands(int gameID) throws PersistenceException;
	
	/**
	 * Gets all the saved commands
	 * @return A list of commands as strings
	 * @throws PersistenceException Thrown if errors occur
	 */
	List<String> GetCommands() throws PersistenceException;
	
	/**
	 * Gets the total number of commands for
	 * @param gameID
	 * @return
	 * @throws PersistenceException
	 */
	int GetCommandCount(int gameID) throws PersistenceException;	
}
