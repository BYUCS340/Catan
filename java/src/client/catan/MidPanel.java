package client.catan;

import java.awt.*;
import javax.swing.*;

import client.map.*;
import client.map.IMapController;
import client.map.view.*;
import shared.model.map.*;

@SuppressWarnings("serial")
public class MidPanel extends JPanel
{

	private TradePanel tradePanel;
	private MapView mapView;
	private MapOverlayView mapOverlayView;
	private RobView robView;
	private MapController mapController;
	private GameStatePanel gameStatePanel;

	public MidPanel()
	{
		this.setLayout(new BorderLayout());

		tradePanel = new TradePanel();

		mapView = new MapView();
		mapOverlayView = new MapOverlayView();
		robView = new RobView();
		
		MapModel model = new MapModel();
		MapGenerator.BeginnerMap(model);
		
		mapController = new MapController(mapView, model);
		//Give the game the right map controller
		mapView.setController(mapController);
		mapOverlayView.setController(mapController);
		robView.setController(mapController);

		gameStatePanel = new GameStatePanel();

		this.add(tradePanel, BorderLayout.NORTH);
		this.add(mapView, BorderLayout.CENTER);
		this.add(gameStatePanel, BorderLayout.SOUTH);

		this.setPreferredSize(new Dimension(800, 700));
	}

	public GameStatePanel getGameStatePanel()
	{
		return gameStatePanel;
	}

	public IMapController getMapController()
	{
		return this.mapController;
	}

}
