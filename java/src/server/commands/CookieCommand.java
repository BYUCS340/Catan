package server.commands;

public abstract class CookieCommand implements ICommand 
{
	protected int playerID;
	
	protected CookieCommand(int playerID)
	{
		this.playerID = playerID;
	}
}
