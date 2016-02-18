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
import org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;

import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.networking.Deserializer;
import shared.networking.JSONDeserializer;
import shared.networking.JSONSerializer;
import shared.networking.Serializer;
import shared.networking.UserCookie;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetPlayer;

/**
 * @author pbridd
 *
 */
public class RealServerProxy implements ServerProxy
{
	private UserCookie userCookie;
	private int gameID;
	private String SERVER_HOST;
	private int SERVER_PORT;
	private String URL_PREFIX;
	private final String HTTP_GET = "GET";
	private final String HTTP_POST = "POST";
	private Serializer serializer;
	private Deserializer deserializer;
	private int userIndex;
	private String userName;
	
	/**
	 * Default constructor. Sets up connection with the server with default
	 * parameters.
	 */
	public RealServerProxy()
	{
		serializer = new JSONSerializer();
		deserializer = new JSONDeserializer();
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
	public RealServerProxy(String server_host, int server_port)
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
		String postData;
		try
		{
			postData = serializer.sCredentials(username, password);
		} catch (Exception e1)
		{
			throw new ServerProxyException(e1.getMessage(), e1.getCause());
		}
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
		String postData;
		try
		{
			postData = serializer.sCredentials(username, password);
		} catch (Exception e1)
		{
			throw new ServerProxyException(e1.getMessage(), e1.getCause());
		}
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
	public List<NetGame> listGames() throws ServerProxyException
	{
		String urlPath = "/games/list";
		String resultStr = null;

		resultStr = doJSONGet(urlPath);
		
		List<NetGame> result;
		try
		{
			result = deserializer.parseNetGameList(resultStr.toString());
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#createGame(boolean, boolean, boolean, java.lang.String)
	 */
	@Override
	public NetGame createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
		throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before creating a game!\n"
					+ "Details: User cookie not found");
		}
		
		String urlPath = "/games/create";
		String postData;
		try
		{
			postData = serializer.sCreateGameReq(randomTiles, randomNumbers, randomPorts, name);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGame createdGame;
		try
		{
			createdGame = deserializer.parseNetGame(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
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
		String postData;
		try
		{
			postData = serializer.sJoinGameReq(id, color);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, true);
		
		//parse the result into a NetGame
//		NetGame joinedGame;
//		try
//		{
//			joinedGame = deserializer.parseNetGame(result);
//		} catch (Exception e)
//		{
//			throw new ServerProxyException(e.getMessage(), e.getCause());
//		}
//		
		//get the userIndex
		System.out.println(result);
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#getGameModel()
	 */
	@Override
	public NetGameModel getGameModel() throws ServerProxyException
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
		String result = doJSONGet(urlPath);
		
		//parse the result into a NetGameModel
		NetGameModel netGameModel;
		try
		{
			netGameModel = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}	
		
		//get user number
		if(userIndex < 0);
		String name = userCookie.getUsername();
		List<NetPlayer> playerList = netGameModel.getNetPlayers();
		for(NetPlayer p : playerList)
		{
			if(p.getName().equals(name))
			{
				userIndex = p.getPlayerIndex();
			}
		}
		
		//the user's name should have been found and the index should have been set.
		//if this is not true, then there is something very wrong
		if (!(userIndex >= 0 && userIndex <= 3))
			throw new ServerProxyException("Bad User Index "+userIndex);
		
		
		return netGameModel;
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
		String postData;
		try
		{
			postData = serializer.sAddAIReq(aiType);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
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
		List<AIType> supportedAI;
		try
		{
			supportedAI = deserializer.parseAIList(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		return supportedAI;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#sendChat(java.lang.String)
	 */
	@Override
	public NetGameModel sendChat(String content) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sSendChatReq(userIndex, content);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#rollNumber(int)
	 */
	@Override
	public NetGameModel rollNumber(int roll) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sRollNumberReq(userIndex, roll);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#robPlayer(int, shared.locations.HexLocation)
	 */
	@Override
	public NetGameModel robPlayer(int victimIndex, HexLocation location) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sRobPlayerReq(userIndex, victimIndex, location);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#finishTurn()
	 */
	@Override
	public NetGameModel finishTurn() throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sFinishTurnReq(userIndex);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buyDevCard()
	 */
	@Override
	public NetGameModel buyDevCard() throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sBuyDevCardReq(userIndex);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#yearOfPlentyCard(java.lang.String, java.lang.String)
	 */
	@Override
	public NetGameModel yearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sYearOfPlentyCardReq(userIndex, resource1, resource2);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
//		System.out.println("Year of Plenty response: " + result);
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#roadBuildingCard(shared.locations.EdgeLocation, shared.locations.EdgeLocation)
	 */
	@Override
	public NetGameModel roadBuildingCard(EdgeLocation location1, EdgeLocation location2) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sRoadBuildingCardReq(userIndex, location1, location2);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#soldierCard(int, shared.locations.HexLocation)
	 */
	@Override
	public NetGameModel soldierCard(int victimIndex, HexLocation hexLocation) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sSoldierCardReq(userIndex, victimIndex, hexLocation);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monopolyCard(java.lang.String)
	 */
	@Override
	public NetGameModel monopolyCard(ResourceType resource) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sMonopolyCardReq(userIndex, resource);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monumentCard()
	 */
	@Override
	public NetGameModel monumentCard() throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sMonumentCardReq(userIndex);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildRoad(shared.locations.EdgeLocation, boolean)
	 */
	@Override
	public NetGameModel buildRoad(EdgeLocation edgeLocation, boolean free) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sBuildRoadReq(userIndex, edgeLocation, free);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildSettlement(shared.locations.VertexLocation, boolean)
	 */
	@Override
	public NetGameModel buildSettlement(VertexLocation vertexLocation, boolean free) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sBuildSettlementReq(userIndex, vertexLocation, free);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildCity(shared.locations.VertexLocation)
	 */
	@Override
	public NetGameModel buildCity(VertexLocation vertexLocation) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sBuildCityReq(userIndex, vertexLocation);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#offerTrade(java.util.List, int)
	 */
	@Override
	public NetGameModel offerTrade(List<Integer> resourceList, int receiver) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sOfferTradeReq(userIndex, resourceList, receiver);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#acceptTrade(boolean)
	 */
	@Override
	public NetGameModel acceptTrade(boolean willAccept) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sAcceptTradeReq(userIndex, willAccept);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#maritimeTrade(int, java.lang.String, java.lang.String)
	 */
	@Override
	public NetGameModel maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource)
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
		String postData;
		try
		{
			postData = serializer.sMaritimeTradeReq(userIndex, ratio,
					inputResource, outputResource);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#discardCards(java.util.List)
	 */
	@Override
	public NetGameModel discardCards(List<Integer> resourceList) throws ServerProxyException
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
		String postData;
		try
		{
			postData = serializer.sDiscardCardsReq(userIndex, resourceList);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		System.out.println(urlPath + " " + postData);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret;
		try
		{
			ret = deserializer.parseNetGameModel(result);
		} catch (Exception e)
		{
			throw new ServerProxyException(e.getMessage(), e.getCause());
		}
		
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
				String cookieString = getCookieString();
				connection.setRequestProperty("Cookie", cookieString);	
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
				if(getUserCookie){
					String uCookie = connection.getHeaderField("Set-cookie");
					uCookie = processUserCookie(uCookie);
					JSONObject obj = new JSONObject(URLDecoder.decode(uCookie));
					String tempUsername = obj.getString("name");
					String tempPassword = obj.getString("password");
					int tempPlayerID = obj.getInt("playerID");
					
					userCookie = new UserCookie(uCookie, tempUsername, tempPassword, tempPlayerID);					
				}
				else if(getGameCookie){
					String gCookie = connection.getHeaderField("Set-cookie");
					gameID = processGameCookie(gCookie);
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
			throw new ServerProxyException("MalformedURLException thrown in client.networking.RealServerProxy.doJSONPost + " + urlPath + "\n"
					+e.getStackTrace());
			
		} catch (IOException e)
		{
			throw new ServerProxyException("IOException thrown in client.networking.RealServerProxy.doJSONPost\n"
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
				String cookieString = getCookieString();
				connection.setRequestProperty("Cookie", cookieString);	
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
			throw new ServerProxyException("MalformedURLException thrown in client.networking.RealServerProxy.doJSONGet + " + urlPath + "\n"
					+e.getStackTrace());
			
		} catch (IOException e)
		{
			throw new ServerProxyException("IOException thrown in client.networking.RealServerProxy.doJSONGet\n"
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
	
	/**
	 * Processes user's cached cookie string
	 * @param uCookie
	 * @return
	 */
	private String processUserCookie(String uCookie){
		String tempStr = uCookie.substring(11);
		tempStr = tempStr.substring(0, tempStr.indexOf(";Path"));
		return tempStr;
	}
	
	/**
	 * Extracts and returns parameters from passed cookie
	 * @param gCookie
	 * @return
	 */
	private int processGameCookie(String gCookie){
		String tempStr = gCookie.substring(11, gCookie.indexOf(';'));
		return Integer.parseInt(tempStr);
	}
	
	/**
	 * Generates a cookie string for the user
	 * @return
	 */
	private String getCookieString(){
		StringBuilder sb = new StringBuilder();
		sb.append("catan.user=");
		sb.append(userCookie.getCookieText());
		
		//add the game cookie information if it exists
		if(gameID >= 0){
			sb.append("; ");
			sb.append("catan.game="+gameID);
		}
		
		return sb.toString();
	}
	
	

}
