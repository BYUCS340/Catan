package client.discard;

import java.util.ArrayList;
import java.util.List;

import client.base.Controller;
import client.misc.IWaitView;
import client.model.ClientGame;
import client.model.ClientGameManager;
import shared.definitions.GameRound;
import shared.definitions.ModelNotification;
import shared.definitions.ResourceType;
import shared.model.ModelObserver;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, ModelObserver {
	
	private IWaitView waitView;
	private List<Integer> resourceList;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		super(view);
		this.waitView = waitView;
		this.initResourceList();
		//TODO change this to STATE only
		ClientGame.getGame().startListening(this, ModelNotification.ALL);
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		IDiscardView dView = getDiscardView();
		int resourceIdx = -1;
		switch(resource)
		{
			case BRICK:
				resourceList.set(0, resourceList.get(0) + 1);
				resourceIdx = 0;
				break;
			case ORE:
				resourceList.set(1, resourceList.get(1) + 1);
				resourceIdx = 1;
				break;
			case SHEEP:
				resourceList.set(2, resourceList.get(2) + 1);
				resourceIdx = 2;
				break;
			case WHEAT:
				resourceList.set(3, resourceList.get(3) + 1);
				resourceIdx = 3;
				break;
			case WOOD:
				resourceList.set(4, resourceList.get(4) + 1);
				resourceIdx = 4;
				break;
			default:
				return;
		}
		
		int maxAmt = ClientGame.getGame().playerResourceCount(resource);
		int currAmt = resourceList.get(resourceIdx);
		boolean increase = currAmt < maxAmt;
		
		dView.setResourceDiscardAmount(resource, currAmt);
		dView.setResourceAmountChangeEnabled(resource, increase, true);
		updateDiscardStatus();
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		IDiscardView dView = getDiscardView();
		int resourceIdx = -1;
		switch(resource)
		{
			case BRICK:
				resourceList.set(0, resourceList.get(0) - 1);
				resourceIdx = 0;
				break;
			case ORE:
				resourceList.set(1, resourceList.get(1) - 1);
				resourceIdx = 1;
				break;
			case SHEEP:
				resourceList.set(2, resourceList.get(2) - 1);
				resourceIdx = 2;
				break;
			case WHEAT:
				resourceList.set(3, resourceList.get(3) - 1);
				resourceIdx = 3;
				break;
			case WOOD:
				resourceList.set(4, resourceList.get(4) - 1);
				resourceIdx = 4;
				break;
			default:
				return;
		}	
		
		int maxAmt = ClientGame.getGame().playerResourceCount(resource);
		int currAmt = resourceList.get(resourceIdx);
		boolean decrease = currAmt > 0;		
		boolean increase = currAmt < maxAmt;

		getDiscardView().setResourceDiscardAmount(resource, resourceList.get(resourceIdx));
		dView.setResourceAmountChangeEnabled(resource, increase, decrease);
		updateDiscardStatus();
	}
	

	
	private void updateDiscardStatus()
	{
		int total = 0;
		for(Integer i : resourceList)
		{
			total += i;
		}
		
		int discardAmt = this.getNumResourceCards() / 2;
		
		getDiscardView().setStateMessage("" + total + "/" + discardAmt);
		getDiscardView().setDiscardButtonEnabled(total == discardAmt);
		
	}

	@Override
	public void discard() {
		ClientGame.getGame().DiscardCards(resourceList);
		getDiscardView().closeModal();
		initResourceList();
		
	}

	@Override
	public void alert()
	{
		ClientGameManager game = ClientGame.getGame();
		if(game.CurrentState() == GameRound.DISCARDING)
		{
			if(this.getNumResourceCards() > 7)
			{
				//at this point, we know that we have more than 7 cards and need to 
				//discard some
				initDiscardView();
				this.getDiscardView().showModal();
			}
			else
			{
				this.getWaitView().showModal();
			}
		}
		else
			this.getWaitView().closeModal();
		
	}
	
	private void initDiscardView()
	{
		IDiscardView discardView = this.getDiscardView();
		ClientGameManager game = ClientGame.getGame();
	    int numWood = game.playerResourceCount(ResourceType.WOOD);
		int numBrick = game.playerResourceCount(ResourceType.BRICK);
		int numSheep = game.playerResourceCount(ResourceType.SHEEP);
		int numWheat = game.playerResourceCount(ResourceType.WHEAT);
		int numOre = game.playerResourceCount(ResourceType.ORE);
		
		discardView.setResourceMaxAmount(ResourceType.WOOD, numWood);
		discardView.setResourceMaxAmount(ResourceType.BRICK, numBrick);
		discardView.setResourceMaxAmount(ResourceType.SHEEP, numSheep);
		discardView.setResourceMaxAmount(ResourceType.WHEAT, numWheat);
		discardView.setResourceMaxAmount(ResourceType.ORE, numOre);
		
		discardView.setResourceAmountChangeEnabled(ResourceType.WOOD, numWood > 0, false);
		discardView.setResourceAmountChangeEnabled(ResourceType.BRICK, numBrick > 0, false);
		discardView.setResourceAmountChangeEnabled(ResourceType.SHEEP, numSheep > 0, false);
		discardView.setResourceAmountChangeEnabled(ResourceType.WHEAT, numWheat > 0, false);
		discardView.setResourceAmountChangeEnabled(ResourceType.ORE, numOre > 0, false);
		
		discardView.setStateMessage("0/" + this.getNumResourceCards()/2);
		discardView.setDiscardButtonEnabled(false);
	}
	
	private int getNumResourceCards()
	{
		 return (ClientGame.getGame().playerResourceCount(ResourceType.BRICK) + 
				ClientGame.getGame().playerResourceCount(ResourceType.ORE) + 
				ClientGame.getGame().playerResourceCount(ResourceType.SHEEP) + 
				ClientGame.getGame().playerResourceCount(ResourceType.WHEAT) + 
				ClientGame.getGame().playerResourceCount(ResourceType.WOOD));
	}
	
	private void initResourceList()
	{
		resourceList = new ArrayList<Integer>();
		resourceList.add(0);
		resourceList.add(0);
		resourceList.add(0);
		resourceList.add(0);
		resourceList.add(0);
	}

}

