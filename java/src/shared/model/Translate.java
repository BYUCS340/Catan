package shared.model;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.*;
import shared.model.chat.*;
import shared.networking.transport.*;

/**
 * The Translate class changes game objects into net game objects, and vice versa
 * @author garrettegan
 *
 */
public class Translate
{
	public GameModel fromNetGameModel(NetGameModel netGameModel)
	{
		GameActionLog gameActionLog = fromNetLog(netGameModel.getNetGameLog());  //I think this works -- but only logs as player 0
		Map map = fromNetMap(netGameModel.getNetMap());
		List<Player> players = fromNetPlayers(netGameModel.getNetPlayers());  //I think this works
		ChatBox chat = fromNetChat(netGameModel.getNetChat());  //I think this works -- but only posts as player 0
		TradeOffer tradeOffer = fromNetTradeOffer(netGameModel.getNetTradeOffer());
		TurnTracker turnTracker = fromNetTurnTracker(netGameModel.getNetTurnTracker());
		int winner = netGameModel.getWinner();
		int version = netGameModel.getVersion();
	}
	
	public ChatBox fromNetChat(NetChat netChat)
	{
		ChatBox chatBox = new ChatBox();
		for (int i = 0; i < netChat.getLines().size(); i++)
		{
			//always posts as player 0 (because I don't have a good way of determining playerID from message source yet)
			chatBox.put(netChat.getLines().get(i).getMessage(), 0);
		}
		return chatBox;
	}
	
	public EdgeLocation fromNetEdgeLocation(NetEdgeLocation netEdgeLocation)
	{
		return null;
	}
	
	public Hex fromNetHex(NetHex netHex)
	{
		return null;
	}
	
	public HexLocation fromNetHexLocation(NetHexLocation netHexLocation)
	{
		return null;
	}
	
	public GameActionLog fromNetLog(NetLog netLog)
	{
		GameActionLog gameActionLog = new GameActionLog();
		for (int i = 0; i < netLog.getLines().size(); i++)
		{
			//always logs as player 0 (because I don't have a good way of determining playerID from message source yet)
			gameActionLog.logAction(0, netLog.getLines().get(i).getMessage());
		}
		return gameActionLog;
	}
	
	public Map fromNetMap(NetMap netMap)
	{
		return null;
	}
	
	public List<Player> fromNetPlayers(List<NetPlayer> netPlayers)
	{
		List<Player> players = new ArrayList<Player>();
		for (int i=0; i < netPlayers.size(); i++)
		{
			players.add(fromNetPlayer(netPlayers.get(i)));
		}
		return players;
	}
	
	public Player fromNetPlayer(NetPlayer netPlayer)
	{
		Player player = new Player(netPlayer.getName(), netPlayer.getPlayerIndex(), netPlayer.getColor(), true);
		
		//Setup the Bank
		try
		{
			//PIECES
			player.playerBank.givePiece(PieceType.CITY, netPlayer.getNumCities());
			player.playerBank.givePiece(PieceType.ROAD, netPlayer.getNumRoads());
			player.playerBank.givePiece(PieceType.SETTLEMENT, netPlayer.getNumMonuments());
			
			//RESOURCES
			player.playerBank.giveResource(ResourceType.BRICK, netPlayer.getNetResourceList().getNumBrick());
			player.playerBank.giveResource(ResourceType.ORE, netPlayer.getNetResourceList().getNumOre());
			player.playerBank.giveResource(ResourceType.SHEEP, netPlayer.getNetResourceList().getNumSheep());
			player.playerBank.giveResource(ResourceType.WHEAT, netPlayer.getNetResourceList().getNumWheat());
			player.playerBank.giveResource(ResourceType.WOOD, netPlayer.getNetResourceList().getNumWood());
			
			//DEV CARDS
			for (int i = 0; i < netPlayer.getNewNetDevCardList().getNumMonopoly(); i++)
			{
				player.playerBank.giveDevCard(DevCardType.MONOPOLY);
			}
			for (int i = 0; i < netPlayer.getNewNetDevCardList().getNumMonument(); i++)
			{
				player.playerBank.giveDevCard(DevCardType.MONUMENT);
			}
			for (int i = 0; i < netPlayer.getNewNetDevCardList().getNumRoadBuilding(); i++)
			{
				player.playerBank.giveDevCard(DevCardType.ROAD_BUILD);
			}
			for (int i = 0; i < netPlayer.getNewNetDevCardList().getNumSoldier(); i++)
			{
				player.playerBank.giveDevCard(DevCardType.SOLDIER);
			}
			for (int i = 0; i < netPlayer.getNewNetDevCardList().getNumYearOfPlenty(); i++)
			{
				player.playerBank.giveDevCard(DevCardType.YEAR_OF_PLENTY);
			}
		} 
		catch (ModelException e)
		{
			System.err.println("An Error has occured while populating the bank in the Translate class");
		}
		
		return null;
	}
	
	public Port fromNetPort(NetPort netPort)
	{
		return null;
	}
	
	public TradeOffer fromNetTradeOffer(NetTradeOffer netTradeOffer)
	{
		return null;
	}
	
	public TurnTracker fromNetTurnTracker(NetTurnTracker netTurnTracker)
	{
		return null;
	}
	
	public VertexObject fromNetVertexObject(NetVertexObject netVertexObject)
	{
		return null;
	}
}
