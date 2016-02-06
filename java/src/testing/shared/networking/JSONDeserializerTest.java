package testing.shared.networking;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.networking.Deserializer;
import shared.networking.JSONDeserializer;
import shared.networking.transport.NetDevCardList;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetHex;
import shared.networking.transport.NetMap;
import shared.networking.transport.NetPlayer;
import shared.networking.transport.NetPort;

public class JSONDeserializerTest
{
	String gameModelReturnedByServer = "{\n  \"deck\": {\n    \"yearOfPlenty\": 2,\n    \"monopoly\": 2,\n    \"soldier\": 14,\n    \"roadBuilding\": 2,\n    \"monument\": 5\n  },\n  \"map\": {\n    \"hexes\": [\n      {\n        \"resource\": \"wood\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": -2\n        },\n        \"number\": 3\n      },\n      {\n        \"resource\": \"sheep\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": -2\n        },\n        \"number\": 10\n      },\n      {\n        \"location\": {\n          \"x\": 2,\n          \"y\": -2\n        }\n      },\n      {\n        \"resource\": \"wood\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": -1\n        },\n        \"number\": 4\n      },\n      {\n        \"resource\": \"brick\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": -1\n        },\n        \"number\": 4\n      },\n      {\n        \"resource\": \"ore\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": -1\n        },\n        \"number\": 9\n      },\n      {\n        \"resource\": \"wheat\",\n        \"location\": {\n          \"x\": 2,\n          \"y\": -1\n        },\n        \"number\": 6\n      },\n      {\n        \"resource\": \"wheat\",\n        \"location\": {\n          \"x\": -2,\n          \"y\": 0\n        },\n        \"number\": 2\n      },\n      {\n        \"resource\": \"ore\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": 0\n        },\n        \"number\": 3\n      },\n      {\n        \"resource\": \"wheat\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": 0\n        },\n        \"number\": 8\n      },\n      {\n        \"resource\": \"brick\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": 0\n        },\n        \"number\": 5\n      },\n      {\n        \"resource\": \"wood\",\n        \"location\": {\n          \"x\": 2,\n          \"y\": 0\n        },\n        \"number\": 6\n      },\n      {\n        \"resource\": \"sheep\",\n        \"location\": {\n          \"x\": -2,\n          \"y\": 1\n        },\n        \"number\": 9\n      },\n      {\n        \"resource\": \"sheep\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": 1\n        },\n        \"number\": 12\n      },\n      {\n        \"resource\": \"wood\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": 1\n        },\n        \"number\": 11\n      },\n      {\n        \"resource\": \"sheep\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": 1\n        },\n        \"number\": 10\n      },\n      {\n        \"resource\": \"ore\",\n        \"location\": {\n          \"x\": -2,\n          \"y\": 2\n        },\n        \"number\": 5\n      },\n      {\n        \"resource\": \"wheat\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": 2\n        },\n        \"number\": 11\n      },\n      {\n        \"resource\": \"brick\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": 2\n        },\n        \"number\": 8\n      }\n    ],\n    \"roads\": [],\n    \"cities\": [],\n    \"settlements\": [],\n    \"radius\": 3,\n    \"ports\": [\n      {\n        \"ratio\": 3,\n        \"direction\": \"NE\",\n        \"location\": {\n          \"x\": -2,\n          \"y\": 3\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"brick\",\n        \"direction\": \"NW\",\n        \"location\": {\n          \"x\": 2,\n          \"y\": 1\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"ore\",\n        \"direction\": \"SW\",\n        \"location\": {\n          \"x\": 3,\n          \"y\": -3\n        }\n      },\n      {\n        \"ratio\": 3,\n        \"direction\": \"NW\",\n        \"location\": {\n          \"x\": 3,\n          \"y\": -1\n        }\n      },\n      {\n        \"ratio\": 3,\n        \"direction\": \"NE\",\n        \"location\": {\n          \"x\": -3,\n          \"y\": 2\n        }\n      },\n      {\n        \"ratio\": 3,\n        \"direction\": \"SE\",\n        \"location\": {\n          \"x\": -3,\n          \"y\": 0\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"sheep\",\n        \"direction\": \"S\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": -3\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"wood\",\n        \"direction\": \"N\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": 3\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"wheat\",\n        \"direction\": \"S\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": -2\n        }\n      }\n    ],\n    \"robber\": {\n      \"x\": 2,\n      \"y\": -2\n    }\n  },\n  \"players\": [\n    {\n      \"resources\": {\n        \"brick\": 0,\n        \"wood\": 0,\n        \"sheep\": 0,\n        \"wheat\": 0,\n        \"ore\": 0\n      },\n      \"oldDevCards\": {\n        \"yearOfPlenty\": 0,\n        \"monopoly\": 0,\n        \"soldier\": 0,\n        \"roadBuilding\": 0,\n        \"monument\": 0\n      },\n      \"newDevCards\": {\n        \"yearOfPlenty\": 0,\n        \"monopoly\": 0,\n        \"soldier\": 0,\n        \"roadBuilding\": 0,\n        \"monument\": 0\n      },\n      \"roads\": 15,\n      \"cities\": 4,\n      \"settlements\": 5,\n      \"soldiers\": 0,\n      \"victoryPoints\": 0,\n      \"monuments\": 0,\n      \"playedDevCard\": false,\n      \"discarded\": false,\n      \"playerID\": 12,\n      \"playerIndex\": 0,\n      \"name\": \"larry\",\n      \"color\": \"yellow\"\n    },\n    null,\n    null,\n    null\n  ],\n  \"log\": {\n    \"lines\": []\n  },\n  \"chat\": {\n    \"lines\": []\n  },\n  \"bank\": {\n    \"brick\": 24,\n    \"wood\": 24,\n    \"sheep\": 24,\n    \"wheat\": 24,\n    \"ore\": 24\n  },\n  \"turnTracker\": {\n    \"status\": \"FirstRound\",\n    \"currentTurn\": 0,\n    \"longestRoad\": -1,\n    \"largestArmy\": -1\n  },\n  \"winner\": -1,\n  \"version\": 0\n}";
	String gameListReturnedByServer = "[\n  {\n    \"title\": \"Default Game\",\n    \"id\": 0,\n    \"players\": [\n      {\n        \"color\": \"orange\",\n        \"name\": \"Sam\",\n        \"id\": 0\n      },\n      {\n        \"color\": \"blue\",\n        \"name\": \"Brooke\",\n        \"id\": 1\n      },\n      {\n        \"color\": \"red\",\n        \"name\": \"Pete\",\n        \"id\": 10\n      },\n      {\n        \"color\": \"green\",\n        \"name\": \"Mark\",\n        \"id\": 11\n      }\n    ]\n  },\n  {\n    \"title\": \"AI Game\",\n    \"id\": 1,\n    \"players\": [\n      {\n        \"color\": \"orange\",\n        \"name\": \"Pete\",\n        \"id\": 10\n      },\n      {\n        \"color\": \"puce\",\n        \"name\": \"Miguel\",\n        \"id\": -2\n      },\n      {\n        \"color\": \"blue\",\n        \"name\": \"Quinn\",\n        \"id\": -3\n      },\n      {\n        \"color\": \"purple\",\n        \"name\": \"Squall\",\n        \"id\": -4\n      }\n    ]\n  },\n  {\n    \"title\": \"Empty Game\",\n    \"id\": 2,\n    \"players\": [\n      {\n        \"color\": \"orange\",\n        \"name\": \"Sam\",\n        \"id\": 0\n      },\n      {\n        \"color\": \"blue\",\n        \"name\": \"Brooke\",\n        \"id\": 1\n      },\n      {\n        \"color\": \"red\",\n        \"name\": \"Pete\",\n        \"id\": 10\n      },\n      {\n        \"color\": \"green\",\n        \"name\": \"Mark\",\n        \"id\": 11\n      }\n    ]\n  }\n]";
	
	
	
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
	public void testPartialParseNetPlayer() throws Exception
	{
		Deserializer testDS = new JSONDeserializer(); 
		JSONObject testObj = new JSONObject();
		testObj.put("color", "blue");
		testObj.put("name", "Brooke");
		testObj.put("id", "1");
		
		NetPlayer resPlayer = testDS.parsePartialNetPlayer(testObj.toString());
		
		//make sure all the information that was put in is present
		assertTrue(resPlayer.getColor() == CatanColor.BLUE);
		assertTrue(resPlayer.getName().equals("Brooke"));
		assertTrue(resPlayer.getPlayerID() == 1);
	}
	
	@Test
	public void testNetGame() throws Exception
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
	
	@Test
	public void testNetGameModel() throws Exception
	{
		Deserializer testDS = new JSONDeserializer(); 
		JSONObject testObj = new JSONObject(gameModelReturnedByServer);
		NetGameModel testNGM = testDS.parseNetGameModel(gameModelReturnedByServer);
		
		NetDevCardList testDeck = testNGM.getNetDeck();
		assertTrue(testObj.getJSONObject("deck").getInt("yearOfPlenty")==testDeck.getNumYearOfPlenty());
		assertTrue(testObj.getJSONObject("deck").getInt("monopoly")==testDeck.getNumMonopoly());
		assertTrue(testObj.getJSONObject("deck").getInt("soldier")==testDeck.getNumSoldier());
		assertTrue(testObj.getJSONObject("deck").getInt("roadBuilding")==testDeck.getNumRoadBuilding());
		assertTrue(testObj.getJSONObject("deck").getInt("monument")==testDeck.getNumMonument());
		
		NetMap testMap = testNGM.getNetMap();
		JSONObject jsonMap = testObj.getJSONObject("map");
		JSONArray jsonNetHexArr = jsonMap.getJSONArray("hexes");
		List<NetHex> testHexes = testMap.getNetHexes();
		//  just assume if the first and last are correct the rest probably are as well
		assertTrue(jsonNetHexArr.getJSONObject(0).getJSONObject("location").getInt("x") == testHexes.get(0).getNetHexLocation().getX());
		assertTrue(jsonNetHexArr.getJSONObject(0).getJSONObject("location").getInt("y") == testHexes.get(0).getNetHexLocation().getY());
		assertTrue(jsonNetHexArr.getJSONObject(0).getString("resource").equalsIgnoreCase(testHexes.get(0).getResourceType().toString()));
		assertTrue(jsonNetHexArr.getJSONObject(0).getInt("number") == testHexes.get(0).getNumberChit());
		
		assertTrue(jsonNetHexArr.getJSONObject(testHexes.size() - 1).getJSONObject("location").getInt("x") == testHexes.get(testHexes.size() - 1).getNetHexLocation().getX());
		assertTrue(jsonNetHexArr.getJSONObject(testHexes.size() - 1).getJSONObject("location").getInt("y") == testHexes.get(testHexes.size() - 1).getNetHexLocation().getY());
		assertTrue(jsonNetHexArr.getJSONObject(testHexes.size() - 1).getString("resource").equalsIgnoreCase(testHexes.get(testHexes.size() - 1).getResourceType().toString()));
		assertTrue(jsonNetHexArr.getJSONObject(testHexes.size() - 1).getInt("number") == testHexes.get(testHexes.size() - 1).getNumberChit());

		//  test roads
		assertTrue(testMap.getNetRoads().size() == jsonMap.getJSONArray("roads").length());
//		if(testMap.getNetRoads().size() > 0){
//			assertTrue(testMap.getNetRoads().get(0).getOwnerID() == testObj.getJSONArray("roads").getJSONObject(0).getInt("id"));
//			assertTrue(testMap.getNetRoads().get(0).getNetEdgeLocation().getX() == testObj.getJSONArray("roads").getJSONObject(0).getJSONObject("edgeLocation").getInt("x"));
//			assertTrue(testMap.getNetRoads().get(0).getNetEdgeLocation().getY() == testObj.getJSONArray("roads").getJSONObject(0).getJSONObject("edgeLocation").getInt("y"));
//			assertTrue(testMap.getNetRoads().get(0).getNetEdgeLocation().getDirection() == testObj.getJSONArray("roads").getJSONObject(0).getJSONObject("edgeLocation").getString("direction"));
//		}
		
		//  test cities
		assertTrue(testMap.getNetCities().size() == jsonMap.getJSONArray("cities").length());
		
		//  test settlements
		assertTrue(testMap.getNetSettlements().size() == jsonMap.getJSONArray("settlements").length());

		//  test radius
		assertTrue(testMap.getRadius() == jsonMap.getInt("radius"));

		//  test ports
		List<NetPort> netPorts = testMap.getNetPorts();
		JSONArray jsonNetPortArray = jsonMap.getJSONArray("ports");
		
		//  just assume if the first and last are correct the rest probably are as well
		assertTrue(jsonNetPortArray.getJSONObject(0).getJSONObject("location").getInt("x") == netPorts.get(0).getNetHexLocation().getX());
		assertTrue(jsonNetPortArray.getJSONObject(0).getJSONObject("location").getInt("y") == netPorts.get(0).getNetHexLocation().getY());
		if(jsonNetPortArray.getJSONObject(0).has("resource"))
		{
			assertTrue(jsonNetPortArray.getJSONObject(0).getString("resource").equalsIgnoreCase(netPorts.get(0).getResource().toString()));
		}
		assertTrue(jsonNetPortArray.getJSONObject(0).getInt("ratio") == netPorts.get(0).getRatio());
		assertTrue(jsonNetPortArray.getJSONObject(0).getString("direction").equalsIgnoreCase(netPorts.get(0).getDirection().toString()));
		
		//  just assume if the first and last are correct the rest probably are as well
		assertTrue(jsonNetPortArray.getJSONObject(netPorts.size() - 1).getJSONObject("location").getInt("x") == netPorts.get(netPorts.size() - 1).getNetHexLocation().getX());
		assertTrue(jsonNetPortArray.getJSONObject(netPorts.size() - 1).getJSONObject("location").getInt("y") == netPorts.get(netPorts.size() - 1).getNetHexLocation().getY());
		if(jsonNetPortArray.getJSONObject(netPorts.size() - 1).has("resource"))
		{
			assertTrue(jsonNetPortArray.getJSONObject(netPorts.size() - 1).getString("resource").equalsIgnoreCase(netPorts.get(netPorts.size() - 1).getResource().toString()));
		}
		assertTrue(jsonNetPortArray.getJSONObject(netPorts.size() - 1).getInt("ratio") == netPorts.get(netPorts.size() - 1).getRatio());
		assertTrue(jsonNetPortArray.getJSONObject(netPorts.size() - 1).getString("direction").equalsIgnoreCase(netPorts.get(netPorts.size() - 1).getDirection().toString()));

		
		
		

	}
	
	
	@Test
	public void testParseNetGameList() throws Exception
	{
		Deserializer testDS = new JSONDeserializer(); 
		JSONArray testObj = new JSONArray(gameListReturnedByServer);
		List<NetGame>gameList = testDS.parseNetGameList(gameListReturnedByServer);
		
		//  testing the first game is sufficient I think
		NetGame testGame = gameList.get(0);
		JSONObject testGameJSON = testObj.getJSONObject(0);
		
		assertTrue(testGame.getTitle().equalsIgnoreCase(testGameJSON.getString("title")));
		assertTrue(testGame.getId() == testGameJSON.getInt("id"));
		assertTrue(testGame.getNetPlayers().size() == testGameJSON.getJSONArray("players").length());
		assertTrue(testGame.getNetPlayers().get(3).getPlayerID() ==  testGameJSON.getJSONArray("players").getJSONObject(3).getInt("id"));
		assertTrue(testGame.getNetPlayers().get(3).getName().equalsIgnoreCase(testGameJSON.getJSONArray("players").getJSONObject(3).getString("name")));
		assertTrue(testGame.getNetPlayers().get(3).getColor().toString().equalsIgnoreCase(testGameJSON.getJSONArray("players").getJSONObject(3).getString("color")));
		
		assertTrue(testGame.getNetPlayers().get(0).getPlayerID() ==  testGameJSON.getJSONArray("players").getJSONObject(0).getInt("id"));
		assertTrue(testGame.getNetPlayers().get(0).getName().equalsIgnoreCase(testGameJSON.getJSONArray("players").getJSONObject(0).getString("name")));
		assertTrue(testGame.getNetPlayers().get(0).getColor().toString().equalsIgnoreCase(testGameJSON.getJSONArray("players").getJSONObject(0).getString("color")));
		
		assertTrue(testGame.getNetPlayers().get(1).getPlayerID() ==  testGameJSON.getJSONArray("players").getJSONObject(1).getInt("id"));
		assertTrue(testGame.getNetPlayers().get(1).getName().equalsIgnoreCase(testGameJSON.getJSONArray("players").getJSONObject(1).getString("name")));
		assertTrue(testGame.getNetPlayers().get(1).getColor().toString().equalsIgnoreCase(testGameJSON.getJSONArray("players").getJSONObject(1).getString("color")));
		
		assertTrue(testGame.getNetPlayers().get(2).getPlayerID() ==  testGameJSON.getJSONArray("players").getJSONObject(2).getInt("id"));
		assertTrue(testGame.getNetPlayers().get(2).getName().equalsIgnoreCase(testGameJSON.getJSONArray("players").getJSONObject(2).getString("name")));
		assertTrue(testGame.getNetPlayers().get(2).getColor().toString().equalsIgnoreCase(testGameJSON.getJSONArray("players").getJSONObject(2).getString("color")));


	}
	
	
	@Test
	public void testParseNetPlayer() throws Exception
	{
		Deserializer testDS = new JSONDeserializer(); 
		JSONObject testObj = new JSONObject(gameModelReturnedByServer);
		NetGameModel testNGM = testDS.parseNetGameModel(gameModelReturnedByServer);
		
		
		JSONArray testPlayers = testObj.getJSONArray("players");
		if(testPlayers.length() > 0){
			//  assert the first player is the same and the sizes are the same
			int testPlayerLength = 0;
			for(int i = 0; i < testPlayers.length(); i++){
				if(!testPlayers.isNull(i)){
					testPlayerLength++;
				}
			}
			assertTrue(testPlayerLength == testNGM.getNetPlayers().size());
			
			JSONObject testPlayerJSON = testPlayers.getJSONObject(0);
			NetPlayer testPlayer = testNGM.getNetPlayers().get(0);
			
			//  check resources
			JSONObject resourcesJSON = testPlayerJSON.getJSONObject("resources");
			assertTrue(resourcesJSON.getInt("brick") == testPlayer.getNetResourceList().getNumBrick());
			assertTrue(resourcesJSON.getInt("wood") == testPlayer.getNetResourceList().getNumWood());
			assertTrue(resourcesJSON.getInt("sheep") == testPlayer.getNetResourceList().getNumSheep());
			assertTrue(resourcesJSON.getInt("wheat") == testPlayer.getNetResourceList().getNumWheat());
			assertTrue(resourcesJSON.getInt("ore") == testPlayer.getNetResourceList().getNumOre());
			
			//  check old dev cards
			JSONObject oldDevCardsJSON = testPlayerJSON.getJSONObject("oldDevCards");
			NetDevCardList testOldDeck = testPlayer.getOldNetDevCardList();
			assertTrue(oldDevCardsJSON.getInt("yearOfPlenty")==testOldDeck.getNumYearOfPlenty());
			assertTrue(oldDevCardsJSON.getInt("monopoly")==testOldDeck.getNumMonopoly());
			assertTrue(oldDevCardsJSON.getInt("soldier")==testOldDeck.getNumSoldier());
			assertTrue(oldDevCardsJSON.getInt("roadBuilding")==testOldDeck.getNumRoadBuilding());
			assertTrue(oldDevCardsJSON.getInt("monument")==testOldDeck.getNumMonument());
			
			
			//  check new dev cards
			JSONObject newDevCardsJSON = testPlayerJSON.getJSONObject("newDevCards");
			NetDevCardList testnewDeck = testPlayer.getNewNetDevCardList();
			assertTrue(newDevCardsJSON.getInt("yearOfPlenty")==testnewDeck.getNumYearOfPlenty());
			assertTrue(newDevCardsJSON.getInt("monopoly")==testnewDeck.getNumMonopoly());
			assertTrue(newDevCardsJSON.getInt("soldier")==testnewDeck.getNumSoldier());
			assertTrue(newDevCardsJSON.getInt("roadBuilding")==testnewDeck.getNumRoadBuilding());
			assertTrue(newDevCardsJSON.getInt("monument")==testnewDeck.getNumMonument());
			
			
			assertTrue(testPlayer.getNumRoads() == testPlayerJSON.getInt("roads"));
			assertTrue(testPlayer.getNumCities() == testPlayerJSON.getInt("cities"));
			assertTrue(testPlayer.getNumSettlements() == testPlayerJSON.getInt("settlements"));
			assertTrue(testPlayer.getNumSoldiers() == testPlayerJSON.getInt("soldiers"));
			assertTrue(testPlayer.getNumVictoryPoints() == testPlayerJSON.getInt("victoryPoints"));
			assertTrue(testPlayer.getNumMonuments() == testPlayerJSON.getInt("monuments"));
			assertTrue(testPlayer.hasPlayedDevCard() == testPlayerJSON.getBoolean("playedDevCard"));
			assertTrue(testPlayer.isHasDiscarded() == testPlayerJSON.getBoolean("discarded"));
			assertTrue(testPlayer.getPlayerID() == testPlayerJSON.getInt("playerID"));
			assertTrue(testPlayer.getPlayerIndex() == testPlayerJSON.getInt("playerIndex"));
			assertTrue(testPlayer.getName().equalsIgnoreCase(testPlayerJSON.getString("name")));
			assertTrue(testPlayer.getColor().toString().equalsIgnoreCase(testPlayerJSON.getString("color")));


		}
	}

}
