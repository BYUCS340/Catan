package server.persistence.plugins.FilePlugin;

import java.io.File;

public class FilenameUtils
{
	
	public static final String dataDir = "data";
	public static final String precommitDir = "temp";
	public static final String precommitDirFull = dataDir + File.separator + precommitDir;
	public static final String userDir = "users";
	public static final String commandDir = "commands";
	public static final String userDirFull = dataDir + File.separator + userDir;
	public static final String userPrecommitDirFull = precommitDirFull +  File.separator + userDir;
	
	
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
		if(!gameStr.contains("Game"))
		{
			return -1;
		}
		
		return Integer.parseInt(gameStr.substring(4));
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
	 * Gets a precommit path from a full path that is postcommit
	 * @param fullPath that is postcommit
	 * @return a precommit path
	 */
	public static String getPrecommitPath(String fullPath)
	{
		if(!fullPath.contains(dataDir))
		{
			return null;
		}
		
		return fullPath.substring(0, dataDir.length()) + File.separator + precommitDir + fullPath.substring(dataDir.length());
	}
	
	/**
	 * returns a postcommit path from a path that is precommit
	 * @param fullPath that is precommit
	 * @return a postcommit path
	 */
	public static String getPostCommitPath(String fullPath)
	{
		if(!fullPath.contains(precommitDirFull))
		{
			return null;
		}
		
		return dataDir + File.separator + fullPath.substring(precommitDirFull.length());
	}
}
