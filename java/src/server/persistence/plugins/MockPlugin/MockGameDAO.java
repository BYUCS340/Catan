package server.persistence.plugins.MockPlugin;

import java.util.ArrayList;
import java.util.List;

import server.persistence.IGameDAO;
import server.persistence.PersistenceException;

/**
 * Fake game DAO implementation
 * @author Jonathan Sadler
 *
 */
public class MockGameDAO implements IGameDAO 
{
	public MockGameDAO() 
	{
		
	}

	@Override
	public void AddGame(int gameID, String blob) throws PersistenceException 
	{
		System.out.println("Saving game");
		System.out.println("GameID: " + gameID);
	}

	@Override
	public void UpdateGame(int gameID, String blob) throws PersistenceException 
	{
		System.out.println("Updating saved game");
		System.out.println("GameID: " + gameID);
	}

	@Override
	public List<String> GetAllGames() throws PersistenceException 
	{
		System.out.println("Getting saved games");
		return new ArrayList<String>();
	}
}
