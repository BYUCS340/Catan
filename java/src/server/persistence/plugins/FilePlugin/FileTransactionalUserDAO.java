package server.persistence.plugins.FilePlugin;

import java.util.List;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;
import server.persistence.plugins.FilePlugin.Commands.FileAddUserCommand;

public class FileTransactionalUserDAO implements IUserDAO {
	private IUserDAO userDAO;
	
	public FileTransactionalUserDAO(IUserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	@Override
	public void AddUser(int id, String username, String password) throws PersistenceException
	{
		FileTransactionManager.addCommand(new FileAddUserCommand(userDAO, id, username, password));
	}

	@Override
	public List<ServerPlayer> GetAllUsers() throws PersistenceException
	{
		return userDAO.GetAllUsers();
	}

}
