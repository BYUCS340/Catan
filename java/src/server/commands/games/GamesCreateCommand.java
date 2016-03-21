package server.commands.games;

import server.commands.ICommand;
import server.model.GameArcade;
import shared.data.GameInfo;
import shared.networking.SerializationUtils;

/**
 * Command to create a new game.
 * @author Jonathan Sadler
 *
 */
public class GamesCreateCommand implements ICommand 
{
	private boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	private String name;
	
	private GameInfo info;
	
	/**
	 * Creates a command object to create a game.
	 * @param randomTiles True if tiles should be placed randomly.
	 * @param randomNumbers True if numbers should be placed randomly.
	 * @param randomPorts True if ports should be placed randomly.
	 * @param name The name of the game to be created.
	 */
	public GamesCreateCommand(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) 
	{
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.name = name;
	}

	@Override
	public boolean Execute() 
	{
		info = GameArcade.games().CreateGame(name, randomTiles, randomNumbers, randomPorts);
		return info != null;
	}

	@Override
	public boolean Unexecute() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String GetResponse() 
	{
		if (info != null)
			return SerializationUtils.serialize(info);
		else
			return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}

}
