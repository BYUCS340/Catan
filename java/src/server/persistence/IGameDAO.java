package server.persistence;

import java.util.Map;

public interface IGameDAO 
{
	/**
	 * 
	 * @param gameID
	 * @param blob
	 * @return
	 */
	boolean AddGame(int gameID, String blob);
	
	/**
	 * 
	 * @param gameID
	 * @param blob
	 * @return
	 */
	boolean UpdateGame(int gameID, String blob);
	
	/**
	 * 
	 * @param gameID
	 * @return
	 */
	boolean DeleteGame(int gameID);
	
	/**
	 * 
	 * @param gameID
	 * @return
	 */
	String GetCheckpoint(int gameID);
	
	/**
	 * 
	 * @return a map of Game ID to blobs
	 */
	Map<Integer, String> GetAllGames();
	
}
