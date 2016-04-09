package server.model;

import java.util.List;

import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.map.Coordinate;

/**
 * This is a mock server game with data for the relevant points
 * It basically populates itself with data
 * @author matthewcarlson
 *
 */
public class MockServerGame extends ServerGameManager 
{
	private static final long serialVersionUID = 4502929772514139535L;

	public MockServerGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) 
	{
		super(name, randomTiles, randomNumbers, randomPorts);
	}

	@Override
	protected void initDiscard(boolean shouldBeBlank) 
	{
		
	}

	@Override
	protected void updateVersion() 
	{
		
	}

	@Override
	public int GetPlayerIndexByID(int playerID) 
	{
		return 0;
	}

	@Override
	public boolean ServerSendChat(int playerID, String message) 
	{
		return false;
	}

	@Override
	public boolean ServerRollNumber(int playerIndex, int number)
	{
		return false;
	}

	@Override
	public boolean ServerRobPlayer(int playerIndex, int victimIndex, Coordinate location) 
	{
		return false;
	}

	@Override
	public boolean ServerFinishTurn(int playerID) 
	{
		return false;
	}

	@Override
	public boolean ServerBuyDevCard(int playerID) 
	{
		return false;
	}

	@Override
	public boolean ServerYearOfPlenty(int playerIndex, ResourceType res1, ResourceType res2) 
	{
		return false;
	}

	@Override
	public boolean ServerMonopoly(int playerIndex, ResourceType res1) 
	{
		return false;
	}

	@Override
	public boolean ServerMonument(int playerIndex) 
	{
		return false;
	}

	@Override
	public boolean ServerRoadBuilding(int playerIndex, Coordinate start1, Coordinate end1, Coordinate start2,
			Coordinate end2) 
	{
		return false;
	}

	@Override
	public boolean ServerSoldier(int playerID, Coordinate location, int victimIndex) 
	{
		return false;
	}

	@Override
	public boolean ServerBuildRoad(int playerID, Coordinate start, Coordinate end, boolean free) 
	{
		return false;
	}

	@Override
	public boolean ServerBuildCity(int playerIndex, Coordinate p)
	{
		return false;
	}

	@Override
	public boolean ServerBuildSettlement(int playerIndex, Coordinate p, boolean free) 
	{
		return false;
	}

	@Override
	public boolean ServerOfferTrade(int playerIndexOffering, int playerIndexReceiving, List<Integer> resourceList) 
	{
		return false;
	}

	@Override
	public boolean ServerAcceptTrade(int playerIndex, boolean willAccept) 
	{
		return false;
	}

	@Override
	public boolean ServerMaritimeTrading(int playerIndex, int ratio, ResourceType input, ResourceType output) 
	{
		return false;
	}

	@Override
	public boolean ServerDiscardCards(int playerIndex, List<Integer> resourceList) 
	{
		return false;
	}

	@Override
	protected ResourceType takeRandomResourceCard(int receiver, int giver) 
	{
		return null;
	}

	@Override
	public GameModel ServerGetModel() 
	{
		return null;
	}
	
	//TODO fill with fake data
}
