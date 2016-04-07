package server.persistence.plugins.FilePlugin;

import java.util.List;
import java.util.Map;

import server.persistence.IGameDAO;
import server.persistence.PersistenceException;

public class FileTransactionalGameDAO implements IGameDAO {

	@Override
	public void AddGame(int gameID, String blob) throws PersistenceException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void UpdateGame(int gameID, String blob) throws PersistenceException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> GetAllGames() throws PersistenceException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
