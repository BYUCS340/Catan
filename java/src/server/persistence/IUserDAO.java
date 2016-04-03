package server.persistence;

import java.util.List;

import server.model.ServerPlayer;

/**
 * This just uses server player since it so closely mirrors what's in the database
 * @author matthewcarlson
 *
 */
public interface IUserDAO
{
	/**
	 * Adds a user to the DAO
	 * @param username
	 * @param password
	 * @return
	 */
	boolean AddUser(String username, String password);
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	ServerPlayer GetPlayer(String username);
	
	/**
	 * 
	 * @param playerID
	 * @return
	 */
	ServerPlayer GetPlayer(int playerID);

	/**
	 * Gets all the players in the DAO
	 * @return
	 */
	List<ServerPlayer> GetAllPlayers();
	
}
