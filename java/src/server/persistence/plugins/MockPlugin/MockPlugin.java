package server.persistence.plugins.MockPlugin;

import server.persistence.ICommandDAO;
import server.persistence.IGameDAO;
import server.persistence.IPersistenceProvider;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;

/**
 * This plug-in is fake, like your face.
 * @author Jonathan Sadler
 *
 */
public class MockPlugin implements IPersistenceProvider
{
	public MockPlugin() 
	{
		
	}

	@Override
	public void Clear() throws PersistenceException 
	{
		
	}

	@Override
	public void StartTransaction() throws PersistenceException 
	{
		System.out.println("Start save transaction");
	}

	@Override
	public void EndTransaction(boolean commit) throws PersistenceException 
	{
		System.out.println("End save transaction");
	}

	@Override
	public IUserDAO GetUserDAO() throws PersistenceException 
	{
		return new MockUserDAO();
	}

	@Override
	public IGameDAO GetGameDAO() throws PersistenceException 
	{
		return new MockGameDAO();
	}

	@Override
	public ICommandDAO GetCommandDAO() throws PersistenceException 
	{
		return new MockCommandDAO();
	}
}
