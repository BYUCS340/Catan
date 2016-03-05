package client.devcards;

import shared.definitions.ResourceType;
import shared.model.ModelException;
import shared.model.ModelObserver;
import shared.definitions.*;
import client.base.*;
import client.model.ClientGame;
import client.model.ClientGameManager;


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
	public void startBuyCard()
	{
		if (ClientGame.getGame().CanBuyDevCard(ClientGame.getGame().myPlayerIndex()))
			getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard()
	{
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard()
	{
		ClientGameManager game = ClientGame.getGame();
		//Check to make sure we can buy a card
		game.BuyDevCard();
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
	public void playMonopolyCard(ResourceType resource)
	{
		System.out.println("Playing Monopoly");
		if (ClientGame.getGame().PlayMonopoly(resource))
			cancelPlayCard();
	}

	@Override
	public void playMonumentCard()
	{
		if (ClientGame.getGame().PlayMonument())
			cancelPlayCard();
	}

	@Override
	public void playRoadBuildCard()
	{
		if (ClientGame.getGame().PlayRoadBuilder())
			cancelPlayCard();
		roadAction.execute();
	}

	@Override
	public void playSoldierCard()
	{
		if (ClientGame.getGame().PlayRoadBuilder())
			cancelPlayCard();
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
	{
		if (ClientGame.getGame().PlayYearOfPlenty(resource1, resource2))
			cancelPlayCard();
	}

	@Override
	public void alert()
	{
		updateDevCards();
	}
	
	public void updateDevCards()
	{
		//get number of cards by type (including new cards)
		int soldierCount = ClientGame.getGame().playerDevCardCount(DevCardType.SOLDIER) + ClientGame.getGame().playerNewDevCardCount(DevCardType.SOLDIER);
		int yearOfPlentyCount = ClientGame.getGame().playerDevCardCount(DevCardType.YEAR_OF_PLENTY) + ClientGame.getGame().playerNewDevCardCount(DevCardType.YEAR_OF_PLENTY);
		int monopolyCount = ClientGame.getGame().playerDevCardCount(DevCardType.MONOPOLY) + ClientGame.getGame().playerNewDevCardCount(DevCardType.MONOPOLY);
		int roadBuildCount = ClientGame.getGame().playerDevCardCount(DevCardType.ROAD_BUILD) + ClientGame.getGame().playerNewDevCardCount(DevCardType.ROAD_BUILD);
		int monumentCount = ClientGame.getGame().playerDevCardCount(DevCardType.MONUMENT) + ClientGame.getGame().playerNewDevCardCount(DevCardType.MONUMENT);
		
		//update view amounts (including new cards)
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, soldierCount);
		getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, yearOfPlentyCount);
		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, monopolyCount);
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, roadBuildCount);
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, monumentCount);
		
		//enable/disable (including only old cards)
		int playerIndex = ClientGame.getGame().myPlayerIndex();
		getPlayCardView().setCardEnabled(DevCardType.SOLDIER, ClientGame.getGame().CanUseSoldier(playerIndex));
		getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, ClientGame.getGame().CanUseYearOfPlenty(playerIndex));
		getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, ClientGame.getGame().CanUseMonopoly(playerIndex));
		getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, ClientGame.getGame().CanUseRoadBuilder(playerIndex));
		getPlayCardView().setCardEnabled(DevCardType.MONUMENT, ClientGame.getGame().CanUseMonument(playerIndex));
	}

}

