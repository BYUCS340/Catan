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
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.networking.UserCookie;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;

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

	@Test
	public void testRegisterUser() throws Exception
	{
		String username1 = UUID.randomUUID().toString();
		String password1 = UUID.randomUUID().toString();
		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
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
		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
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
		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
		
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
		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
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
		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
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
		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
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
		RealServerProxy testProxy1 = new RealServerProxy("104.236.179.174", 8081);
		testProxy1.registerUser(username1, password1);
		testProxy1.loginUser(username1, password1);
		testProxy1.createGame(true, true, true, game);
		List<NetGame> gList = testProxy1.listGames();
		
		//sign second user in
		RealServerProxy testProxy2 = new RealServerProxy("104.236.179.174", 8081);
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
		RealServerProxy testProxy1 = new RealServerProxy("104.236.179.174", 8081);
		testProxy1.registerUser(username1, password1);
		testProxy1.loginUser(username1, password1);
		testProxy1.createGame(true, true, true, game);
		List<NetGame> gList = testProxy1.listGames();
		
		//sign second user in
		RealServerProxy testProxy2 = new RealServerProxy("104.236.179.174", 8081);
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
		RealServerProxy testProxy = new RealServerProxy("104.236.179.174", 8081);
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
}
