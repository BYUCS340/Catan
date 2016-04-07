package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.IUserDAO;

public class FileAddUserCommand implements IFileCommand{
	String username, password, userID;
	IUserDAO userDAO;
	
	public FileAddUserCommand(IUserDAO userDAO, String username, String password, String userID)
	{
		this.username = username;
		this.password = password;
		this.userID = userID;
	}
	
	@Override
	public boolean execute() {
		return userDAO.AddUser(userID, username, password);
	}

}
