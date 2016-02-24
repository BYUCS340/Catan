package shared.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shared.model.map.*;
import shared.model.map.model.MapModel;
import shared.model.map.objects.Edge;
import shared.definitions.*;
import shared.locations.*;
import shared.model.chat.ChatBox;
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

		gameModel.gameState = fromNetTurnTracker(netGameModel.getNetTurnTracker());  //FINISHED? -- but NetGameModel doesn't return a GameStatus
		gameModel.gameBank = fromNetBank(netGameModel.getNetBank());  //FINISHED? -- but only has resource cards (no dev cards)
		gameModel.players = fromNetPlayers(netGameModel.getNetPlayers());  //FINISHED?
		gameModel.victoryPointManager = fromNetVPManager(netGameModel.getNetTurnTracker(), netGameModel.getNetPlayers());  //UNFINISHED
		gameModel.waterCooler = fromNetChat(netGameModel.getNetChat());  //FINISHED? -- but only posts as player 0
		gameModel.log = fromNetLog(netGameModel.getNetGameLog());  //FINISHED? -- but only logs as player 0
		gameModel.version = netGameModel.getVersion();  //FINISHED
		gameModel.mapModel = fromNetMap(netGameModel.getNetMap(), netGameModel.getNetPlayers());  //FINISHED -- I think ...

		if (gameModel.players.size() <= 3)
		{
			gameModel.gameState.state = GameRound.WAITING;
			gameModel.gameState.activePlayerIndex = -1;
		}

		return gameModel;
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
		//System.out.println(player);
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
		int points[] = new int[4];
		Arrays.fill(points, 0);
		for (int i=0; i < netPlayers.size(); i++)
		{
			points[i] = netPlayers.get(i).getNumVictoryPoints();
		}


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

		VictoryPointManager victoryPointManager = new VictoryPointManager(points[0], points[1], points[2], points[2], longRoad, largeArmy,armySize);

		return victoryPointManager;
	}

	/**
	 * Translates NetChat into ChatBox
	 * @param netChat
	 * @return ChatBox
	 */
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

	/**
	 * Translates NetLog into GameActionLog
	 * @param netLog
	 * @return GameActionLog
	 */
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

	/**
	 * Translates a NetMap into a MapModel
	 * @param netMap the map returned by the server
	 * @param players the list of players returned by the server
	 * @return MapModel
	 */
	public MapModel fromNetMap(NetMap netMap, List<NetPlayer> players)
	{
		MapModel model = new MapModel();
		MapGenerator.WaterSetup(model);

		SetHexes(model, netMap.getNetHexes());
		SetPorts(model, netMap.getNetPorts());
		SetSettlements(model, netMap.getNetSettlements(), players);
		SetCities(model, netMap.getNetCities(), players);
		SetRoads(model, netMap.getNetRoads(), players);
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

			HexType hexType = HexType.DESERT;
			if (resourceType != null)
				hexType = HexType.GetFromResource(resourceType);

			NetHexLocation location = hex.getNetHexLocation();
			int x = location.getX();
			int y = location.getY();

			Coordinate point = GetHexCoordinate(x, y);

			try
			{
				model.PlaceHex(hexType, point);

				int value = hex.getNumberChit();
				model.PlacePip(value, point);
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
				model.PlaceSettlement(vertex, color);
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
				//Place settlement first so the city placement doesn't get mad.
				model.PlaceSettlement(vertex, color);
				model.PlaceCity(vertex, color);
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
				model.PlaceRoad(edgeCoordinates.get(0), edgeCoordinates.get(1), color);
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
		//These points we can determine the edge, which is good as it isn't always given.
		Coordinate invalidTopRight = new Coordinate(6, 3);
		Coordinate invalidTop = new Coordinate(3, 6);
		Coordinate invalidTopLeft = new Coordinate(0, 3);
		Coordinate invalidBottomRight = new Coordinate(6, -3);
		Coordinate invalidBottom = new Coordinate(3, -6);
		Coordinate invalidBottomLeft = new Coordinate(0, -3);

		for (NetPort port : ports)
		{
			try
			{
				NetHexLocation hexLocation = port.getNetHexLocation();
				int x = hexLocation.getX();
				int y = hexLocation.getY();

				Coordinate hex = GetHexCoordinate(x, y);
				//System.out.println("Port:"+port);
				//System.out.println("Hex coord:"+hex);
				Direction edgeDirection = port.getDirection();

				if (edgeDirection == null)
				{
					if (hex.equals(invalidTopRight))
						edgeDirection = Direction.SW;
					else if (hex.equals(invalidTop))
						edgeDirection = Direction.S;
					else if (hex.equals(invalidTopLeft))
						edgeDirection = Direction.SE;
					else if (hex.equals(invalidBottomRight))
						edgeDirection = Direction.NW;
					else if (hex.equals(invalidBottom))
						edgeDirection = Direction.N;
					else if (hex.equals(invalidBottomLeft))
						edgeDirection = Direction.NE;
					else
					{
						Exception e = new ModelException("Port edge direction not provided and unable to infer");
						e.printStackTrace();
						//Since we can't get the port information, we are going to continue without it.
						continue;
					}
				}

				List<Coordinate> edgeCoordinates = GetEdgeCoordinates(hex, edgeDirection);

				PortType type = GetPortType(port.getRatio(), port.getResource());

				model.PlacePort(type, hex, edgeCoordinates.get(0), edgeCoordinates.get(1));
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
			model.PlaceRobber(hexPoint);
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
	private static Coordinate GetHexCoordinate(int x, int y)
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

		return coordinates;
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

	public static EdgeLocation GetEdgeLocation(Coordinate p1, Coordinate p2)
	{
		HexLocation hexLocation = GetHexLocation(p1, p2);
		Coordinate hex = GetHexCoordinate(hexLocation.getX(), hexLocation.getY());

		EdgeDirection direction = GetEdgeDirection(hex, p1, p2);

		return new EdgeLocation(hexLocation, direction);
	}

	public static VertexLocation GetVertexLocation(Coordinate point)
	{
		HexLocation hexLocation = null;
		if (point.isLeftHandCoordinate() && point.getX() <= 5)
			hexLocation = GetHexLocation(point);
		else if (point.isLeftHandCoordinate() && point.getY() > 0)
			hexLocation = GetHexLocation(point.GetSouthWest());
		else if (point.isLeftHandCoordinate())
			hexLocation = GetHexLocation(point.GetNorthWest());
		else
			hexLocation = GetHexLocation(point.GetWest());

		Coordinate hex = GetHexCoordinate(hexLocation.getX(), hexLocation.getY());

		VertexDirection direction = GetVertexDirection(hex, point);

		return new VertexLocation(hexLocation, direction);
	}

	public static HexLocation GetHexLocation(Coordinate point)
	{
		int x = point.getX();
		int y = point.getY();

		//Our y location that should return 0 for theirs
		int yZero = 3 - x;

		//yZero - y should always be a multiple of 2
		return new HexLocation(x - 3, (yZero - y) / 2);
	}

	private static HexLocation GetHexLocation(Coordinate p1, Coordinate p2)
	{
		Coordinate primary = null;

		//Horizontal edges
		if (p1.getY() == p2.getY())
		{
			//Grab the edge closest to critical vertex (hex coordinate based on left)
			if (p1.getX() < p2.getX())
				primary = p1;
			else
				primary = p2;

			//If below zero, we need the point above, else point below critical
			if (primary.getY() <= 0)
				return GetHexLocation(primary.GetNorth());
			else
				return GetHexLocation(primary.GetSouth());
		}
		//Diagonal edges
		else
		{
			//Grab the point that is closest to zero
			if (Math.abs(p1.getY()) < Math.abs(p2.getY()))
				primary = p1;
			else
				primary = p2;

			//If left, we have critical, else we need west edge
			if (primary.isLeftHandCoordinate())
				return GetHexLocation(primary);
			else
				return GetHexLocation(primary.GetWest());
		}
	}

	private static EdgeDirection GetEdgeDirection(Coordinate hex, Coordinate p1, Coordinate p2)
	{
		int rotation = Edge.GetRotation(hex, p1, p2);

		switch(rotation)
		{
		case 0:
			return EdgeDirection.South;
		case 60:
			return EdgeDirection.SouthEast;
		case 120:
			return EdgeDirection.NorthEast;
		case 180:
			return EdgeDirection.North;
		case 240:
			return EdgeDirection.NorthWest;
		case 300:
			return EdgeDirection.SouthWest;
		default:
			assert false;
			return null;
		}
	}

	private static VertexDirection GetVertexDirection(Coordinate hex, Coordinate point)
	{
		if (hex.getY() == point.getY())
		{
			if (hex.getX() == point.getX())
				return VertexDirection.West;
			else
				return VertexDirection.East;
		}
		else if (hex.getY() < point.getY())
		{
			if (hex.getX() == point.getX())
				return VertexDirection.NorthWest;
			else
				return VertexDirection.NorthEast;
		}
		else
		{
			if (hex.getX() == point.getX())
				return VertexDirection.SouthWest;
			else
				return VertexDirection.SouthEast;
		}
	}
}
