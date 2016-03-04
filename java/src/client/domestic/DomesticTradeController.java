package client.domestic;

import shared.definitions.*;
import shared.model.ModelObserver;

import java.util.ArrayList;

import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import client.model.ClientGame;
import client.model.ClientGameManager;
import client.networking.ServerProxyException;
import shared.model.ModelException;


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

		ClientGame.getGame().startListening(this, ModelNotification.STATE);
		this.alert();
		System.out.println(ResourcePositions.iBRICK.ordinal());
		System.out.println(ResourcePositions.iWHEAT.ordinal());
		System.out.println(ResourcePositions.iSHEEP.ordinal());
		System.out.println(ResourcePositions.iWOOD.ordinal());
		System.out.println(ResourcePositions.iORE.ordinal());
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
		if(game.CurrentState() == GameRound.PLAYING && game.getTurnState() == TurnState.PLAYING)
		{
			System.out.println("Player index in alert (my turn): " + game.myPlayerIndex());

			getTradeView().enableDomesticTrade(true);
			//  TODO:  probably need more fucntionality here to check why we're being alerted
		}
		else	//  Maritime Trade is disabled when not playing
		{	
			getTradeView().enableDomesticTrade(false);
			
			
			//  TODO:  check to see if I have an offer
			System.out.println("Player index in alert: " + game.myPlayerIndex());
			System.out.println("Other: " + game.getPlayerIndexWithTradeOffer() );
			System.out.println("Truth Value: " + (game.getPlayerIndexWithTradeOffer() == game.myPlayerIndex()));
			if(game.getPlayerIndexWithTradeOffer() == game.myPlayerIndex()){
				System.out.println("Inside");
				if (!this.getAcceptOverlay().isModalShowing()){
					System.out.println("Setting up modal");
					this.getAcceptOverlay().setPlayerName(game.getPlayerNameByIndex(game.getPlayerSendingOfferIndex()));
					int[] resourcesToTrade = game.getResourceToTrade();
					if(resourcesToTrade[0] != 0){
						if(resourcesToTrade[0] < 0){
							this.getAcceptOverlay().addGiveResource(ResourceType.BRICK, Math.abs(resourcesToTrade[0]));
						}else{
							this.getAcceptOverlay().addGetResource(ResourceType.BRICK, resourcesToTrade[0]);
						}
					}
					if(resourcesToTrade[1] != 0){
						if(resourcesToTrade[1] < 0){
							this.getAcceptOverlay().addGiveResource(ResourceType.ORE, Math.abs(resourcesToTrade[1]));
						}else{
							this.getAcceptOverlay().addGetResource(ResourceType.ORE, resourcesToTrade[1]);
						}
					}
					if(resourcesToTrade[2] != 0){
						if(resourcesToTrade[2] < 0){
							this.getAcceptOverlay().addGiveResource(ResourceType.SHEEP, Math.abs(resourcesToTrade[2]));
						}else{
							this.getAcceptOverlay().addGetResource(ResourceType.SHEEP, resourcesToTrade[2]);
						}
					}
					if(resourcesToTrade[3] != 0){
						if(resourcesToTrade[3] < 0){
							this.getAcceptOverlay().addGiveResource(ResourceType.WHEAT, Math.abs(resourcesToTrade[3]));
						}else{
							this.getAcceptOverlay().addGetResource(ResourceType.WHEAT, resourcesToTrade[3]);
						}
					}
					if(resourcesToTrade[4] != 0){
						if(resourcesToTrade[4] < 0){
							this.getAcceptOverlay().addGiveResource(ResourceType.WOOD, Math.abs(resourcesToTrade[4]));
						}else{
							this.getAcceptOverlay().addGetResource(ResourceType.WOOD, resourcesToTrade[4]);
						}
					}

					System.out.println("Showing accept modal");
					this.getAcceptOverlay().showModal();
					//  TODO: implement accept logic here
					if(playerCanAcceptTrade(resourcesToTrade))
						this.getAcceptOverlay().setAcceptEnabled(true);
					else
						this.getAcceptOverlay().setAcceptEnabled(false);

				}
				
			}
		}
		if(game.getPlayerIndexWithTradeOffer() == -1){
			//  if there is no existing trade then close the waiting regardless, if it's open
			if(this.getWaitOverlay().isModalShowing())
				this.getWaitOverlay().closeModal();
		}
	}


	/**
	*  Returns true if the player can accept the trade offer
	*/
	private boolean playerCanAcceptTrade(int[] resources){
		ClientGameManager game = ClientGame.getGame();
		if(resources[0] < -1 * game.playerResourceCount(ResourceType.BRICK)){
			return false;
		}else if(resources[1] < -1 * game.playerResourceCount(ResourceType.ORE)){
			return false;
		}else if(resources[2] < -1 * game.playerResourceCount(ResourceType.SHEEP)){
			return false;
		}else if(resources[3] < -1 * game.playerResourceCount(ResourceType.WHEAT)){
			return false;
		}else if(resources[4] < -1 * game.playerResourceCount(ResourceType.WOOD)){
			return false;
		}
		return true;
	}
	

	@Override
	public void startTrade() {
		initializePlayersToTradeWith();
		resetResourcesToTrade();
		resetTradeResesourceStates();
		playerIndexToTradeWith = -1;

		resetTradeOverlay();
		resetUpDownButtons();
		
		if(!getTradeOverlay().isModalShowing())
			getTradeOverlay().showModal();
		

				
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
	
	private void resetTradeOverlay(){
		getTradeOverlay().reset();
	}
	
	/**
	 * Check if the trade is ready, enable and disable the trade button accordingly
	 */
	private void checkIfTradeIsReady(){
		System.out.println("Resources to Trade: " + this.resourcesToTrade[0]);
		System.out.println("Resources to Trade: " + this.resourcesToTrade[1]);
		System.out.println("Resources to Trade: " + this.resourcesToTrade[2]);
		System.out.println("Resources to Trade: " + this.resourcesToTrade[3]);
		System.out.println("Resources to Trade: " + this.resourcesToTrade[4]);

		if(!sendAndReceiveResourcesSet()){
			System.out.println("need resources");
			getTradeOverlay().setStateMessage("Select Send and Receive Resources");
			getTradeOverlay().setTradeEnabled(false);
		}else if(playerIndexToTradeWith < 0){
			System.out.println("need players");
			getTradeOverlay().setStateMessage("Select Player to Trade With");	
			getTradeOverlay().setTradeEnabled(false);
		}else{
			System.out.println("can trade");
			getTradeOverlay().setStateMessage("Trade!");	
			getTradeOverlay().setTradeEnabled(true);
		}
	}
	
	/**
	 * Checks to see if send and receive resources are set in the trade
	 * @return
	 */
	private boolean sendAndReceiveResourcesSet(){
		boolean sendResourcesSet = false;
		boolean receiveResourceSet = false;
		
		for(int value : resourcesToTrade){
			if(value > 0)
				receiveResourceSet = true;
			else if(value < 0)
				sendResourcesSet = true;
		}
		return (sendResourcesSet && receiveResourceSet);
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		if(resourceStates[getResourceIndex(resource)] == TradeResourceStates.SEND){
			resourcesToTrade[getResourceIndex(resource)] += 1;
		}else{
			resourcesToTrade[getResourceIndex(resource)] -= 1;
		}
		updateUpDownForResource(resource);
		checkIfTradeIsReady();
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		if(resourceStates[getResourceIndex(resource)] == TradeResourceStates.SEND){
			resourcesToTrade[getResourceIndex(resource)] -= 1;
		}else{
			resourcesToTrade[getResourceIndex(resource)] += 1;
		}
		updateUpDownForResource(resource);
		checkIfTradeIsReady();
	}
	
	private void resetUpDownButtons(){
		updateUpDownForResource(ResourceType.BRICK);
		updateUpDownForResource(ResourceType.WOOD);
		updateUpDownForResource(ResourceType.WHEAT);
		updateUpDownForResource(ResourceType.ORE);
		updateUpDownForResource(ResourceType.SHEEP);
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
		//  resourcesToTrade is negative so 
		int playerCount = ClientGame.getGame().playerResourceCount(type);
		boolean canIncrease, canDecrease;
		if(playerCount > Math.abs(resourcesToTrade[getResourceIndex(type)])){
			canIncrease = true;
		}else{
			canIncrease = false;
		}
		if(Math.abs(resourcesToTrade[getResourceIndex(type)]) <= 0){
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
//			System.out.println("Wood");
//			System.out.println(ResourcePositions.iWOOD.ordinal());
			return ResourcePositions.iWOOD.ordinal();
		case WHEAT:
//			System.out.println("Wheat");
//			System.out.println(ResourcePositions.iWHEAT.ordinal());
			return ResourcePositions.iWHEAT.ordinal();
		case SHEEP:
//			System.out.println("Sheep");
//			System.out.println(ResourcePositions.iSHEEP.ordinal());
			return ResourcePositions.iSHEEP.ordinal();
		case ORE:
//			System.out.println("Ore");
//			System.out.println(ResourcePositions.iORE.ordinal());
			return ResourcePositions.iORE.ordinal();
		case BRICK:
//			System.out.println("Brick");
//			System.out.println(ResourcePositions.iBRICK.ordinal());
			return ResourcePositions.iBRICK.ordinal();
		default:
			return -1;
		}		
	}

	@Override
	public void sendTradeOffer() {
		//  TODO:  need a lot more work here as well
		System.out.println("Sending offer");
		this.getWaitOverlay().showModal();
		this.getWaitOverlay().setMessage("Un moment...");
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for (int resourceCount : this.resourcesToTrade){
			//  TODO:  might not want these negative
			tmp.add(-1 * resourceCount);
		}
		try {
			ClientGame.getGame().offerTrade(tmp ,this.playerIndexToTradeWith);
		} catch (ServerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(this.getTradeOverlay().isModalShowing())
				this.getTradeOverlay().closeModal();
		}
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		// System.out.println("Player to TradeWith: " + playerIndex);
		// System.out.println("My Index: " + game.myPlayerIndex());
		playerIndexToTradeWith = playerIndex;
		checkIfTradeIsReady();
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
//		System.out.println("Setting Resource to Receive: " + resource);
//		System.out.println("Resource Index: " + getResourceIndex(resource));
		resourceStates[getResourceIndex(resource)] = TradeResourceStates.RECEIVE;
		//  make it positive
		resourcesToTrade[getResourceIndex(resource)] = 0;
		getTradeOverlay().setResourceAmount(resource, "0");
		updateUpDownForResource(resource);
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
//		System.out.println("Setting Resource to Send: " + resource);
//		System.out.println("Resource Index: " + getResourceIndex(resource));
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
		checkIfTradeIsReady();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	
	@Override
	public void acceptTrade(boolean willAccept) {
		try {
			ClientGame.getCurrentProxy().acceptTrade(willAccept);
			ClientGame.getGame().RefreshFromServer();
		} catch (ServerProxyException | ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(getAcceptOverlay().isModalShowing())
				getAcceptOverlay().closeModal();
		}
		if(getAcceptOverlay().isModalShowing())
			getAcceptOverlay().closeModal();
	}
}

