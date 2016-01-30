package testing.shared.networking;

import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.definitions.CatanColor;
import shared.networking.Deserializer;
import shared.networking.JSONDeserializer;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetPlayer;

public class JSONDeserializerTest
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
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testPartialParseNetPlayer()
	{
		Deserializer testDS = new JSONDeserializer(); 
		JSONObject testObj = new JSONObject();
		testObj.put("color", "blue");
		testObj.put("name", "Brooke");
		testObj.put("id", "1");
		
		NetPlayer resPlayer = testDS.parseNetPlayer(testObj.toString());
		
		//make sure all the information that was put in is present
		assertTrue(resPlayer.getColor() == CatanColor.BLUE);
		assertTrue(resPlayer.getName().equals("Brooke"));
		assertTrue(resPlayer.getPlayerID() == 1);
	}
	
	@Test
	public void testNetGame()
	{
		Deserializer testDS = new JSONDeserializer(); 
		JSONObject testGame = new JSONObject();
		
		//populate the object
		testGame.put("title", "Default Game");
		testGame.put("id", "0");
		
		JSONArray playerArray = new JSONArray();
		
		JSONObject tempPlayer0 = new JSONObject();
		tempPlayer0.put("color", "orange");
		tempPlayer0.put("name", "Sam");
		tempPlayer0.put("id", "0");
		playerArray.put(tempPlayer0);
		
		JSONObject tempPlayer1 = new JSONObject();		
		tempPlayer1.put("color", "blue");
		tempPlayer1.put("name", "Brooke");
		tempPlayer1.put("id", "1");
		playerArray.put(tempPlayer1);
		
		JSONObject tempPlayer2 = new JSONObject();		
		tempPlayer2.put("color", "red");
		tempPlayer2.put("name", "Pete");
		tempPlayer2.put("id", "10");
		playerArray.put(tempPlayer2);
		
		
		JSONObject tempPlayer3 = new JSONObject();		
		tempPlayer3.put("color", "green");
		tempPlayer3.put("name", "Mark");
		tempPlayer3.put("id", "11");
		playerArray.put(tempPlayer3);
		
		testGame.put("players", playerArray);
		
		//call the method
		NetGame testGameRes = testDS.parseNetGame(testGame.toString());
		
		
		//test data fields
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
		
		
		
		
	}

}
