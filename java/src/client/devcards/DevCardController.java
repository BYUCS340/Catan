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
		try
		{
			ClientGame.getGame().PlayMonopoly(resource);
		}
		catch (ModelException e)
		{
			e.printStackTrace();
		};
	}

	@Override
	public void playMonumentCard()
	{
		try
		{
			ClientGame.getGame().PlayMonument();
		}
		catch (ModelException e)
		{
			e.printStackTrace();
		};
	}

	@Override
	public void playRoadBuildCard()
	{
		roadAction.execute();
	}

	@Override
	public void playSoldierCard()
	{
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
	{
		try
		{
			ClientGame.getGame().playDevCard(ClientGame.getGame().myPlayerIndex(), DevCardType.YEAR_OF_PLENTY);
		}
		catch (ModelException e)
		{
			e.printStackTrace();
		};
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

