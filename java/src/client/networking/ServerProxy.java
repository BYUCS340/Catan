package client.networking;

import java.util.List;

public interface ServerProxy 
{
	public User loginUser(String username, String password);
	public boolean registerUser(String username, String password);
	
	public List<Game> listGames();
	public Game createGame();
	public Game joinGame(int id, String color);

	
	public GameModel getGameModel(User user);
	public void addAI(User user);
	public AI listAI(User user);
	
	public GameModel sendChat(User user, String content);
	public GameModel rollNumber(User user, int roll);
	public GameModel robPlayer(User user);
	public GameModel finishTurn(User user);
	public GameModel buyDevCard(User user);
	public GameModel yearOfPlentyCard(User user, String resource1, String resource2);
	public GameModel roadBuildingCard(User user, EdgeLocation location1, EdgeLocation location2);
	public GameModel soldierCard(User user, int victimIndex, HexLocation hexLocation);
	public GameModel monopolyCard(User user, String resource);
	public GameModel monumentCard(User user);
	public GameModel buildRoad(User user, EdgeLocation edgeLocation, boolean free);
	public GameModel buildSettlement(User user, VertexLocation vertexLocation, boolean free);
	public GameModel buildCity(User user, VertexLocation vertexLocation);
	public GameModel offerTrade(User user, List<String> resourceList, int receiver);
	public GameModel acceptTrade(User user, boolean willAccept);
	public GameModel maritimeTrade(User user, int ratio, String inputResource, String outputResource);
	public GameModel discardCards(User user);
	
}
