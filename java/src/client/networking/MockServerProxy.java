/**
 * 
 */
package client.networking;

import java.util.List;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.networking.transport.NetAI;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;

/**
 * @author pbridd
 *
 */
public class MockServerProxy implements ServerProxy
{

	/**
	 * Default constructor; sets up everything needed for 
	 * mock server
	 */
	public MockServerProxy(){
		//TODO implement
	}
	
	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#loginUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean loginUser(String username, String password)
	{
		// TODO Auto-generated method stub
		return false;

	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#registerUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean registerUser(String username, String password) 
	{
		// TODO Auto-generated method stub
		return false;
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

}
