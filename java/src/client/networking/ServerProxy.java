package client.networking;

import java.util.List;

public interface ServerProxy 
{
	public void loginUser(String username, String password);
	public void registerUser(String username, String password);
	
	public String listGames();
	public String createGame();
	public void joinGame(int id, String color);

	
	public String getGameModel(User user);
	public void addAI(User user);
	public String listAI(User user);
	
	public String sendChat(User user);
	public String rollNumber(User user);
	public String robPlayer(User user);
	public String finishTurn(User user);
	public String buyDevCard(User user);
	public String yearOfPlentyCard(User user);
	public String roadBuildingCard(User user);
	public String soldierCard(User user);
	public String monopolyCard(User user);
	public String monumentCard(User user);
	public String buildRoad(User user, EdgeLocation edgeLocation, boolean free);
	public String buildSettlement(User user, VertexLocation vertexLocation, boolean free);
	public String buildCity(User user, VertexLocation vertexLocation);
	public String offerTrade(User user, List<String> resourceList, int receiver);
	public String acceptTrade(User user, boolean willAccept);
	public String maritimeTrade(User user, int ratio, String inputResource, String outputResource);
	public String discardCards(User user);
	
}
