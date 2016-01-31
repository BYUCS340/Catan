/**
 * 
 */
package shared.networking;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.networking.transport.NetAI;
import shared.networking.transport.NetBank;
import shared.networking.transport.NetChat;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetHex;
import shared.networking.transport.NetHexLocation;
import shared.networking.transport.NetLine;
import shared.networking.transport.NetLog;
import shared.networking.transport.NetMap;
import shared.networking.transport.NetPlayer;
import shared.networking.transport.NetResourceList;
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
	public NetAI parseNetAI(String rawData)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Deserializer#parseNetGame(java.lang.String)
	 */
	@Override
	public NetGame parseNetGame(String rawData)
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
			NetPlayer tempNetPlayer = parseNetPlayer(playerArr.getJSONObject(i).toString());
			netPlayers.add(tempNetPlayer);
		}
		
		netGame.setNetPlayers(netPlayers);
		
		
		return netGame;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Deserializer#parseNetPlayer(java.lang.String)
	 */
	@Override
	public NetPlayer parseNetPlayer(String rawData)
	{
		NetPlayer netPlayer = new NetPlayer();
		JSONObject obj = new JSONObject(rawData);
		
		CatanColor color = CatanColor.fromString(obj.getString("color"));
		String name = obj.getString("name");
		int id = obj.getInt("id");
		
		netPlayer.setColor(color);
		netPlayer.setName(name);
		netPlayer.setPlayerID(id);
		
		//TODO implement the rest of the information for NetGameModel
		
		return netPlayer;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Deserializer#parseNetGameModel(java.lang.String)
	 */
	@Override
	public NetGameModel parseNetGameModel(String rawData)
	{
		// TODO Auto-generated method stub
		//setup needed objects
		NetGameModel result = new NetGameModel();
		JSONObject obj = new JSONObject(rawData);
		NetMap netMap = new NetMap();
		
		//extract simple information from the JSON
		int winner = obj.getInt("winner");
		int version = obj.getInt("version");
		
		result.setWinner(winner);
		result.setVersion(version);
		
		//extract objects from JSON
		result.setNetBank((NetBank)parseNetResourceList(obj.getJSONObject("bank").toString()));
		result.setNetGameLog(parseNetLog(obj.getJSONObject("log").toString()));
		result.setNetChat((NetChat)parseNetLog(obj.getJSONObject("chat").toString()));
		result.setNetTurnTracker(parseNetTurnTracker(obj.getJSONObject("turnTracker").toString()));
		
		//process map
		JSONArray jsonNetHexArr = obj.getJSONArray("hexes");
		List<NetHex> hexArray = new ArrayList<NetHex>();
		
		for(int i = 0; i < jsonNetHexArr.length(); i++)
		{
			NetHex tempNetHex = parseNetHex(jsonNetHexArr.getJSONObject(i).toString());
			hexArray.add(tempNetHex);
		}
		
		netMap.setNetHexes(hexArray);
		
		
		return result;
	}
	
	public NetMap parseNetMap(String rawData)
	{
		
		return null;
	}
	
	public NetHex parseNetHex(String rawData)
	{
		//set up needed objects
		NetHex result = new NetHex();
		JSONObject obj = new JSONObject(rawData);
		
		//get data
		int number = obj.getInt("number");
		ResourceType resource = ResourceType.fromString(obj.getString("resource"));
		NetHexLocation location = parseNetHexLocation(obj.getJSONObject("location").toString());
		
		//put data in new object
		result.setResourceType(resource);
		result.setNetHexLocation(location);
		result.setNumberChit(number);
		
		return null;
	}
	
	public NetHexLocation parseNetHexLocation(String rawData)
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
	
	public NetTurnTracker parseNetTurnTracker(String rawData)
	{
		//set up needed objects
		NetTurnTracker result = new NetTurnTracker();
		JSONObject obj = new JSONObject(rawData);
		
		//get data from JSON
		int currentTurn = obj.getInt("currentTurn");
		String status = obj.getString("status");
		int longestRoad = obj.getInt("longestRoad");
		int largestArmy = obj.getInt("largestArmy");
		
		//put data into new object
		result.setCurrentTurn(currentTurn);
		//TODO add logic for setting the status
		result.setLongestRoad(longestRoad);
		result.setLargestArmy(largestArmy);
		
		return result;
		
	}
	
	public NetResourceList parseNetResourceList(String rawData)
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
	
	public NetLog parseNetLog(String rawData)
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
	
	public NetLine parseNetLine(String rawData)
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
	public List<NetGame> parseNetGameList(String rawData)
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
