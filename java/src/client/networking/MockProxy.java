/**
 * 
 */
package client.networking;

import java.util.List;

/**
 * @author pbridd
 *
 */
public class MockProxy implements ServerProxy
{

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#loginUser(java.lang.String, java.lang.String)
	 */
	@Override
	public User loginUser(String username, String password)
	{
		// TODO Auto-generated method stub
		return null;
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
	public List<Game> listGames()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#createGame()
	 */
	@Override
	public Game createGame()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#joinGame(int, java.lang.String)
	 */
	@Override
	public Game joinGame(int id, String color)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#getGameModel(client.networking.User)
	 */
	@Override
	public GameModel getGameModel(User user)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#addAI(client.networking.User)
	 */
	@Override
	public void addAI(User user)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#listAI(client.networking.User)
	 */
	@Override
	public AI listAI(User user)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#sendChat(client.networking.User, java.lang.String)
	 */
	@Override
	public GameModel sendChat(User user, String content)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#rollNumber(client.networking.User, int)
	 */
	@Override
	public GameModel rollNumber(User user, int roll)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#robPlayer(client.networking.User)
	 */
	@Override
	public GameModel robPlayer(User user)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#finishTurn(client.networking.User)
	 */
	@Override
	public GameModel finishTurn(User user)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buyDevCard(client.networking.User)
	 */
	@Override
	public GameModel buyDevCard(User user)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#yearOfPlentyCard(client.networking.User, java.lang.String, java.lang.String)
	 */
	@Override
	public GameModel yearOfPlentyCard(User user, String resource1, String resource2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#roadBuildingCard(client.networking.User, client.networking.EdgeLocation, client.networking.EdgeLocation)
	 */
	@Override
	public GameModel roadBuildingCard(User user, EdgeLocation location1, EdgeLocation location2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#soldierCard(client.networking.User, int, client.networking.HexLocation)
	 */
	@Override
	public GameModel soldierCard(User user, int victimIndex, HexLocation hexLocation)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monopolyCard(client.networking.User, java.lang.String)
	 */
	@Override
	public GameModel monopolyCard(User user, String resource)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#monumentCard(client.networking.User)
	 */
	@Override
	public GameModel monumentCard(User user)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildRoad(client.networking.User, client.networking.EdgeLocation, boolean)
	 */
	@Override
	public GameModel buildRoad(User user, EdgeLocation edgeLocation, boolean free)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildSettlement(client.networking.User, client.networking.VertexLocation, boolean)
	 */
	@Override
	public GameModel buildSettlement(User user, VertexLocation vertexLocation, boolean free)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#buildCity(client.networking.User, client.networking.VertexLocation)
	 */
	@Override
	public GameModel buildCity(User user, VertexLocation vertexLocation)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#offerTrade(client.networking.User, java.util.List, int)
	 */
	@Override
	public GameModel offerTrade(User user, List<String> resourceList, int receiver)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#acceptTrade(client.networking.User, boolean)
	 */
	@Override
	public GameModel acceptTrade(User user, boolean willAccept)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#maritimeTrade(client.networking.User, int, java.lang.String, java.lang.String)
	 */
	@Override
	public GameModel maritimeTrade(User user, int ratio, String inputResource, String outputResource)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see client.networking.ServerProxy#discardCards(client.networking.User)
	 */
	@Override
	public GameModel discardCards(User user)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
