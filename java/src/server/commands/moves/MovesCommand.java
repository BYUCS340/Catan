package server.commands.moves;

import server.commands.CookieCommand;

public abstract class MovesCommand extends CookieCommand 
{
	protected int playerIndex;
	
	public MovesCommand(int playerID, int playerIndex) 
	{
		super(playerID);
		this.playerIndex = playerIndex;
	}
}
