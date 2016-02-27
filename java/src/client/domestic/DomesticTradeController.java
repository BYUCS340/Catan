package client.domestic;

import shared.definitions.*;
import shared.model.ModelObserver;

import java.util.ArrayList;

import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import client.model.ClientGame;
import client.model.ClientGameManager;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, ModelObserver {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private boolean playersToTradeWithAlreadySet;
	
	private enum ResourcePositions{iBRICK, iORE, iSHEEP, iWHEAT, iWOOD;}
	private int[] resourcesToTrade;  //  negative = receive
	private enum TradeResourceStates{SEND, NONE, RECEIVE;}
	private TradeResourceStates[] resourceStates;
	private int playerIndexToTradeWith;
	

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		playersToTradeWithAlreadySet = false;

		ClientGame.getGame().startListening(this, ModelNotification.ALL);
		this.alert();
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}
	
	
	@Override
	public void alert() {
		ClientGameManager game = ClientGame.getGame();
		//  TODO:  this should be more efficient later, should probably check for changed in state before updating everything
		if(game.CurrentState() == GameRound.PLAYING)
		{
			getTradeView().enableDomesticTrade(true);
			//  TODO:  probably need more fucntionality here to check why we're being alerted
		}
		else	//  Maritime Trade is disabled when not playing
		{	
			getTradeView().enableDomesticTrade(false);
			
			
			//  TODO:  check to see if I have an offer
		}
	}
	

	@Override
	public void startTrade() {
		initializePlayersToTradeWith();

		
		resetResourcesToTrade();
		resetTradeResesourceStates();
		playerIndexToTradeWith = -1;
		getTradeOverlay().showModal();
		
		
		
		
//		game.playerResourceCount(ResourceType.ORE);
		
		//  TODO:  I need to test to make sure the button numbers line up with the internal trade numbers
	}
	
	private void initializePlayersToTradeWith(){
//		System.out.println("Game Players: " + ClientGame.getGame().allCurrentPlayers());
		if(!playersToTradeWithAlreadySet){
			PlayerInfo[] currentPlayers = ClientGame.getGame().allCurrentPlayers();
			int currentPlayerIndex = ClientGame.getGame().CurrentPlayersTurn();
			
			ArrayList<PlayerInfo> otherPlayersInfo = new ArrayList<PlayerInfo>();
			for (int i = 0; i < 4; i++){
				if(currentPlayerIndex == i)
					continue;
				else
					otherPlayersInfo.add(currentPlayers[i]);
			}
			tradeOverlay.setPlayers(otherPlayersInfo.toArray(new PlayerInfo[]{}));
		}

		
		playersToTradeWithAlreadySet = true;
	}
	
	/**
	 * Sets initial settings for upDown buttons for all resources
	 */
	private void initializeAllUpDown(){
		//  TODO: probably not necessary
		updateUpDownForResource(ResourceType.BRICK);
		updateUpDownForResource(ResourceType.ORE);
		updateUpDownForResource(ResourceType.WHEAT);
		updateUpDownForResource(ResourceType.WOOD);
		updateUpDownForResource(ResourceType.SHEEP);
	}
	
	
	
	/**
	 * Just resets the resource to trade each time it's called
	 */
	private void resetResourcesToTrade(){
		resourcesToTrade = new int[]{0,0,0,0,0};
	}
	
	/**
	 * Researchs the internal button states each time it's called, should only be called when
	 * domestic trade modal opens
	 */
	private void resetTradeResesourceStates(){
		resourceStates = new TradeResourceStates[]{TradeResourceStates.NONE,TradeResourceStates.NONE,TradeResourceStates.NONE,TradeResourceStates.NONE,TradeResourceStates.NONE};
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		resourcesToTrade[getResourceIndex(resource)] -= 1;
		updateUpDownForResource(resource);
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		resourcesToTrade[getResourceIndex(resource)] += 1;
		updateUpDownForResource(resource);
	}
	
	/**
	 * Called whenever the state of the upDown buttons has changed, disables or enables them
	 * after checking context
	 * @param type
	 */
	private void updateUpDownForResource(ResourceType type){
		if(this.resourceStates[getResourceIndex(type)] == TradeResourceStates.RECEIVE){
			enableUpDownButtonsForReceiveResource(type);
		}else if(this.resourceStates[getResourceIndex(type)] == TradeResourceStates.SEND){
			enableUpDownButtonsForSendResource(type);
		}else{
		}
	}
	
	/**
	 * Handles button enabling/disabling for send resources
	 * @param type
	 */
	private void enableUpDownButtonsForSendResource(ResourceType type){
		
		int playerCount = ClientGame.getGame().playerResourceCount(type);
		boolean canIncrease, canDecrease;
		if(playerCount > resourcesToTrade[getResourceIndex(type)]){
			canIncrease = true;
		}else{
			canIncrease = false;
		}
		if(resourcesToTrade[getResourceIndex(type)] <= 0){
			canDecrease = false;
		}else{
			canDecrease = true;
		}
		getTradeOverlay().setResourceAmountChangeEnabled(type, canIncrease, canDecrease);
	}
	
	/**
	 * Handles button enabling/disabling for receive resources
	 * @param type
	 */
	private void enableUpDownButtonsForReceiveResource(ResourceType type){
		
		int playerCount = ClientGame.getGame().playerResourceCount(type);
		boolean canIncrease = true, canDecrease;
		if(resourcesToTrade[getResourceIndex(type)] <= 0){
			canDecrease = false;
		}else{
			canDecrease = true;
		}
		getTradeOverlay().setResourceAmountChangeEnabled(type, canIncrease, canDecrease);
	}
	
	/**
	 * Helper method for converting a resource type into an index of the resource 
	 * to trade array
	 * @param type
	 * @return
	 */
	private int getResourceIndex(ResourceType type){
		switch(type){
		case WOOD:
			return resourcesToTrade[ResourcePositions.iWOOD.ordinal()];
		case WHEAT:
			return resourcesToTrade[ResourcePositions.iWHEAT.ordinal()];
		case SHEEP:
			return resourcesToTrade[ResourcePositions.iSHEEP.ordinal()];
		case ORE:
			return resourcesToTrade[ResourcePositions.iORE.ordinal()];
		case BRICK:
			return resourcesToTrade[ResourcePositions.iBRICK.ordinal()];
		default:
			return -1;
		}		
	}

	@Override
	public void sendTradeOffer() {
		//  TODO:  need a lot more work here as well
		getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		playerIndexToTradeWith = playerIndex;
		getTradeOverlay().setTradeEnabled(true);
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		resourceStates[getResourceIndex(resource)] = TradeResourceStates.RECEIVE;
		//  make it positive
		resourcesToTrade[getResourceIndex(resource)] = 0;
		getTradeOverlay().setResourceAmount(resource, "0");
		updateUpDownForResource(resource);
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		resourceStates[getResourceIndex(resource)] = TradeResourceStates.SEND;
		//  make it negative
		resourcesToTrade[getResourceIndex(resource)] = 0;
		getTradeOverlay().setResourceAmount(resource, "0");
		updateUpDownForResource(resource);
	}

	@Override
	public void unsetResource(ResourceType resource) {
		resourceStates[getResourceIndex(resource)] = TradeResourceStates.NONE;
		resourcesToTrade[getResourceIndex(resource)] = 0;
		updateUpDownForResource(resource);
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		//  TODO:  need a lot more logic here
		getAcceptOverlay().closeModal();
	}



}

