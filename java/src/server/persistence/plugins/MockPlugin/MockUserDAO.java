package server.persistence.plugins.MockPlugin;

import java.util.ArrayList;
import java.util.List;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;

/**
 * Fake user DAO implementation.
 * @author Jonathan Sadler
 *
 */
public class MockUserDAO implements IUserDAO
{
	public MockUserDAO() 
	{

	}

	@Override
	public void AddUser(int id, String username, String password) throws PersistenceException 
	{
		System.out.println("Saving user");
		System.out.println("Username: " + username);
	}

	@Override
	public List<ServerPlayer> GetAllUsers() throws PersistenceException 
	{
		System.out.println("Getting saved users");
		return new ArrayList<ServerPlayer>();
	}
}
