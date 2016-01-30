package shared.networking;

import java.util.List;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public interface Serializer
{
	
	/**
	 * Serializes the information for any requests that require user credentials
	 * @param username The user's username
	 * @param password The user's password
	 * @return Serialized data
	 */
	public String sCredentials(String username, String password);
	
	/**
	 * Serializes data for requests to create a game
	 * @param randomTiles Whether to create a game with random tiles
	 * @param randomNumbers Whether to create a game with random chit placement
	 * @param randomPorts Whether to create a game with random ports
	 * @param name The name of the game to create
	 * @return Serialized data
	 */
	public String sCreateGameReq(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
	
	/**
	 * Serializes a request from a user to join a game
	 * @param id ID of the user who wishes to join the game
	 * @param color The color the user requests
	 * @return Serialized data
	 */
	public String sJoinGameReq(int id, CatanColor color);
	
	/**
	 * Serializes a request to add AI to a game
	 * @param AIType The type of AI to add to the game
	 * @return Serialized data
	 */
	public String sAddAIReq(String AIType);
	
	/**
	 * Serializes a request to send a chat message
	 * @param playerIndex The index of the player sending the chat
	 * @param content The chat content
	 * @return Serialized data
	 */
	public String sSendChatReq(int playerIndex, String content);
	
	/**
	 * Serializes a request to roll a dice, where the result is already known
	 * @param playerIndex The index of the player who rolls
	 * @param number The number rolled
	 * @return Serialized data
	 */
	public String sRollNumberReq(int playerIndex, int number);
	
	/**
	 * Serializes a request to rob a player
	 * @param playerIndex The index of the player doing the robbing
	 * @param victimIndex The index of the victim
	 * @param location The new location of the robber
	 * @return Serialized data
	 */
	public String sRobPlayerReq(int playerIndex, int victimIndex, HexLocation location);
	
	/**
	 * Serializes a request to finish a turn
	 * @param playerIndex The index of the player who is finishing their turn
	 * @return Serialized data
	 */
	public String sFinishTurnReq(int playerIndex);
	
	/**
	 * Serializes a request to buy a development card
	 * @param playerIndex The index of the player who is buying the development card
	 * @return Serialized data
	 */
	public String sBuyDevCardReq(int playerIndex);
	
	/**
	 * Serializes a request to play a Year of Plenty card
	 * @param playerIndex The index of the player who wishes to play the card
	 * @param resource1 The first resource to take from the bank
	 * @param resource2 The second resource to take from the bank
	 * @return Serialized data
	 */
	public String sYearOfPlentyCardReq(int playerIndex, ResourceType resource1, ResourceType resource2);
	
	/**
	 * Serializes a request to play a road building card
	 * @param playerIndex The index of the player who wishes to play the card
	 * @param edgeLoc1 The location of the first road
	 * @param edgeLoc2 The location of the second road
	 * @return Serialized data
	 */
	public String sRoadBuildingCardReq(int playerIndex, EdgeLocation edgeLoc1, EdgeLocation edgeLoc2);
	
	/**
	 * Serializes a request to play a soldier card
	 * @param playerIndex The index of the player who wishes to play the card
	 * @param victimIndex The index of the victim
	 * @param location The new hex location of the soldier
	 * @return Serialized data
	 */
	public String sSoldierCardReq(int playerIndex, int victimIndex, HexLocation location);
	
	/**
	 * Serializes a request to play a monopoly card
	 * @param playerIndex The index of the player who wishes to play the card
	 * @param resource The resource to have a monopoly on
	 * @return Serialized data
	 */
	public String sMonopolyCardReq(int playerIndex, ResourceType resource);
	
	/**
	 * Serializes a request to play a monument card
	 * @param playerIndex The index of the player who wishes to play the card
	 * @return Serialized data
	 */
	public String sMonumentCardReq(int playerIndex);
	
	/**
	 * Serializes a request to build a road
	 * @param playerIndex The index of the player who wishes to build the road
	 * @param roadLocation The location of the road
	 * @param free Whether this road is free (doesn't cost resources) or not
	 * @return Serialized data
	 */
	public String sBuildRoadReq(int playerIndex, EdgeLocation roadLocation, boolean free);
	
	/**
	 * Serializes a request to build a settlement 
	 * @param playerIndex The index of the player who wishes to build the settlement
	 * @param vertexLocation Where to build the settlement
	 * @param free Whether this settlement is free (doesn't cost resources) or not
	 * @return Serialized data
	 */
	public String sBuildSettlementReq(int playerIndex, VertexLocation vertexLocation, boolean free);
	
	/**
	 * Serializes a request to build a city
	 * @param playerIndex The index of the player who wishes to build the city
	 * @param vertexLocation Where to build the city
	 * @return Serialized data
	 */
	public String sBuildCityReq(int playerIndex, VertexLocation vertexLocation);
	
	/**
	 * Serializes a request to offer a trade
	 * @param playerIndex The index of the player offering the trade
	 * @param resourceList The list of resources being traded and asked for
	 * @param receiver The index of the receiver
	 * @return Serialized data
	 */
	public String sOfferTradeReq(int playerIndex, List<Integer> resourceList, int receiver);
	
	/**
	 * Serializes a request to accept (or reject) a trade
	 * @param playerIndex The index of the player who is accepting or rejecting the trade
	 * @param willAccept Whether the trade is accepted
	 * @return Serialized data
	 */
	public String sAcceptTradeReq(int playerIndex, boolean willAccept);
	
	/**
	 * Serializes a request to perform a maritime trade
	 * @param playerIndex The index of the player who is performing the trade
	 * @param ratio The trade ratio of the port
	 * @param inputResource The resource being traded
	 * @param outputResource The resource being traded for
	 * @return Serialized data
	 */
	public String sMaritimeTrade(int playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource);
	
	/**
	 * Serializes a request to discard cards
	 * @param playerIndex The index of the player discarding cards
	 * @param resourceList A resource list with the cards being discarded
	 * @return Serialized data
	 */
	public String sDiscardCards(int playerIndex, List<Integer> resourceList);
	
	
}
