/**
 * 
 */
package shared.networking;

import java.util.List;

import org.json.JSONObject;

import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;


/**
 * @author pbridd
 *
 */
public class JSONSerializer implements Serializer
{
	
	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sCredentials(java.lang.String, java.lang.String)
	 */
	@Override
	public String sCredentials(String username, String password)
	{
		
		JSONObject obj = new JSONObject();
		obj.put("username", username);
		obj.put("password", password);
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sCreateGameReq(boolean, boolean, boolean, java.lang.String)
	 */
	@Override
	public String sCreateGameReq(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("randomTiles", randomTiles);
		obj.put("randomNumbers", randomNumbers);
		obj.put("randomPorts", randomPorts);
		obj.put("name", name);
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sJoinGameReq(int, java.lang.String)
	 */
	@Override
	public String sJoinGameReq(int id, CatanColor color)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("id", id);
		obj.put("color", CatanColor.toString(color));
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sAddAIReq(java.lang.String)
	 */
	@Override
	public String sAddAIReq(AIType aitype)
	{
		JSONObject obj = new JSONObject();
	
		obj.put("AIType", AIType.toString(aitype));
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sSendChatReq(int, java.lang.String)
	 */
	@Override
	public String sSendChatReq(int playerIndex, String content)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "sendChat");
		obj.put("playerIndex", playerIndex);
		obj.put("content", content);
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sRollNumberReq(int, int)
	 */
	@Override
	public String sRollNumberReq(int playerIndex, int number)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "rollNumber");
		obj.put("playerIndex", playerIndex);
		obj.put("number", number);
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sRobPlayerReq(int, int, shared.locations.HexLocation)
	 */
	@Override
	public String sRobPlayerReq(int playerIndex, int victimIndex, HexLocation location)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "robPlayer");
		obj.put("playerIndex", playerIndex);
		obj.put("victimIndex", victimIndex);
		obj.put("location", oHexLocation(location));
		
		return obj.toString();
	}
	
	private JSONObject oHexLocation(HexLocation location){
		JSONObject obj = new JSONObject();
		
		obj.put("x", location.getX());
		obj.put("y", location.getY());
		
		return obj;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sFinishTurnReq(int)
	 */
	@Override
	public String sFinishTurnReq(int playerIndex)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "finishTurn");
		obj.put("playerIndex", playerIndex);
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sBuyDevCardReq(int)
	 */
	@Override
	public String sBuyDevCardReq(int playerIndex)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "buyDevCard");
		obj.put("playerIndex", playerIndex);
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sYearOfPlentyCardReq(int, shared.definitions.ResourceType, shared.definitions.ResourceType)
	 */
	@Override
	public String sYearOfPlentyCardReq(int playerIndex, ResourceType resource1, ResourceType resource2)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "Year_of_Plenty");
		obj.put("playerIndex", playerIndex);
		obj.put("resource1", ResourceType.toString(resource1));
		obj.put("resource2", ResourceType.toString(resource2));
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sRoadBuildingCardReq(int, shared.locations.EdgeLocation, shared.locations.EdgeLocation)
	 */
	@Override
	public String sRoadBuildingCardReq(int playerIndex, EdgeLocation edgeLoc1, EdgeLocation edgeLoc2)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "Road_Building");
		obj.put("playerIndex", playerIndex);
		obj.put("spot1", oEdgeLocation(edgeLoc1));
		obj.put("spot2", oEdgeLocation(edgeLoc2));
		
		return obj.toString();
	}
	
	private JSONObject oEdgeLocation(EdgeLocation edgeLocation)
	{
		JSONObject obj = new JSONObject();
		obj.put("x", edgeLocation.getHexLoc().getX());
		obj.put("y", edgeLocation.getHexLoc().getY());
		obj.put("direction", EdgeDirection.toString(edgeLocation.getDir()).toUpperCase());
		
		return obj;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sSoldierCardReq(int, int, shared.locations.HexLocation)
	 */
	@Override
	public String sSoldierCardReq(int playerIndex, int victimIndex, HexLocation location)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "Soldier");
		obj.put("playerIndex", playerIndex);
		obj.put("victimIndex", victimIndex);
		obj.put("location", oHexLocation(location));
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sMonopolyCardReq(int, shared.definitions.ResourceType)
	 */
	@Override
	public String sMonopolyCardReq(int playerIndex, ResourceType resource)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "Monopoly");
		obj.put("playerIndex", playerIndex);
		obj.put("resource", ResourceType.toString(resource));
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sMonumentCardReq(int)
	 */
	@Override
	public String sMonumentCardReq(int playerIndex)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "Monument");
		obj.put("playerIndex", playerIndex);
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sBuildRoadReq(int, shared.locations.EdgeLocation, boolean)
	 */
	@Override
	public String sBuildRoadReq(int playerIndex, EdgeLocation roadLocation, boolean free)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "buildRoad");
		obj.put("playerIndex", playerIndex);
		obj.put("roadLocation", oEdgeLocation(roadLocation));
		obj.put("free", free);
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sBuildSettlementReq(int, shared.locations.VertexLocation, boolean)
	 */
	@Override
	public String sBuildSettlementReq(int playerIndex, VertexLocation vertexLocation, boolean free)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "buildSettlement");
		obj.put("playerIndex", playerIndex);
		obj.put("vertexLocation", oVertexLocation(vertexLocation));
		obj.put("free", free);
		
		return obj.toString();
	}
	
	private JSONObject oVertexLocation(VertexLocation vertexLocation)
	{
		JSONObject obj = new JSONObject();
		obj.put("x", vertexLocation.getHexLoc().getX());
		obj.put("y", vertexLocation.getHexLoc().getY());
		obj.put("direction", VertexDirection.toString(vertexLocation.getDir()).toUpperCase());
		
		return obj;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sBuildCityReq(int, shared.locations.VertexLocation)
	 */
	@Override
	public String sBuildCityReq(int playerIndex, VertexLocation vertexLocation)
	{
		JSONObject obj = new JSONObject();
		
		obj.put("type", "buildCity");
		obj.put("playerIndex", playerIndex);
		obj.put("vertexLocation", oVertexLocation(vertexLocation));
		
		return obj.toString();
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sOfferTradeReq(int, java.util.List, int)
	 */
	@Override
	public String sOfferTradeReq(int playerIndex, List<Integer> resourceList, int receiver)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sAcceptTradeReq(int, boolean)
	 */
	@Override
	public String sAcceptTradeReq(int playerIndex, boolean willAccept)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sMaritimeTrade(int, int, shared.definitions.ResourceType, shared.definitions.ResourceType)
	 */
	@Override
	public String sMaritimeTrade(int playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sDiscardCards(int, java.util.List)
	 */
	@Override
	public String sDiscardCards(int playerIndex, List<Integer> resourceList)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
