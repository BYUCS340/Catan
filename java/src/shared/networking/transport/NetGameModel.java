package shared.networking.transport;

import java.util.List;

public class NetGameModel
{
	NetLog netGameLog;
	NetMap netMap;
	NetDeck netDeck;
	NetBank netBank;
	NetChat netChat;
	NetTradeOffer netTradeOffer;
	NetTurnTracker netTurnTracker;
	List<NetRoad> netRoads;
	List<NetVertexObject> netCities;
	List<NetVertexObject> netSettlements;
	int winner;
	int version;
	
	
}
