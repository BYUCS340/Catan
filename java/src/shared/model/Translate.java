package shared.model;

import java.util.ArrayList;
import java.util.List;

import client.map.*;
import shared.definitions.*;
import shared.model.chat.*;
import shared.model.map.Coordinate;
import shared.model.map.MapException;
import shared.model.map.MapModel;
import shared.model.map.objects.Hex;
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
		
		gameModel.gameState = fromNetTurnTracker(netGameModel.getNetTurnTracker());  //FINISHED? -- but NetGameModel doesn't return a GameStatus
		gameModel.gameBank = fromNetBank(netGameModel.getNetBank());  //FINISHED? -- but only has resource cards (no dev cards)
		gameModel.players = fromNetPlayers(netGameModel.getNetPlayers());  //FINISHED?
		gameModel.victoryPointManager = fromNetVPManager(netGameModel.getNetTurnTracker(), netGameModel.getNetPlayers());  //UNFINISHED
		gameModel.waterCooler = fromNetChat(netGameModel.getNetChat());  //FINISHED? -- but only posts as player 0
		gameModel.log = fromNetLog(netGameModel.getNetGameLog());  //FINISHED? -- but only logs as player 0
		gameModel.version = netGameModel.getVersion();  //FINISHED
		gameModel.mapModel = fromNetMap(netGameModel.getNetMap());  //UNSTARTED -- this is tricky cause I don't know our Map very well

		return gameModel;
	}
	
	public MapModel fromNetMap(NetMap netMap)
	{
		MapModel model = new MapModel();
		
		SetHexes(model, netMap);
		SetSettlements(model, netMap);
		SetCities(model, netMap);
		
		return model;
	}
	
	private void SetHexes(MapModel model, NetMap netMap)
	{
		if (model.IsInitialized())
			return;
		
		List<NetHex> hexes = netMap.getNetHexes();
		for (NetHex hex : hexes)
		{
			//TODO How do we know about dessert and water?
			ResourceType resourceType = hex.getResourceType();
			HexType hexType = HexType.GetFromResource(resourceType);
			
			NetHexLocation location = hex.getNetHexLocation();
			int x = location.getX();
			int y = location.getY();
			
			Coordinate point = GetHexCoordinate(x, y);
			
			try
			{
				model.SetHex(hexType, point);
				
				Hex hexFromModel = model.GetHex(point);
				
				int value = hex.getNumberChit();
				model.SetPip(value, hexFromModel);
			}
			catch (MapException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void SetSettlements(MapModel model, NetMap netMap)
	{
		List<NetSettlement> settlements = netMap.getNetSettlements();
		
		for (NetSettlement settlement : settlements)
		{
			
			NetEdgeLocation location = settlement.getNetEdgeLocation();
			int x = location.getX();
			int y = location.getY();
			
			
			Coordinate hex = GetHexCoordinate(x, y);
			Coordinate vertex = GetVertexCoordinate(hex);
			
			//model.SetSettlement(vertex, color);
		}
	}
	
	private void SetCities(MapModel model, NetMap netMap)
	{
		List<NetCity> cities = netMap.getNetCities();
		
		for (NetCity city : cities)
		{
			//Set up like settlement
		}
	}
	
	private void SetRobber(MapModel model, NetMap netMap)
	{
		NetHexLocation hexLocation = netMap.getRobberLocation();
		int x = hexLocation.getX();
		int y = hexLocation.getY();
		
		try
		{
			Coordinate hexPoint = GetHexCoordinate(x, y);
			Hex hex = model.GetHex(hexPoint);
			model.SetRobber(hex);
		}
		catch (MapException e) 
		{
			e.printStackTrace();
		}
	}
	
	private Coordinate GetHexCoordinate(int x, int y)
	{
		return new Coordinate (x + 3, -2 * y - x);
	}
	
	private Coordinate GetVertexCoordinate(Coordinate hex) //Needs vertex direction
	{
		//TODO Finish with correct stuff
		return null;
		
		
		/*
		 * if west      - return hex;
		 * if northwest - return hex.GetNorth();
		 * if northeast - return hex.GetNorthEast();
		 * if east      - return hex.GetEast();
		 * if southeast - return hex.GetSouthEast();
		 * if southwest - return hex.GetWest();
		 */
	}
	
	public GameState fromNetTurnTracker(NetTurnTracker netTurnTracker)
	{
		GameState gameState = new GameState();
		gameState.state = netTurnTracker.getRound();
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
		int p1Points = netPlayers.get(0).getNumVictoryPoints();
		int p2Points = netPlayers.get(1).getNumVictoryPoints();
		int p3Points = netPlayers.get(2).getNumVictoryPoints();
		int p4Points = netPlayers.get(3).getNumVictoryPoints();
		
		int longRoad = netTurnTracker.getLongestRoad();
		int largeArmy = netTurnTracker.getLargestArmy();
		
		int armySize = 0;
		int soliders = 0;
		
		//Figure out the largest army
		for (int i=0; i< netPlayers.size(); i++)
		{
			soliders = netPlayers.get(i).getNumSoldiers();
			if (soliders > armySize)
				armySize = soliders;
		}
		
		VictoryPointManager victoryPointManager = new VictoryPointManager(p1Points, p2Points, p3Points, p4Points, longRoad, largeArmy,armySize);
		
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
