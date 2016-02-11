package client.data;

import java.util.List;

import shared.networking.transport.NetGame;
import shared.networking.transport.NetPlayer;

public class ClientDataTranslator {
	
	/**
	 * Converts the net player into the player info
	 * @param player
	 * @return
	 */
	static public PlayerInfo convert(NetPlayer player){
		PlayerInfo pi = new PlayerInfo();
		pi.setName(player.getName());
		pi.setId(player.getPlayerID());
		pi.setPlayerIndex(player.getPlayerID());
		pi.setColor(player.getColor());
		return pi;
		
	}
	/**
	 * Converts a netgame into a game info
	 * @param game
	 * @return
	 */
	static public GameInfo convert(NetGame game)
	{
		GameInfo gi = new GameInfo();
		gi.setId(game.getId());
		gi.setTitle(game.getTitle());
		
		//Get all the player in the game
		List<NetPlayer> players = game.getNetPlayers();
		for (int i=0; i< players.size(); i++)
		{
			//Add each player
			gi.addPlayer(ClientDataTranslator.convert(players.get(i)));
		}
		return gi;
		
	}
}
