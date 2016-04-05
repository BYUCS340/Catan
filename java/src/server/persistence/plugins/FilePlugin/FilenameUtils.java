package server.persistence.plugins.FilePlugin;

import java.io.File;

public class FilenameUtils
{
	
	public static final String dataDir = "data";
	public static final String userDir = "users";
	public static final String commandDir = "commands";
	public static final String gameDir = "game";
	public static final String userDirFull = dataDir + File.separator + userDir;
	
	public static final String fileSuffix = ".catan";
	public static final String gameFilePrefix = "GameBlob";
	public static final String gameFilename = gameFilePrefix + fileSuffix;
	public static final String commandPrefix = "Command";
	
	/**
	 * Returns a string that represents what the game directory is called
	 * @param gameID the gameID to construct the DirString from
	 * @return the string that represents that game directory
	 */
	public static String getGameDirString(int gameID)
	{
		return "Game" + gameID;
	}
	
	
	/**
	 * Gets the game ID that pertains to the game directory name.
	 * Returns -1 if the string is invalid.
	 * @param gameStr the string of the game Dir
	 * @return the ID of the game represented or -1 if the string is invalid
	 */
	public static int getGameIDFromDirString(String gameStr)
	{
		if(!gameStr.contains(gameDir))
		{
			return -1;
		}
		
//		if(gameStr.contains("."))
//			return Integer.parseInt(gameStr.substring(gameDir.length(), gameStr.indexOf('.')));
//		else
		return Integer.parseInt(gameStr.substring(gameDir.length()));
	}
	
	/**
	 * Gets the full game relative path from the gameID
	 * @param gameID the gameID to get the path for
	 * @return the full game directory path
	 */
	public static String getFullGameDir(int gameID)
	{
		return dataDir + File.separator + getGameDirString(gameID);
	}
	
	
	/**
	 * Gets the full command relative path from the gameID
	 * @param gameID the gameID to get the path for
	 * @return the full commands directory path
	 */
	public static String getFullCommandsDir(int gameID)
	{
		return dataDir + File.separator + getGameDirString(gameID) + File.separator + commandDir;
	}

}
