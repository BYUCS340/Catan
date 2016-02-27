package client.resources;

import java.util.*;

import client.base.*;
import client.model.ClientGame;
import client.model.ClientGameManager;
import shared.definitions.ModelNotification;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.model.ModelObserver;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, ModelObserver
{

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		ClientGame.getGame().startListening(this, ModelNotification.RESOURCES);
		ClientGame.getGame().startListening(this, ModelNotification.STATE);
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
		ClientGame.getGame().startBuilding(PieceType.ROAD);
		
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
		ClientGame.getGame().startBuilding(PieceType.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
		ClientGame.getGame().startBuilding(PieceType.CITY);
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
	private void updateResources()
	{
		ClientGameManager game = ClientGame.getGame();
		this.getView().setElementAmount(ResourceBarElement.BRICK, game.playerResourceCount(ResourceType.BRICK));
		this.getView().setElementAmount(ResourceBarElement.ORE,   game.playerResourceCount(ResourceType.ORE));
		this.getView().setElementAmount(ResourceBarElement.SHEEP, game.playerResourceCount(ResourceType.SHEEP));
		this.getView().setElementAmount(ResourceBarElement.WOOD,  game.playerResourceCount(ResourceType.WOOD));
		this.getView().setElementAmount(ResourceBarElement.WHEAT, game.playerResourceCount(ResourceType.WHEAT));
		
		this.getView().setElementAmount(ResourceBarElement.ROAD, game.playerPieceCount(PieceType.ROAD));
		this.getView().setElementAmount(ResourceBarElement.CITY, game.playerPieceCount(PieceType.CITY));
		this.getView().setElementAmount(ResourceBarElement.SETTLEMENT, game.playerPieceCount(PieceType.SETTLEMENT));
		
		this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, game.CanBuyDevCard(game.myPlayerIndex()));
		this.getView().setElementEnabled(ResourceBarElement.ROAD, game.CanBuildRoad(game.myPlayerIndex()));
		this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, game.CanBuildSettlement(game.myPlayerIndex()));
		this.getView().setElementEnabled(ResourceBarElement.CITY, game.CanBuildCity(game.myPlayerIndex()));
	}

	@Override
	public void alert()
	{
		updateResources();
	}

}

