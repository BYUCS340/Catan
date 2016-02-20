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
		ClientGame.getGame().startListening(this, ModelNotification.STATE);
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
		
		int discardAmt = (ClientGame.getGame().playerResourceCount(ResourceType.BRICK) + 
				ClientGame.getGame().playerResourceCount(ResourceType.ORE) + 
				ClientGame.getGame().playerResourceCount(ResourceType.SHEEP) + 
				ClientGame.getGame().playerResourceCount(ResourceType.WHEAT) + 
				ClientGame.getGame().playerResourceCount(ResourceType.WOOD)) / 2;
		
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
		if(game.CurrentState() == GameRound.ROBBING)
		{
			
		}
		
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

