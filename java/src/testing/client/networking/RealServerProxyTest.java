package testing.client.networking;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.networking.RealServerProxy;
import client.networking.ServerProxyException;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.networking.UserCookie;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetResourceList;

public class RealServerProxyTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		//TODO put data on server
	}

	@After
	public void tearDown() throws Exception
	{
		//TODO wipe server
	}
	
	/**
	 * This only works if the testProxy can register/loging users, and create games. This is for setting up clean games for testing other methods
	 * @param newUsername
	 * @param password
	 * @return
	 */
	private boolean joinNewFilledGame(String newUsername, String password) throws Exception
	{
		RealServerProxy testProxy = new RealServerProxy();
		boolean couldRegister = testProxy.registerUser(newUsername, password);
		if(!couldRegister)
			return false;
		
		boolean couldLogin = testProxy.loginUser(newUsername, password);
		if(!couldLogin)
			return false;
		
		testProxy.createGame(true, true, false, "testCreatGame...Game");

		
		
		
		return false;
	}
	
	@Test
	public void testCreateGame() throws Exception
	{
		String username1 = UUID.randomUUID().toString();
		String password1 = UUID.randomUUID().toString();
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);		


		boolean couldRegister = testProxy.registerUser(username1, password1);
		assertTrue(couldRegister);
		
		boolean couldLogin = testProxy.loginUser(username1, password1);
		assertTrue(couldLogin);
		
		String game1 = UUID.randomUUID().toString();
//		List<NetGame> gamesBeforeCreate = testProxy.listGames();
		testProxy.createGame(true, true, false, UUID.randomUUID().toString());
//		List<NetGame> gamesAfterCreate = testProxy.listGames();
		
//		assertTrue(gamesBeforeCreate.size() + 1 == gamesAfterCreate.size());
//		assertTrue(gamesAfterCreate.get(gamesAfterCreate.size() - 1).getTitle().equals(game1));
	}
	
	@Test
	public void testJoinGame() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
		RealServerProxy testProxy = new RealServerProxy();
		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		//if 200 is returned, assuming that test completed succesfully
	}
	
	
	@Test
	public void testGetGameModel() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
		RealServerProxy testProxy = new RealServerProxy();
		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
		//if 200 is returned, assuming that test completed succesfully
	}
	
//	@Test
//	public void testAddAI() throws Exception
//	{
//		String username = UUID.randomUUID().toString();
//		String password = UUID.randomUUID().toString();
//		String game = UUID.randomUUID().toString();
//		CatanColor col = CatanColor.BLUE;
//		
//		//set up the game
//		RealServerProxy testProxy = new RealServerProxy();
//		testProxy.registerUser(username, password);
//		testProxy.loginUser(username, password);
//		testProxy.createGame(true, true, true, game);
//		List<NetGame> gList = testProxy.listGames();
//		
//		//get the number of the game to join
//		NetGame targetGame = null;
//		for(NetGame g : gList){
//			if(g.getTitle().equals(game)){
//				targetGame = g;
//			}
//		}
//		
//		//join target game
//		testProxy.joinGame(targetGame.getId(), col);
//		testProxy.getGameModel();
//		
//		testProxy.addAI(AIType.LARGEST_ARMY);
//		//if 200 is returned, assuming that test completed succesfully
//	}
	
	@Test
	public void testListAI() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
		RealServerProxy testProxy = new RealServerProxy();
		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
		
		testProxy.addAI(AIType.LARGEST_ARMY);
		List<AIType> ai = testProxy.listAI();
		assertTrue(ai.size() == 1);
		//if 200 is returned, assuming that test completed succesfully
	}
	
	

	@Test
	public void testRegisterUser() throws Exception
	{
		String username1 = UUID.randomUUID().toString();
		String password1 = UUID.randomUUID().toString();
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);		


		boolean couldRegister = testProxy.registerUser(username1, password1);
		
		//double check that the user could be registered
		assertTrue(couldRegister);
		
		//make sure the same user can't register twice
		couldRegister = testProxy.registerUser(username1, password1);
		assertTrue(!couldRegister);
		
	}
	
	
	@Test
	public void testLoginUser() throws Exception
	{
		String username1 = UUID.randomUUID().toString();
		String password1 = UUID.randomUUID().toString();
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);		


		boolean couldRegister = testProxy.registerUser(username1, password1);
		
		assertTrue(couldRegister);
		boolean couldLogin = testProxy.loginUser(username1, password1);
		assertTrue(couldLogin);
		
		UserCookie uCookie = testProxy.getUserCookie();
		
		//make sure the cookie was set correctly
		String user = uCookie.getUsername();
		assertTrue(user.equalsIgnoreCase(username1));
		
		String password = uCookie.getPassword();
		assertTrue(password.equalsIgnoreCase(password1));
		
		testProxy.clearCookies();
		
		String username2 = UUID.randomUUID().toString();
		String password2 = UUID.randomUUID().toString();
		
		//make sure bad login attempts are rejected without an exception
		couldLogin = testProxy.loginUser(username2, password2);
		assertTrue(!couldLogin);
		
	}
	
	@Test
	public void testListGames() throws Exception
	{
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);		


		
		//get result from server
		List<NetGame> gList = testProxy.listGames();
		NetGame testGameRes = gList.get(0);
		
		//test the game at index 0
		assertTrue(testGameRes.getId() == 0);
		assertTrue(testGameRes.getTitle().equals("Default Game"));
		assertTrue(testGameRes.getNetPlayers().get(0).getName().equals("Sam"));
		assertTrue(testGameRes.getNetPlayers().get(0).getColor() == CatanColor.ORANGE);
		assertTrue(testGameRes.getNetPlayers().get(0).getPlayerID() == 0);
		assertTrue(testGameRes.getNetPlayers().get(1).getName().equals("Brooke"));
		assertTrue(testGameRes.getNetPlayers().get(1).getColor() == CatanColor.BLUE);
		assertTrue(testGameRes.getNetPlayers().get(1).getPlayerID() == 1);
		assertTrue(testGameRes.getNetPlayers().get(2).getName().equals("Pete"));
		assertTrue(testGameRes.getNetPlayers().get(2).getColor() == CatanColor.RED);
		assertTrue(testGameRes.getNetPlayers().get(2).getPlayerID() == 10);
		assertTrue(testGameRes.getNetPlayers().get(3).getName().equals("Mark"));
		assertTrue(testGameRes.getNetPlayers().get(3).getColor() == CatanColor.GREEN);
		assertTrue(testGameRes.getNetPlayers().get(3).getPlayerID() == 11);
		
		//TODO: Make this test more complete and not reliant on the default instance
		//of the server
		
	}
	
	@Test
	public void testBuildRoad() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);		

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
		
		//REALLY TEST THE BUILDROAD
		//1. Set up needed road structure
		HexLocation hexLoc = new HexLocation(0,0);
		EdgeLocation edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.North);
		
		//send request to the server
		testProxy.buildRoad(edgeLoc, true);
		
		//if there are no errors, call it good.
		//TODO expand this past a "200" test
	}
	
	@Test
	public void testBuildSettlement() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);		

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
		
		//REALLY TEST THE BUILDSETTLEMENT
		//1. Set up needed settlement structures
		HexLocation hexLoc = new HexLocation(0,0);
		VertexLocation vertLoc = new VertexLocation(hexLoc, VertexDirection.West);	
		
		//send request to the server
		testProxy.buildSettlement(vertLoc, true);
		
		//if there are no errors, call it good.
		//TODO expand this past a "200" test
	}

	@Test
	public void testBuildCity() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);		

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
		
		//REALLY TEST THE BUILDCITY
		//1. Set up needed city structures
		HexLocation hexLoc = new HexLocation(0,0);
		VertexLocation vertLoc = new VertexLocation(hexLoc, VertexDirection.West);	
		
		//send request to the server
		testProxy.buildCity(vertLoc);
		
		//if there are no errors, call it good.
		//TODO expand this past a "200" test
	}
	
	@Test
	public void testOfferTrade() throws Exception
	{
		String username1 = UUID.randomUUID().toString();
		String password1 = UUID.randomUUID().toString();
		String username2 = UUID.randomUUID().toString();
		String password2 = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col1 = CatanColor.BLUE;
		CatanColor col2 = CatanColor.RED;
		
		//sign first user in
//		RealServerProxy testProxy1 = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy1 = new RealServerProxy("127.0.0.1", 8081);		


		testProxy1.registerUser(username1, password1);
		testProxy1.loginUser(username1, password1);
		testProxy1.createGame(true, true, true, game);
		List<NetGame> gList = testProxy1.listGames();
		
		//sign second user in
//		RealServerProxy testProxy2 = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy2 = new RealServerProxy("127.0.0.1", 8081);		


		testProxy2.registerUser(username2, password2);
		testProxy2.loginUser(username2, password2);
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy1.joinGame(targetGame.getId(), col1);
		testProxy1.getGameModel();
		
		testProxy2.joinGame(targetGame.getId(), col2);
		testProxy2.getGameModel();
		
		//REALLY TEST THE offerTrade
		//1. Set up needed structures
		List<Integer> tradeList = new ArrayList<Integer>();
		tradeList.add(1);
		tradeList.add(-1);
		tradeList.add(0);
		tradeList.add(0);
		tradeList.add(0);
		
		//send request to the server
		testProxy1.offerTrade(tradeList, 1);
		
		//make sure the request was really registered
		NetGameModel mod = testProxy2.getGameModel();
		assertTrue(mod.getNetTradeOffer().getReceiver() == 1);
		
		//if there are no errors, call it good.
	}
	
	@Test
	public void testAcceptTrade() throws Exception
	{
		String username1 = UUID.randomUUID().toString();
		String password1 = UUID.randomUUID().toString();
		String username2 = UUID.randomUUID().toString();
		String password2 = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col1 = CatanColor.BLUE;
		CatanColor col2 = CatanColor.RED;
		
		//sign first user in
//		RealServerProxy testProxy1 = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy1 = new RealServerProxy("127.0.0.1", 8081);		


		testProxy1.registerUser(username1, password1);
		testProxy1.loginUser(username1, password1);
		testProxy1.createGame(true, true, true, game);
		List<NetGame> gList = testProxy1.listGames();
		
		//sign second user in
//		RealServerProxy testProxy2 = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy2 = new RealServerProxy("127.0.0.1", 8081);		


		testProxy2.registerUser(username2, password2);
		testProxy2.loginUser(username2, password2);
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy1.joinGame(targetGame.getId(), col1);
		testProxy1.getGameModel();
		
		testProxy2.joinGame(targetGame.getId(), col2);
		testProxy2.getGameModel();
		
		//REALLY TEST THE offerTrade
		//1. Set up needed structures
		List<Integer> tradeList = new ArrayList<Integer>();
		tradeList.add(1);
		tradeList.add(-1);
		tradeList.add(0);
		tradeList.add(0);
		tradeList.add(0);
		
		//send request to the server
		testProxy1.offerTrade(tradeList, 1);
		
		//make sure the request was really registered
		NetGameModel mod = testProxy2.getGameModel();
		assertTrue(mod.getNetTradeOffer().getReceiver() == 1);
		
		//have the second user accept the trade
		mod = testProxy2.acceptTrade(true);
		
		//make sure the request made it through to the server
		assertTrue(mod.getNetTradeOffer() == null);
		//if there are no errors, call it good.
	}

	@Test
	public void testMaritimeTrade() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);		

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
		
		
		//send request to the server
		testProxy.maritimeTrade(2, ResourceType.WHEAT, ResourceType.WOOD);
		
		//if there are no errors, call it good.
		//TODO expand this past a "200" test
	}
	
	@Test
	public void testSendChat() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
		RealServerProxy testProxy = new RealServerProxy();
		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
				
		//send request to the server
		testProxy.sendChat("I'm a silly test case!");
		
		//if there are no errors, call it good.
		//TODO expand this past a "200" test
	}
	
	
	
	

	@Test
	public void testRollNumber() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
		RealServerProxy testProxy = new RealServerProxy();
		testProxy.registerUser(username, password);
		assertTrue(testProxy.loginUser(username, password));
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		

		NetGameModel mod = testProxy.getGameModel();
		assertTrue(mod != null);

		testProxy.addAI(AIType.LARGEST_ARMY);
		testProxy.addAI(AIType.LARGEST_ARMY);
		testProxy.addAI(AIType.LARGEST_ARMY);

				
		//send request to the server_host
		int num = 5;
		testProxy.rollNumber(num);
		
		//if there are no errors, call it good.
		//TODO expand this past a "200" test
	}
	
	
	
	
	
	@Test
	public void buyDevCard() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
		NetGameModel gameModelBefore = testProxy.getGameModel();
		int currentUserIndex = testProxy.getUserIndex();
//		System.out.println(currentUserIndex);
		NetGameModel gameModelAfter = testProxy.buyDevCard();
//		int cardsAddedCount = 0;
//		//  if a dev card was added
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonopoly() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonopoly();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonument() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonument();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumRoadBuilding() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumRoadBuilding();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumSoldier() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumSoldier();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumYearOfPlenty() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumYearOfPlenty();
////		System.out.println(cardsAddedCount);
//		assertTrue(cardsAddedCount != 0);
//				
//		int bankCardsSubtractedCount = 0;
//		//  if a dev card was subtracted
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumMonopoly() - gameModelBefore.getNetDeck().getNumMonopoly();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumMonument() - gameModelBefore.getNetDeck().getNumMonument();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumRoadBuilding() - gameModelBefore.getNetDeck().getNumRoadBuilding();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumSoldier() - gameModelBefore.getNetDeck().getNumSoldier();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumYearOfPlenty() - gameModelBefore.getNetDeck().getNumYearOfPlenty();
//		assertTrue(bankCardsSubtractedCount != 0);
//		
//		
//		
//		
//		
//		gameModelBefore = testProxy.getGameModel();
//		gameModelAfter = testProxy.buyDevCard();
//		cardsAddedCount = 0;
//		//  if a dev card was added
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonopoly() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonopoly();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonument() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonument();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumRoadBuilding() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumRoadBuilding();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumSoldier() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumSoldier();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumYearOfPlenty() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumYearOfPlenty();
//		assertTrue(cardsAddedCount != 0);
//				
//		bankCardsSubtractedCount = 0;
//		//  if a dev card was subtracted
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumMonopoly() - gameModelBefore.getNetDeck().getNumMonopoly();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumMonument() - gameModelBefore.getNetDeck().getNumMonument();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumRoadBuilding() - gameModelBefore.getNetDeck().getNumRoadBuilding();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumSoldier() - gameModelBefore.getNetDeck().getNumSoldier();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumYearOfPlenty() - gameModelBefore.getNetDeck().getNumYearOfPlenty();
//		assertTrue(bankCardsSubtractedCount != 0);
//		
//		
//		
//		gameModelBefore = testProxy.getGameModel();
//		gameModelAfter = testProxy.buyDevCard();
//		cardsAddedCount = 0;
//		//  if a dev card was added
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonopoly() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonopoly();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonument() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumMonument();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumRoadBuilding() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumRoadBuilding();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumSoldier() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumSoldier();
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumYearOfPlenty() - gameModelBefore.getNetPlayers().get(currentUserIndex).getNewNetDevCardList().getNumYearOfPlenty();
//		assertTrue(cardsAddedCount != 0);
//				
//		bankCardsSubtractedCount = 0;
//		//  if a dev card was subtracted
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumMonopoly() - gameModelBefore.getNetDeck().getNumMonopoly();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumMonument() - gameModelBefore.getNetDeck().getNumMonument();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumRoadBuilding() - gameModelBefore.getNetDeck().getNumRoadBuilding();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumSoldier() - gameModelBefore.getNetDeck().getNumSoldier();
//		bankCardsSubtractedCount += gameModelAfter.getNetDeck().getNumYearOfPlenty() - gameModelBefore.getNetDeck().getNumYearOfPlenty();
//		assertTrue(bankCardsSubtractedCount != 0);
		

		
	}
	
	
	@Test
	public void yearOfPlentyCard() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);

		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		NetGameModel gameModelBefore = testProxy.getGameModel();
		NetGameModel gameModelAfter = testProxy.yearOfPlentyCard(ResourceType.BRICK, ResourceType.ORE);
		
//		System.out.println(gameModelBefore);
//		System.out.println(gameModelAfter);
		int cardsAddedCount = 0;
//		//  if a dev card was added
//		cardsAddedCount += gameModelAfter.getNetPlayers().get(0).getNewNetDevCardList().getNumYearOfPlenty() - gameModelBefore.getNetPlayers().get(0).getNewNetDevCardList().getNumYearOfPlenty();
//		System.out.println("Year of plenty diff: " + cardsAddedCount);
//		assertTrue(cardsAddedCount == -1);
				

		
		int resourcesAddedCount = 0;
		//  if a dev card was subtracted
		resourcesAddedCount += gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumBrick() - gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumBrick();
		resourcesAddedCount += gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumOre() - gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumOre();
		resourcesAddedCount += gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumSheep() - gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumSheep();
		resourcesAddedCount += gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumWheat() - gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumWheat();
		resourcesAddedCount += gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumWood() - gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumWood();
//		System.out.println("Year of plenty diff2: " + resourcesAddedCount);
		assertTrue(resourcesAddedCount == 2);
		
		
	}
	
	@Test
	public void roadBuildingCard() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		NetGameModel gameModelBefore = testProxy.getGameModel();
		NetGameModel gameModelAfter = testProxy.roadBuildingCard(new EdgeLocation(new HexLocation(1,2), EdgeDirection.North),  new EdgeLocation(new HexLocation(1,2), EdgeDirection.NorthWest));
		
		assertTrue(gameModelAfter.getNetMap().getNetRoads().size() > 0);
		assertTrue(gameModelAfter.getNetPlayers().get(0).getNumRoads() > 0);
		assertTrue(gameModelAfter.getNetMap().getNetRoads().get(0).getNetEdgeLocation().getX() == 0);
		assertTrue(gameModelAfter.getNetMap().getNetRoads().get(0).getNetEdgeLocation().getY() == 2);

		
		//  TODO:  should expand this if I have time
	}
	
	
//	@Test
//	public void soldierCard() throws Exception
//	{
//		String username = UUID.randomUUID().toString();
//		String password = UUID.randomUUID().toString();
//		String game = UUID.randomUUID().toString();
//		CatanColor col = CatanColor.BLUE;
//		
//		//set up the game
////		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
//		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);
//
//		testProxy.registerUser(username, password);
//		testProxy.loginUser(username, password);
//		testProxy.createGame(true, true, true, game);
//		List<NetGame> gList = testProxy.listGames();
//		
//		//get the number of the game to join
//		NetGame targetGame = null;
//		for(NetGame g : gList){
//			if(g.getTitle().equals(game)){
//				targetGame = g;
//			}
//		}
//		
//		//join target game
//		testProxy.addAI(AIType.LARGEST_ARMY);
//
//		testProxy.joinGame(targetGame.getId(), col);
//		NetGameModel gameModelBefore = testProxy.getGameModel();
//		NetGameModel gameModelAfter = testProxy.soldierCard(1, new HexLocation(1,1));
//
//		testProxy.addAI(AIType.LARGEST_ARMY);
//		testProxy.addAI(AIType.LARGEST_ARMY);
//		testProxy.addAI(AIType.LARGEST_ARMY);
//		
//		
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//		testProxy.buyDevCard();
//
//		
//		NetGameModel gameModelAfter2 = testProxy.soldierCard(2, new HexLocation(1,1));
//
//		
//		assertTrue(gameModelAfter.getNetMap().getRobberLocation().getX() != gameModelAfter.getNetMap().getRobberLocation().getX() ||
//				gameModelAfter.getNetMap().getRobberLocation().getY() != gameModelAfter.getNetMap().getRobberLocation().getY() );
//		assertTrue(gameModelAfter.getNetMap().getRobberLocation().getX() == 2);
//		assertTrue(gameModelAfter.getNetMap().getRobberLocation().getY() == 2);
//		
//		//  no exceptions is great
//	}
	
	@Test
	public void monopolyCard() throws Exception

	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);

		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}


		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.addAI(AIType.LARGEST_ARMY);
		testProxy.addAI(AIType.LARGEST_ARMY);
		testProxy.addAI(AIType.LARGEST_ARMY);


		NetGameModel gameModelBefore = testProxy.getGameModel();
		NetGameModel gameModelAfter = testProxy.monopolyCard(ResourceType.BRICK);
		//  TODO:  this will only be effective if we can add other players
	}
	
	@Test
	public void monumentCard() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		NetGameModel gameModelBefore = testProxy.getGameModel();
		NetGameModel gameModelAfter = testProxy.monumentCard();
		
		assertTrue(gameModelAfter.getNetPlayers().get(0).getNumVictoryPoints() > 0);
		
	}
	
	@Test
	public void discardCards() throws Exception
	{
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		String game = UUID.randomUUID().toString();
		CatanColor col = CatanColor.BLUE;
		
		//set up the game
//		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		RealServerProxy testProxy = new RealServerProxy("127.0.0.1", 8081);

		testProxy.registerUser(username, password);
		testProxy.loginUser(username, password);
		testProxy.createGame(true, true, true, game);
		List<NetGame> gList = testProxy.listGames();
		
		//get the number of the game to join
		NetGame targetGame = null;
		for(NetGame g : gList){
			if(g.getTitle().equals(game)){
				targetGame = g;
			}
		}
		
		//join target game
		testProxy.joinGame(targetGame.getId(), col);
		testProxy.getGameModel();
		testProxy.yearOfPlentyCard(ResourceType.BRICK, ResourceType.ORE);
		testProxy.yearOfPlentyCard(ResourceType.BRICK, ResourceType.SHEEP);
		testProxy.yearOfPlentyCard(ResourceType.WHEAT, ResourceType.ORE);
		testProxy.yearOfPlentyCard(ResourceType.WHEAT, ResourceType.SHEEP);
		testProxy.yearOfPlentyCard(ResourceType.WHEAT, ResourceType.SHEEP);
		testProxy.yearOfPlentyCard(ResourceType.ORE, ResourceType.ORE);
		testProxy.yearOfPlentyCard(ResourceType.ORE, ResourceType.ORE);
		NetGameModel gameModelBefore = testProxy.getGameModel();

		
		
		List<Integer> cardsToDiscard = new ArrayList<Integer>();
		cardsToDiscard.add(1);
		cardsToDiscard.add(1);
		cardsToDiscard.add(1);
		cardsToDiscard.add(2);
		cardsToDiscard.add(1);

		
		
		//  TODO:  we need to set the client to be in "discarding mode" before we can test this, doesn't seem 
		//  TODO:  possible right now
		
		
		
//		NetGameModel gameModelAfter = testProxy.discardCards(cardsToDiscard);
//		
//		assertTrue(gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumBrick() == gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumBrick() );
//		assertTrue(gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumOre() == gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumOre());
//		assertTrue(gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumSheep() == gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumSheep() );
//		assertTrue(gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumWheat() == gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumWheat() );
//		assertTrue(gameModelAfter.getNetPlayers().get(0).getNetResourceList().getNumWood() == gameModelBefore.getNetPlayers().get(0).getNetResourceList().getNumWood() );

	}

	
}
