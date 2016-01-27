package shared.networking.transport;

import java.util.List;

public class NetGameModel
{
	NetLog netGameLog;
	NetMap netMap;
	List<NetPlayer> netPlayers;
	NetDevCardList netDeck;
	NetBank netBank;
	NetChat netChat;
	NetTradeOffer netTradeOffer;
	NetTurnTracker netTurnTracker;
	int winner;
	int version;
	
	/**
	 * @return the netGameLog
	 */
	public NetLog getNetGameLog() {
		return netGameLog;
	}
	/**
	 * @param netGameLog the netGameLog to set
	 */
	public void setNetGameLog(NetLog netGameLog) {
		this.netGameLog = netGameLog;
	}
	/**
	 * @return the netMap
	 */
	public NetMap getNetMap() {
		return netMap;
	}
	/**
	 * @param netMap the netMap to set
	 */
	public void setNetMap(NetMap netMap) {
		this.netMap = netMap;
	}
	/**
	 * @return the netPlayers
	 */
	public List<NetPlayer> getNetPlayers() {
		return netPlayers;
	}
	/**
	 * @param netPlayers the netPlayers to set
	 */
	public void setNetPlayers(List<NetPlayer> netPlayers) {
		this.netPlayers = netPlayers;
	}
	/**
	 * @return the netDeck
	 */
	public NetDevCardList getNetDeck() {
		return netDeck;
	}
	/**
	 * @param netDeck the netDeck to set
	 */
	public void setNetDeck(NetDevCardList netDeck) {
		this.netDeck = netDeck;
	}
	/**
	 * @return the netBank
	 */
	public NetBank getNetBank() {
		return netBank;
	}
	/**
	 * @param netBank the netBank to set
	 */
	public void setNetBank(NetBank netBank) {
		this.netBank = netBank;
	}
	/**
	 * @return the netChat
	 */
	public NetChat getNetChat() {
		return netChat;
	}
	/**
	 * @param netChat the netChat to set
	 */
	public void setNetChat(NetChat netChat) {
		this.netChat = netChat;
	}
	/**
	 * @return the netTradeOffer
	 */
	public NetTradeOffer getNetTradeOffer() {
		return netTradeOffer;
	}
	/**
	 * @param netTradeOffer the netTradeOffer to set
	 */
	public void setNetTradeOffer(NetTradeOffer netTradeOffer) {
		this.netTradeOffer = netTradeOffer;
	}
	/**
	 * @return the netTurnTracker
	 */
	public NetTurnTracker getNetTurnTracker() {
		return netTurnTracker;
	}
	/**
	 * @param netTurnTracker the netTurnTracker to set
	 */
	public void setNetTurnTracker(NetTurnTracker netTurnTracker) {
		this.netTurnTracker = netTurnTracker;
	}
	/**
	 * @return the winner
	 */
	public int getWinner() {
		return winner;
	}
	/**
	 * @param winner the winner to set
	 */
	public void setWinner(int winner) {
		this.winner = winner;
	}
	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	
}
