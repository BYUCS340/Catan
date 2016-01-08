package client.catan;

import java.awt.*;
import javax.swing.*;

import client.map.*;

@SuppressWarnings("serial")
public class MidPanel extends JPanel
{
	
	private TradePanel tradePanel;
	private MapView mapView;
	private RobView robView;
	private MapController mapController;
	private GameStatePanel gameStatePanel;
	
	public MidPanel()
	{
		
		this.setLayout(new BorderLayout());
		
		tradePanel = new TradePanel();
		
		mapView = new MapView();
		robView = new RobView();
		mapController = new MapController(mapView, robView);
		mapView.setController(mapController);
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
		return mapController;
	}
	
}

