package server.commands.moves;

import shared.definitions.ResourceType;

/**
 * Command for handling the monopoly card.
 * @author Jonathan Sadler
 *
 */
public class MovesMonopolyCommand extends MovesCommand 
{
	private ResourceType resource;
	
	/**
	 * Creates a command object to play the monopoly card.
	 * @param playerID The player ID.
	 * @param playerIndex The player index.
	 * @param resource The resource type to monopolize.
	 */
	public MovesMonopolyCommand(int playerID, int playerIndex, ResourceType resource) 
	{
		super(playerID, playerIndex);
		this.resource = resource;
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
