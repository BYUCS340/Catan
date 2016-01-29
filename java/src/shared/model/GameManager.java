package shared.model;

import java.util.List;

import client.map.MapController;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.chat.ChatBox;
import shared.networking.transport.NetGameModel;

/**
 * The game manager class acts as a facade between the player/game objects and the ServerProxy/UI
 * @author matthewcarlson, garrettegan
 *
 */
public class GameManager
{
	private GameState gameState;
	private Bank gameBank;
	private List<Player> players;
	private VictoryPointManager victoryPointManager;
	private MapController mapController;
	private ChatBox waterCooler;
	
	/**
	 * @see <a href="https://imgs.xkcd.com/comics/random_number.png">Sauce</a>
	 * @post all player's banks will be added resources
	 * @return the number rolled
	 */
	public int RollDice() throws ModelException
	{
		gameState.startBuildPhase();
		return 4; // chosen by fair dice roll
				  // guaranteed to be random
	}
	
	
	//--------------------------------------------------------------------------
	//Networking methods
	
	/**
	 * Loads in a game 
	 * @param model the model to be loaded in
	 * @throws ModelException if model is incorrect
	 */
	public void LoadGame(NetGameModel model) throws ModelException
	{
		throw new ModelException();
	}
	
	/**
	 * What the poller pokes to refresh the game model from teh server
	 * @see client.networking.Poller
	 */
	public void RefreshFromServer() throws ModelException
	{
		NetGameModel model = null;
		this.loadGame(model);
	}
	
	
	//--------------------------------------------------------------------------
	//Player Turn Game methods
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildRoad(int playerID) throws ModelException
	{
		//check to see if player has resources
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildSettlement(int playerID) throws ModelException
	{
		//check to see if player has resources
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuildCity(int playerID) throws ModelException
	{
		//check to see if player has resources
	}
	
	/**
	 * Attempts to build a road
	 * @param playerID 0 to 3
	 * @throws ModelException if the player doesn't have the resources
	 */
	public void BuyDevCard(int playerID) throws ModelException
	{
		if (!this.CanBuyDevCard(playerID)) throw new ModelException();
	}
	
	/**
	 * Gets the trade ratio for a given resource for a player
	 * @param playerID
	 * @return never more than 4 or less than 2
	 */
	public int getTradeRatio(int playerID, ResourceType type)
	{
		//Check the Map to see if they're connected to a port
		//Otherwise return the default trade in value
		return 4;
	}
	
	//--------------------------------------------------------------------------
	//CanDo methods
	
	/**
	 * Checks to see if it's a player's turn
	 * @param playerID 0 to 3
	 * @return if possible
	 */
	public boolean CanPlayerPlay(int playerID)
	{
		//If we aren't in the building phase and this player isn't their turn
		if (this.gameState.gameState != GameStatus.BUILDING || this.gameState.activePlayerIndex == playerID)
			return false;
		else
			return false;
	}
	/**
	 * See if a player can discard cards
	 * @param playerID 0 to 3
	 * @param type the type of the resource
	 * @param amount number to discard
	 * @return
	 */
	public boolean CanDiscardCards(int playerID,ResourceType type, int amount)
	{
		
		return false;
	}
	
	/**
	 * Checks to see if a player can roll their number
	 * @param playerID
	 * @return
	 */
	public boolean CanRollNumber(int playerID)
	{
		if (!CanPlayerPlay(playerID))
			return false;
		if (gameState.gameState == GameStatus.ROLLING)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks to see if a player can build a road at a location
	 * @param playerID 0-3
	 * @param location the edge's location
	 * @return true if possible
	 */
	public boolean CanBuildRoad(int playerID,EdgeLocation location)
	{
		return false;
	}
	
	/**
	 * checks to see if a player can build a settlement
	 * @param playerID
	 * @param location the vertex
	 * @return
	 */
	public boolean CanBuildSettlement(int playerID, VertexLocation location)
	{
		return false;
	}
	
	/**
	 * Checks to see if a player can build a city 
	 * @param playerID 0 to 3
	 * @param location the vertex
	 * @return
	 */
	public boolean CanBuildCity (int playerID, VertexLocation location)
	{
		return false;
	}
	
	public boolean CanOfferTrade (int playerID)
	{
		return false;
	}
	public boolean CanMaritimeTrade (int playerID)
	{
		return false;
	}
	public boolean CanFinishTurn (int playerID)
	{
		return false;
	}
	
	/**
	 * Checks to see if a player can buy a dev card
	 * @param playerID 0 to 3
	 * @return true if possible, always false if not the players turn
	 */
	public boolean CanBuyDevCard (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		//This is a total message chain but eh
		try 
		{
			return this.GetPlayer(playerID).playerBank.canBuyDevCard();
		} 
		catch (ModelException e) 
		{
			// if the player isn't found
			return false;
		}
	}
	
	private boolean PlayerHasADevCard(int playerID, DevCardType type)
	{
		//This is a total message chain but eh
		try 
		{
			int count = this.GetPlayer(playerID).playerBank.getDevCardCount(type);
			if (count > 0)
				return true;
			else
				return false;
		} 
		catch (ModelException e) 
		{
			// if the player isn't found
			return false;
		}
	}
	
	/**
	 * Checks to see if the player can use a year of plenty picture
	 * @param playerID
	 * @return
	 */
	public boolean CanUseYearOfPlenty (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.YEAR_OF_PLENTY);
	}
	
	/**
	 * Checks if a player can use the road builder?
	 * @todo figure out what this function does
	 * @param playerID
	 * @return
	 */
	public boolean CanUseRoadBuilder (int playerID)
	{
		return false;
	}
	
	/**
	 * Checks if a player can play a solider card
	 * @param playerID
	 * @return
	 */
	public boolean CanUseSoldier (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.SOLDIER);

	}
	
	/**
	 * Checks to see if a player can play a monopoly card
	 * @param playerID
	 * @return
	 */
	public boolean CanUseMonopoly (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.MONOPOLY);
		
	}
	
	/**
	 * 
	 * @param playerID
	 * @return
	 */
	public boolean CanUseMonument (int playerID)
	{
		if (!this.CanPlayerPlay(playerID)) 
			return false;
		return this.PlayerHasADevCard(playerID, DevCardType.MONUMENT);

	}
	
	/**
	 * Checks to see if a player can place the robber
	 * @param playerID
	 * @todo figure out this function
	 * @return
	 */
	public boolean CanPlaceRobber (int playerID)
	{
		return false;
	}
	
	
	//--------------------------------------------------------------------------
	//Chat methods
	/**
	 * When the player chats currently playing 
	 * @param message
	 */
	public void currentPlayerChat(String message)
	{
		this.playerChat(gameState.activePlayerIndex, message);
	}
	
	/**
	 * Chats for the specified player
	 * @param playerId 0-3
	 * @param message
	 */
	public void playerChat(int playerId, String message)
	{
		waterCooler.put(message, playerId);
	}
	
	//--------------------------------------------------------------------------
	//Game methods
	
	/**
	 * Starts the game
	 */
	public void startGame()
	{
		gameState.startGame();
	}
	
	/**
	 * Ends the current player's turn
	 */
	public void finishTurn()
	{
		gameState.activePlayerIndex++;
		if (gameState.activePlayerIndex > 3)
			gameState.activePlayerIndex = 0;
		gameState.gameState = GameStatus.ROLLING;
	}
	
	/**
	 * Gets the player index of the current player
	 * @return 0 to 3 or -1 if no player is playing
	 */
	public int currentPlayersTurn()
	{
		if (gameState.gameState == GameStatus.START)
		{
			return -1;
		}
		return gameState.activePlayerIndex;
	}
	
	/**
	 * Gets the specified player
	 * @param playerId 0 to 3
	 * @return
	 */
	private Player GetPlayer(int playerId) throws ModelException
	{
		if (playerId >= 0 && playerId < 4)
		{
			return this.players.get(playerId);
		}
		else
		{
			throw new ModelException();
		}
	}
	
	/**
	 * Gets the current player
	 * @return the player
	 */
	private Player GetCurrentPlayer() throws ModelException
	{
		return this.GetPlayer(gameState.activePlayerIndex);
	}
	
	/**
	 * Returns the current State of the game
	 * @return
	 */
	public GameStatus currentState()
	{
		return gameState.gameState;
	}
}
