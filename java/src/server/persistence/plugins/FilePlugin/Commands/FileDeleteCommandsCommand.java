/**
 * 
 */
package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.ICommandDAO;

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
	public boolean execute()
	{
		return commandDAO.DeleteCommandFor(gameID);
	}

}
