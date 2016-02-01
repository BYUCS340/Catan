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

/**
 * @author pbridd
 *
 */
public class RealServerProxy implements ServerProxy
{
	UserCookie userCookie;
	int gameID;
	private String SERVER_HOST;
	private int SERVER_PORT;
	private String URL_PREFIX;
	private final String HTTP_GET = "GET";
	private final String HTTP_POST = "POST";
	private Serializer serializer;
	private Deserializer deserializer;
	
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
	}
	
	/**
	 * Sets up connection with the server with specified parameters
	 * @param server_host this string represents the hostname of the server
	 * @param server_port this int is the port to send requests to on the server
	 */
	public RealServerProxy(String server_host, int server_port)
	{
		serializer = new JSONSerializer();
		deserializer = new JSONDeserializer();
		SERVER_HOST = server_host;
		SERVER_PORT = server_port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		userCookie = null;
		gameID = -1;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#loginUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean loginUser(String username, String password) throws ServerProxyException
	{
		String postData = serializer.sCredentials(username, password);
		String urlPath = "/user/login";
		
		try{
			String response = doJSONPost(urlPath, postData, true, false);
		}
		catch(ServerProxyException e){
			if(e.getMessage().toLowerCase().contains("failed to login")){
				return false;
			}
			else
				throw e;
		}
		
		return true;

	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#registerUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean registerUser(String username, String password) throws ServerProxyException
	{
		String postData = serializer.sCredentials(username, password);
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
		
		List<NetGame> result = deserializer.parseNetGameList(resultStr.toString());
		
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
		String postData = serializer.sCreateGameReq(randomTiles, randomNumbers, randomPorts, name);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGame createdGame = deserializer.parseNetGame(result);
		
		return createdGame;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#joinGame(java.lang.String)
	 */
	@Override
	public NetGame joinGame(int id, CatanColor color) throws ServerProxyException
	{
		if(userCookie == null)
		{
			throw new ServerProxyException("A user must be logged in before joining a game!\n"
					+ "Details: User cookie not found");
		}
		
		//send the request to the server
		String urlPath = "/games/join";
		String postData = serializer.sJoinGameReq(id, color);
		String result = doJSONPost(urlPath, postData, false, true);
		
		//parse the result into a NetGame
		NetGame joinedGame = deserializer.parseNetGame(result);
		
		return joinedGame;
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
		NetGameModel netGameModel = deserializer.parseNetGameModel(result);	
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
		String urlPath = "/games/addAI";
		String postData = serializer.sAddAIReq(aiType);
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
		String urlPath = "/games/listAI";
		String result = doJSONGet(urlPath);
		
		//deserialize
		List<AIType> supportedAI = deserializer.parseAIList(result);
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
		String postData = serializer.sSendChatReq(userCookie.getPlayerID(), content);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sRollNumberReq(userCookie.getPlayerID(), roll);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sRobPlayerReq(userCookie.getPlayerID(), victimIndex, location);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sFinishTurnReq(userCookie.getPlayerID());
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sBuyDevCardReq(userCookie.getPlayerID());
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sYearOfPlentyCardReq(userCookie.getPlayerID(), resource1, resource2);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sRoadBuildingCardReq(userCookie.getPlayerID(), location1, location2);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sSoldierCardReq(userCookie.getPlayerID(), victimIndex, hexLocation);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monopolyCard(java.lang.String)
	 */
	@Override
	public NetGameModel monopolyCard(ResourceType resource) throws ServerProxyException
	{
		//TODO make this a ResourceType method instead of a resource method
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
		//TODO fix this hacky way of getting ResourceType param to work
		String postData = serializer.sMonopolyCardReq(userCookie.getPlayerID(), resource);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sMonumentCardReq(userCookie.getPlayerID());
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sBuildRoadReq(userCookie.getPlayerID(), edgeLocation, free);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sBuildSettlementReq(userCookie.getPlayerID(), vertexLocation, free);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sBuildCityReq(userCookie.getPlayerID(), vertexLocation);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sOfferTradeReq(userCookie.getPlayerID(), resourceList, receiver);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sAcceptTradeReq(userCookie.getPlayerID(), willAccept);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#maritimeTrade(int, java.lang.String, java.lang.String)
	 */
	@Override
	public NetGameModel maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource)
		throws ServerProxyException
	{
		//TODO fix the interface to accept a ResourceType instead of a string
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
		String postData = serializer.sMaritimeTradeReq(userCookie.getPlayerID(), ratio,
				inputResource, outputResource);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
		String postData = serializer.sDiscardCardsReq(userCookie.getPlayerID(), resourceList);
		String result = doJSONPost(urlPath, postData, false, false);
		
		NetGameModel ret = deserializer.parseNetGameModel(result);
		
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
	
	private String processUserCookie(String uCookie){
		String tempStr = uCookie.substring(11);
		tempStr = tempStr.substring(0, tempStr.indexOf(";Path"));
		return tempStr;
	}
	
	private int processGameCookie(String gCookie){
		String tempStr = gCookie.substring(11, gCookie.indexOf(';'));
		return Integer.parseInt(tempStr);
	}
	
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
