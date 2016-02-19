package client.discard;

import java.util.ArrayList;
import java.util.List;

import client.base.Controller;
import client.misc.IWaitView;
import client.model.ClientGame;
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
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource)
		{
			case BRICK:
				resourceList.set(0, resourceList.get(0) + 1);
			case ORE:
				resourceList.set(1, resourceList.get(1) + 1);
			case SHEEP:
				resourceList.set(2, resourceList.get(2) + 1);
			case WHEAT:
				resourceList.set(3, resourceList.get(3) + 1);
			case WOOD:
				resourceList.set(4, resourceList.get(4) + 1);
			default:
				break;
		}
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		switch(resource)
		{
			case BRICK:
				resourceList.set(0, resourceList.get(0) - 1);
			case ORE:
				resourceList.set(1, resourceList.get(1) - 1);
			case SHEEP:
				resourceList.set(2, resourceList.get(2) - 1);
			case WHEAT:
				resourceList.set(3, resourceList.get(3) - 1);
			case WOOD:
				resourceList.set(4, resourceList.get(4) - 1);
			default:
				break;
		}		
	}

	@Override
	public void discard() {
		ClientGame.getGame().DiscardCards(resourceList);
		getDiscardView().closeModal();
		
	}

	@Override
	public void alert()
	{
		// TODO Auto-generated method stub
		
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

