/**
 * 
 */
package shared.networking;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.Direction;
import shared.definitions.GameRound;
import shared.definitions.ResourceType;
import shared.networking.transport.NetBank;
import shared.networking.transport.NetChat;
import shared.networking.transport.NetCity;
import shared.networking.transport.NetDevCardList;
import shared.networking.transport.NetDirectionalLocation;
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
import shared.networking.transport.NetTradeOffer;
import shared.networking.transport.NetTurnTracker;

/**
 * @author pbridd
 *
 */
public class JSONDeserializer implements Deserializer
{

	/* (non-Javadoc)
	 * @see shared.networking.Deserializer#parseNetAI(java.lang.String)
	 */
	@Override
	public List<AIType> parseAIList(String rawData) throws JSONException
	{
		JSONArray obj = new JSONArray(rawData);
		
		List<AIType> aiList = new ArrayList<AIType>();
		for(int i = 0; i < obj.length(); i++)
		{
			if(obj.isNull(i) || obj.getString(i).length() == 0){
				continue;
			}
			AIType tempAI = AIType.fromString(obj.getString(i));
			aiList.add(tempAI);
		}
		
		return aiList;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Deserializer#parseNetGame(java.lang.String)
	 */
	@Override
	public NetGame parseNetGame(String rawData) throws JSONException
	{
		NetGame netGame = new NetGame();
		
		//parse into JSONObject
		JSONObject obj = new JSONObject(rawData);
		
		//get nonarray values
		String title = obj.getString("title");
		int id = obj.getInt("id");
		
		netGame.setTitle(title);
		netGame.setId(id);
		
		//get array (player) values
		JSONArray playerArr = obj.getJSONArray("players");
		
		List<NetPlayer> netPlayers = new ArrayList<NetPlayer>();
		for(int i = 0; i < playerArr.length(); i++)
		{
			if(playerArr.isNull(i) || playerArr.getJSONObject(i).length() == 0){
				continue;
			}
			NetPlayer tempNetPlayer = parsePartialNetPlayer(playerArr.getJSONObject(i).toString());
			tempNetPlayer.setPlayerIndex(i);
			netPlayers.add(tempNetPlayer);
		}
		
		netGame.setNetPlayers(netPlayers);
		
		
		return netGame;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Deserializer#parsePartialNetPlayer(java.lang.String)
	 */
	@Override
	public NetPlayer parsePartialNetPlayer(String rawData) throws JSONException
	{
		NetPlayer netPlayer = new NetPlayer();
		JSONObject obj = new JSONObject(rawData);
		
		CatanColor color = CatanColor.fromString(obj.getString("color"));
		String name = obj.getString("name");
		int id = obj.getInt("id");
		
		netPlayer.setColor(color);
		netPlayer.setName(name);
		netPlayer.setPlayerID(id);
		
		return netPlayer;
	}
	
	/* (non-Javadoc)
	 * @see shared.networking.Deserializer#parseNetPlayer(java.lang.String)
	 */
	@Override
	public NetPlayer parseNetPlayer(String rawData) throws JSONException
	{
		NetPlayer netPlayer = new NetPlayer();
		JSONObject obj = new JSONObject(rawData);
		
		//get data
		CatanColor color = CatanColor.fromString(obj.getString("color"));
		String name = obj.getString("name");
		int id = obj.getInt("playerID");
		int numCities = obj.getInt("cities");
		boolean discarded = obj.getBoolean("discarded");
		int numMonuments = obj.getInt("monuments");
		NetDevCardList newDevCards = parseNetDevCardList(obj.getJSONObject("newDevCards").toString());
		NetDevCardList oldDevCards = parseNetDevCardList(obj.getJSONObject("oldDevCards").toString());
		int playerIndex = obj.getInt("playerIndex");
		boolean playedDevCard = obj.getBoolean("playedDevCard");
		NetResourceList resources = parseNetResourceList(obj.getJSONObject("resources").toString());
		int roads = obj.getInt("roads");
		int settlements = obj.getInt("settlements");
		int soldiers = obj.getInt("soldiers");
		int victoryPoints = obj.getInt("victoryPoints");
		
		netPlayer.setColor(color);
		netPlayer.setName(name);
		netPlayer.setPlayerID(id);
		netPlayer.setNumCities(numCities);
		netPlayer.setHasDiscarded(discarded);
		netPlayer.setNumMonuments(numMonuments);
		netPlayer.setNewNetDevCardList(newDevCards);
		netPlayer.setOldNetDevCardList(oldDevCards);
		netPlayer.setPlayerIndex(playerIndex);
		netPlayer.setPlayedDevCard(playedDevCard);
		netPlayer.setNetResourceList(resources);
		netPlayer.setNumRoads(roads);
		netPlayer.setNumSettlements(settlements);
		netPlayer.setNumSoldiers(soldiers);
		netPlayer.setNumVictoryPoints(victoryPoints);
		
		
		return netPlayer;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Deserializer#parseNetGameModel(java.lang.String)
	 */
	@Override
	public NetGameModel parseNetGameModel(String rawData) throws JSONException
	{
		//setup needed objects
		NetGameModel result = new NetGameModel();
		JSONObject obj = new JSONObject(rawData);
		
		//extract simple information from the JSON
		int winner = obj.getInt("winner");
		int version = obj.getInt("version");
		
		result.setWinner(winner);
		result.setVersion(version);
		
		//extract player array from JSON
		JSONArray playerArr = obj.getJSONArray("players");
		List<NetPlayer> playerList = new ArrayList<NetPlayer>();
		for(int i = 0; i < playerArr.length(); i++)
		{
			if(playerArr.isNull(i) || playerArr.getJSONObject(i).length() == 0)
			{
				continue;
			}
			NetPlayer tempPlayer = parseNetPlayer(playerArr.getJSONObject(i).toString());
			playerList.add(tempPlayer);
		}
		
		//extract objects from JSON
		result.setNetBank(parseNetBank(obj.getJSONObject("bank").toString()));
		result.setNetGameLog(parseNetLog(obj.getJSONObject("log").toString()));
		result.setNetChat(parseNetChat(obj.getJSONObject("chat").toString()));
		result.setNetTurnTracker(parseNetTurnTracker(obj.getJSONObject("turnTracker").toString()));
		result.setNetMap(parseNetMap(obj.getJSONObject("map").toString()));
		result.setNetDeck(parseNetDevCardList(obj.getJSONObject("deck").toString()));
		
		try
		{
			result.setNetTradeOffer(parseNetTradeOffer(obj.getJSONObject("tradeOffer").toString()));
		}
		catch(JSONException e)
		{
			//do nothing, because this field was optional anyway
		}
		
		result.setNetPlayers(playerList);
				
		return result;
	}
	
	public NetTradeOffer parseNetTradeOffer(String rawData) throws JSONException
	{
		//set up needed objects
		JSONObject obj = new JSONObject(rawData);
		NetTradeOffer result = new NetTradeOffer();
		
		//get data from JSON
		int sender = obj.getInt("sender");
		int receiver = obj.getInt("receiver");
		NetResourceList offer = parseNetResourceList(obj.getJSONObject("offer").toString());
		
		//put data in new object
		result.setSender(sender);
		result.setReceiver(receiver);
		result.setNetResourceList(offer);
		
		return result;
	}
	
	public NetDevCardList parseNetDevCardList(String rawData) throws JSONException
	{
		//set up needed objects
		JSONObject obj = new JSONObject(rawData);
		NetDevCardList result = new NetDevCardList();
		
		//get data from JSON
		int monopoly = obj.getInt("monopoly");
		int monument = obj.getInt("monument");
		int roadBuilding = obj.getInt("roadBuilding");
		int soldier = obj.getInt("soldier");
		int yearOfPlenty = obj.getInt("yearOfPlenty");
		
		//put data into new object
		result.setNumMonopoly(monopoly);
		result.setNumMonument(monument);
		result.setNumRoadBuilding(roadBuilding);
		result.setNumSoldier(soldier);
		result.setNumYearOfPlenty(yearOfPlenty);
		
		return result;
		
	}
	
	public NetMap parseNetMap(String rawData) throws JSONException
	{
		//set up needed objects
		JSONObject obj = new JSONObject(rawData);
		NetMap result = new NetMap();
		
		//parse hex tiles
		JSONArray jsonNetHexArr = obj.getJSONArray("hexes");
		List<NetHex> hexArray = new ArrayList<NetHex>();
		
		for(int i = 0; i < jsonNetHexArr.length(); i++)
		{
			NetHex tempNetHex = parseNetHex(jsonNetHexArr.getJSONObject(i).toString());
			hexArray.add(tempNetHex);
		}
		
		//parse trading ports
		JSONArray jsonNetPortArr = obj.getJSONArray("ports");
		List<NetPort> portArray = new ArrayList<NetPort>();
		
		for(int i = 0; i < jsonNetPortArr.length(); i++)
		{
			NetPort tempNetPort = parseNetPort(jsonNetPortArr.getJSONObject(i).toString());
			portArray.add(tempNetPort);
		}
		
		//parse roads
		JSONArray jsonNetRoadArr = obj.getJSONArray("roads");
		List<NetRoad> roadArray = new ArrayList<NetRoad>();
		
		for(int i = 0; i < jsonNetRoadArr.length(); i++)
		{
			NetRoad tempNetRoad = parseNetRoad(jsonNetRoadArr.getJSONObject(i).toString());
			roadArray.add(tempNetRoad);
		}
	
		
		//parse settlements
		JSONArray jsonNetSettlementArr = obj.getJSONArray("settlements");
		List<NetSettlement> settlementArr = new ArrayList<NetSettlement>();
		
		for(int i = 0; i < jsonNetSettlementArr.length(); i++)
		{
			NetSettlement tempNetSettlement = parseNetSettlement(jsonNetSettlementArr.getJSONObject(i).toString());
			settlementArr.add(tempNetSettlement);
		}
		
		//parse cities
		JSONArray jsonNetCityArr = obj.getJSONArray("cities");
		List<NetCity> cityArr = new ArrayList<NetCity>();
		
		for(int i = 0; i < jsonNetCityArr.length(); i++)
		{
			NetCity tempNetCity = parseNetCity(jsonNetCityArr.getJSONObject(i).toString());
			cityArr.add(tempNetCity);
		}
		
		//parse robber location
		NetHexLocation robberLocation = parseNetHexLocation(obj.getJSONObject("robber").toString());
		
		//parse radius
		int radius = obj.getInt("radius");
		
		//put data in new netmap object
		result.setNetHexes(hexArray);
		result.setNetPorts(portArray);
		result.setNetRoads(roadArray);
		result.setNetSettlements(settlementArr);
		result.setNetCities(cityArr);
		result.setRobberLocation(robberLocation);
		result.setRadius(radius);
		
		return result;
	}
	
	public NetSettlement parseNetSettlement(String rawData) throws JSONException
	{
		//set up needed Objects
		JSONObject obj = new JSONObject(rawData);
		NetSettlement result = new NetSettlement();
		
		//get data from JSON
		int owner = obj.getInt("owner");
		NetDirectionalLocation location = parseNetEdgeLocation(obj.getJSONObject("location").toString());
		
		//put data into new object
		result.setNetEdgeLocation(location);
		result.setOwner(owner);
		
		return result;
	}
	
	public NetCity parseNetCity(String rawData) throws JSONException
	{
		//set up needed Objects
		JSONObject obj = new JSONObject(rawData);
		NetCity result = new NetCity();
		
		//get data from JSON
		int owner = obj.getInt("owner");
		NetDirectionalLocation location = parseNetEdgeLocation(obj.getJSONObject("location").toString());
		
		//put data into new object
		result.setNetEdgeLocation(location);
		result.setOwner(owner);
		
		return result;
	}
	
	public NetRoad parseNetRoad(String rawData) throws JSONException
	{
		//set up needed Objects
		JSONObject obj = new JSONObject(rawData);
		NetRoad result = new NetRoad();
		
		//get data from road
		int owner = obj.getInt("owner");
		NetDirectionalLocation location = parseNetEdgeLocation(obj.getJSONObject("location").toString());
		
		//put data into new object
		result.setNetEdgeLocation(location);
		result.setOwnerID(owner);
		
		return result;
	}
	
	public NetDirectionalLocation parseNetEdgeLocation(String rawData) throws JSONException
	{
		//set up needed Objects
		JSONObject obj = new JSONObject(rawData);
		NetDirectionalLocation result = new NetDirectionalLocation();
		
		//get data from json object
		int x = obj.getInt("x");
		int y = obj.getInt("y");
		Direction direction = Direction.fromString(obj.getString("direction"));
		
		//put the data into the new object
		result.setX(x);
		result.setY(y);
		result.setDirection(direction);
		
		return result;
	}
	
	public NetPort parseNetPort(String rawData) throws JSONException
	{
		//set up needed objects
		JSONObject obj = new JSONObject(rawData);
		NetPort result = new NetPort();
		
		//extract simple data
		ResourceType resource = null;
		try{
			resource = ResourceType.fromString(obj.getString("resource"));
		}
		catch(JSONException e){
			//do nothing since resource is optional
		}
		Direction direction = Direction.fromString(obj.getString("direction"));
		int ratio = obj.getInt("ratio");
		NetHexLocation location = parseNetHexLocation(obj.getJSONObject("location").toString());
		
		//put the data into the new object
		result.setResource(resource);
		result.setDirection(direction);
		result.setRatio(ratio);
		result.setNetHexLocation(location);
		
		return result;
	}
	
	public NetHex parseNetHex(String rawData) throws JSONException
	{
		//set up needed objects
		NetHex result = new NetHex();
		JSONObject obj = new JSONObject(rawData);
		
		//get data
		int number = -1;
		ResourceType resource = null;
		try{
			number = obj.getInt("number");
			
		}
		catch(JSONException e){
			//keep going, because these are optional
		}
		
		try{
			resource = ResourceType.fromString(obj.getString("resource"));
		}
		catch(JSONException e){
			//do nothing because the resource is optional
		}
		
		NetHexLocation location = parseNetHexLocation(obj.getJSONObject("location").toString());
		
		//put data in new object
		result.setResourceType(resource);
		result.setNetHexLocation(location);
		result.setNumberChit(number);
		
		return result;
	}
	
	public NetHexLocation parseNetHexLocation(String rawData) throws JSONException
	{
		//Set up needed objects
		NetHexLocation result = new NetHexLocation();
		JSONObject obj = new JSONObject(rawData);
		
		//get data
		int x = obj.getInt("x");
		int y = obj.getInt("y");
		
		//put data in new object
		result.setX(x);
		result.setY(y);
		
		return result;
	}
	
	public NetTurnTracker parseNetTurnTracker(String rawData) throws JSONException
	{
		//set up needed objects
		NetTurnTracker result = new NetTurnTracker();
		JSONObject obj = new JSONObject(rawData);
		
		//get data from JSON
		int currentTurn = obj.getInt("currentTurn");
		String status = obj.getString("status");
		int longestRoad = -1;
		int largestArmy = -1;
		
		try{
			longestRoad = obj.getInt("longestRoad");
		}
		catch(JSONException e){
			//do nothing since longestRoad is optional
		}
		
		try{
			largestArmy = obj.getInt("largestArmy");
		}
		catch(JSONException e){
			//do nothing since longestRoad is optional
		}
		
		//put data into new object
		result.setCurrentTurn(currentTurn);
		result.setRound(GameRound.fromString(status));
		//TODO add logic for setting the status
		result.setLongestRoad(longestRoad);
		result.setLargestArmy(largestArmy);
		
		return result;
		
	}
	
	public NetResourceList parseNetResourceList(String rawData) throws JSONException
	{
		//set up needed objects
		NetResourceList result = new NetResourceList();
		JSONObject obj = new JSONObject(rawData);
		
		//get data from object
		int brick = obj.getInt("brick");
		int ore = obj.getInt("ore");
		int sheep = obj.getInt("sheep");
		int wheat = obj.getInt("wheat");
		int wood = obj.getInt("wood");
		
		//add data to new object
		result.setNumBrick(brick);
		result.setNumOre(ore);
		result.setNumSheep(sheep);
		result.setNumWheat(wheat);
		result.setNumWood(wood);
		
		return result;
	}
	
	public NetBank parseNetBank(String rawData) throws JSONException
	{
		//set up needed objects
		NetBank result = new NetBank();
		JSONObject obj = new JSONObject(rawData);
		
		//get data from object
		int brick = obj.getInt("brick");
		int ore = obj.getInt("ore");
		int sheep = obj.getInt("sheep");
		int wheat = obj.getInt("wheat");
		int wood = obj.getInt("wood");
		
		//add data to new object
		result.setNumBrick(brick);
		result.setNumOre(ore);
		result.setNumSheep(sheep);
		result.setNumWheat(wheat);
		result.setNumWood(wood);
		
		return result;
	}
	
	public NetLog parseNetLog(String rawData) throws JSONException
	{
		//set up needed objects
		NetLog result = new NetLog();
		JSONObject obj = new JSONObject(rawData);
		
		//extract data from JSON
		JSONArray logLines = obj.getJSONArray("lines");
		List<NetLine> lines = new ArrayList<NetLine>();
		
		for(int i = 0; i < logLines.length(); i++){
			NetLine tempLine = parseNetLine(logLines.get(i).toString());
			lines.add(tempLine);
		}
		
		//add data to new object
		result.setLines(lines);
		
		return result;
	}
	
	public NetChat parseNetChat(String rawData) throws JSONException
	{
		//set up needed objects
		NetChat result = new NetChat();
		JSONObject obj = new JSONObject(rawData);
		
		//extract data from JSON
		JSONArray logLines = obj.getJSONArray("lines");
		List<NetLine> lines = new ArrayList<NetLine>();
		
		for(int i = 0; i < logLines.length(); i++){
			NetLine tempLine = parseNetLine(logLines.get(i).toString());
			lines.add(tempLine);
		}
		
		//add data to new object
		result.setLines(lines);
		
		return result;
	}
	
	public NetLine parseNetLine(String rawData) throws JSONException
	{
		//set up needed objects
		NetLine result = new NetLine();
		JSONObject obj = new JSONObject(rawData);
		
		//extract data from JSON
		String message = obj.getString("message");
		String source = obj.getString("source");
		
		//add data to new object
		result.setMessage(message);
		result.setSource(source);
		
		return result;
	}
	

	@Override
	public List<NetGame> parseNetGameList(String rawData) throws JSONException
	{
		List<NetGame> netGames = new ArrayList<NetGame>();
	
		//create a top level array of JSON Objects
		JSONArray arrayOfGames = new JSONArray(rawData);
		
		int arrLen = arrayOfGames.length();
		for(int i = 0; i < arrLen; i++){
			NetGame netGame = parseNetGame(arrayOfGames.getJSONObject(i).toString());
			netGames.add(netGame);
		}
		
		return netGames; 
	}

}
