package server.persistence.plugins.FilePlugin;

import java.util.Map;

import server.persistence.IGameDAO;

public class FileTransactionalGameDAO implements IGameDAO {

	@Override
	public boolean AddGame(int gameID, String blob) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateGame(int gameID, String blob) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteGame(int gameID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteAllGames() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String GetCheckpoint(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> GetAllGames() {
		// TODO Auto-generated method stub
		return null;
	}

}
