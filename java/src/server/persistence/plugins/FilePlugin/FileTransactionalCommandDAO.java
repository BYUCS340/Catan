package server.persistence.plugins.FilePlugin;

import java.util.List;

import server.persistence.ICommandDAO;
import server.persistence.PersistenceException;
import server.persistence.plugins.FilePlugin.Commands.FileAddCommandCommand;
import server.persistence.plugins.FilePlugin.Commands.FileDeleteCommandsCommand;

public class FileTransactionalCommandDAO implements ICommandDAO {
	private ICommandDAO commandDAO;
	
	public FileTransactionalCommandDAO(ICommandDAO commandDAO)
	{
		this.commandDAO = commandDAO;
	}

	@Override
	public List<String> GetCommands() throws PersistenceException
	{
		return commandDAO.GetCommands();
	}

	@Override
	public void AddCommand(int gameID, String blob) throws PersistenceException
	{
		FileTransactionManager.addCommand(new FileAddCommandCommand(commandDAO, gameID, blob));
	}

	@Override
	public void DeleteCommands(int gameID) throws PersistenceException
	{
		FileTransactionManager.addCommand(new FileDeleteCommandsCommand(commandDAO, gameID));
	}

	@Override
	public int GetCommandCount(int gameID) throws PersistenceException
	{
		return commandDAO.GetCommandCount(gameID);
	}

}
