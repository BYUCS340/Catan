package server.persistence.plugins.FilePlugin;

import java.util.List;

import server.persistence.ICommandDAO;
import server.persistence.PersistenceException;

public class FileTransactionalCommandDAO implements ICommandDAO {

	@Override
	public List<String> GetCommandsFor(int gameID) throws PersistenceException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AddCommand(int gameID, String blob) throws PersistenceException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DeleteCommandFor(int gameID) throws PersistenceException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int GetCommandCountFor(int gameID) throws PersistenceException
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
