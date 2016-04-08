package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.IUserDAO;
import server.persistence.PersistenceException;

public class FileAddUserCommand implements IFileCommand{
	String username, password;
	int userID;
	IUserDAO userDAO;
	
	public FileAddUserCommand(IUserDAO userDAO, int userID, String username, String password)
	{
		this.userDAO = userDAO;
		this.username = username;
		this.password = password;
		this.userID = userID;
	}
	
	@Override
	public void execute() throws PersistenceException {
		userDAO.AddUser(userID, username, password);
	}

}
