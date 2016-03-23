/**
 * 
 */
package client.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import shared.data.GameInfo;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.Player;
import shared.model.map.Coordinate;
import shared.networking.SerializationUtils;
import shared.networking.cookie.UserCookie;
import shared.networking.parameter.PAcceptTrade;
import shared.networking.parameter.PAddAI;
import shared.networking.parameter.PBuildCity;
import shared.networking.parameter.PBuildRoad;
import shared.networking.parameter.PBuildSettlement;
import shared.networking.parameter.PCreateGame;
import shared.networking.parameter.PCredentials;
import shared.networking.parameter.PDiscardCards;
import shared.networking.parameter.PGetModel;
import shared.networking.parameter.PJoinGame;
import shared.networking.parameter.PMaritimeTrade;
import shared.networking.parameter.PMonopolyCard;
import shared.networking.parameter.POfferTrade;
import shared.networking.parameter.PRoadBuildingCard;
import shared.networking.parameter.PRobPlayer;
import shared.networking.parameter.PRollDice;
import shared.networking.parameter.PSendChat;
import shared.networking.parameter.PSoldierCard;
import shared.networking.parameter.PYearOfPlentyCard;

/**
 * @author Parker Ridd
 *
 */
public class GSONServerProxy implements ServerProxy
{

	private UserCookie userCookie;
	private int gameID;
	private String SERVER_HOST;
	private int SERVER_PORT;
	private String URL_PREFIX;
	private final String HTTP_GET = "GET";
	private final String HTTP_POST = "POST";
	private int userIndex;
	private String userName;
	
	/**
	 * Default constructor. Sets up connection with the server with default
	 * parameters.
	 */
	public GSONServerProxy()
	{
		SERVER_HOST = "localhost";
		SERVER_PORT = 8081;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		userCookie = null;
		gameID = -1;
		userIndex = -1;
		userName = null;
	}
	
	/**
	 * Sets up connection with the server with specified parameters
	 * @param server_host this string represents the hostname of the server
	 * @param server_port this int is the port to send requests to on the server
	 */
	public GSONServerProxy(String server_host, int server_port)
	{
		this();
		SERVER_HOST = server_host;
		SERVER_PORT = server_port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	
	
	/**
	 * Getting for current user index
	 * @return
	 */
	public int getUserIndex()
	{
		return userIndex;
	}
	
	/**
	 * Gets the player ID
	 * @return
	 */
	public int getUserId()
	{
		return this.userCookie.getPlayerID();
	}
	
	/**
	 * Gets the current user's name
	 * @return
	 * @throws ServerProxyException if not logged in
	 */
	public String getUserName() throws ServerProxyException 
	{		
		if (this.userName == null) 
			throw new ServerProxyException("Not loggged in");
		else
			return this.userName;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#loginUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean loginUser(String username, String password) throws ServerProxyException
	{
		PCredentials cred = new PCredentials(username, password);
		String postData = SerializationUtils.serialize(cred);
		String urlPath = "/user/login";
		try{
			doJSONPost(urlPath, postData, true, false);
		}
		catch(ServerProxyException e){
			if(e.getMessage().toLowerCase().contains("failed to login")){
				return false;
			}
			else
				throw e;
		}
		
		this.userName = username;
		return true;

	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#registerUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean registerUser(String username, String password) throws ServerProxyException
	{
		PCredentials cred = new PCredentials(username, password);
		String postData = SerializationUtils.serialize(cred);
		String urlPath = "/user/register";
		try{
			doJSONPost(urlPath, postData, false, false);
		}
		catch(ServerProxyException e){
			if(e.getMessage().toLowerCase().contains("failed to register")){
				return false;
			}
			else
				throw e;
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#listGames()
	 */
	@Override
	public List<GameInfo> listGames() throws ServerProxyException
	{
		String urlPath = "/games/list";
		String resultStr = null;

		resultStr = doJSONGet(urlPath);
		//TODO deobfuscate this method once functionality is verified
		List<GameInfo> result = (new Gson()).fromJson(resultStr, new TypeToken<List<GameInfo>>(){}.getType());
		
		return result;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#createGame(boolean, boolean, boolean, java.lang.String)
	 */
	@Override
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
		throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before creating a game!\n"
					+ "Details: User cookie not found");
		}
		
		String urlPath = "/games/create";
		PCreateGame create = new PCreateGame(randomTiles, randomNumbers, randomPorts, name);
		String postData = SerializationUtils.serialize(create);
		
		String result = doJSONPost(urlPath, postData, false, false);
		GameInfo createdGame = SerializationUtils.deserialize(result, GameInfo.class);
		
		return createdGame;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#joinGame(java.lang.String)
	 */
	@Override
	public void joinGame(int id, CatanColor color) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before joining a game!\n"
					+ "Details: User cookie not found");
		}
		
		//send the request to the server
		String urlPath = "/games/join";
		PJoinGame join = new PJoinGame(id, color);
		String postData = SerializationUtils.serialize(join);

		String result = doJSONPost(urlPath, postData, false, true);
		System.out.println(result);
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#getGameModel()
	 */
	@Override
	public GameModel getGameModel(int version) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before retrieving a game model!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before retrieving a game model!\n"
					+ "Details: Game ID not valid");
		}
		
		//send the request to the server		
		String urlPath = "/game/model";
		PGetModel obj = new PGetModel(version);
		String data = SerializationUtils.serialize(obj);
		String result = doJSONPost(urlPath, data, false, false);
		
		if(result.contains("No new model"))
		{
			return null;
		}
		
		//parse the result into a GameModel
		GameModel gameModel = SerializationUtils.deserialize(result, GameModel.class);
			
		
		//get user number
		if(userIndex < 0);
		String name = userCookie.getUsername();
		List<Player> playerList = gameModel.players;
		for(Player p : playerList)
		{
			if(p.name.equals(name))
			{
				userIndex = p.playerIndex();
			}
		}
		
		//the user's name should have been found and the index should have been set.
		//if this is not true, then there is something very wrong
		if (!(userIndex >= 0 && userIndex <= 3))
			throw new ServerProxyException("Bad User Index "+userIndex);
		
		
		return gameModel;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#addAI(java.lang.String)
	 */
	@Override
	public void addAI(AIType aiType) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before adding an AIl!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before retrieving an AI!\n"
					+ "Details: Game ID not valid");
		}
		
		//send the request to the server
		String urlPath = "/game/addAI";
		PAddAI add = new PAddAI(aiType);
		String postData = SerializationUtils.serialize(add);

		doJSONPost(urlPath, postData, false, false);
		//if there is no exception, this operation succeeded
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#listAI()
	 */
	@Override
	public List<AIType> listAI() throws ServerProxyException
	{
		//get data from server
		String urlPath = "/game/listAI";
		String result = doJSONGet(urlPath);
		
		//deserialize
		//TODO deobfuscate when functionality is confirmed
		List<AIType> supportedAI = (new Gson()).fromJson(result, new TypeToken<List<AIType>>(){}.getType());

		return supportedAI;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#sendChat(java.lang.String)
	 */
	@Override
	public GameModel sendChat(String content) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before sending a chat message!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before sending a chat message!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/sendChat";
		PSendChat chat = new PSendChat(content);
		String postData = SerializationUtils.serialize(chat);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
				
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#rollNumber(int)
	 */
	@Override
	public GameModel rollNumber(int roll) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before rolling a number!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before rolling a number!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/rollNumber";
		PRollDice rolld = new PRollDice(roll);
		String postData = SerializationUtils.serialize(rolld);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#robPlayer(int, shared.locations.HexLocation)
	 */
	@Override
	public GameModel robPlayer(int victimIndex, Coordinate location) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before robbing a player!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before robbing a player!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/robPlayer";
		PRobPlayer rob = new PRobPlayer(victimIndex, location);
		String postData = SerializationUtils.serialize(rob);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#finishTurn()
	 */
	@Override
	public GameModel finishTurn() throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before finishing your turn!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before finishing your turn!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/finishTurn";
		String postData = "";

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buyDevCard()
	 */
	@Override
	public GameModel buyDevCard() throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before buying a development card!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before buying a development card!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/buyDevCard";
		String postData = "";
		
		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#yearOfPlentyCard(java.lang.String, java.lang.String)
	 */
	@Override
	public GameModel yearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before playing a year of plenty card!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before playing a year of plenty card!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/Year_of_Plenty";
		PYearOfPlentyCard yop = new PYearOfPlentyCard(resource1, resource2);
		String postData = SerializationUtils.serialize(yop);

		String result = doJSONPost(urlPath, postData, false, false);
//		System.out.println("Year of Plenty response: " + result);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#roadBuildingCard(shared.locations.EdgeLocation, shared.locations.EdgeLocation)
	 */
	@Override
	public GameModel roadBuildingCard(Coordinate start1, Coordinate end1, Coordinate start2, Coordinate end2) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before playing a road building card!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before playing a road building card!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/Road_Building";
		PRoadBuildingCard rbcard = new PRoadBuildingCard(start1, start2, end1, end2);
		String postData = SerializationUtils.serialize(rbcard);

		String result = doJSONPost(urlPath, postData, false, false);
		
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);

		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#soldierCard(int, shared.locations.HexLocation)
	 */
	@Override
	public GameModel soldierCard(int victimIndex, Coordinate location) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before playing a soldier card!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before playing a soldier card!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/Soldier";
		PSoldierCard soldier = new PSoldierCard(victimIndex, location);
		String postData = SerializationUtils.serialize(soldier);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monopolyCard(java.lang.String)
	 */
	@Override
	public GameModel monopolyCard(ResourceType resource) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before playing a monopoly card!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before playing a monopoly card!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/Monopoly";
		PMonopolyCard monopoly = new PMonopolyCard(resource);
		String postData = SerializationUtils.serialize(monopoly);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);

		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monumentCard()
	 */
	@Override
	public GameModel monumentCard() throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before playing a monument card!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before playing a monument card!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/Monument";
		String postData = "";
		String result = doJSONPost(urlPath, postData, false, false);

		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildRoad(shared.locations.EdgeLocation, boolean)
	 */
	@Override
	public GameModel buildRoad(Coordinate start, Coordinate end, boolean free) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before building a road!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before building a road!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/buildRoad";
		PBuildRoad road = new PBuildRoad(start, end, free);
		String postData = SerializationUtils.serialize(road);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildSettlement(shared.locations.VertexLocation, boolean)
	 */
	@Override
	public GameModel buildSettlement(Coordinate location, boolean free) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before building a settlement!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before building a road!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/buildSettlement";
		PBuildSettlement settle = new PBuildSettlement(location, free);
		String postData = SerializationUtils.serialize(settle);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildCity(shared.locations.VertexLocation)
	 */
	@Override
	public GameModel buildCity(Coordinate location) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before building a city!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before building a city!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/buildCity";
		PBuildCity bcity = new PBuildCity(location);
		String postData = SerializationUtils.serialize(bcity);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#offerTrade(java.util.List, int)
	 */
	@Override
	public GameModel offerTrade(List<Integer> resourceList, int receiver) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before offering a trade!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before offering a trade!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/offerTrade";
		POfferTrade trade = new POfferTrade(resourceList, receiver);
		String postData = SerializationUtils.serialize(trade);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#acceptTrade(boolean)
	 */
	@Override
	public GameModel acceptTrade(boolean willAccept) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before accepting a trade!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before accepting a trade!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/acceptTrade";
		PAcceptTrade acctrade = new PAcceptTrade(willAccept);
		String postData = SerializationUtils.serialize(acctrade);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#maritimeTrade(int, java.lang.String, java.lang.String)
	 */
	@Override
	public GameModel maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource)
		throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before maritime trading!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before maritime trading!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/maritimeTrade";
		PMaritimeTrade trade = new PMaritimeTrade(ratio, inputResource, outputResource);
		String postData = SerializationUtils.serialize(trade);

		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#discardCards(java.util.List)
	 */
	@Override
	public GameModel discardCards(List<Integer> resourceList) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before discarding cards!\n"
					+ "Details: User cookie not found");
		}
		if(gameID < 0)
		{
			throw new ServerProxyException("You must be a part of a game before discarding cards!\n"
					+ "Details: Game ID not valid");
		}
		
		String urlPath = "/moves/discardCards";
		PDiscardCards discard = new PDiscardCards(resourceList);
		String postData = SerializationUtils.serialize(discard);
		

		System.out.println(urlPath + " " + postData);
		String result = doJSONPost(urlPath, postData, false, false);
		GameModel ret = SerializationUtils.deserialize(result, GameModel.class);
		
		return ret;
	}
	
	@SuppressWarnings("deprecation")
	private String doJSONPost(String urlPath, String postData, boolean getUserCookie, 
			boolean getGameCookie) throws ServerProxyException
	{
		HttpURLConnection connection = null;
		String result = null;
		try
		{
			//Set up connection and connect to the specified path
			URL url = new URL(URL_PREFIX + urlPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			
			//add cookie to headers if there is a logged-in user
			if(userCookie != null){
				connection.setRequestProperty("Cookie", userCookie.getCookieText());	
			}
			
			connection.setDoOutput(true);
			connection.connect();
			
			
			
			//send JSON data
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(postData);
			osw.flush();
			osw.close();
			
			//get the server's response
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				BufferedReader br = new BufferedReader(new InputStreamReader( connection.getInputStream(),"utf-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
				    sb.append(line + "\n");
				}
				br.close();	
				result = sb.toString();
				
				//parse the header, if requested
				if(getUserCookie)
				{
					String uCookie = connection.getHeaderField("Set-cookie");
					int toRemove = uCookie.indexOf(';');
					uCookie = uCookie.substring(0, toRemove);
					
					JSONObject obj = new JSONObject(uCookie);
					String tempUsername = obj.getString("name");
					String tempPassword = obj.getString("password");
					int tempPlayerID = obj.getInt("playerID");
					
					userCookie = new UserCookie(uCookie, tempUsername, tempPassword, tempPlayerID);					
				}
				else if(getGameCookie)
				{
					String gCookie = connection.getHeaderField("Set-cookie");
					int toRemove = gCookie.indexOf(';');
					gCookie = gCookie.substring(0, toRemove);
					
					JSONObject obj = new JSONObject(gCookie);
					gameID = obj.getInt("gameID");
					userCookie.setCookie(gCookie);
				}
				connection.disconnect();
			}
			else{
				InputStream is = connection.getErrorStream();
				Scanner scan = new Scanner(is);
				StringBuilder sb = new StringBuilder();
				while(scan.hasNextLine()){
					sb.append(scan.nextLine());
				}
				scan.close();
				connection.disconnect();
				throw new ServerProxyException(sb.toString());	
			}
		} catch (MalformedURLException e)
		{
			throw new ServerProxyException("MalformedURLException thrown in client.networking.GSONServerProxy.doJSONPost + " + urlPath + "\n"
					+e.getStackTrace());
			
		} catch (IOException e)
		{
			throw new ServerProxyException("IOException thrown in client.networking.GSONServerProxy.doJSONPost\n"
					+e.getStackTrace());
		} catch (JSONException e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		finally
		{
			if(connection != null)
			{
				connection.disconnect();
			}
		}
		
		return result;
	}
	
	private String doJSONGet(String urlPath) throws ServerProxyException
	{
		HttpURLConnection connection = null;
		String result = null;
		try
		{
			//Set up connection and connect to the specified path
			URL url = new URL(URL_PREFIX + urlPath);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			
			//add cookie to headers if there is a logged-in user
			if(userCookie != null){
				connection.setRequestProperty("Cookie", userCookie.getCookieText());	
			}
			
			connection.setDoOutput(true);
			connection.connect();
			
			//get the server's response
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader( connection.getInputStream(),"utf-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
				    sb.append(line + "\n");
				}
				br.close();	
				result = sb.toString();
				
				connection.disconnect();
			}
			else{
				InputStream is = connection.getErrorStream();
				Scanner scan = new Scanner(is);
				StringBuilder sb = new StringBuilder();
				while(scan.hasNextLine()){
					sb.append(scan.nextLine());
				}
				scan.close();
				connection.disconnect();
				throw new ServerProxyException(sb.toString());	
			}
		} catch (MalformedURLException e)
		{
			throw new ServerProxyException("MalformedURLException thrown in client.networking.GSONServerProxy.doJSONGet + " + urlPath + "\n"
					+e.getStackTrace());
			
		} catch (IOException e)
		{
			throw new ServerProxyException("IOException thrown in client.networking.GSONServerProxy.doJSONGet\n"
					+e.getStackTrace());
		}
		finally
		{
			if(connection != null)
			{
				connection.disconnect();
			}
		}
		
		return result;
	}
	
	/**
	 * Gets the user cookie
	 * @return the user cookie
	 */
	public UserCookie getUserCookie()
	{
		return userCookie;
	}
	
	/**
	 * FOR DEBUGGING ONLY
	 * TODO make private for distro
	 * Clears all cookies
	 */
	public void clearCookies(){
		userCookie = null;
		gameID = -1;
	}

}
