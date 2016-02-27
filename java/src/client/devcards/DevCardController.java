package client.devcards;

import shared.definitions.ResourceType;
import shared.model.ModelObserver;
import shared.definitions.*;
import client.base.*;
import client.model.ClientGame;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, ModelObserver
{

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction)
	{
		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		
		ClientGame.getGame().startListening(this, ModelNotification.RESOURCES);
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		
	}

	@Override
	public void playMonumentCard() {
		
	}

	@Override
	public void playRoadBuildCard() {
		
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		
	}

	@Override
	public void alert()
	{
		updateDevCards();
	}
	
	public void updateDevCards()
	{
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, ClientGame.getGame().playerDevCardCount(DevCardType.SOLDIER));
		getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, ClientGame.getGame().playerDevCardCount(DevCardType.YEAR_OF_PLENTY));
		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, ClientGame.getGame().playerDevCardCount(DevCardType.MONOPOLY));
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, ClientGame.getGame().playerDevCardCount(DevCardType.ROAD_BUILD));
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, ClientGame.getGame().playerDevCardCount(DevCardType.MONUMENT));
	}

}

