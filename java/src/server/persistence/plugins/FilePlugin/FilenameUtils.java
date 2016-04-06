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
	
	/**
	 * Returns a fully constructed filename path to the specified user
	 * @param userID
	 * @return a string that represents the path of the user
	 */
	public static String getFullUserPath(int userID)
	{
		return userDirFull + File.separator + userID + fileSuffix;
	}
	
	/**
	 * Gets the userID from the passed in string
	 * @param path
	 * @return
	 */
	public static int getUserIDFromString(String path)
	{
		//if we know that this is a path and not a name, handle it specially
		if(path.contains(userDir))
		{
			String ret = path.substring(path.lastIndexOf("/")+1, path.lastIndexOf('.'));
			return Integer.parseInt(ret);
		}
		else
		{
			if(!Character.isDigit(path.charAt(0)))
			{
				return -1;
			}
			return Integer.parseInt(path.substring(0, path.indexOf('.')));
		}
	}

}
