package server.persistence.plugins.FilePlugin;

import java.util.List;

import server.persistence.IGameDAO;
import server.persistence.PersistenceException;
import server.persistence.plugins.FilePlugin.Commands.FileAddGameCommand;
import server.persistence.plugins.FilePlugin.Commands.FileUpdateGameCommand;

public class FileTransactionalGameDAO implements IGameDAO {
	private IGameDAO gameDAO;
	
	public FileTransactionalGameDAO(IGameDAO gameDAO)
	{
		this.gameDAO = gameDAO;
	}
	
	@Override
	public void AddGame(int gameID, String blob) throws PersistenceException
	{
		FileTransactionManager.addCommand(new FileAddGameCommand(gameDAO, gameID, blob));		
	}

	@Override
	public void UpdateGame(int gameID, String blob) throws PersistenceException
	{
		FileTransactionManager.addCommand(new FileUpdateGameCommand(gameDAO, gameID, blob));		
	}

	@Override
	public List<String> GetAllGames() throws PersistenceException
	{
		return gameDAO.GetAllGames();
	}

}
