/**
 * 
 */
package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.ICommandDAO;

/**
 * @author Parker Ridd
 *
 */
public class FileAddCommandCommand implements IFileCommand
{
	private ICommandDAO commandDAO;
	private int gameID;
	private String command;

	public FileAddCommandCommand(ICommandDAO commandDAO, int gameID, String command)
	{
		this.commandDAO = commandDAO;
		this.gameID = gameID;
		this.command = command;
	}
	
	/* (non-Javadoc)
	 * @see server.persistence.plugins.FilePlugin.Commands.IFileCommand#execute()
	 */
	@Override
	public boolean execute()
	{
		return commandDAO.AddCommand(gameID, command);
	}

}
