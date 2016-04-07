package server.persistence.plugins.MockPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import server.persistence.ICommandDAO;
import server.persistence.PersistenceException;

/**
 * Fake command DAO implementation.
 * @author Jonathan Sadler
 *
 */
public class MockCommandDAO implements ICommandDAO  
{
	public MockCommandDAO()
	{
		
	}

	@Override
	public void AddCommand(int gameID, String blob) throws PersistenceException 
	{
		System.out.println("Saving command");
		System.out.println("GameID: " + gameID);
	}

	@Override
	public void DeleteCommands(int gameID) throws PersistenceException 
	{
		System.out.println("Deleting saved commands");
		System.out.println("GameID: " + gameID);
	}

	@Override
	public List<String> GetCommands() throws PersistenceException 
	{
		System.out.println("Getting saved commands");
		return new ArrayList<String>();
	}

	@Override
	public int GetCommandCount(int gameID) throws PersistenceException 
	{
		Random random = new Random();
		int count = random.nextInt(20);
		
		System.out.println("Getting saved command count");
		System.out.println("Command count randomly assigned: " + count);
		
		return count;
	}

}
