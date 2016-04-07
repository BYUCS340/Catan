/**
 * 
 */
package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.IGameDAO;
import server.persistence.PersistenceException;

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
	public void execute() throws PersistenceException
	{
		gameDAO.UpdateGame(gameID, blob);
	}

}
