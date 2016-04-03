package server.persistence;

public interface IGameDAO 
{
	boolean AddGame(int gameID, String blob);
	
	boolean UpdateGame(int gameID, String blob);
	
	boolean DeleteGame(int gameID);
	
	String GetCheckpoint(int gameID);
	
}
