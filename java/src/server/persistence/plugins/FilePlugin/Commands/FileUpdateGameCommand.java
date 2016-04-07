/**
 * 
 */
package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.IGameDAO;

/**
 * @author Parker Ridd
 *
 */
public class FileUpdateGameCommand implements IFileCommand
{
	private IGameDAO gameDAO;
	private int gameID;
	private String blob;
	
	public FileUpdateGameCommand(IGameDAO gameDAO, int gameID, String blob)
	{
		this.gameDAO = gameDAO;
		this.gameID = gameID;
		this.blob = blob;
	}

	@Override
	public boolean execute()
	{
		return gameDAO.UpdateGame(gameID, blob);
	}

}
