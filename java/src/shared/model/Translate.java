package shared.model;

import java.util.ArrayList;
import java.util.List;

import client.map.*;
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
		GameModel gameModel = new GameModel();
		
		gameModel.mapController = fromNetMap(netGameModel.getNetMap());  //UNSTARTED -- this is tricky cause I don't know our Map very well
		gameModel.gameState = fromNetTurnTracker(netGameModel.getNetTurnTracker());  //FINISHED? -- but NetGameModel doesn't return a GameStatus
		gameModel.gameBank = fromNetBank(netGameModel.getNetBank());  //FINISHED? -- but only has resource cards (no dev cards)
		gameModel.players = fromNetPlayers(netGameModel.getNetPlayers());  //FINISHED?
		gameModel.victoryPointManager = fromNetVPManager(netGameModel.getNetTurnTracker(), netGameModel.getNetPlayers());  //UNFINISHED
		gameModel.waterCooler = fromNetChat(netGameModel.getNetChat());  //FINISHED? -- but only posts as player 0
		gameModel.log = fromNetLog(netGameModel.getNetGameLog());  //FINISHED? -- but only logs as player 0
		gameModel.version = netGameModel.getVersion();  //FINISHED
		
		return gameModel;
	}
	
	public MapController fromNetMap(NetMap netMap)
	{
		return null;
	}
	
	public GameState fromNetTurnTracker(NetTurnTracker netTurnTracker)
	{
		GameState gameState = new GameState();
		gameState.gameRound = netTurnTracker.getRound();
		gameState.activePlayerIndex = netTurnTracker.getCurrentTurn();
		return null;
	}
	
	public Bank fromNetBank(NetBank netBank)
	{
		Bank gameBank = new Bank();
		
		try
		{
			gameBank.giveResource(ResourceType.BRICK, netBank.getNumBrick());
			gameBank.giveResource(ResourceType.ORE, netBank.getNumOre());
			gameBank.giveResource(ResourceType.SHEEP, netBank.getNumSheep());
			gameBank.giveResource(ResourceType.WHEAT, netBank.getNumWheat());
			gameBank.giveResource(ResourceType.WOOD, netBank.getNumWood());
		}
		catch (ModelException e)
		{
			System.err.println("An Error has occured while populating the game bank in the Translate class");
		}
		
		return gameBank;
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
			System.err.println("An Error has occured while populating the player bank in the Translate class");
		}
		
		return null;
	}
	
	public VictoryPointManager fromNetVPManager(NetTurnTracker netTurnTracker, List<NetPlayer> netPlayers)
	{
		VictoryPointManager victoryPointManager = new VictoryPointManager();
		
		victoryPointManager.setPlayerToHaveLongestRoad(netTurnTracker.getLongestRoad());
		victoryPointManager.setPlayerToHaveLargestArmy(netTurnTracker.getLargestArmy());
		
		//Currently can't change player victory points from outside VPManager, so I'm not sure what to do about that here...
		//Also can't change size of current largest army
		
		return victoryPointManager;
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
}
