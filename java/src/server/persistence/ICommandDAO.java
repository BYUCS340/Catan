package server.persistence;

import java.util.List;

public interface ICommandDAO 
{
	
	/**
	 * gets command blobs for a game ID
	 * @param gameID
	 * @return
	 */
	List<String> GetCommandsFor(int gameID);
	
	/**
	 * Adds a command to the DAO
	 * @param gameID
	 * @param blob
	 * @return
	 */
	boolean AddCommand(int gameID, String blob);
	
	/**
	 * 
	 * @param gameID
	 * @return
	 */
	boolean DeleteCommandFor(int gameID);
	
	/**
	 * Gets the total number of commands for
	 * @param gameID
	 * @return
	 */
	int GetCommandCountFor(int gameID);
	
	
	
}
