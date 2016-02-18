package shared.model;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.*;
import shared.model.chat.*;
import shared.model.map.*;
import shared.model.map.objects.*;
import shared.networking.transport.*;

/**
 * The Translate class changes game objects into net game objects, and vice versa
 * @author garrettegan, Jonathan Sadler (Map)
 *
 */
public class Translate
{
	/**
	 * Main function -- this translates a netGameModel (from server) into a GameModel (for model)
	 * All of the other functions in this class are sub-functions of this main function
	 * @param netGameModel
	 * @return GameModel
	 */
	public GameModel fromNetGameModel(NetGameModel netGameModel)
	{
		GameModel gameModel = new GameModel();
		
		gameModel.gameState = fromNetTurnTracker(netGameModel.getNetTurnTracker());  //NetGameModel doesn't return a GameStatus
		gameModel.gameBank = fromNetBank(netGameModel.getNetBank(), netGameModel.getNetDeck());
		gameModel.players = fromNetPlayers(netGameModel.getNetPlayers());
		gameModel.victoryPointManager = fromNetVPManager(netGameModel.getNetTurnTracker(), netGameModel.getNetPlayers());
		gameModel.waterCooler = fromNetChat(netGameModel.getNetChat(), netGameModel.getNetPlayers());
		gameModel.log = fromNetLog(netGameModel.getNetGameLog(), netGameModel.getNetPlayers());
		gameModel.version = netGameModel.getVersion();
		gameModel.mapModel = fromNetMap(netGameModel.getNetMap(), netGameModel.getNetPlayers());

		return gameModel;
	}
	
	/**
	 * Translates a NetMap into a MapModel
	 * @param netMap the map returned by the server
	 * @param players the list of players returned by the server
	 * @return MapModel
	 */
	public MapModel fromNetMap(NetMap netMap, List<NetPlayer> players)
	{
		MapModel model = new MapModel();
		
		SetHexes(model, netMap.getNetHexes());
		SetSettlements(model, netMap.getNetSettlements(), players);
		SetCities(model, netMap.getNetCities(), players);
		SetRoads(model, netMap.getNetRoads(), players);
		SetPorts(model, netMap.getNetPorts());
		SetRobber(model, netMap.getRobberLocation());
		
		return model;
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param model
	 * @param hexes
	 */
	private void SetHexes(MapModel model, List<NetHex> hexes)
	{
		for (NetHex hex : hexes)
		{
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
	
	/**
	 * Helper function for fromNetMap function
	 * @param model
	 * @param settlements
	 * @param players
	 */
	private void SetSettlements(MapModel model, List<NetSettlement> settlements, List<NetPlayer> players)
	{
		for (NetSettlement settlement : settlements)
		{
			NetDirectionalLocation location = settlement.getNetDirectionalLocation();
			int x = location.getX();
			int y = location.getY();
			
			Coordinate hex = GetHexCoordinate(x, y);
			Coordinate vertex = GetVertexCoordinate(hex, location.getDirection());
			
			int owner = settlement.getOwner();
			CatanColor color = GetColorFromOwnerInt(owner, players);
			
			try
			{
				model.SetSettlement(vertex, color);
			}
			catch (MapException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param model
	 * @param cities
	 * @param players
	 */
	private void SetCities(MapModel model, List<NetCity> cities, List<NetPlayer> players)
	{
		for (NetCity city : cities)
		{
			NetDirectionalLocation location = city.getNetDirectionalLocation();
			int x = location.getX();
			int y = location.getY();
			
			Coordinate hex = GetHexCoordinate(x, y);
			Coordinate vertex = GetVertexCoordinate(hex, location.getDirection());
			
			int owner = city.getOwner();
			CatanColor color = GetColorFromOwnerInt(owner, players);
			
			try
			{
				model.SetCity(vertex, color);
			}
			catch (MapException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param model
	 * @param roads
	 * @param players
	 */
	private void SetRoads(MapModel model, List<NetRoad> roads, List<NetPlayer> players)
	{
		for (NetRoad road : roads)
		{
			NetDirectionalLocation location = road.getNetEdgeLocation();
			int x = location.getX();
			int y = location.getY();
			
			Coordinate hex = GetHexCoordinate(x, y);
			List<Coordinate> edgeCoordinates = GetEdgeCoordinates(hex, location.getDirection());
			
			int owner = road.getOwnerID();
			CatanColor color = GetColorFromOwnerInt(owner, players);
			
			try
			{
				model.SetRoad(edgeCoordinates.get(0), edgeCoordinates.get(1), color);
			}
			catch (MapException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param model
	 * @param ports
	 */
	private void SetPorts(MapModel model, List<NetPort> ports)
	{
		for (NetPort port : ports)
		{
			try
			{
				NetHexLocation hexLocation = port.getNetHexLocation();
				int x = hexLocation.getX();
				int y = hexLocation.getY();
				
				Coordinate hexCoordinate = GetHexCoordinate(x, y);
				Hex hex = model.GetHex(hexCoordinate);
				
				Direction edgeDirection = port.getDirection();
				List<Coordinate> edgeCoordinates = GetEdgeCoordinates(hexCoordinate, edgeDirection);
				Edge edge = model.GetEdge(edgeCoordinates.get(0), edgeCoordinates.get(1));
				
				PortType type = GetPortType(port.getRatio(), port.getResource());
				
				model.SetPort(type, edge, hex);
			}
			catch (MapException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param model
	 * @param robberLocation
	 */
	private void SetRobber(MapModel model, NetHexLocation robberLocation)
	{
		int x = robberLocation.getX();
		int y = robberLocation.getY();
		
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
	
	/**
	 * Helper function for fromNetMap function
	 * @param x
	 * @param y
	 * @return Coordinate
	 */
	private Coordinate GetHexCoordinate(int x, int y)
	{
		return new Coordinate (x + 3, -2 * y - x);
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param owner
	 * @param players
	 * @return CatanColor
	 */
	private CatanColor GetColorFromOwnerInt(int owner, List<NetPlayer> players)
	{
		for (NetPlayer player : players)
		{
			if (player.getPlayerIndex() == owner)
				return player.getColor();
		}
		
		return null;
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param hex
	 * @param direction
	 * @return Coordinate
	 */
	private Coordinate GetVertexCoordinate(Coordinate hex, Direction direction) //Needs vertex direction
	{
		switch (direction)
		{
			case W: return hex;
			case NW: return hex.GetNorth();
			case NE: return hex.GetNorthEast();
			case E: return hex.GetEast();
			case SE: return hex.GetSouthEast();
			case SW: return hex.GetSouth();
			default: return null;
		}
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param hex
	 * @param direction
	 * @return List<Coordinate>
	 */
	private List<Coordinate> GetEdgeCoordinates(Coordinate hex, Direction direction)
	{
		List<Coordinate> coordinates = new ArrayList<Coordinate>(2);
		
		switch (direction)
		{
		case N:
			coordinates.add(hex.GetNorth());
			coordinates.add(hex.GetNorthEast());
			break;
		case NE:
			coordinates.add(hex.GetNorthEast());
			coordinates.add(hex.GetEast());
			break;
		case SE:
			coordinates.add(hex.GetEast());
			coordinates.add(hex.GetSouthEast());
			break;
		case S:
			coordinates.add(hex.GetSouthEast());
			coordinates.add(hex.GetSouth());
			break;
		case SW:
			coordinates.add(hex.GetSouth());
			coordinates.add(hex);
			break;
		case NW:
			coordinates.add(hex);
			coordinates.add(hex.GetNorth());
			break;
		default:
			break;
		}
		
		return null;
	}
	
	/**
	 * Helper function for fromNetMap function
	 * @param ratio
	 * @param type
	 * @return PortType
	 */
	private PortType GetPortType(int ratio, ResourceType type)
	{
		if (ratio == 3)
			return PortType.THREE;
		else
			return PortType.GetFromResource(type);
	}
	
	/**
	 * Translates NetTurnTracker into GameState
	 * @param netTurnTracker
	 * @return GameState
	 */
	public GameState fromNetTurnTracker(NetTurnTracker netTurnTracker)
	{
		GameState gameState = new GameState();
		gameState.state = netTurnTracker.getRound();
		gameState.activePlayerIndex = netTurnTracker.getCurrentTurn();
		return gameState;
	}
	
	/**
	 * Translates NetBank into Bank
	 * @param netBank
	 * @return Bank
	 */
	public Bank fromNetBank(NetBank netBank, NetDevCardList netDevCardList)
	{
		Bank gameBank = new Bank();
		
		try
		{
			//RESOURCES
			gameBank.giveResource(ResourceType.BRICK, netBank.getNumBrick());
			gameBank.giveResource(ResourceType.ORE, netBank.getNumOre());
			gameBank.giveResource(ResourceType.SHEEP, netBank.getNumSheep());
			gameBank.giveResource(ResourceType.WHEAT, netBank.getNumWheat());
			gameBank.giveResource(ResourceType.WOOD, netBank.getNumWood());
			
			//DEV CARDS
			for (int i = 0; i < netDevCardList.getNumMonopoly(); i++)
			{
				gameBank.giveDevCard(DevCardType.MONOPOLY);
			}
			for (int i = 0; i < netDevCardList.getNumMonument(); i++)
			{
				gameBank.giveDevCard(DevCardType.MONUMENT);
			}
			for (int i = 0; i < netDevCardList.getNumRoadBuilding(); i++)
			{
				gameBank.giveDevCard(DevCardType.ROAD_BUILD);
			}
			for (int i = 0; i < netDevCardList.getNumSoldier(); i++)
			{
				gameBank.giveDevCard(DevCardType.SOLDIER);
			}
			for (int i = 0; i < netDevCardList.getNumYearOfPlenty(); i++)
			{
				gameBank.giveDevCard(DevCardType.YEAR_OF_PLENTY);
			}
		}
		catch (ModelException e)
		{
			System.err.println("An Error has occured while populating the game bank in the Translate class");
		}
		
		return gameBank;
	}
	
	/**
	 * Translates list of NetPlayer into list of Player
	 * @param netPlayers
	 * @return List<Player>
	 */
	public List<Player> fromNetPlayers(List<NetPlayer> netPlayers)
	{
		List<Player> players = new ArrayList<Player>();
		for (int i=0; i < netPlayers.size(); i++)
		{
			Player p = fromNetPlayer(netPlayers.get(i));
			players.add(p);
		}
		return players;
	}
	
	/**
	 * Translates NetPlayer into Player
	 * @param netPlayer
	 * @return Player
	 */
	public Player fromNetPlayer(NetPlayer netPlayer)
	{
		Player player = new Player(netPlayer.getName(), netPlayer.getPlayerIndex(), netPlayer.getColor(), true, netPlayer.getPlayerID());
		System.out.println(player);
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
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.err.println("An Error has occured while populating the player bank in the Translate class.");
		}
		
		return player;
	}
	
	/**
	 * Translates NetTurnTracker into VictoryPointManager
	 * @param netTurnTracker
	 * @param netPlayers
	 * @return VictoryPointManager
	 */
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
	
	/**
	 * Translates NetChat into ChatBox
	 * @param netChat
	 * @return ChatBox
	 */
	public ChatBox fromNetChat(NetChat netChat, List<NetPlayer> netPlayers)
	{
		ChatBox chatBox = new ChatBox();
		for (int i = 0; i < netChat.getLines().size(); i++)
		{
			int playerIndex = playerIDToPlayerIndex(netPlayers, Integer.parseInt(netChat.getLines().get(i).getSource()));
			chatBox.put(netChat.getLines().get(i).getMessage(), playerIndex);
		}
		return chatBox;
	}
	
	/**
	 * Translates NetLog into GameActionLog
	 * @param netLog
	 * @return GameActionLog
	 */
	public GameActionLog fromNetLog(NetLog netLog, List<NetPlayer> netPlayers)
	{
		GameActionLog gameActionLog = new GameActionLog();
		for (int i = 0; i < netLog.getLines().size(); i++)
		{
			int playerIndex = playerIDToPlayerIndex(netPlayers, Integer.parseInt(netLog.getLines().get(i).getSource()));
			gameActionLog.logAction(playerIndex, netLog.getLines().get(i).getMessage());
		}
		return gameActionLog;
	}
	
	public int playerIDToPlayerIndex(List<NetPlayer> netPlayers, int playerID)
	{
		int playerIndex = -1;
		for (int i = 0; i < netPlayers.size(); i++)
		{
			if (netPlayers.get(i).getPlayerID() == playerID)
			{
				playerIndex = netPlayers.get(i).getPlayerIndex();
			}
		}
		if (playerIndex == -1)
		{
			System.err.println("Player ID does not match any player in the game.");
		}
		return playerIndex;
	}
}
