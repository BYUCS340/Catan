package server.persistence.plugins.FilePlugin;

import java.util.List;

import server.persistence.ICommandDAO;
import server.persistence.PersistenceException;

public class FileTransactionalCommandDAO implements ICommandDAO {

	@Override
	public List<String> GetCommands() throws PersistenceException
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
	public void DeleteCommands(int gameID) throws PersistenceException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int GetCommandCount(int gameID) throws PersistenceException
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
