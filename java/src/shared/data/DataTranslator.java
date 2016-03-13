package shared.data;

import java.util.Iterator;
import java.util.List;

import shared.definitions.CatanColor;
import shared.model.Player;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetPlayer;

public class DataTranslator {
	
	/**
	 * Converts the net player into the player info
	 * @param player
	 * @return
	 */
	static public PlayerInfo convertPlayerInfo(NetPlayer player){
		PlayerInfo pi = new PlayerInfo();
		pi.setName(player.getName());
		pi.setId(player.getPlayerID());
		pi.setPlayerIndex(player.getPlayerIndex());
		pi.setColor(player.getColor());
		return pi;
		
	}
	
	/**
	 * Adds up all the player resources
	 * @param players
	 * @return
	 */
	static public int totalPlayerResouces(List<Player> players)
	{
		int resources = 0;
		Iterator<Player> i = players.iterator();
		while (i.hasNext()) resources += i.next().totalResources();
		return resources;
	}
	
	/**
	 * Converts a player to player info
	 * @param player
	 * @return
	 */
	static public PlayerInfo convertPlayerInfo(Player player){
		PlayerInfo pi = new PlayerInfo();
		pi.setName(player.name);
		pi.setId(player.playerID());
		pi.setPlayerIndex(player.playerIndex());
		pi.setColor(player.color);
		return pi;
		
	}
	
	/**
	 * Converts a player
	 * @param player
	 * @return
	 */
	static public Player convertPlayerInfo(PlayerInfo player){
		//String name, int index, CatanColor playerColor, boolean isHuman)
		return new Player(player.getName(), player.getPlayerIndex(), player.getColor(), true, player.getId());		
	}
	
	/**
	 * Converts a netgame into a game info
	 * @param game
	 * @return
	 */
	static public GameInfo convertGame(NetGame game)
	{
		GameInfo gi = new GameInfo();
		gi.setId(game.getId());
		gi.setTitle(game.getTitle());
		
		//Get all the player in the game
		List<NetPlayer> players = game.getNetPlayers();
		for (int i=0; i< players.size(); i++)
		{
			//Add each player
			gi.addPlayer(DataTranslator.convertPlayerInfo(players.get(i)));
		}
		return gi;
		
	}
}
