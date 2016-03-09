/**
 * 
 */
package client.networking;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.Direction;
import shared.definitions.GameRound;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.networking.transport.NetBank;
import shared.networking.transport.NetChat;
import shared.networking.transport.NetCity;
import shared.networking.transport.NetDevCardList;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetHex;
import shared.networking.transport.NetHexLocation;
import shared.networking.transport.NetLine;
import shared.networking.transport.NetLog;
import shared.networking.transport.NetMap;
import shared.networking.transport.NetPlayer;
import shared.networking.transport.NetPort;
import shared.networking.transport.NetResourceList;
import shared.networking.transport.NetRoad;
import shared.networking.transport.NetSettlement;
import shared.networking.transport.NetTurnTracker;

/**
 * @author cFortuna
 *
 */
public class MockServerProxy implements JSONServerProxy
{

	/**
	 * Default constructor; sets up everything needed for 
	 * mock server
	 */
	public MockServerProxy()
	{	
		loginCredentials = new ArrayList<String[]>();
		userLoggedIn = false;
		userJoinedGame = false;
		
		initializeStaticStateOfGame();
		initializeStaticListOfGames();
	}
	
	/**
	 * Initializes a hard-coded state of the game
	 */
	private void initializeStaticStateOfGame()
	{
		currentStateOfGame = new NetGameModel();
		
		NetLog currentLog = new NetLog();
		NetLine line = new NetLine();
		line.setMessage("hi");
		line.setSource("source1");
		currentLog.addLine(line);
		currentStateOfGame.setNetGameLog(currentLog);
		
		NetMap currentMap = new NetMap();
		ArrayList<NetHex> mapHexes = new ArrayList<NetHex>();
		
		NetHex hex1 = new NetHex();
		NetHexLocation location1 = new NetHexLocation();
		location1.setX(0);
		location1.setY(-2);
		hex1.setNetHexLocation(location1);
		hex1.setResourceType(ResourceType.ORE);
		hex1.setNumberChit(9);
		mapHexes.add(hex1);
		
		NetHex hex2 = new NetHex();
		NetHexLocation location2 = new NetHexLocation();
		location2.setX(1);
		location2.setY(-2);
		hex2.setNetHexLocation(location2);
		hex2.setResourceType(ResourceType.SHEEP);
		hex2.setNumberChit(10);
		mapHexes.add(hex2);
		
		NetHex hex3 = new NetHex();
		NetHexLocation location3 = new NetHexLocation();
		location3.setX(2);
		location3.setY(-2);
		hex3.setNetHexLocation(location3);
		hex3.setResourceType(ResourceType.SHEEP);
		hex3.setNumberChit(12);
		mapHexes.add(hex3);
		
		NetHex hex4 = new NetHex();
		NetHexLocation location4 = new NetHexLocation();
		location4.setX(-1);
		location4.setY(-1);
		hex4.setNetHexLocation(location4);
		hex4.setResourceType(ResourceType.WOOD);
		hex4.setNumberChit(3);
		mapHexes.add(hex4);
		
		NetHex hex5 = new NetHex();
		NetHexLocation location5 = new NetHexLocation();
		location5.setX(0);
		location5.setY(-1);
		hex5.setNetHexLocation(location5);
		hex5.setResourceType(ResourceType.WHEAT);
		hex5.setNumberChit(8);
		mapHexes.add(hex5);
		
		NetHex hex6 = new NetHex();
		NetHexLocation location6 = new NetHexLocation();
		location6.setX(1);
		location6.setY(-1);
		hex6.setNetHexLocation(location6);
		hex6.setResourceType(ResourceType.WHEAT);
		hex6.setNumberChit(2);
		mapHexes.add(hex6);
		
		NetHex hex7 = new NetHex();
		NetHexLocation location7 = new NetHexLocation();
		location7.setX(2);
		location7.setY(-1);
		hex7.setNetHexLocation(location7);
		hex7.setResourceType(ResourceType.WOOD);
		hex7.setNumberChit(4);
		mapHexes.add(hex7);
		
		NetHex hex8 = new NetHex();
		NetHexLocation location8 = new NetHexLocation();
		location8.setX(-2);
		location8.setY(0);
		hex8.setNetHexLocation(location8);
		hex8.setResourceType(ResourceType.WOOD);
		hex8.setNumberChit(11);
		mapHexes.add(hex8);
		
		NetHex hex9 = new NetHex();
		NetHexLocation location9 = new NetHexLocation();
		location9.setX(-1);
		location9.setY(0);
		hex9.setNetHexLocation(location9);
		hex9.setResourceType(ResourceType.SHEEP);
		hex9.setNumberChit(9);
		mapHexes.add(hex9);
		
		NetHex hex10 = new NetHex();
		NetHexLocation location10 = new NetHexLocation();
		location10.setX(0);
		location10.setY(0);
		hex10.setNetHexLocation(location10);
		hex10.setResourceType(ResourceType.WHEAT);
		hex10.setNumberChit(6);
		mapHexes.add(hex10);
		
		NetHex hex11 = new NetHex();
		NetHexLocation location11 = new NetHexLocation();
		location11.setX(1);
		location11.setY(0);
		hex11.setNetHexLocation(location11);
		hex11.setResourceType(ResourceType.BRICK);
		hex11.setNumberChit(4);
		mapHexes.add(hex11);
		
		NetHex hex12 = new NetHex();
		NetHexLocation location12 = new NetHexLocation();
		location12.setX(-2);
		location12.setY(1);
		hex12.setNetHexLocation(location12);
		hex12.setResourceType(ResourceType.ORE);
		hex12.setNumberChit(5);
		mapHexes.add(hex12);
		
		NetHex hex13 = new NetHex();
		NetHexLocation location13 = new NetHexLocation();
		location13.setX(-1);
		location13.setY(1);
		hex13.setNetHexLocation(location13);
		hex13.setResourceType(ResourceType.WOOD);
		hex13.setNumberChit(6);
		mapHexes.add(hex13);
		
		NetHex hex14 = new NetHex();
		NetHexLocation location14 = new NetHexLocation();
		location14.setX(0);
		location14.setY(1);
		hex14.setNetHexLocation(location14);
		hex14.setResourceType(ResourceType.SHEEP);
		hex14.setNumberChit(10);
		mapHexes.add(hex14);
		
		NetHex hex15 = new NetHex();
		NetHexLocation location15 = new NetHexLocation();
		location15.setX(1);
		location15.setY(1);
		hex15.setNetHexLocation(location15);
		hex15.setResourceType(ResourceType.ORE);
		hex15.setNumberChit(3);
		mapHexes.add(hex15);
		
		NetHex hex16 = new NetHex();
		NetHexLocation location16 = new NetHexLocation();
		location16.setX(-2);
		location16.setY(2);
		hex16.setNetHexLocation(location16);
		hex16.setResourceType(ResourceType.WHEAT);
		hex16.setNumberChit(11);
		mapHexes.add(hex16);
		
		NetHex hex17 = new NetHex();
		NetHexLocation location17 = new NetHexLocation();
		location17.setX(-1);
		location17.setY(2);
		hex17.setNetHexLocation(location17);
		hex17.setResourceType(ResourceType.BRICK);
		hex17.setNumberChit(8);
		mapHexes.add(hex17);
		
		NetHex hex18 = new NetHex();
		NetHexLocation location18 = new NetHexLocation();
		location18.setX(0);
		location18.setY(2);
		hex18.setNetHexLocation(location18);
		hex18.setResourceType(ResourceType.BRICK);
		hex18.setNumberChit(5);
		mapHexes.add(hex18);
		
		currentMap.setNetHexes(mapHexes);
		
		//  no roads placed
		ArrayList<NetRoad> mapRoads = new ArrayList<NetRoad>();
		currentMap.setNetRoads(mapRoads);
		
		//  no cities placed
		ArrayList<NetCity> mapCities = new ArrayList<NetCity>();
		currentMap.setNetCities(mapCities);
		
		//  no settlements placed
		ArrayList<NetSettlement> mapSettlements = new ArrayList<NetSettlement>();
		currentMap.setNetSettlements(mapSettlements);
		
		currentMap.setRadius(3);
		
		
		ArrayList<NetPort> mapPorts = new ArrayList<NetPort>();
		
		NetPort port1 = new NetPort();
		port1.setDirection(Direction.S);
		port1.setRatio(2);
		port1.setResource(ResourceType.ORE);
		NetHexLocation location01 = new NetHexLocation();
		location01.setX(-1);
		location01.setY(-2);
		port1.setNetHexLocation(location01);
		mapPorts.add(port1);
		
		NetPort port2 = new NetPort();
		port2.setDirection(Direction.NE);
		port2.setRatio(3);
		NetHexLocation locatoin02 = new NetHexLocation();
		locatoin02.setX(-2);
		locatoin02.setY(3);
		port2.setNetHexLocation(locatoin02);
		mapPorts.add(port2);

		
		NetPort port3 = new NetPort();
		port3.setDirection(Direction.S);
		port3.setRatio(3);
		NetHexLocation locatoin03 = new NetHexLocation();
		locatoin03.setX(1);
		locatoin03.setY(-3);
		port3.setNetHexLocation(locatoin03);
		mapPorts.add(port3);

		
		NetPort port4 = new NetPort();
		port4.setDirection(Direction.NW);
		port4.setRatio(2);
		port4.setResource(ResourceType.BRICK);
		NetHexLocation location04 = new NetHexLocation();
		location04.setX(2);
		location04.setY(1);
		port4.setNetHexLocation(location04);
		mapPorts.add(port4);

		
		NetPort port5 = new NetPort();
		port5.setDirection(Direction.SW);
		port5.setRatio(3);
		NetHexLocation location05 = new NetHexLocation();
		location05.setX(3);
		location05.setY(-3);
		port5.setNetHexLocation(location05);
		mapPorts.add(port5);

		
		NetPort port6 = new NetPort();
		port6.setDirection(Direction.NW);
		port6.setRatio(3);
		NetHexLocation location06 = new NetHexLocation();
		location06.setX(3);
		location06.setY(-1);
		port6.setNetHexLocation(location06);
		mapPorts.add(port6);

		
		NetPort port7 = new NetPort();
		port7.setDirection(Direction.N);
		port7.setRatio(2);
		port7.setResource(ResourceType.WOOD);
		NetHexLocation location07 = new NetHexLocation();
		location07.setX(0);
		location07.setY(3);
		port7.setNetHexLocation(location07);
		mapPorts.add(port7);

		
		NetPort port8 = new NetPort();
		port8.setDirection(Direction.SE);
		port8.setRatio(2);
		port8.setResource(ResourceType.WHEAT);
		NetHexLocation location08 = new NetHexLocation();
		location08.setX(-3);
		location08.setY(0);
		port8.setNetHexLocation(location08);
		mapPorts.add(port8);

		
		NetPort port9 = new NetPort();
		port9.setDirection(Direction.NE);
		port9.setRatio(2);
		port9.setResource(ResourceType.SHEEP);
		NetHexLocation location09 = new NetHexLocation();
		location09.setX(-3);
		location09.setY(2);
		port9.setNetHexLocation(location09);
		mapPorts.add(port9);
		
		currentMap.setNetPorts(mapPorts);
		
		NetHexLocation robberLocation = new NetHexLocation();
		robberLocation.setX(2);
		robberLocation.setY(0);
		currentMap.setRobberLocation(robberLocation);
		
		currentStateOfGame.setNetMap(currentMap);
		
		
		
		
		ArrayList<NetPlayer> netPlayers = new ArrayList<NetPlayer>();
		NetPlayer player1 = new NetPlayer();
		player1.setNumCities(4);
		player1.setColor(CatanColor.BLUE);
		player1.setHasDiscarded(false);
		player1.setNumMonuments(0);
		player1.setName("SAMM2");
		NetDevCardList devCardList1 = new NetDevCardList();
		player1.setOldNetDevCardList(devCardList1);
		NetDevCardList devCardList2 = new NetDevCardList();
		player1.setNewNetDevCardList(devCardList2);
		player1.setPlayerIndex(0);
		player1.setPlayedDevCard(false);
		player1.setPlayerID(13);
		NetResourceList resourceList = new NetResourceList();
		player1.setNetResourceList(resourceList);
		player1.setNumRoads(15);
		player1.setNumSoldiers(0);
		player1.setNumVictoryPoints(0);
		player1.setNumSettlements(5);
		
		netPlayers.add(player1);
		
		currentStateOfGame.setNetPlayers(netPlayers);
		
		NetDevCardList netDeck = new NetDevCardList();
		netDeck.setNumMonopoly(2);
		netDeck.setNumMonument(5);
		netDeck.setNumRoadBuilding(2);
		netDeck.setNumSoldier(14);
		netDeck.setNumYearOfPlenty(2);
		currentStateOfGame.setNetDeck(netDeck);
		
		NetBank netBank = new NetBank();
		netBank.setNumBrick(24);
		netBank.setNumOre(24);
		netBank.setNumWood(24);
		netBank.setNumSheep(24);
		netBank.setNumWheat(24);
		currentStateOfGame.setNetBank(netBank);
		
		
		NetChat netChat = new NetChat();
		NetLine newLine = new NetLine();
		newLine.setMessage("string");
		newLine.setSource("SAMM2");
		netChat.addLine(newLine);
		currentStateOfGame.setNetChat(netChat);
		currentStateOfGame.setNetTradeOffer(null);
		
		NetTurnTracker turnTracker = new NetTurnTracker();
		turnTracker.setCurrentTurn(0);
		turnTracker.setLongestRoad(-1);
		turnTracker.setLargestArmy(-1);
		turnTracker.setRound(GameRound.FIRSTROUND);
		currentStateOfGame.setNetTurnTracker(turnTracker);
		
		currentStateOfGame.setWinner(-1);
		currentStateOfGame.setVersion(0);
	}
	
	/**
	 * Initializes a hard coded static state of the game
	 */
	private void initializeStaticListOfGames()
	{
		gameList = new ArrayList<NetGame>();
		
		NetGame game1 = new NetGame();
		game1.setId(0);
		game1.setTitle("Default Game");

		ArrayList<NetPlayer> netPlayers = new ArrayList<NetPlayer>();
		
		NetPlayer player1 = new NetPlayer();
		player1.setNumCities(4);
		player1.setColor(CatanColor.ORANGE);
		player1.setHasDiscarded(false);
		player1.setNumMonuments(0);
		player1.setName("Sam");
		NetDevCardList devCardList1 = new NetDevCardList();
		player1.setOldNetDevCardList(devCardList1);
		NetDevCardList devCardList2 = new NetDevCardList();
		player1.setNewNetDevCardList(devCardList2);
		player1.setPlayerIndex(0);
		player1.setPlayedDevCard(false);
		player1.setPlayerID(0);
		NetResourceList resourceList = new NetResourceList();
		player1.setNetResourceList(resourceList);
		player1.setNumRoads(15);
		player1.setNumSoldiers(0);
		player1.setNumVictoryPoints(0);
		player1.setNumSettlements(5);
		netPlayers.add(player1);
		
		NetPlayer player2 = new NetPlayer();
		player2.setNumCities(4);
		player2.setColor(CatanColor.RED);
		player2.setHasDiscarded(false);
		player2.setNumMonuments(0);
		player2.setName("Pete");
		NetDevCardList devCardList3 = new NetDevCardList();
		player2.setOldNetDevCardList(devCardList3);
		NetDevCardList devCardList4 = new NetDevCardList();
		player2.setNewNetDevCardList(devCardList4);
		player2.setPlayerIndex(0);
		player2.setPlayedDevCard(false);
		player2.setPlayerID(10);
		NetResourceList resourceList2 = new NetResourceList();
		player2.setNetResourceList(resourceList2);
		player2.setNumRoads(15);
		player2.setNumSoldiers(0);
		player2.setNumVictoryPoints(0);
		player2.setNumSettlements(5);
		netPlayers.add(player2);
		
		NetPlayer player3 = new NetPlayer();
		player3.setNumCities(4);
		player3.setColor(CatanColor.BLUE);
		player3.setHasDiscarded(false);
		player3.setNumMonuments(0);
		player3.setName("Brooke");
		NetDevCardList devCardList5 = new NetDevCardList();
		player3.setOldNetDevCardList(devCardList5);
		NetDevCardList devCardList6 = new NetDevCardList();
		player3.setNewNetDevCardList(devCardList6);
		player3.setPlayerIndex(0);
		player3.setPlayedDevCard(false);
		player3.setPlayerID(1);
		NetResourceList resourceList3 = new NetResourceList();
		player3.setNetResourceList(resourceList3);
		player3.setNumRoads(15);
		player3.setNumSoldiers(0);
		player3.setNumVictoryPoints(0);
		player3.setNumSettlements(5);
		netPlayers.add(player3);
		
		NetPlayer player4 = new NetPlayer();
		player4.setNumCities(4);
		player4.setColor(CatanColor.GREEN);
		player4.setHasDiscarded(false);
		player4.setNumMonuments(0);
		player4.setName("Mark");
		NetDevCardList devCardList7 = new NetDevCardList();
		player4.setOldNetDevCardList(devCardList7);
		NetDevCardList devCardList8 = new NetDevCardList();
		player4.setNewNetDevCardList(devCardList8);
		player4.setPlayerIndex(0);
		player4.setPlayedDevCard(false);
		player4.setPlayerID(11);
		NetResourceList resourceList4 = new NetResourceList();
		player4.setNetResourceList(resourceList4);
		player4.setNumRoads(15);
		player4.setNumSoldiers(0);
		player4.setNumVictoryPoints(0);
		player4.setNumSettlements(5);
		netPlayers.add(player4);
		
		game1.setNetPlayers(netPlayers);
		gameList.add(game1);
		
		
		
		
		
		NetGame game2 = new NetGame();
		game2.setId(1);
		game2.setTitle("AI Game");

		ArrayList<NetPlayer> netPlayers2 = new ArrayList<NetPlayer>();
		
		NetPlayer aplayer1 = new NetPlayer();
		aplayer1.setNumCities(4);
		aplayer1.setColor(CatanColor.ORANGE);
		aplayer1.setHasDiscarded(false);
		aplayer1.setNumMonuments(0);
		aplayer1.setName("Pete");
		NetDevCardList adevCardList1 = new NetDevCardList();
		aplayer1.setOldNetDevCardList(devCardList1);
		NetDevCardList adevCardList2 = new NetDevCardList();
		aplayer1.setNewNetDevCardList(devCardList2);
		aplayer1.setPlayerIndex(0);
		aplayer1.setPlayedDevCard(false);
		aplayer1.setPlayerID(10);
		NetResourceList aresourceList = new NetResourceList();
		aplayer1.setNetResourceList(resourceList);
		aplayer1.setNumRoads(15);
		aplayer1.setNumSoldiers(0);
		aplayer1.setNumVictoryPoints(0);
		aplayer1.setNumSettlements(5);
		netPlayers2.add(aplayer1);
		
		NetPlayer aplayer2 = new NetPlayer();
		aplayer2.setNumCities(4);
		aplayer2.setColor(CatanColor.PUCE);
		aplayer2.setHasDiscarded(false);
		aplayer2.setNumMonuments(0);
		aplayer2.setName("Hannah");
		NetDevCardList adevCardList3 = new NetDevCardList();
		aplayer2.setOldNetDevCardList(devCardList3);
		NetDevCardList adevCardList4 = new NetDevCardList();
		aplayer2.setNewNetDevCardList(devCardList4);
		aplayer2.setPlayerIndex(0);
		aplayer2.setPlayedDevCard(false);
		aplayer2.setPlayerID(-2);
		NetResourceList aresourceList2 = new NetResourceList();
		aplayer2.setNetResourceList(resourceList2);
		aplayer2.setNumRoads(15);
		aplayer2.setNumSoldiers(0);
		aplayer2.setNumVictoryPoints(0);
		aplayer2.setNumSettlements(5);
		netPlayers2.add(aplayer2);
		
		NetPlayer aplayer3 = new NetPlayer();
		aplayer3.setNumCities(4);
		aplayer3.setColor(CatanColor.YELLOW);
		aplayer3.setHasDiscarded(false);
		aplayer3.setNumMonuments(0);
		aplayer3.setName("Squall");
		NetDevCardList adevCardList5 = new NetDevCardList();
		aplayer3.setOldNetDevCardList(devCardList5);
		NetDevCardList adevCardList6 = new NetDevCardList();
		aplayer3.setNewNetDevCardList(devCardList6);
		aplayer3.setPlayerIndex(0);
		aplayer3.setPlayedDevCard(false);
		aplayer3.setPlayerID(-3);
		NetResourceList aresourceList3 = new NetResourceList();
		aplayer3.setNetResourceList(resourceList3);
		aplayer3.setNumRoads(15);
		aplayer3.setNumSoldiers(0);
		aplayer3.setNumVictoryPoints(0);
		aplayer3.setNumSettlements(5);
		netPlayers2.add(aplayer3);
		
		NetPlayer aplayer4 = new NetPlayer();
		aplayer4.setNumCities(4);
		aplayer4.setColor(CatanColor.RED);
		aplayer4.setHasDiscarded(false);
		aplayer4.setNumMonuments(0);
		aplayer4.setName("Quinn");
		NetDevCardList adevCardList7 = new NetDevCardList();
		aplayer4.setOldNetDevCardList(devCardList7);
		NetDevCardList adevCardList8 = new NetDevCardList();
		aplayer4.setNewNetDevCardList(devCardList8);
		aplayer4.setPlayerIndex(0);
		aplayer4.setPlayedDevCard(false);
		aplayer4.setPlayerID(-4);
		NetResourceList aresourceList4 = new NetResourceList();
		aplayer4.setNetResourceList(resourceList4);
		aplayer4.setNumRoads(15);
		aplayer4.setNumSoldiers(0);
		aplayer4.setNumVictoryPoints(0);
		aplayer4.setNumSettlements(5);
		netPlayers2.add(aplayer4);
		
		game2.setNetPlayers(netPlayers2);
		gameList.add(game2);
	}
	
	/**
	 * Logs the specified user in and returns a User object if the user was
	 * successfully logged in. If not, a null user is returned
	 * @param username The username of the user to log in
	 * @param password The password of the user to log in
	 * @throws ServerProxyException if the user could not be logged in
	 * @return boolean true when the user could be logged in, false when he couldn't
	 */
	@Override
	public boolean loginUser(String username, String password)
	{
		// if the username is in use then check the password, if both are the same true, false otherwise
		for(String[] credPair : loginCredentials)
		{
			if(username.equals(credPair[0]))
			{
				if(password.equals(credPair[1]))
				{
					userLoggedIn = true;
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		return false;
	}

	/**
	 * Registers a user with the specified username and password
	 * @param username The username of the user to be registered
	 * @param password The password of the user to be registered
	 * @throws ServerProxyException if this user could not be registered
	 * @return boolean true when the user could be registered, false when he couldn't
	 */
	@Override
	public boolean registerUser(String username, String password) 
	{
		if(usernameIsValid(username) && passwordIsValid(password))
		{
			String[] userCredentials = {username, password};
			loginCredentials.add(userCredentials);
			return true;
		}
		
		return false;
	}
	
	private boolean usernameIsValid(String username)
	{
		if (username != null && username.length() > 0)
		{
			// if the username isn't in use then allow it, otherwise return false
			for(String[] credPair : loginCredentials)
			{
				if(username.equals(credPair[0]))
				{
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
	
	private boolean passwordIsValid(String password)
	{
		if(password != null && password.length() > 0)
		{
			return true;
		}
		
		return false;
	}

	/**
	 * Fetches a list of ongoing games
	 * @return a list of all ongoing games on the server
	 */
	@Override
	public List<NetGame> listGames()
	{
		return gameList;
	}

	/**
	 * Creates a game on the server
	 * @param randomTiles Whether the server should place random tiles
	 * @param randomNumbers Whether the server should place random number chits
	 * @param randomPorts Whether the server should place random ports
	 * @param name The name of the game to be created
	 * @return a ProxyGame object that represents the game that was created
	 * @throws ServerProxyException if something goes wrong
	 */
	@Override
	public NetGame createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerProxyException
	{
        if(name == null || name.equals(""))
        {
            throw new ServerProxyException("Invalid name for creat game");
        }
        
		return gameList.get(0);
	}

	/**
	 * 
	 * @param color the color the user wishes to represent him
	 * @return a ProxyGame object that represents the game the user joined
	 * @throws ServerProxyException if something goes wrong
	 */
	@Override
	public void joinGame(int id, CatanColor color) throws ServerProxyException
	{
        if(!userLoggedIn)
        {
            throw new ServerProxyException("JoinGame invalid when user not logged in");
        }
	}

	/**
	 * Retrieves the game model from the server
	 * @return a NetGameModel object
	 * @throws ServerProxyException if something goes wrong
	 */
	@Override
	public NetGameModel getGameModel() throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("GetGameModel invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Adds an AI to the game
	 * @param aiType the type of AI the user wishes to add
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public void addAI(AIType aiType) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("AddAI invalid when user not logged in");
        }
		//  mock does nothing/returns nothing :)
	}

	/**
	 * 
	 * @return a list of supported AI player types
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public List<AIType> listAI()
	{
		ArrayList<AIType> aiList = new ArrayList<AIType>();
		aiList.add(AIType.LARGEST_ARMY);
		return aiList;
	}

	/**
	 * Sends a chat message from the specified user to the server
	 * @param content The content of the chat message
	 * @return A NetGameModel object
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel sendChat(String content) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("sednChat invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Reports the result of a dice roll to the server
	 * @param roll The result of the user's roll
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel rollNumber(int roll) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("RollNumber invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has decided to rob another player
	 * @param victimIndex The index of the victim of the user's robbing
	 * @param location The new hex location of the robber
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel robPlayer(int victimIndex, HexLocation location) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("RobPlayer invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has finished his turn
	 * @param user The User who has finished his turn
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel finishTurn() throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("FinishTurn invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has bought a development card
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel buyDevCard() throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("BuyDevCard invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has played a year of plenty card
	 * @param resource1 The first chosen resource
	 * @param resource2 The second chosen resource
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel yearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("YearOfPlentyCard invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has played a road building card
	 * @param location1 The EdgeLocation location of the first road
	 * @param location2 The EdgeLocation location of the second card
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel roadBuildingCard(EdgeLocation location1, EdgeLocation location2) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("RoadBuildingCard invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has played a soldier card
	 * @param victimIndex The index of the player who is being robbed
	 * @param hexLocation The new hex location of the robber
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel soldierCard(int victimIndex, HexLocation hexLocation) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("soldierCard invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has played a monopoly card
	 * @param resource The resource that the player has chosen to have a monopoly on
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel monopolyCard(ResourceType resource) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("monopolyCard invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has played a monument card
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel monumentCard() throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("monumentCard invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has decided to build a road
	 * @param edgeLocation The edge location of the road
	 * @param free Whether this road was free (only true during the set up phases)
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel buildRoad(EdgeLocation edgeLocation, boolean free) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("buildRoad invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has decided to build a settlement
	 * @param vertexLocation The vertex location of the settlement
	 * @param free Whether this settlement was free (only true during the set up phases)
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel buildSettlement(VertexLocation vertexLocation, boolean free) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("buildSettlement invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has decided to build a city
	 * @param vertexLocation The vertex location of the city
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel buildCity(VertexLocation vertexLocation) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("buildCity invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has decided to offer a trade to another player
	 * @param resourceList A list of the resources that the user wishes to trade, in this order: brick, ore, sheep,
	 * wheat, and wood. Negative values denotes that this user will give these resources, and positive values denote 
	 * the resources that will be received
	 * @param receiver The index of the player who will receive this trade offer
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel offerTrade(List<Integer> resourceList, int receiver) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("offerTrade invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server whether this player has decided to accept or reject a trade
	 * @param willAccept true if the user will accept the trade, false if not
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel acceptTrade(boolean willAccept) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("acceptTrade invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has decided to initiate a maritime trade
	 * @param ratio The ratio of resources demanded by the harbor
	 * @param inputResource The resources traded away
	 * @param outputResource The resource received
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("maritimeTrade invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}

	/**
	 * Notifies the server that the user has discarded cards
	 * @param resourceList A list of integers that denotes how many of each resource the user will discard. The
	 * order is brick, ore, sheep, wheat, and wood
	 * @return a NetGameModel object that reflects the current state of the Game
	 * @throws ServerProxyException if there is no logged in user
	 */
	@Override
	public NetGameModel discardCards(List<Integer> resourceList) throws ServerProxyException
	{
        if(!userLoggedIn || !userJoinedGame)
        {
            throw new ServerProxyException("Discard Cards invalid when user not logged in");
        }
        
		return currentStateOfGame;
	}
	
	
	
	private NetGameModel currentStateOfGame;
	private ArrayList<NetGame> gameList;
	private ArrayList<String[]> loginCredentials;
	private boolean userLoggedIn;
	private boolean userJoinedGame;
	@Override
	public String getUserName() throws ServerProxyException
	{
		return null;
	}

	@Override
	public int getUserId()
	{
		return 0;
	}
}
