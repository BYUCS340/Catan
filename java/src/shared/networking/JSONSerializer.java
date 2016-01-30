/**
 * 
 */
package shared.networking;

import java.util.List;

import org.json.JSONObject;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
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
	public String sAddAIReq(String AIType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sSendChatReq(int, java.lang.String)
	 */
	@Override
	public String sSendChatReq(int playerIndex, String content)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sRollNumberReq(int, int)
	 */
	@Override
	public String sRollNumberReq(int playerIndex, int number)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sRobPlayerReq(int, int, shared.locations.HexLocation)
	 */
	@Override
	public String sRobPlayerReq(int playerIndex, int victimIndex, HexLocation location)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sFinishTurnReq(int)
	 */
	@Override
	public String sFinishTurnReq(int playerIndex)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sBuyDevCardReq(int)
	 */
	@Override
	public String sBuyDevCardReq(int playerIndex)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sYearOfPlentyCardReq(int, shared.definitions.ResourceType, shared.definitions.ResourceType)
	 */
	@Override
	public String sYearOfPlentyCardReq(int playerIndex, ResourceType resource1, ResourceType resource2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sRoadBuildingCardReq(int, shared.locations.EdgeLocation, shared.locations.EdgeLocation)
	 */
	@Override
	public String sRoadBuildingCardReq(int playerIndex, EdgeLocation edgeLoc1, EdgeLocation edgeLoc2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sSoldierCardReq(int, int, shared.locations.HexLocation)
	 */
	@Override
	public String sSoldierCardReq(int playerIndex, int victimIndex, HexLocation location)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sMonopolyCardReq(int, shared.definitions.ResourceType)
	 */
	@Override
	public String sMonopolyCardReq(int playerIndex, ResourceType resource)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sMonumentCardReq(int)
	 */
	@Override
	public String sMonumentCardReq(int playerIndex)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sBuildRoadReq(int, shared.locations.EdgeLocation, boolean)
	 */
	@Override
	public String sBuildRoadReq(int playerIndex, EdgeLocation roadLocation, boolean free)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sBuildSettlementReq(int, shared.locations.VertexLocation, boolean)
	 */
	@Override
	public String sBuildSettlementReq(int playerIndex, VertexLocation vertexLocation, boolean free)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shared.networking.Serializer#sBuildCityReq(int, shared.locations.VertexLocation)
	 */
	@Override
	public String sBuildCityReq(int playerIndex, VertexLocation vertexLocation)
	{
		// TODO Auto-generated method stub
		return null;
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
