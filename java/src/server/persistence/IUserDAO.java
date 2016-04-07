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
	 * @throws PersistenceException
	 */
	void AddUser(int id, String username, String password) throws PersistenceException;

	/**
	 * Gets all the players in the DAO
	 * @return
	 * @throws PersistenceException
	 */
	List<ServerPlayer> GetAllUsers() throws PersistenceException;

	
}
