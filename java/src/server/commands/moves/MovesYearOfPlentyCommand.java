package server.commands.moves;

import shared.definitions.ResourceType;

/**
 * Command object that allows the playing of the Year of Plenty card.
 * @author Jonathan Sadler
 *
 */
public class MovesYearOfPlentyCommand extends MovesCommand 
{
	private ResourceType resource1;
	private ResourceType resource2;
	
	/**
	 * Creates a command object that plays the Year of Plenty card.
	 * @param playerID The player ID.
	 * @param playerIndex The Player index.
	 * @param resource1 The resource type of card 1.
	 * @param resource2 The resource type of card 2.
	 */
	public MovesYearOfPlentyCommand(int playerID, int playerIndex, ResourceType resource1, ResourceType resource2) 
	{
		super(playerID, playerIndex);
		this.resource1 = resource1;
		this.resource2 = resource2;
	}

	@Override
	public boolean Execute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Unexecute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String Response() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
