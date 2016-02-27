package client.resources;

import java.util.*;

import client.base.*;
import client.model.ClientGame;
import shared.definitions.ModelNotification;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.model.ModelObserver;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, ModelObserver {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		ClientGame.getGame().startListening(this, ModelNotification.RESOURCES);
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}
	
	/**
	 * Update the resources on the bar
	 */
	private void updateResources(){
		this.getView().setElementAmount(ResourceBarElement.BRICK, ClientGame.getGame().playerResourceCount(ResourceType.BRICK));
		this.getView().setElementAmount(ResourceBarElement.ORE, ClientGame.getGame().playerResourceCount(ResourceType.ORE));
		this.getView().setElementAmount(ResourceBarElement.SHEEP, ClientGame.getGame().playerResourceCount(ResourceType.SHEEP));
		this.getView().setElementAmount(ResourceBarElement.WOOD, ClientGame.getGame().playerResourceCount(ResourceType.WOOD));
		this.getView().setElementAmount(ResourceBarElement.WHEAT, ClientGame.getGame().playerResourceCount(ResourceType.WHEAT));
		
		this.getView().setElementAmount(ResourceBarElement.ROAD, ClientGame.getGame().playerPieceCount(PieceType.ROAD));
		this.getView().setElementAmount(ResourceBarElement.CITY, ClientGame.getGame().playerPieceCount(PieceType.CITY));
		
	}

	@Override
	public void alert() {
		updateResources();
		
	}

}

