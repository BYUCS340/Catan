package server.persistence.plugins.FilePlugin;

import java.util.List;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;
import server.persistence.PersistenceException;

public class FileTransactionalUserDAO implements IUserDAO {

	@Override
	public void AddUser(int id, String username, String password) throws PersistenceException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ServerPlayer> GetAllUsers() throws PersistenceException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
