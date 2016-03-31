package server.commands.games;

import server.commands.ICommand;
import server.model.GameArcade;

/**
 * Command to handle saving a game.
 * @author Jonathan Sadler
 *
 */
public class GamesSaveCommand implements ICommand 
{
	private int id;
	private String name;
	
	/**
	 * Creates a command to save the game.
	 * @param id The ID of the game to save.
	 * @param name The file name to save the game under.
	 */
	public GamesSaveCommand(int id, String name) 
	{
		this.id = id;
		this.name = name;	
	}

	@Override
	public boolean Execute() {
		return GameArcade.games().SaveGame(id, name);
	}

	@Override
	public boolean Unexecute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String GetResponse() {
		// TODO Auto-generated method stub
		return "Not Found";
	}

	@Override
	public String GetHeader() 
	{
		return null;
	}
}
