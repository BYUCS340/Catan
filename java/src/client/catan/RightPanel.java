package client.catan;

import javax.swing.*;

import shared.definitions.PieceType;
import client.points.*;
import client.resources.*;
import client.base.*;
import client.map.*;
import client.devcards.*;

@SuppressWarnings("serial")
public class RightPanel extends JPanel
{
	
	private PlayDevCardView playCardView;
	private BuyDevCardView buyCardView;
	private DevCardController devCardController;
	private PointsView pointsView;
	private GameFinishedView finishedView;
	private PointsController pointsController;
	private ResourceBarView resourceView;
	private ResourceBarController resourceController;
	
	public RightPanel(final IMapController mapController)
	{
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		// Initialize development card views and controller
		//
		playCardView = new PlayDevCardView();
		buyCardView = new BuyDevCardView();
		IAction soldierAction = new IAction() {
			@Override
			public void execute()
			{
				mapController.playSoldierCard();
			}
		};
		IAction roadAction = new IAction() {
			@Override
			public void execute()
			{
				mapController.playRoadBuildingCard();
			}
		};
		devCardController = new DevCardController(playCardView, buyCardView,
												  soldierAction, roadAction);
		playCardView.setController(devCardController);
		buyCardView.setController(devCardController);
		
		// Initialize victory point view and controller
		//
		pointsView = new PointsView();
		finishedView = new GameFinishedView();
		pointsController = new PointsController(pointsView, finishedView);
		pointsView.setController(pointsController);
		
		// Initialize resource bar view and controller
		//
		resourceView = new ResourceBarView();
		resourceController = new ResourceBarController(resourceView);
		resourceController.setElementAction(ResourceBarElement.ROAD,
											createStartMoveAction(mapController,
																  PieceType.ROAD));
		resourceController.setElementAction(ResourceBarElement.SETTLEMENT,
											createStartMoveAction(mapController,
																  PieceType.SETTLEMENT));
		resourceController.setElementAction(ResourceBarElement.CITY,
											createStartMoveAction(mapController,
																  PieceType.CITY));
		resourceController.setElementAction(ResourceBarElement.BUY_CARD,
											new IAction() {
												@Override
												public void execute()
												{
													devCardController.startBuyCard();
												}
											});
		resourceController.setElementAction(ResourceBarElement.PLAY_CARD,
											new IAction() {
												@Override
												public void execute()
												{
													devCardController.startPlayCard();
												}
											});
		resourceView.setController(resourceController);
		
		this.add(pointsView);
		this.add(resourceView);
	}
	
	private IAction createStartMoveAction(final IMapController mapController,
										  final PieceType pieceType)
	{
		
		return new IAction() {
			
			@Override
			public void execute()
			{
				boolean isFree = false;
				boolean allowDisconnected = false;
				mapController.startMove(pieceType, isFree, allowDisconnected);
			}
		};
	}
	
}

