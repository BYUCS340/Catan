package server.persistence.plugins.FilePlugin;

import java.util.List;

import server.model.ServerPlayer;
import server.persistence.IUserDAO;

public class FileTransactionalUserDAO implements IUserDAO {

	@Override
	public boolean AddUser(String id, String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ServerPlayer GetUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerPlayer GetUser(int playerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServerPlayer> GetAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean DeleteAllUsers() {
		// TODO Auto-generated method stub
		return false;
	}

}
