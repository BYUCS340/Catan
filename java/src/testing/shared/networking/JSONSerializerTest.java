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
import shared.networking.JSONSerializer;
import shared.networking.Serializer;
import shared.networking.transport.NetDevCardList;
import shared.networking.transport.NetGame;
import shared.networking.transport.NetGameModel;
import shared.networking.transport.NetHex;
import shared.networking.transport.NetMap;
import shared.networking.transport.NetPlayer;
import shared.networking.transport.NetPort;

public class JSONSerializerTest
{
	String gameModelReturnedByServer = "{\n  \"deck\": {\n    \"yearOfPlenty\": 2,\n    \"monopoly\": 2,\n    \"soldier\": 14,\n    \"roadBuilding\": 2,\n    \"monument\": 5\n  },\n  \"map\": {\n    \"hexes\": [\n      {\n        \"resource\": \"wood\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": -2\n        },\n        \"number\": 3\n      },\n      {\n        \"resource\": \"sheep\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": -2\n        },\n        \"number\": 10\n      },\n      {\n        \"location\": {\n          \"x\": 2,\n          \"y\": -2\n        }\n      },\n      {\n        \"resource\": \"wood\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": -1\n        },\n        \"number\": 4\n      },\n      {\n        \"resource\": \"brick\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": -1\n        },\n        \"number\": 4\n      },\n      {\n        \"resource\": \"ore\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": -1\n        },\n        \"number\": 9\n      },\n      {\n        \"resource\": \"wheat\",\n        \"location\": {\n          \"x\": 2,\n          \"y\": -1\n        },\n        \"number\": 6\n      },\n      {\n        \"resource\": \"wheat\",\n        \"location\": {\n          \"x\": -2,\n          \"y\": 0\n        },\n        \"number\": 2\n      },\n      {\n        \"resource\": \"ore\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": 0\n        },\n        \"number\": 3\n      },\n      {\n        \"resource\": \"wheat\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": 0\n        },\n        \"number\": 8\n      },\n      {\n        \"resource\": \"brick\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": 0\n        },\n        \"number\": 5\n      },\n      {\n        \"resource\": \"wood\",\n        \"location\": {\n          \"x\": 2,\n          \"y\": 0\n        },\n        \"number\": 6\n      },\n      {\n        \"resource\": \"sheep\",\n        \"location\": {\n          \"x\": -2,\n          \"y\": 1\n        },\n        \"number\": 9\n      },\n      {\n        \"resource\": \"sheep\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": 1\n        },\n        \"number\": 12\n      },\n      {\n        \"resource\": \"wood\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": 1\n        },\n        \"number\": 11\n      },\n      {\n        \"resource\": \"sheep\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": 1\n        },\n        \"number\": 10\n      },\n      {\n        \"resource\": \"ore\",\n        \"location\": {\n          \"x\": -2,\n          \"y\": 2\n        },\n        \"number\": 5\n      },\n      {\n        \"resource\": \"wheat\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": 2\n        },\n        \"number\": 11\n      },\n      {\n        \"resource\": \"brick\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": 2\n        },\n        \"number\": 8\n      }\n    ],\n    \"roads\": [],\n    \"cities\": [],\n    \"settlements\": [],\n    \"radius\": 3,\n    \"ports\": [\n      {\n        \"ratio\": 3,\n        \"direction\": \"NE\",\n        \"location\": {\n          \"x\": -2,\n          \"y\": 3\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"brick\",\n        \"direction\": \"NW\",\n        \"location\": {\n          \"x\": 2,\n          \"y\": 1\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"ore\",\n        \"direction\": \"SW\",\n        \"location\": {\n          \"x\": 3,\n          \"y\": -3\n        }\n      },\n      {\n        \"ratio\": 3,\n        \"direction\": \"NW\",\n        \"location\": {\n          \"x\": 3,\n          \"y\": -1\n        }\n      },\n      {\n        \"ratio\": 3,\n        \"direction\": \"NE\",\n        \"location\": {\n          \"x\": -3,\n          \"y\": 2\n        }\n      },\n      {\n        \"ratio\": 3,\n        \"direction\": \"SE\",\n        \"location\": {\n          \"x\": -3,\n          \"y\": 0\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"sheep\",\n        \"direction\": \"S\",\n        \"location\": {\n          \"x\": 1,\n          \"y\": -3\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"wood\",\n        \"direction\": \"N\",\n        \"location\": {\n          \"x\": 0,\n          \"y\": 3\n        }\n      },\n      {\n        \"ratio\": 2,\n        \"resource\": \"wheat\",\n        \"direction\": \"S\",\n        \"location\": {\n          \"x\": -1,\n          \"y\": -2\n        }\n      }\n    ],\n    \"robber\": {\n      \"x\": 2,\n      \"y\": -2\n    }\n  },\n  \"players\": [\n    {\n      \"resources\": {\n        \"brick\": 0,\n        \"wood\": 0,\n        \"sheep\": 0,\n        \"wheat\": 0,\n        \"ore\": 0\n      },\n      \"oldDevCards\": {\n        \"yearOfPlenty\": 0,\n        \"monopoly\": 0,\n        \"soldier\": 0,\n        \"roadBuilding\": 0,\n        \"monument\": 0\n      },\n      \"newDevCards\": {\n        \"yearOfPlenty\": 0,\n        \"monopoly\": 0,\n        \"soldier\": 0,\n        \"roadBuilding\": 0,\n        \"monument\": 0\n      },\n      \"roads\": 15,\n      \"cities\": 4,\n      \"settlements\": 5,\n      \"soldiers\": 0,\n      \"victoryPoints\": 0,\n      \"monuments\": 0,\n      \"playedDevCard\": false,\n      \"discarded\": false,\n      \"playerID\": 12,\n      \"playerIndex\": 0,\n      \"name\": \"larry\",\n      \"color\": \"yellow\"\n    },\n    null,\n    null,\n    null\n  ],\n  \"log\": {\n    \"lines\": []\n  },\n  \"chat\": {\n    \"lines\": []\n  },\n  \"bank\": {\n    \"brick\": 24,\n    \"wood\": 24,\n    \"sheep\": 24,\n    \"wheat\": 24,\n    \"ore\": 24\n  },\n  \"turnTracker\": {\n    \"status\": \"FirstRound\",\n    \"currentTurn\": 0,\n    \"longestRoad\": -1,\n    \"largestArmy\": -1\n  },\n  \"winner\": -1,\n  \"version\": 0\n}";
	String gameListReturnedByServer = "[\n  {\n    \"title\": \"Default Game\",\n    \"id\": 0,\n    \"players\": [\n      {\n        \"color\": \"orange\",\n        \"name\": \"Sam\",\n        \"id\": 0\n      },\n      {\n        \"color\": \"blue\",\n        \"name\": \"Brooke\",\n        \"id\": 1\n      },\n      {\n        \"color\": \"red\",\n        \"name\": \"Pete\",\n        \"id\": 10\n      },\n      {\n        \"color\": \"green\",\n        \"name\": \"Mark\",\n        \"id\": 11\n      }\n    ]\n  },\n  {\n    \"title\": \"AI Game\",\n    \"id\": 1,\n    \"players\": [\n      {\n        \"color\": \"orange\",\n        \"name\": \"Pete\",\n        \"id\": 10\n      },\n      {\n        \"color\": \"puce\",\n        \"name\": \"Miguel\",\n        \"id\": -2\n      },\n      {\n        \"color\": \"blue\",\n        \"name\": \"Quinn\",\n        \"id\": -3\n      },\n      {\n        \"color\": \"purple\",\n        \"name\": \"Squall\",\n        \"id\": -4\n      }\n    ]\n  },\n  {\n    \"title\": \"Empty Game\",\n    \"id\": 2,\n    \"players\": [\n      {\n        \"color\": \"orange\",\n        \"name\": \"Sam\",\n        \"id\": 0\n      },\n      {\n        \"color\": \"blue\",\n        \"name\": \"Brooke\",\n        \"id\": 1\n      },\n      {\n        \"color\": \"red\",\n        \"name\": \"Pete\",\n        \"id\": 10\n      },\n      {\n        \"color\": \"green\",\n        \"name\": \"Mark\",\n        \"id\": 11\n      }\n    ]\n  }\n]";
	
	private NetGameModel originalNetGameModel;
	private List<NetGame> originalGameList;
	
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

		originalNetGameModel = testDS.parseNetGameModel(gameModelReturnedByServer);
		originalGameList = testDS.parseNetGameList(gameListReturnedByServer);

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
	public void testSJoinGameRez() throws Exception
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
		String results = testS.sAddAIReq(AIType.LARGEST_ARMY);
		
		JSONObject obj = new JSONObject(results);
		assertTrue(obj.getString("AIType").equalsIgnoreCase("largest_army"));
	}

}
