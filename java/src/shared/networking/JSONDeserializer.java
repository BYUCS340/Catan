/**
 * 
 */
package shared.networking;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import shared.definitions.CatanColor;
import shared.networking.transport.NetAI;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetPlayer;

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
		return null;
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
