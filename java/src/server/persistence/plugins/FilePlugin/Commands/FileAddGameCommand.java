package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.IGameDAO;

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
	public boolean execute() {
		return gameDAO.AddGame(gameID, blob);
	}

}
