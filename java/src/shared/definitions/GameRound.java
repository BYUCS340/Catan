package shared.definitions;

public enum GameRound
{
	ROLLING, ROBBING, PLAYING, DISCARDING, FIRSTROUND, SECONDROUND, GAMEOVER;
	
	//NOTE: MAINROUND and GAMEOVER are are not valid server states and need to be removed.
	public static String toString(GameRound type)
	{
		switch(type)
		{
			case ROLLING: return "rolling";
			case ROBBING: return "robbing";
			case PLAYING: return "playing";
			case DISCARDING: return "discarding";
			case FIRSTROUND: return "firstround";
			case SECONDROUND: return "secondround";
			default: return "unknown";
		}
	}
	
	public static GameRound fromString(String s)
	{
		switch(s.toLowerCase())
		{
			case "rolling": return GameRound.ROLLING;
			case "robbing" : return GameRound.ROBBING;
			case "playing": return GameRound.PLAYING;
			case "discarding": return GameRound.DISCARDING;
			case "firstround": return GameRound.FIRSTROUND;
			case "secondround": return GameRound.SECONDROUND;
		}
		return null;
	}
}
