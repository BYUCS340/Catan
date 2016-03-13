package server.model;

public class GameArcade {
	private static GameTable games = null;
	
	public static GameTable games()
	{
		if (games == null)
		{
			games = new GameTable();
		}
		return games;
	}
}
