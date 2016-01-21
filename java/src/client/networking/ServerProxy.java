package client.networking;

import shared.networking.*;
import shared.definitions.*;
import shared.locations.*;
import shared.model.*;
import java.util.List;

public interface ServerProxy 
{
	/**
	 * Logs the specified user in and returns a User object if the user was
	 * successfully logged in. If not, a null user is returned
	 * @param username The username of the user to log in
	 * @param password The password of the user to log in
	 */
	public void loginUser(String username, String password);
	
	/**
	 * Registers a user with the specified username and password
	 * @param username The username of the user to be registered
	 * @param password The password of the user to be registered
	 * @return true if the user could be registered, false if not
	 */
	public boolean registerUser(String username, String password);
	
	/**
	 * Fetches a list of ongoing games
	 * @return a list of all ongoing games on the server
	 */
	public List<Game> listGames();
	
	/**
	 * Creates a game on the server
	 * @param randomTiles Whether the server should place random tiles
	 * @param randomNumbers Whether the server should place random number chits
	 * @param randomPorts Whether the server should place random ports
	 * @param name The name of the game to be created
	 * @return a game object that represents the game that was created
	 */
	public Game createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
	
	/**
	 * 
	 * @param color the color the user wishes to represent him
	 * @return a game object that represents the game the user joined
	 */
	public Game joinGame(String color); 

	/**
	 * Retrieves the game model from the server
	 * @return a GameModel object
	 */
	public GameModel getGameModel();
	
	/**
	 * Adds an AI to the game
	 * @param aiType the type of AI the user wishes to add
	 * @throws ProxyException if there is no logged in user
	 */
	public void addAI(String aiType);
	
	/**
	 * 
	 * @return a list of supported AI player types
	 * @throws ProxyException if there is no logged in user
	 */
	public List<AI> listAI();
	
	
	/**
	 * Sends a chat message from the specified user to the server
	 * @param content The content of the chat message
	 * @return A GameModel object
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel sendChat(String content);
	
	/**
	 * Reports the result of a dice roll to the server
	 * @param roll The result of the user's roll
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel rollNumber(int roll);
	
	/**
	 * Notifies the server that the user has decided to rob another player
	 * @param victimIndex The index of the victim of the user's robbing
	 * @param location The new hex location of the robber
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel robPlayer(int victimIndex, HexLocation location);
	
	/**
	 * Notifies the server that the user has finished his turn
	 * @param user The User who has finished his turn
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel finishTurn();
	
	/**
	 * Notifies the server that the user has bought a development card
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel buyDevCard();
	
	/**
	 * Notifies the server that the user has played a year of plenty card
	 * @param resource1 The first chosen resource
	 * @param resource2 The second chosen resource
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel yearOfPlentyCard(String resource1, String resource2);
	
	/**
	 * Notifies the server that the user has played a road building card
	 * @param location1 The EdgeLocation location of the first road
	 * @param location2 The EdgeLocation location of the second card
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel roadBuildingCard(EdgeLocation location1, EdgeLocation location2);
	
	/**
	 * Notifies the server that the user has played a soldier card
	 * @param victimIndex The index of the player who is being robbed
	 * @param hexLocation The new hex location of the robber
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel soldierCard(int victimIndex, HexLocation hexLocation);
	
	/**
	 * Notifies the server that the user has played a monopoly card
	 * @param resource The resource that the player has chosen to have a monopoly on
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel monopolyCard(String resource);
	
	/**
	 * Notifies the server that the user has played a monument card
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel monumentCard();
	
	/**
	 * Notifies the server that the user has decided to build a road
	 * @param edgeLocation The edge location of the road
	 * @param free Whether this road was free (only true during the set up phases)
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel buildRoad(EdgeLocation edgeLocation, boolean free);
	
	/**
	 * Notifies the server that the user has decided to build a settlement
	 * @param vertexLocation The vertex location of the settlement
	 * @param free Whether this settlement was free (only true during the set up phases)
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel buildSettlement(VertexLocation vertexLocation, boolean free);
	
	/**
	 * Notifies the server that the user has decided to build a city
	 * @param vertexLocation The vertex location of the city
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel buildCity(VertexLocation vertexLocation);
	
	/**
	 * Notifies the server that the user has decided to offer a trade to another player
	 * @param resourceList A list of the resources that the user wishes to trade, in this order: brick, ore, sheep,
	 * wheat, and wood. Negative values denotes that this user will give these resources, and positive values denote 
	 * the resources that will be received
	 * @param receiver The index of the player who will receive this trade offer
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel offerTrade(List<Integer> resourceList, int receiver);
	
	/**
	 * Notifies the server whether this player has decided to accept or reject a trade
	 * @param willAccept true if the user will accept the trade, false if not
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel acceptTrade(boolean willAccept);
	
	/**
	 * Notifies the server that the user has decided to initiate a maritime trade
	 * @param ratio The ratio of resources demanded by the harbor
	 * @param inputResource The resources traded away
	 * @param outputResource The resource received
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel maritimeTrade(int ratio, String inputResource, String outputResource);
	
	/**
	 * Notifies the server that the user has discarded cards
	 * @param resourceList A list of integers that denotes how many of each resource the user will discard. The
	 * order is brick, ore, sheep, wheat, and wood
	 * @return a GameModel object that reflects the current state of the Game
	 * @throws ProxyException if there is no logged in user
	 */
	public GameModel discardCards(List<Integer> resourceList);
	
}
