package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.IGameDAO;
import server.persistence.PersistenceException;

public class FileAddGameCommand implements IFileCommand {

	IGameDAO gameDAO;
	String blob;
	int gameID;
	
	public FileAddGameCommand(IGameDAO gameDAO, int gameID, String blob)
	{
		this.gameDAO = gameDAO;
		this.gameID = gameID;
		this.blob = blob;
	}
	
	@Override
	public void execute() throws PersistenceException {
		gameDAO.AddGame(gameID, blob);
	}

}
