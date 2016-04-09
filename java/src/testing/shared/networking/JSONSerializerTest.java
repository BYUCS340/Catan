package testing.shared.networking;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.networking.Deserializer;
import shared.networking.JSONDeserializer;
import shared.networking.JSONSerializer;
import shared.networking.Serializer;

public class JSONSerializerTest
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
		Deserializer testDS = new JSONDeserializer(); 

		testDS.parseNetGameModel(gameModelReturnedByServer);
		testDS.parseNetGameList(gameListReturnedByServer);

	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testSCredentials() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sCredentials("Larry", "larry");
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getString("username").equals("Larry"));
		assertTrue(obj.getString("password").equals("larry"));

	}
	
	@Test
	public void testCreateGameReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sCreateGameReq(true, false, true, "gamey time");
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getBoolean("randomTiles") == true);
		assertTrue(obj.getBoolean("randomNumbers") == false);
		assertTrue(obj.getBoolean("randomPorts") == true);
		assertTrue(obj.getString("name").equals("gamey time"));

	}
	
	@Test
	public void testSJoinGameReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sJoinGameReq(7, CatanColor.YELLOW);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("id") == 7);
		assertTrue(obj.getString("color").equalsIgnoreCase("YELLOW"));
	}
	
	@Test
	public void testSAddAIReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sAddAIReq(AIType.BEGINNER);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getString("AIType").equalsIgnoreCase("beginner"));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void sSendChatReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sSendChatReq(7, "larry");
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 7);
		assertTrue(obj.getString("content").equals("larry"));
		assertTrue(obj.getString("type").equals("sendChat"));
	}

	@Test
	public void sRollNumberReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sRollNumberReq(6, 7);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 6);
		assertTrue(obj.getInt("number") == 7);
		assertTrue(obj.getString("type").equals("rollNumber"));
	}

	@Test
	public void sRobPlayerReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sRobPlayerReq(6, 7, new HexLocation(1,2));
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 6);
		assertTrue(obj.getInt("victimIndex") == 7);
		assertTrue(obj.getJSONObject("location").getInt("x") == 1);
		assertTrue(obj.getJSONObject("location").getInt("y") == 2);
		assertTrue(obj.getString("type").equals("robPlayer"));

	}
	
	
	
	
	
	

	@Test
	public void sFinishTurnReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sFinishTurnReq(4);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 4);
		assertTrue(obj.getString("type").equals("finishTurn"));
	}

	
	
	
	
	
	
	@Test
	public void sBuyDevCardReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sBuyDevCardReq(5);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 5);
		assertTrue(obj.getString("type").equals("buyDevCard"));
	}

	@Test
	public void sYearOfPlentyCardReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sYearOfPlentyCardReq(6, ResourceType.BRICK, ResourceType.WHEAT);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 6);
		assertTrue(obj.getString("resource1").equalsIgnoreCase("BRICK"));
		assertTrue(obj.getString("resource2").equalsIgnoreCase("WHEAT"));
		assertTrue(obj.getString("type").equals("Year_of_Plenty"));
	}

	
	
	
	
	
	@Test
	public void sRoadBuildingCardReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sRoadBuildingCardReq(7, new EdgeLocation(new HexLocation(1,2), EdgeDirection.North),  new EdgeLocation(new HexLocation(2,2), EdgeDirection.North));
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 7);
		assertTrue(obj.getJSONObject("spot1").getInt("x") == 1);
		assertTrue(obj.getJSONObject("spot1").getInt("y") == 2);
		assertTrue(obj.getJSONObject("spot2").getInt("x") == 2);
		assertTrue(obj.getJSONObject("spot2").getInt("y") == 2);
		assertTrue(obj.getString("type").equals("Road_Building"));
	}
	

	@Test
	public void sSoldierCardReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sSoldierCardReq(6, 7, new HexLocation(1,2));
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 6);
		assertTrue(obj.getInt("victimIndex") == 7);
		assertTrue(obj.getJSONObject("location").getInt("x") == 1);
		assertTrue(obj.getJSONObject("location").getInt("y") == 2);
		assertTrue(obj.getString("type").equals("Soldier"));
	}

	@Test
	public void sMonopolyCardReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sMonopolyCardReq(4, ResourceType.BRICK);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 4);
		assertTrue(obj.getString("resource").equalsIgnoreCase("BRICK"));
		assertTrue(obj.getString("type").equals("Monopoly"));
	}

	@Test
	public void sMonumentCardReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sMonumentCardReq(6);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 6);
		assertTrue(obj.getString("type").equals("Monument"));
	}

	@Test
	public void sBuildRoadReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sBuildRoadReq(7, new EdgeLocation(new HexLocation(1,2), EdgeDirection.North), true);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 7);
		assertTrue(obj.getJSONObject("roadLocation").getInt("x") == 1);
		assertTrue(obj.getJSONObject("roadLocation").getInt("y") == 2);
		assertTrue(obj.getString("type").equals("buildRoad"));
		assertTrue(obj.getBoolean("free") == true);

	}

	@Test
	public void sBuildSettlementReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sBuildSettlementReq(7, new VertexLocation(new HexLocation(1,2), VertexDirection.East), true);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 7);
		assertTrue(obj.getBoolean("free") == true);
		assertTrue(obj.getJSONObject("vertexLocation").getInt("x") == 1);
		assertTrue(obj.getJSONObject("vertexLocation").getInt("y") == 2);
		assertTrue(obj.getString("type").equals("buildSettlement"));
	}
	


	@Test
	public void sBuildCityReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sBuildCityReq(1,  new VertexLocation(new HexLocation(1,2), VertexDirection.East));
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 1);
		assertTrue(obj.getJSONObject("vertexLocation").getInt("x") == 1);
		assertTrue(obj.getJSONObject("vertexLocation").getInt("y") == 2);

		assertTrue(obj.getJSONObject("vertexLocation").getString("direction").equalsIgnoreCase("E"));
		assertTrue(obj.getString("type").equals("buildCity"));
	}

	@Test
	public void sOfferTradeReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		List<Integer> resourceList = new ArrayList<Integer>();
		resourceList.add(2);
		resourceList.add(2);
		resourceList.add(2);
		resourceList.add(2);
		resourceList.add(2);
		String results = testS.sOfferTradeReq(7, resourceList, 2);
		
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 7);
		assertTrue(obj.getInt("receiver") == 2);
		assertTrue(obj.getJSONObject("offer").getInt("brick") == 2);
		assertTrue(obj.getJSONObject("offer").getInt("ore") == 2);
		assertTrue(obj.getJSONObject("offer").getInt("sheep") == 2);
		assertTrue(obj.getJSONObject("offer").getInt("wheat") == 2);
		assertTrue(obj.getJSONObject("offer").getInt("wood") == 2);


		assertTrue(obj.getString("type").equals("offerTrade"));
	}
	

	@Test
	public void sAcceptTradeReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sAcceptTradeReq(7, false);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 7);
		assertTrue(obj.getBoolean("willAccept") == false);
		assertTrue(obj.getString("type").equals("acceptTrade"));
	}

	@Test
	public void sMaritimeTradeReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		String results = testS.sMaritimeTradeReq(6,3,ResourceType.BRICK, ResourceType.SHEEP);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 6);
		assertTrue(obj.getInt("ratio") == 3);
		assertTrue(obj.getString("inputResource").equalsIgnoreCase("BRICK"));
		assertTrue(obj.getString("outputResource").equalsIgnoreCase("SHEEP"));
		assertTrue(obj.getString("type").equals("maritimeTrade"));
	}

	
	
	@Test
	public void sDiscardCardsReq() throws Exception
	{
		Serializer testS = new JSONSerializer(); 
		
		List<Integer> resourceList = new ArrayList<Integer>();
		resourceList.add(2);
		resourceList.add(2);
		resourceList.add(2);
		resourceList.add(2);
		resourceList.add(2);
		String results = testS.sDiscardCardsReq(7, resourceList);
		
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getInt("playerIndex") == 7);
		assertTrue(obj.getJSONObject("discardedCards").getInt("brick") == 2);
		assertTrue(obj.getJSONObject("discardedCards").getInt("ore") == 2);
		assertTrue(obj.getJSONObject("discardedCards").getInt("sheep") == 2);
		assertTrue(obj.getJSONObject("discardedCards").getInt("wheat") == 2);
		assertTrue(obj.getJSONObject("discardedCards").getInt("wood") == 2);		
		assertTrue(obj.getString("type").equals("discardCards"));
	}
}
