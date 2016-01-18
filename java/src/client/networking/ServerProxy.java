package client.networking;

public interface ServerProxy 
{
	public void loginUser();
	public void registerUser();
	
	public String listGames();
	public void createGame();
	public void joinGame();
	public void saveGame();
	public void loadGame();
	
	public String getGameModel();
	public void resetGame();
	public void executeCommand();
	public String getCommands();
	public void addAI();
	public String listAI();
	
	public void sendChat();
	public void rollNumber();
	public void robPlayer();
	public void finishTurn();
	public void buyDevCard();
	public void yearOfPlentyCard();
	public void roadBuildingCard();
	public void soldierCard();
	public void monopolyCard();
	public void buildRoad();
	public void buildSettlement();
	public void buildCity();
	public void offerTrade();
	public void acceptTrade();
	public void maritimeTrade();
	public void discardCards();
	
	public void changeLogLevel();

}
