package server.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.definitions.ResourceType;
import shared.model.GameManager;
import shared.model.GameModel;
import shared.model.map.Coordinate;
import shared.model.map.model.MapGenerator;

/**
 * Special formation of the game manager
 * @author matthewcarlson
 *
 */
public abstract class ServerGameManager extends GameManager implements Serializable
{
	/**
	 *
	 */
	protected static final long serialVersionUID = 1293281;
	protected boolean randomTiles;
	protected boolean randomNumbers;
	protected boolean randomPorts;

	protected Map<Integer,Integer> playerIndexLookup;

	protected List<Boolean> discardList;

	public ServerGameManager(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)
	{
		super();
		this.gameTitle = name;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.randomTiles = randomTiles;
		this.playerIndexLookup = new HashMap<Integer,Integer>();
		this.map = MapGenerator.GenerateMap(randomTiles, randomNumbers, randomPorts);
	}

	public ServerGameManager()
	{
		super();
		this.gameTitle = "Default";
		this.gameID = 1;
		this.randomNumbers = false;
		this.randomPorts = false;
		this.randomTiles = false;
		this.playerIndexLookup = new HashMap<Integer,Integer>();

		this.map = MapGenerator.GenerateMap(false, false, false);
	}
	
	/**
	 * Sets the game ID of the Game Manager
	 * @param id The game ID.
	 */
	public void SetGameID(int id)
	{
		this.gameID = id;
	}

	/**
	 * Initializes the discard list. One entry is allocated per player in the game.
	 * If shouldBeBlank is true, all of the entries in the list are set to false.
	 * If shouldBeBlank is false, each entry is set to true if the corresponding player
	 * should discard, or false if the player does not need to discard (ie, they have
	 * 7 or less cards)
	 * @param shouldBeBlank whether to set all values to false
	 */
	protected abstract void initDiscard(boolean shouldBeBlank);

	/**
	 * Updates the version when doing an action
	 */
	protected abstract void updateVersion();

	/**
	 * Returns the player index by id
	 * @param playerID
	 * @return
	 */
	public abstract int GetPlayerIndexByID(int playerID);


	/**
	 * Sends a chat for the user
	 * @param playerID by Player ID
	 * @param message to chat with
	 */
	public abstract boolean ServerSendChat(int playerID, String message);

	/**
	 *
	 * @param playerID
	 * @param number
	 * @return
	 */
	public abstract boolean ServerRollNumber(int playerIndex, int number);

	/**
	 *
	 * @param playerID the ID of the player who is moving the robber
	 * @param victimIndex the index of the victim
	 * @param location the new location of the robber
	 * @return true if successful, false if not
	 */
	public abstract boolean ServerRobPlayer(int playerIndex, int victimIndex, Coordinate location);

	/**
	 * Ends a player's turn
	 * @param playerID
	 * @return
	 */
	public abstract boolean ServerFinishTurn(int playerID);

	/**
	 * Buys a dev card
	 * @param playerID
	 * @return
	 */
	public abstract boolean ServerBuyDevCard(int playerID);

	/**
	 * plays a year of plenty card
	 * @param playerIndex
	 * @param res1
	 * @param res2
	 * @return
	 */
	public abstract boolean ServerYearOfPlenty(int playerIndex, ResourceType res1, ResourceType res2);

	/**
	 * plays a monopoly card
	 * @param playerIndex
	 * @param res1
	 * @return
	 */
	public abstract boolean ServerMonopoly(int playerIndex, ResourceType res1);

	/**
	 * plays a monument card
	 * @param playerIndex
	 * @return
	 */
	public abstract boolean ServerMonument(int playerIndex);

	/**
	 * plays a road building card
	 * @param playerIndex
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public abstract boolean ServerRoadBuilding(int playerIndex, Coordinate start1, Coordinate end1,  Coordinate start2, Coordinate end2);

	/**
	 * plays a solider card
	 * @param playerID
	 * @param location
	 * @param victimIndex the victim
	 * @return
	 */
	public abstract boolean ServerSoldier(int playerID, Coordinate location, int victimIndex);
	/**
	 * builds a road
	 * @param playerID
	 * @param p
	 * @return
	 */
	public abstract boolean ServerBuildRoad(int playerID, Coordinate start, Coordinate end, boolean free);

	/**
	 * builds a city
	 * @param playerIndex
	 * @param p
	 * @return
	 */
	public abstract boolean ServerBuildCity(int playerIndex, Coordinate p);
	
	/**
	 * builds settlement
	 * @param playerIndex
	 * @param p
	 * @return
	 */
	public abstract boolean ServerBuildSettlement(int playerIndex, Coordinate p, boolean free);

	/**
	 * offers a trade
	 * @param playerIndexOffering
	 * @param playerIndexReceiving
	 * @param resourceList
	 * @return
	 */
	public abstract boolean ServerOfferTrade(int playerIndexOffering, int playerIndexReceiving, List<Integer> resourceList );

	/**
	 * accepts a trade
	 * @param playerIndex
	 * @param willAccept
	 * @return
	 */
	public abstract boolean ServerAcceptTrade(int playerIndex, boolean willAccept);

	/***
	 * trades in the maritime
	 * @param playerIndex
	 * @param ratio
	 * @param input
	 * @param output
	 * @return
	 */
	public abstract boolean ServerMaritimeTrading(int playerIndex, int ratio, ResourceType input, ResourceType output);

	/**
	 * discards cards
	 * @param playerIndex
	 * @param resourceList
	 * @return
	 */
	public abstract boolean ServerDiscardCards(int playerIndex, List<Integer> resourceList);	
	
	/**
	 * takes a random resource from one player and gives it to another
	 * @param receiver
	 * @param giver
     * @return
     */
	protected abstract ResourceType takeRandomResourceCard(int receiver, int giver);


	/**
	 * Gets the server's current game model in a serializable form
	 * @return
	 */
	public Serializable ServerGetSerializableModel()
	{
		return (Serializable) this.ServerGetModel();
	}

	/**
	 * Gets the current game model
	 * @return
	 */
	public abstract GameModel ServerGetModel();

}
