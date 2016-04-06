package server.persistence.plugins.FilePlugin;

import java.util.List;

import server.persistence.ICommandDAO;

public class FileTransactionalCommandDAO implements ICommandDAO {

	@Override
	public List<String> GetCommandsFor(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean AddCommand(int gameID, String blob) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteCommandFor(int gameID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteAllCommands() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int GetCommandCountFor(int gameID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
