package server.commands.games;

import server.commands.ICommand;

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
	public String GetResponse() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}

}
