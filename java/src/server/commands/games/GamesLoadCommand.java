package server.commands.games;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import server.commands.ICommand;
import server.model.GameArcade;
import server.model.ServerGameManager;
import shared.networking.SerializationUtils;

/**
 * Command that handles loading games that have been previously saved.
 * @author Jonathan Sadler
 *
 */
public class GamesLoadCommand implements ICommand 
{
	private String name;
	
	/**
	 * Creates a command object to load a game.
	 * @param name The name of the saved game file.
	 */
	public GamesLoadCommand(String name)
	{
		this.name = name;
	}

	@Override
	public boolean Execute() 
	{
		
			 return GameArcade.games().LoadGame(name);
			 
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
