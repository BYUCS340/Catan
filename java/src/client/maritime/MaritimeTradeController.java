package client.maritime;

import shared.definitions.*;
import shared.model.ModelException;
import shared.model.ModelObserver;

import java.util.ArrayList;
import java.util.Iterator;

import client.base.*;
import client.model.ClientGame;
import client.model.ClientGameManager;
import client.networking.ServerProxyException;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, ModelObserver 
{
	//  NOTE: use ClientGameManager game = ClientGame.getGame();   
	//  to access the ClientGameManager
	
	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType giveResource;
	private ResourceType getResource;
	//  I can't be dependant on PortType and ResourceType having the same oridinal positions so
	//  I'll use this enum to record resource indexes in a readable way
	private enum ResourcePositions{iWOOD, iBRICK, iSHEEP, iWHEAT, iORE;}
	private int[] tradeRates;
	private ArrayList<ResourceType> resourcesPlayerCanGive;
	private ArrayList<ResourceType> resourcesPlayerCanGet;
	
	
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay)
	{
		super(tradeView);
		setTradeOverlay(tradeOverlay);
		tradeRates = new int[]{4,4,4,4,4};  //  default trade rates
		getResource = null;
		giveResource = null;
		resourcesPlayerCanGive = new ArrayList<ResourceType>();
		resourcesPlayerCanGet = new ArrayList<ResourceType>();
		getTradeView().enableMaritimeTrade(false);
		ClientGame.getGame().startListening(this, ModelNotification.STATE);
	}
	
	public IMaritimeTradeView getTradeView() 
	{
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() 
	{
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) 
	{
		this.tradeOverlay = tradeOverlay;
	}
	
	@Override
	public void alert()
	{
		ClientGameManager game = ClientGame.getGame();
		
		if (game.getTurnState() == TurnState.PLAYING)
			getTradeView().enableMaritimeTrade(true);
		else
			getTradeView().enableMaritimeTrade(false);
	}
	
	
	@Override
	public void startTrade() 
	{
		if (!ClientGame.getGame().CanOfferTrade(ClientGame.getGame().CurrentPlayersTurn()))
		{
			System.out.println("GM says current player can't make a trade right now");
			cancelTrade();
			return;
		}
		
		getResource = null;
		giveResource = null;
		//  update the trade rates and the resources that can be given and taken, this only needs to happen at the start of 
		//  a trade because other functionality is disabled so bank and player resources will not change after this modal is opened 
		updateTradeRates();
		updateResourcesPlayerCanGive();
		updateResourcesPlayerCanGet();
		
		if(!getTradeOverlay().isModalShowing())
			getTradeOverlay().showModal();
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().showGiveOptions(resourcesPlayerCanGive.toArray(new ResourceType[]{}));
		getTradeOverlay().hideGetOptions();
		if(resourcesPlayerCanGive.size() <= 0)
			getTradeOverlay().setStateMessage("No Resources to give");
		else
			getTradeOverlay().setStateMessage("Choose what to give up");
	}

	@Override
	public void makeTrade()
	{
		try
		{
			if(giveResource != null && getResource != null)
			{
				int giveResourceRate = getResourceTradeRate(giveResource);
				if (giveResourceRate <= 0)
				{
					//  something failed so start over gracefully
					throw new ModelException("Maritime Trade: Error with trade rates");
				}
				
				ClientGame.getGame().maritimeTradeCurrentPlayer(giveResourceRate, giveResource, getResource);

				tradeRates = new int[]{4,4,4,4,4};  //  default trade rates
				getResource = null;
				giveResource = null;
				resourcesPlayerCanGive = new ArrayList<ResourceType>();
				resourcesPlayerCanGet = new ArrayList<ResourceType>();
				startTrade();
			}
			else
			{
				//  something failed so start over gracefully
				throw new ModelException("Maritime Trade: Unknown error");
			}
		}
		catch(ModelException | ServerProxyException e)
		{
			startTrade();
			System.out.println("Error processing trade, please try again. Choose what to give up");
			getTradeOverlay().setStateMessage("Error processing trade, please try again. Choose what to give up");
			System.out.println(e.getMessage());
		}
	}
	

	@Override
	public void cancelTrade() 
	{
		getResource = null;
		giveResource = null;
		if(getTradeOverlay().isModalShowing())
			getTradeOverlay().closeModal();
	}

	@Override
	public void setGiveResource(ResourceType resource) 
	{
		getTradeOverlay().selectGiveOption(resource, getResourceTradeRate(resource));
		giveResource = resource;
		getTradeOverlay().showGetOptions(resourcesPlayerCanGet.toArray(new ResourceType[]{}));
		getTradeOverlay().setStateMessage("Choose what to get");
	}
	
	@Override
	public void setGetResource(ResourceType resource) 
	{
		getTradeOverlay().selectGetOption(resource, 1);
		getResource = resource;
		getTradeOverlay().setStateMessage("Trade?");
		getTradeOverlay().setTradeEnabled(true);
	}
	
	@Override
	public void unsetGiveValue() 
	{
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().setTradeEnabled(false);
		giveResource = null;
		getResource = null;
		getTradeOverlay().showGiveOptions(resourcesPlayerCanGive.toArray(new ResourceType[]{}));
		getTradeOverlay().setStateMessage("Choose what to give up");

	}

	@Override
	public void unsetGetValue() 
	{
		getTradeOverlay().setTradeEnabled(false);
		getResource = null;
		getTradeOverlay().showGetOptions(resourcesPlayerCanGet.toArray(new ResourceType[]{}));
		getTradeOverlay().setStateMessage("Choose what to get");
	}

	

	/**
	 * Checks a player's ports and changes trade rates accordingly
	 */
	private void updateTradeRates()
	{
		//  reset trade rates
		tradeRates = new int[]{4,4,4,4,4};
		//  get current ports :)
		Iterator<PortType> portsItr = ClientGame.getGame().GetMapModel().GetPorts(ClientGame.getGame().myPlayerColor());
		
		for(;portsItr.hasNext();)
		{
			PortType port = portsItr.next();
			if(port != PortType.NONE){
				
				switch(port)
				{
				case THREE:
					tradeRates = new int[]{3,3,3,3,3};
					break;
				case ORE:
					tradeRates[ResourcePositions.iORE.ordinal()] = 2;
					break;
				case BRICK:
					tradeRates[ResourcePositions.iBRICK.ordinal()] = 2;
					break;
				case WHEAT:
					tradeRates[ResourcePositions.iWHEAT.ordinal()] = 2;
					break;
				case SHEEP:
					tradeRates[ResourcePositions.iSHEEP.ordinal()] = 2;
					break;
				case WOOD:
					tradeRates[ResourcePositions.iWOOD.ordinal()] = 2;
					break;
				default:
					break;
				}
			}
		}
	}
	
	/**
	 * Checks to make sure the player has enough to trade of each type of resource at the corresponding
	 * trade rate for that resource. Updates the list of resources the player can give 
	 */
	private void updateResourcesPlayerCanGive()
	{
		resourcesPlayerCanGive = new ArrayList<ResourceType>();
		ClientGameManager game = ClientGame.getGame();
		if(game.playerResourceCount(ResourceType.ORE) >= tradeRates[ResourcePositions.iORE.ordinal()])
		{
			resourcesPlayerCanGive.add(ResourceType.ORE);
		}
		if(game.playerResourceCount(ResourceType.BRICK) >= tradeRates[ResourcePositions.iBRICK.ordinal()])
		{
			resourcesPlayerCanGive.add(ResourceType.BRICK);
		}
		if(game.playerResourceCount(ResourceType.WHEAT) >= tradeRates[ResourcePositions.iWHEAT.ordinal()])
		{
			resourcesPlayerCanGive.add(ResourceType.WHEAT);
		}
		if(game.playerResourceCount(ResourceType.SHEEP) >= tradeRates[ResourcePositions.iSHEEP.ordinal()])
		{
			resourcesPlayerCanGive.add(ResourceType.SHEEP);
		}
		if(game.playerResourceCount(ResourceType.WOOD) >= tradeRates[ResourcePositions.iWOOD.ordinal()])
		{
			resourcesPlayerCanGive.add(ResourceType.WOOD);
		}
	}
	
	private void updateResourcesPlayerCanGet()
	{
		resourcesPlayerCanGet = new ArrayList<ResourceType>();
		ClientGameManager game = ClientGame.getGame();
		if(game.getBankResourceCount(ResourceType.ORE) > 0)
		{
			resourcesPlayerCanGet.add(ResourceType.ORE);
		}
		if(game.getBankResourceCount(ResourceType.BRICK) > 0)
		{
			resourcesPlayerCanGet.add(ResourceType.BRICK);
		}
		if(game.getBankResourceCount(ResourceType.WHEAT) > 0)
		{
			resourcesPlayerCanGet.add(ResourceType.WHEAT);
		}
		if(game.getBankResourceCount(ResourceType.SHEEP) > 0)
		{
			resourcesPlayerCanGet.add(ResourceType.SHEEP);
		}
		if(game.getBankResourceCount(ResourceType.WOOD) > 0)
		{
			resourcesPlayerCanGet.add(ResourceType.WOOD);
		}
	}

	private int getResourceTradeRate(ResourceType type){
		switch(type){
		case ORE:
			return tradeRates[ResourcePositions.iORE.ordinal()];
		case BRICK:
			return tradeRates[ResourcePositions.iBRICK.ordinal()];
		case WHEAT:
			return tradeRates[ResourcePositions.iWHEAT.ordinal()];
		case SHEEP:
			return tradeRates[ResourcePositions.iSHEEP.ordinal()];
		case WOOD:
			return tradeRates[ResourcePositions.iWOOD.ordinal()];
		default:
			return 0;
		}
	}
	
}

