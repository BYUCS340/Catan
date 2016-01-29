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

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.networking.Cookie;
import shared.networking.JSONSerializer;
import shared.networking.Serializer;
import shared.networking.UserCookie;
import shared.networking.transport.NetAI;
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
	
	/**
	 * Default constructor. Sets up connection with the server with default
	 * parameters.
	 */
	public RealServerProxy()
	{
		//TODO implement
		serializer = new JSONSerializer();
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
			String response = doJSONPost(urlPath, postData, false, false);
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
	public List<NetGame> listGames()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#createGame(boolean, boolean, boolean, java.lang.String)
	 */
	@Override
	public NetGame createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#joinGame(java.lang.String)
	 */
	@Override
	public NetGame joinGame(String color)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#getGameModel()
	 */
	@Override
	public NetGameModel getGameModel()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#addAI(java.lang.String)
	 */
	@Override
	public void addAI(String aiType)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#listAI()
	 */
	@Override
	public List<NetAI> listAI()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#sendChat(java.lang.String)
	 */
	@Override
	public NetGameModel sendChat(String content)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#rollNumber(int)
	 */
	@Override
	public NetGameModel rollNumber(int roll)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#robPlayer(int, shared.locations.HexLocation)
	 */
	@Override
	public NetGameModel robPlayer(int victimIndex, HexLocation location)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#finishTurn()
	 */
	@Override
	public NetGameModel finishTurn()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buyDevCard()
	 */
	@Override
	public NetGameModel buyDevCard()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#yearOfPlentyCard(java.lang.String, java.lang.String)
	 */
	@Override
	public NetGameModel yearOfPlentyCard(String resource1, String resource2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#roadBuildingCard(shared.locations.EdgeLocation, shared.locations.EdgeLocation)
	 */
	@Override
	public NetGameModel roadBuildingCard(EdgeLocation location1, EdgeLocation location2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#soldierCard(int, shared.locations.HexLocation)
	 */
	@Override
	public NetGameModel soldierCard(int victimIndex, HexLocation hexLocation)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monopolyCard(java.lang.String)
	 */
	@Override
	public NetGameModel monopolyCard(String resource)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monumentCard()
	 */
	@Override
	public NetGameModel monumentCard()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildRoad(shared.locations.EdgeLocation, boolean)
	 */
	@Override
	public NetGameModel buildRoad(EdgeLocation edgeLocation, boolean free)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildSettlement(shared.locations.VertexLocation, boolean)
	 */
	@Override
	public NetGameModel buildSettlement(VertexLocation vertexLocation, boolean free)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildCity(shared.locations.VertexLocation)
	 */
	@Override
	public NetGameModel buildCity(VertexLocation vertexLocation)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#offerTrade(java.util.List, int)
	 */
	@Override
	public NetGameModel offerTrade(List<Integer> resourceList, int receiver)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#acceptTrade(boolean)
	 */
	@Override
	public NetGameModel acceptTrade(boolean willAccept)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#maritimeTrade(int, java.lang.String, java.lang.String)
	 */
	@Override
	public NetGameModel maritimeTrade(int ratio, String inputResource, String outputResource)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#discardCards(java.util.List)
	 */
	@Override
	public NetGameModel discardCards(List<Integer> resourceList)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private String doJSONPost(String urlPath, String postData, boolean getUserCookie, 
			boolean getGameCookie) throws ServerProxyException
	{
		String result = null;
		try
		{
			//Set up connection and connect to the specified path
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_POST);
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
				
			}
			else{
				InputStream is = connection.getErrorStream();
				Scanner scan = new Scanner(is);
				StringBuilder sb = new StringBuilder();
				while(scan.hasNextLine()){
					sb.append(scan.nextLine());
				}
				scan.close();
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
	
	private String processUserCookie(String uCookie){
		String tempStr = uCookie.substring(11);
		tempStr = tempStr.substring(0, tempStr.indexOf(";Path"));
		return tempStr;
	}
	
	private int processGameCookie(String gCookie){
		String tempStr = gCookie.substring(11, gCookie.indexOf(';'));
		return Integer.parseInt(tempStr);
	}
	
	
	

}
