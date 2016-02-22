package client.maritime;

import shared.definitions.*;
import shared.model.ModelObserver;
import client.base.*;
import client.model.ClientGame;
import client.model.ClientGameManager;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, ModelObserver {
	//  use 		ClientGameManager game = ClientGame.getGame();   to access the ClientGameManager
	
	
	private IMaritimeTradeOverlay tradeOverlay;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}
	
	
	
	
	
	
	
	@Override
	public void alert()
	{
		ClientGameManager game = ClientGame.getGame();
		//  TODO:  this should be more efficient later, should probably check for changed in state before updating everything
		if(game.CurrentState() == GameRound.PLAYING)
		{
			
			getTradeView().enableMaritimeTrade(true);
			
			
			//  TODO:  probably need more fucntionality here to check why we're being alerted
		}
		else	//  Maritime Trade is disabled when not playing
		{	
			getTradeView().enableMaritimeTrade(false);
		}
			
		
	}
	

	
	
	@Override
	public void startTrade() {
		
		getTradeOverlay().showModal();
		getTradeOverlay().setTradeEnabled(false);
		
		getTradeOverlay().showGiveOptions(new ResourceType[]{ResourceType.BRICK, ResourceType.SHEEP, ResourceType.WOOD});
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().setStateMessage("Choose what to give up");
	}

	@Override
	public void makeTrade() {

		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().showGiveOptions(new ResourceType[]{ResourceType.BRICK, ResourceType.SHEEP, ResourceType.WOOD});
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().setStateMessage("Choose what to give up");
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		getTradeOverlay().selectGiveOption(resource, 1);
//		getTradeOverlay().showGetOptions(new ResourceType[]{ResourceType.BRICK, ResourceType.ORE, ResourceType.WHEAT, ResourceType.SHEEP, ResourceType.WOOD});
		getTradeOverlay().showGetOptions(new ResourceType[]{ResourceType.ORE, ResourceType.WHEAT, ResourceType.SHEEP, ResourceType.WOOD});
		getTradeOverlay().setStateMessage("Choose what to get");
	}
	
	@Override
	public void setGetResource(ResourceType resource) {
		getTradeOverlay().selectGetOption(resource, 1);
		getTradeOverlay().setStateMessage("Trade?");
		getTradeOverlay().setTradeEnabled(true);
	}
	
	@Override
	public void unsetGiveValue() {
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().showGiveOptions(new ResourceType[]{ResourceType.BRICK, ResourceType.SHEEP, ResourceType.WOOD});
		getTradeOverlay().setStateMessage("Choose what to give up");

	}

	@Override
	public void unsetGetValue() {
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().showGetOptions(new ResourceType[]{ResourceType.ORE, ResourceType.WHEAT, ResourceType.SHEEP, ResourceType.WOOD});
		getTradeOverlay().setStateMessage("Choose what to get");

	}

}

