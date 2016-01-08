package client.map;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;

import client.base.*;
import client.utils.*;
import shared.definitions.*;
import shared.locations.*;

// Wood, Brick, Sheep, Wheat, Ore, Desert, Water

/*
 * Custom component for displaying and interacting with Catan maps
 */
@SuppressWarnings("serial")
public class MapComponent extends JComponent
{
	
	public static final int HEX_IMAGE_WIDTH = 298;
	public static final int HEX_IMAGE_HEIGHT = 298; // 258;
	
	private static final int WORLD_WIDTH = HEX_IMAGE_WIDTH * 5
										   + HEX_IMAGE_WIDTH / 2;
	private static final int WORLD_HEIGHT = HEX_IMAGE_HEIGHT * 7;
	
	private static final int ROAD_HEIGHT = (int)(HEX_IMAGE_WIDTH * 0.11);
	private static final int ROAD_WIDTH = (int)(HEX_IMAGE_WIDTH * 0.4);
	
	private static final int SETTLEMENT_WIDTH = (int)(HEX_IMAGE_WIDTH * 0.2);
	private static final int SETTLEMENT_WALL_HEIGHT = (int)(SETTLEMENT_WIDTH * 0.7);
	private static final int SETTLEMENT_ROOF_HEIGHT = SETTLEMENT_WIDTH;
	
	private static final int CITY_WIDTH = (int)(HEX_IMAGE_WIDTH * 0.25);
	private static final int CITY_WALL_HEIGHT = (int)(CITY_WIDTH * 0.7);
	private static final int CITY_ROOF_HEIGHT = (int)(CITY_WIDTH * 1.0);
	
	private static Map<HexType, BufferedImage> HEX_IMAGES;
	private static Map<PortType, BufferedImage> PORT_IMAGES;
	private static Map<Integer, BufferedImage> NUMBER_IMAGES;
	private static BufferedImage ROBBER_IMAGE;
	private static BufferedImage DISALLOW_IMAGE;
	private static Map<EdgeDirection, List<Point2D>> ROADS;
	private static List<Point2D> SETTLEMENT;
	private static List<Point2D> CITY;
	private static Map<VertexDirection, Point2D> VERTEX_POINTS;
	private static Map<EdgeDirection, Point2D> EDGE_POINTS;
	private static Map<EdgeDirection, java.lang.Double> PORT_ROTATIONS;
	
	static
	{
		HEX_IMAGES = new HashMap<HexType, BufferedImage>();
		
		for (HexType hexType : HexType.values())
		{
			HEX_IMAGES.put(hexType, loadHexImage(hexType));
		}
		
		PORT_IMAGES = new HashMap<PortType, BufferedImage>();
		
		for (PortType portType : PortType.values())
		{
			PORT_IMAGES.put(portType, loadPortImage(portType));
		}
		
		NUMBER_IMAGES = new HashMap<Integer, BufferedImage>();
		
		for (int i = 2; i <= 12; ++i)
		{
			if(i != 7)
			{
				NUMBER_IMAGES.put(i, loadNumberImage(i));
			}
		}
		
		ROBBER_IMAGE = loadRobberImage();
		DISALLOW_IMAGE = loadDisallowImage();
		
		List<Point2D> ROAD_0 = new ArrayList<Point2D>();
		ROAD_0.add(new Point2D.Double(-ROAD_WIDTH / 2, -ROAD_HEIGHT / 2));
		ROAD_0.add(new Point2D.Double(ROAD_WIDTH / 2, -ROAD_HEIGHT / 2));
		ROAD_0.add(new Point2D.Double(ROAD_WIDTH / 2, ROAD_HEIGHT / 2));
		ROAD_0.add(new Point2D.Double(-ROAD_WIDTH / 2, ROAD_HEIGHT / 2));
		
		List<Point2D> ROAD_60 = rotateShape(ROAD_0, -(Math.PI / 3));
		List<Point2D> ROAD_120 = rotateShape(ROAD_0, -(2 * Math.PI / 3));
		
		ROADS = new HashMap<EdgeDirection, List<Point2D>>();
		ROADS.put(EdgeDirection.NorthWest, ROAD_60);
		ROADS.put(EdgeDirection.North, ROAD_0);
		ROADS.put(EdgeDirection.NorthEast, ROAD_120);
		ROADS.put(EdgeDirection.SouthEast, ROAD_60);
		ROADS.put(EdgeDirection.South, ROAD_0);
		ROADS.put(EdgeDirection.SouthWest, ROAD_120);
		
		SETTLEMENT = new ArrayList<Point2D>();
		SETTLEMENT.add(new Point2D.Double(SETTLEMENT_WIDTH / 2,
										  -SETTLEMENT_WALL_HEIGHT / 2));
		SETTLEMENT.add(new Point2D.Double(SETTLEMENT_WIDTH / 2,
										  SETTLEMENT_WALL_HEIGHT / 2));
		SETTLEMENT.add(new Point2D.Double(-SETTLEMENT_WIDTH / 2,
										  SETTLEMENT_WALL_HEIGHT / 2));
		SETTLEMENT.add(new Point2D.Double(-SETTLEMENT_WIDTH / 2,
										  -SETTLEMENT_WALL_HEIGHT / 2));
		Point2D sMidBase = average(SETTLEMENT.get(1), SETTLEMENT.get(2));
		Point2D sHighPoint = new Point2D.Double(
												sMidBase.getX(),
												sMidBase.getY()
														- SETTLEMENT_ROOF_HEIGHT);
		SETTLEMENT.add(sHighPoint);
		
		CITY = new ArrayList<Point2D>();
		CITY.add(new Point2D.Double(CITY_WIDTH / 2, -CITY_WALL_HEIGHT * 0.6));
		CITY.add(new Point2D.Double(CITY_WIDTH / 2, CITY_WALL_HEIGHT * 0.4));
		CITY.add(new Point2D.Double(-CITY_WIDTH / 2, CITY_WALL_HEIGHT * 0.4));
		CITY.add(new Point2D.Double(-CITY_WIDTH / 2, -CITY_WALL_HEIGHT * 0.3));
		CITY.add(new Point2D.Double(0, -CITY_WALL_HEIGHT * 0.3));
		CITY.add(new Point2D.Double(0, -CITY_WALL_HEIGHT * 0.6));
		Point2D cMidBase = average(CITY.get(1), CITY.get(2));
		Point2D cThreeQuarterBase = average(cMidBase, CITY.get(1));
		Point2D cHighPoint = new Point2D.Double(cThreeQuarterBase.getX(),
												cThreeQuarterBase.getY()
														- CITY_ROOF_HEIGHT);
		CITY.add(cHighPoint);
		
		VERTEX_POINTS = new HashMap<VertexDirection, Point2D>();
		VERTEX_POINTS.put(VertexDirection.West,
						  new Point2D.Double(-HEX_IMAGE_WIDTH / 2, 0));
		VERTEX_POINTS.put(VertexDirection.NorthWest,
						  new Point2D.Double(-HEX_IMAGE_WIDTH / 4,
											 -HEX_IMAGE_HEIGHT / 2));
		VERTEX_POINTS.put(VertexDirection.NorthEast,
						  new Point2D.Double(HEX_IMAGE_WIDTH / 4,
											 -HEX_IMAGE_HEIGHT / 2));
		VERTEX_POINTS.put(VertexDirection.East,
						  new Point2D.Double(HEX_IMAGE_WIDTH / 2, 0));
		VERTEX_POINTS.put(VertexDirection.SouthEast,
						  new Point2D.Double(HEX_IMAGE_WIDTH / 4,
											 HEX_IMAGE_HEIGHT / 2));
		VERTEX_POINTS.put(VertexDirection.SouthWest,
						  new Point2D.Double(-HEX_IMAGE_WIDTH / 4,
											 HEX_IMAGE_HEIGHT / 2));
		
		EDGE_POINTS = new HashMap<EdgeDirection, Point2D>();
		EDGE_POINTS.put(EdgeDirection.NorthWest,
						average(VERTEX_POINTS.get(VertexDirection.West),
								VERTEX_POINTS.get(VertexDirection.NorthWest)));
		EDGE_POINTS.put(EdgeDirection.North,
						average(VERTEX_POINTS.get(VertexDirection.NorthWest),
								VERTEX_POINTS.get(VertexDirection.NorthEast)));
		EDGE_POINTS.put(EdgeDirection.NorthEast,
						average(VERTEX_POINTS.get(VertexDirection.NorthEast),
								VERTEX_POINTS.get(VertexDirection.East)));
		EDGE_POINTS.put(EdgeDirection.SouthEast,
						average(VERTEX_POINTS.get(VertexDirection.East),
								VERTEX_POINTS.get(VertexDirection.SouthEast)));
		EDGE_POINTS.put(EdgeDirection.South,
						average(VERTEX_POINTS.get(VertexDirection.SouthEast),
								VERTEX_POINTS.get(VertexDirection.SouthWest)));
		EDGE_POINTS.put(EdgeDirection.SouthWest,
						average(VERTEX_POINTS.get(VertexDirection.SouthWest),
								VERTEX_POINTS.get(VertexDirection.West)));
		
		PORT_ROTATIONS = new HashMap<EdgeDirection, java.lang.Double>();
		PORT_ROTATIONS.put(EdgeDirection.NorthWest,
						   new java.lang.Double(Math.toRadians(120)));
		PORT_ROTATIONS.put(EdgeDirection.North,
						   new java.lang.Double(Math.toRadians(180)));
		PORT_ROTATIONS.put(EdgeDirection.NorthEast,
						   new java.lang.Double(Math.toRadians(240)));
		PORT_ROTATIONS.put(EdgeDirection.SouthEast,
						   new java.lang.Double(Math.toRadians(300)));
		PORT_ROTATIONS.put(EdgeDirection.South,
						   new java.lang.Double(Math.toRadians(0)));
		PORT_ROTATIONS.put(EdgeDirection.SouthWest,
						   new java.lang.Double(Math.toRadians(60)));
	}
	
	private IMapController controller;
	private Map<HexLocation, HexType> hexes;
	private Map<EdgeLocation, CatanColor> roads;
	private Map<VertexLocation, CatanColor> settlements;
	private Map<VertexLocation, CatanColor> cities;
	private Map<EdgeLocation, PortType> ports;
	private Map<HexLocation, Integer> numbers;
	private HexLocation robber;
	private Map<HexLocation, Point2D> allHexPoints;
	private Map<VertexLocation, Point2D> allVertexPoints;
	private Map<EdgeLocation, Point2D> allEdgePoints;
	private PieceType dropType;
	private CatanColor dropColor;
	private boolean dropAllowed;
	private HexLocation dropHexLoc;
	private EdgeLocation dropEdgeLoc;
	private VertexLocation dropVertLoc;
	private double scale;
	private AffineTransform transform;
	
	public MapComponent()
	{
		
		setBackground(Color.white);
		setOpaque(true);
		
		int prefWidth = (int)(8 * MapComponent.HEX_IMAGE_WIDTH);
		int prefHeight = (int)(8 * MapComponent.HEX_IMAGE_HEIGHT);
		setPreferredSize(new Dimension(prefWidth, prefHeight));
		
		setController(controller);
		
		hexes = new HashMap<HexLocation, HexType>();
		roads = new HashMap<EdgeLocation, CatanColor>();
		settlements = new HashMap<VertexLocation, CatanColor>();
		cities = new HashMap<VertexLocation, CatanColor>();
		ports = new HashMap<EdgeLocation, PortType>();
		numbers = new HashMap<HexLocation, Integer>();
		robber = null;
		allHexPoints = new HashMap<HexLocation, Point2D>();
		allVertexPoints = new HashMap<VertexLocation, Point2D>();
		allEdgePoints = new HashMap<EdgeLocation, Point2D>();
		
		initDrop();
		
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		addComponentListener(componentAdapter);
		
		scale = Double.NaN;
	}
	
	private void initDrop()
	{
		
		dropType = null;
		dropColor = null;
		dropAllowed = false;
		dropHexLoc = null;
		dropEdgeLoc = null;
		dropVertLoc = null;
	}
	
	public MapComponent copy()
	{
		
		MapComponent copy = new MapComponent();
		
		// copy.controller = this.controller;
		copy.hexes = this.hexes;
		copy.roads = this.roads;
		copy.settlements = this.settlements;
		copy.cities = this.cities;
		copy.ports = this.ports;
		copy.numbers = this.numbers;
		copy.robber = this.robber;
		copy.allHexPoints = this.allHexPoints;
		copy.allVertexPoints = this.allVertexPoints;
		copy.allEdgePoints = this.allEdgePoints;
		copy.dropType = this.dropType;
		copy.dropColor = this.dropColor;
		copy.dropAllowed = this.dropAllowed;
		copy.dropHexLoc = this.dropHexLoc;
		copy.dropEdgeLoc = this.dropEdgeLoc;
		copy.dropVertLoc = this.dropVertLoc;
		// copy.scale = this.scale;
		// copy.transform = this.transform;
		
		return copy;
	}
	
	private void updateScale()
	{
		
		this.scale = 1.0;
		
		if(this.getWidth() != 0 && this.getHeight() != 0)
		{
			
			double scaleX = (double)this.getWidth() / (double)WORLD_WIDTH;
			double scaleY = (double)this.getHeight() / (double)WORLD_HEIGHT;
			
			double newScale = Math.min(scaleX, scaleY);
			
			if(newScale > 0)
			{
				this.scale = newScale;
			}
		}
		
		this.transform = new AffineTransform();
		this.transform.translate(this.getWidth() / 2, this.getHeight() / 2);
		this.transform.scale(scale, scale);
		this.transform.translate(-WORLD_WIDTH / 2, -WORLD_HEIGHT / 2);
	}
	
	public IMapController getController()
	{
		return controller;
	}
	
	public void setController(IController controller)
	{
		this.controller = (IMapController)controller;
	}
	
	public double getScale()
	{
		
		return scale;
	}
	
	public void addHex(HexLocation hexLoc, HexType hexType)
	{
		
		// Add hex to hex map
		hexes.put(hexLoc, hexType);
		
		// Compute hex point for the new hex
		allHexPoints.put(hexLoc, getHexPoint(hexLoc));
		
		// Compute edge points for the new hex
		for (EdgeDirection edgeDir : EdgeDirection.values())
		{
			EdgeLocation edgeLoc = new EdgeLocation(hexLoc, edgeDir).getNormalizedLocation();
			allEdgePoints.put(edgeLoc, getEdgePoint(edgeLoc));
		}
		
		// Compute vertex points for the new hex
		for (VertexDirection vertDir : VertexDirection.values())
		{
			VertexLocation vertLoc = new VertexLocation(hexLoc, vertDir).getNormalizedLocation();
			allVertexPoints.put(vertLoc, getVertexPoint(vertLoc));
		}
		
		// Repaint
		this.repaint();
	}
	
	public void addNumber(HexLocation hexLoc, int num)
	{
		
		numbers.put(hexLoc, num);
		
		this.repaint();
	}
	
	public void placeRoad(EdgeLocation edgeLoc, CatanColor color)
	{
		
		roads.put(edgeLoc.getNormalizedLocation(), color);
		
		this.repaint();
	}
	
	public void placeSettlement(VertexLocation vertLoc, CatanColor color)
	{
		
		VertexLocation normVertLoc = vertLoc.getNormalizedLocation();
		
		if(cities.containsKey(normVertLoc))
		{
			cities.remove(normVertLoc);
		}
		
		settlements.put(normVertLoc, color);
	}
	
	public void placeCity(VertexLocation vertLoc, CatanColor color)
	{
		
		VertexLocation normVertLoc = vertLoc.getNormalizedLocation();
		
		if(settlements.containsKey(normVertLoc))
		{
			settlements.remove(normVertLoc);
		}
		
		cities.put(normVertLoc, color);
	}
	
	public void placePort(EdgeLocation edgeLoc, PortType portType)
	{
		ports.put(edgeLoc, portType);
	}
	
	public void placeRobber(HexLocation hexLoc)
	{
		
		robber = hexLoc;
	}
	
	public void startDrop(PieceType pieceType, CatanColor pieceColor)
	{
		
		dropType = pieceType;
		dropColor = pieceColor;
		
		this.repaint();
	}
	
	public void cancelDrop()
	{
		
		initDrop();
		
		this.repaint();
	}
	
	private ComponentAdapter componentAdapter = new ComponentAdapter() {
		
		@Override
		public void componentResized(ComponentEvent e)
		{
			
			super.componentResized(e);
			
			updateScale();
			repaint();
		}
		
	};
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		
		@Override
		public void mouseMoved(MouseEvent e)
		{
			
			if(dropType == null)
			{
				return;
			}
			if(transform == null)
			{
				return;
			}
			
			// Find
			// closest
			// drop
			// point
			
			Point2D mousePoint = new Point2D.Double(e.getX(), e.getY());
			
			try
			{
				transform.inverseTransform(mousePoint, mousePoint);
			}
			catch(NoninvertibleTransformException ex)
			{
				ex.printStackTrace();
				return;
			}
			
			if(dropType == PieceType.ROAD)
			{
				
				EdgeLocation closestEdgeLoc = null;
				double closestDistance = 0;
				
				for (Map.Entry<EdgeLocation, Point2D> entry : allEdgePoints.entrySet())
				{
					
					EdgeLocation edgeLoc = entry.getKey();
					Point2D edgePoint = entry.getValue();
					
					double distance = mousePoint.distance(edgePoint);
					
					if(closestEdgeLoc == null || (distance < closestDistance))
					{
						closestEdgeLoc = edgeLoc;
						closestDistance = distance;
					}
				}
				
				dropEdgeLoc = closestEdgeLoc;
				dropAllowed = getController().canPlaceRoad(dropEdgeLoc);
			}
			else if(dropType == PieceType.CITY
					|| dropType == PieceType.SETTLEMENT)
			{
				
				VertexLocation closestVertLoc = null;
				double closestDistance = 0;
				
				for (Map.Entry<VertexLocation, Point2D> entry : allVertexPoints.entrySet())
				{
					
					VertexLocation vertLoc = entry.getKey();
					Point2D vertPoint = entry.getValue();
					
					double distance = mousePoint.distance(vertPoint);
					
					if(closestVertLoc == null || (distance < closestDistance))
					{
						closestVertLoc = vertLoc;
						closestDistance = distance;
					}
				}
				
				dropVertLoc = closestVertLoc;
				
				if(dropType == PieceType.CITY)
				{
					dropAllowed = getController().canPlaceCity(dropVertLoc);
				}
				else if(dropType == PieceType.SETTLEMENT)
				{
					dropAllowed = getController().canPlaceSettlement(dropVertLoc);
				}
				else
				{
					assert false;
				}
			}
			else if(dropType == PieceType.ROBBER)
			{
				
				HexLocation closestHexLoc = null;
				double closestDistance = 0;
				
				for (Map.Entry<HexLocation, Point2D> entry : allHexPoints.entrySet())
				{
					
					HexLocation hexLoc = entry.getKey();
					Point2D hexPoint = entry.getValue();
					
					double distance = mousePoint.distance(hexPoint);
					
					if(closestHexLoc == null || (distance < closestDistance))
					{
						closestHexLoc = hexLoc;
						closestDistance = distance;
					}
				}
				
				dropHexLoc = closestHexLoc;
				dropAllowed = getController().canPlaceRobber(dropHexLoc);
			}
			else
			{
				assert false;
			}
			
			repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			
			if(dropType != null)
			{
				
				if(dropAllowed)
				{
					switch (dropType)
					{
						case ROAD:
							getController().placeRoad(dropEdgeLoc);
							break;
						case SETTLEMENT:
							getController().placeSettlement(dropVertLoc);
							break;
						case CITY:
							getController().placeCity(dropVertLoc);
							break;
						case ROBBER:
							getController().placeRobber(dropHexLoc);
							break;
						default:
							assert false;
							break;
					}
					
					initDrop();
				}
				
				repaint();
			}
		}
		
	};
	
	@Override
	protected void paintComponent(Graphics g)
	{
		
		if(Double.isNaN(scale))
		{
			return;
		}
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(this.getBackground());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.translate(this.getWidth() / 2, this.getHeight() / 2);
		g2.scale(scale, scale);
		g2.translate(-WORLD_WIDTH / 2, -WORLD_HEIGHT / 2);
		
		drawHexes(g2);
		drawPorts(g2);
		drawNumbers(g2);
		drawRobber(g2);
		drawRoads(g2);
		drawSettlements(g2);
		drawCities(g2);
		drawDropShape(g2);
	}
	
	private void drawHexes(Graphics2D g2)
	{
		
		for (Map.Entry<HexLocation, HexType> entry : hexes.entrySet())
		{
			
			BufferedImage hexImage = getHexImage(entry.getValue());
			
			Point2D hexCenter = getHexPoint(entry.getKey());
			Point2D hexCorner = new Point2D.Double(
												   (int)(hexCenter.getX() - HEX_IMAGE_WIDTH / 2),
												   (int)(hexCenter.getY() - HEX_IMAGE_HEIGHT / 2));
			
			g2.drawImage(hexImage, (int)hexCorner.getX(),
						 (int)hexCorner.getY(),
						 (int)(hexCorner.getX() + HEX_IMAGE_WIDTH),
						 (int)(hexCorner.getY() + HEX_IMAGE_HEIGHT), 0, 0,
						 HEX_IMAGE_WIDTH, HEX_IMAGE_HEIGHT, null);
		}
	}
	
	private void drawNumbers(Graphics2D g2)
	{
		
		for (Map.Entry<HexLocation, Integer> entry : numbers.entrySet())
		{
			
			BufferedImage numImage = getNumberImage(entry.getValue());
			
			Point2D hexCenter = getHexPoint(entry.getKey());
			
			drawImage(g2, numImage, hexCenter);
		}
	}
	
	private void drawRobber(Graphics2D g2)
	{
		if(robber != null)
		{
			drawRobber(g2, robber);
		}
	}
	
	private void drawRobber(Graphics2D g2, HexLocation hexLoc)
	{
		Point2D hexPoint = getHexPoint(hexLoc);
		BufferedImage robberImage = getRobberImage();
		drawImage(g2, robberImage, hexPoint);
	}
	
	private void drawRoads(Graphics2D g2)
	{
		for (Map.Entry<EdgeLocation, CatanColor> entry : roads.entrySet())
		{
			EdgeLocation edgeLoc = entry.getKey();
			CatanColor color = entry.getValue();
			drawRoad(g2, edgeLoc, color);
		}
	}
	
	private void
			drawRoad(Graphics2D g2, EdgeLocation edgeLoc, CatanColor color)
	{
		
		Point2D edgePoint = getEdgePoint(edgeLoc);
		
		List<Point2D> roadShape = translateShape(ROADS.get(edgeLoc.getDir()),
												 edgePoint);
		
		Polygon poly = toPolygon(roadShape);
		Color baseColor = color.getJavaColor();
		Color darkColor = baseColor.darker();
		
		g2.setColor(baseColor);
		g2.fillPolygon(poly);
		
		g2.setColor(darkColor);
		g2.setStroke(new BasicStroke(ROAD_HEIGHT / 3));
		g2.drawPolygon(poly);
	}
	
	private void drawSettlements(Graphics2D g2)
	{
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (Map.Entry<VertexLocation, CatanColor> entry : settlements.entrySet())
		{
			VertexLocation vertLoc = entry.getKey();
			CatanColor color = entry.getValue();
			drawSettlement(g2, vertLoc, color);
		}
	}
	
	private void drawSettlement(Graphics2D g2, VertexLocation vertLoc,
								CatanColor color)
	{
		
		Point2D vertPoint = getVertexPoint(vertLoc);
		
		List<Point2D> settlementShape = translateShape(SETTLEMENT, vertPoint);
		
		Polygon poly = toPolygon(settlementShape);
		Color baseColor = color.getJavaColor();
		Color darkColor = baseColor.darker();
		
		g2.setColor(baseColor);
		g2.fillPolygon(poly);
		
		g2.setColor(darkColor);
		g2.setStroke(new BasicStroke(ROAD_HEIGHT / 3));
		g2.drawPolygon(poly);
	}
	
	private void drawCities(Graphics2D g2)
	{
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (Map.Entry<VertexLocation, CatanColor> entry : cities.entrySet())
		{
			VertexLocation vertLoc = entry.getKey();
			CatanColor color = entry.getValue();
			drawCity(g2, vertLoc, color);
		}
	}
	
	private void drawCity(Graphics2D g2, VertexLocation vertLoc,
						  CatanColor color)
	{
		
		Point2D vertPoint = getVertexPoint(vertLoc);
		
		List<Point2D> cityShape = translateShape(CITY, vertPoint);
		
		Polygon poly = toPolygon(cityShape);
		Color baseColor = color.getJavaColor();
		Color darkColor = baseColor.darker();
		
		g2.setColor(baseColor);
		g2.fillPolygon(poly);
		
		g2.setColor(darkColor);
		g2.setStroke(new BasicStroke(ROAD_HEIGHT / 3));
		g2.drawPolygon(poly);
	}
	
	private void drawPorts(Graphics2D g2)
	{
		for (Map.Entry<EdgeLocation, PortType> entry : ports.entrySet())
		{
			EdgeLocation edgeLoc = entry.getKey();
			PortType portType = entry.getValue();
			drawPort(g2, edgeLoc, portType);
		}
	}
	
	private void
			drawPort(Graphics2D g2, EdgeLocation edgeLoc, PortType portType)
	{
		Point2D imageLoc = getHexPoint(edgeLoc.getHexLoc());
		drawRotatedImage(g2, getPortImage(portType), imageLoc,
						 getPortRotation(edgeLoc));
	}
	
	private void drawDisallowImage(Graphics2D g2, Point2D location)
	{
		drawImage(g2, getDisallowImage(), location);
	}
	
	private void drawDropShape(Graphics2D g2)
	{
		
		if(dropType == null)
		{
			return;
		}
		
		switch (dropType)
		{
			case ROAD:
			{
				if(dropEdgeLoc != null)
				{
					if(dropAllowed)
					{
						drawRoad(g2, dropEdgeLoc, dropColor);
					}
					else
					{
						drawDisallowImage(g2, getEdgePoint(dropEdgeLoc));
					}
				}
			}
				break;
			case CITY:
			{
				if(dropVertLoc != null)
				{
					if(dropAllowed)
					{
						drawCity(g2, dropVertLoc, dropColor);
					}
					else
					{
						drawDisallowImage(g2, getVertexPoint(dropVertLoc));
					}
				}
			}
				break;
			case SETTLEMENT:
			{
				if(dropVertLoc != null)
				{
					if(dropAllowed)
					{
						drawSettlement(g2, dropVertLoc, dropColor);
					}
					else
					{
						drawDisallowImage(g2, getVertexPoint(dropVertLoc));
					}
				}
			}
				break;
			case ROBBER:
			{
				if(dropHexLoc != null)
				{
					if(dropAllowed)
					{
						drawRobber(g2, dropHexLoc);
					}
					else
					{
						drawDisallowImage(g2, getHexPoint(dropHexLoc));
					}
				}
			}
				break;
			default:
			{
				assert false;
			}
				break;
		}
	}
	
	private void drawRotatedImage(Graphics2D g2, BufferedImage image,
								  Point2D location, double radians)
	{
		
		int centerX = image.getWidth() / 2;
		int centerY = image.getHeight() / 2;
		
		AffineTransform tx = AffineTransform.getRotateInstance(radians,
															   centerX, centerY);
		AffineTransformOp op = new AffineTransformOp(
													 tx,
													 AffineTransformOp.TYPE_BILINEAR);
		
		drawImage(g2, op.filter(image, null), location);
	}
	
	private void
			drawImage(Graphics2D g2, BufferedImage image, Point2D location)
	{
		
		int centerX = image.getWidth() / 2;
		int centerY = image.getHeight() / 2;
		
		g2.drawImage(image, (int)location.getX() - centerX,
					 (int)location.getY() - centerY, null);
	}
	
	private double getPortRotation(EdgeLocation edgeLoc)
	{
		
		return PORT_ROTATIONS.get(edgeLoc.getDir());
	}
	
	private static Point2D getHexPoint(HexLocation hexLoc)
	{
		
		final double SQRT_OF_3 = 1.7320508075688772935274463415059;
		
		int wCenterX = WORLD_WIDTH / 2;
		int wCenterY = WORLD_HEIGHT / 2;
		
		double doubleX = hexLoc.getX();
		double doubleY = hexLoc.getY();
		
		int wX = wCenterX + (int)(3 * doubleX * HEX_IMAGE_WIDTH / 4);
		int wY = wCenterY
				 + (int)((double)HEX_IMAGE_HEIGHT / 2 * SQRT_OF_3 * (doubleY + doubleX / 2));
		
		return new Point2D.Double(wX, wY);
	}
	
	private static Polygon toPolygon(List<Point2D> points)
	{
		
		Polygon result = new Polygon();
		
		for (Point2D pt : points)
		{
			result.addPoint((int)pt.getX(), (int)pt.getY());
		}
		
		return result;
	}
	
	private static Point2D getEdgePoint(EdgeLocation edgeLoc)
	{
		
		EdgeLocation normEdgeLoc = edgeLoc.getNormalizedLocation();
		
		Point2D hexPoint = getHexPoint(normEdgeLoc.getHexLoc());
		
		Point2D edgePoint = EDGE_POINTS.get(normEdgeLoc.getDir());
		
		return add(hexPoint, edgePoint);
	}
	
	private static Point2D getVertexPoint(VertexLocation vertLoc)
	{
		
		VertexLocation normVertLoc = vertLoc.getNormalizedLocation();
		
		Point2D hexPoint = getHexPoint(normVertLoc.getHexLoc());
		
		Point2D vertPoint = VERTEX_POINTS.get(normVertLoc.getDir());
		
		return add(hexPoint, vertPoint);
	}
	
	private static List<Point2D> rotateShape(List<Point2D> points,
											 double radians)
	{
		
		AffineTransform affine = new AffineTransform();
		affine.rotate(radians);
		
		List<Point2D> result = new ArrayList<Point2D>();
		
		for (Point2D pt : points)
		{
			Point2D newPt = new Point2D.Double();
			affine.transform(pt, newPt);
			result.add(newPt);
		}
		
		return result;
	}
	
	private static List<Point2D> translateShape(List<Point2D> points,
												Point2D delta)
	{
		
		List<Point2D> result = new ArrayList<Point2D>();
		
		for (Point2D pt : points)
		{
			result.add(add(pt, delta));
		}
		
		return result;
	}
	
	private static BufferedImage getHexImage(HexType hexType)
	{
		
		return HEX_IMAGES.get(hexType);
	}
	
	private static BufferedImage loadHexImage(HexType hexType)
	{
		
		String imageFile = getHexImageFile(hexType);
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private static BufferedImage getPortImage(PortType portType)
	{
		
		return PORT_IMAGES.get(portType);
	}
	
	private static BufferedImage loadPortImage(PortType portType)
	{
		
		String imageFile = getPortImageFile(portType);
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private static BufferedImage getNumberImage(int num)
	{
		
		return NUMBER_IMAGES.get(num);
	}
	
	private static BufferedImage loadNumberImage(int num)
	{
		
		String imageFile = getNumberImageFile(num);
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private static BufferedImage getRobberImage()
	{
		
		return ROBBER_IMAGE;
	}
	
	private static BufferedImage loadRobberImage()
	{
		
		String imageFile = getRobberImageFile();
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private static BufferedImage getDisallowImage()
	{
		
		return DISALLOW_IMAGE;
	}
	
	private static BufferedImage loadDisallowImage()
	{
		
		String imageFile = getDisallowImageFile();
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private static String getHexImageFile(HexType hexType)
	{
		
		switch (hexType)
		{
			case WOOD:
				return "images/land/forest.gif";
			case BRICK:
				return "images/land/brick.gif";
			case SHEEP:
				return "images/land/pasture.gif";
			case WHEAT:
				return "images/land/wheat.gif";
			case ORE:
				return "images/land/ore.gif";
			case DESERT:
				return "images/land/desert.gif";
			case WATER:
				return "images/land/water.gif";
			default:
				assert false;
				return null;
		}
	}
	
	private static String getPortImageFile(PortType portType)
	{
		
		switch (portType)
		{
			case WOOD:
				return "images/ports/port_wood.png";
			case BRICK:
				return "images/ports/port_brick.png";
			case SHEEP:
				return "images/ports/port_sheep.png";
			case WHEAT:
				return "images/ports/port_wheat.png";
			case ORE:
				return "images/ports/port_ore.png";
			case THREE:
				return "images/ports/port_three.png";
			default:
				assert false;
				return null;
		}
	}
	
	private static String getNumberImageFile(int num)
	{
		
		if((2 <= num && num <= 6) || (8 <= num && num <= 12))
		{
			return "images/numbers/small_prob/" + num + ".png";
		}
		else
		{
			assert false;
			return null;
		}
	}
	
	private static String getRobberImageFile()
	{
		return "images/misc/robber.gif";
	}
	
	private static String getDisallowImageFile()
	{
		return "images/misc/noIcon.png";
	}
	
	private static Point2D average(Point2D pt1, Point2D pt2)
	{
		
		return new Point2D.Double((int)((pt1.getX() + pt2.getX()) / 2),
								  (int)((pt1.getY() + pt2.getY()) / 2));
	}
	
	private static Point2D add(Point2D pt1, Point2D pt2)
	{
		
		return new Point2D.Double((int)(pt1.getX() + pt2.getX()),
								  (int)(pt1.getY() + pt2.getY()));
	}
	
}

