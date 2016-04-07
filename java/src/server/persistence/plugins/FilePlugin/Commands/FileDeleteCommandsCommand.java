/**
 * 
 */
package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.ICommandDAO;
import server.persistence.PersistenceException;

/**
 * @author Parker Ridd
 *
 */
public class FileDeleteCommandsCommand implements IFileCommand
{
	private ICommandDAO commandDAO;
	private int gameID;
	
	public FileDeleteCommandsCommand(ICommandDAO commandDAO, int gameID)
	{
		this.commandDAO = commandDAO;
		this.gameID = gameID;
	}

	@Override
	public void execute() throws PersistenceException
	{
		commandDAO.DeleteCommandFor(gameID);
	}

}
