package server.persistence;

import java.util.List;

import server.commands.ICommand;
import server.model.*;

public class PersistenceFacade {
	
	/**
	 * Gets the most recent users
	 * @return
	 */
	public List<ServerPlayer> GetAllUsers()
	{
		return null;
	}
	
	/**
	 * Gets the games
	 * @return not guarantted to be perfectly up to date
	 */
	public List<ServerGameManager> GetAllGames()
	{
		return null;
	}
	
	/**
	 * Get all the commands that have been played but haven't been updated into the blob
	 * @return the commands for all games
	 */
	public List<ICommand> GetAllCommands()
	{
		return null;
	}
	
	/**
	 * Get all the commands associated with a game
	 * @param gameID the game ID
	 * @return
	 */
	public List<ICommand> GetCommands(int gameID)
	{
		return null;
	}
	
	
	/**
	 * Adds a user to be in the database
	 * @param player
	 */
	public void AddUser(ServerPlayer player)
	{
	
	}
	
	/**
	 * Adds a game to the database
	 * @param sgm the server game manager to 
	 */
	public void AddGame(ServerGameManager sgm)
	{
		
	}
	
	/**
	 * Updates a game in the database
	 * @param sgm
	 * @throws PersistenceException 
	 */
	public void UpdateGame(ServerGameManager sgm) throws PersistenceException
	{
		throw new PersistenceException("Game does not exist");
	}
	
	/**
	 * Adds a command to the database
	 * @param gameID
	 * @param command
	 */
	public void AddCommand(int gameID, ICommand command) 
	{
		
	}
	

}
