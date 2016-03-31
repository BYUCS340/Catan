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
public class MockServerGame extends ServerGameManager {

	public MockServerGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) 
	{
		super(name, randomTiles, randomNumbers, randomPorts);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initDiscard(boolean shouldBeBlank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateVersion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int GetPlayerIndexByID(int playerID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean ServerSendChat(int playerID, String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerRollNumber(int playerIndex, int number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerRobPlayer(int playerIndex, int victimIndex, Coordinate location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerFinishTurn(int playerID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerBuyDevCard(int playerID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerYearOfPlenty(int playerIndex, ResourceType res1, ResourceType res2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerMonopoly(int playerIndex, ResourceType res1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerMonument(int playerIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerRoadBuilding(int playerIndex, Coordinate start1, Coordinate end1, Coordinate start2,
			Coordinate end2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerSoldier(int playerID, Coordinate location, int victimIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerBuildRoad(int playerID, Coordinate start, Coordinate end, boolean free) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerBuildCity(int playerIndex, Coordinate p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerBuildSettlement(int playerIndex, Coordinate p, boolean free) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerOfferTrade(int playerIndexOffering, int playerIndexReceiving, List<Integer> resourceList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerAcceptTrade(int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerMaritimeTrading(int playerIndex, int ratio, ResourceType input, ResourceType output) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ServerDiscardCards(int playerIndex, List<Integer> resourceList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ResourceType takeRandomResourceCard(int receiver, int giver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel ServerGetModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
		//TODO fill with fake data
		
	
}
