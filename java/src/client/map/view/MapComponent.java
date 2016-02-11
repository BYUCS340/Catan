package client.map.view;

import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;

import client.base.*;
import client.map.view.helpers.ImageHandler;
import shared.definitions.*;
import shared.model.map.*;
import shared.model.map.objects.*;

// Wood, Brick, Sheep, Wheat, Ore, Desert, Water

/*
 * Custom component for displaying and interacting with Catan maps
 */
@SuppressWarnings("serial")
public class MapComponent extends JComponent
{
	
	public static final int HEX_IMAGE_WIDTH = 298;
	public static final int HEX_IMAGE_HEIGHT = 258;
	
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
	
	private static List<Point2D> SETTLEMENT;
	private static List<Point2D> CITY;
	private static List<Point2D> ROAD;
	
	static
	{
		List<Point2D> ROAD = new ArrayList<Point2D>();
		ROAD.add(new Point2D.Double(-ROAD_WIDTH / 2, -ROAD_HEIGHT / 2));
		ROAD.add(new Point2D.Double(ROAD_WIDTH / 2, -ROAD_HEIGHT / 2));
		ROAD.add(new Point2D.Double(ROAD_WIDTH / 2, ROAD_HEIGHT / 2));
		ROAD.add(new Point2D.Double(-ROAD_WIDTH / 2, ROAD_HEIGHT / 2));
		
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
	}
	
	private IMapController controller;
	
	//Drag drop details
	private PieceType dropType;
	private CatanColor dropColor;
	private boolean dropAllowed;
	private Hex dropHexLoc;
	private Edge dropEdgeLoc;
	private Vertex dropVertLoc;
	
	//View details
	private double scale;
	private AffineTransform transform;
	
	private MapModel model;
	
	public MapComponent()
	{
		setBackground(Color.white);
		setOpaque(true);
		
		int prefWidth = (int)(8 * MapComponent.HEX_IMAGE_WIDTH);
		int prefHeight = (int)(8 * MapComponent.HEX_IMAGE_HEIGHT);
		setPreferredSize(new Dimension(prefWidth, prefHeight));
		
		setController(controller);
		
		initDrop();
		
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		addComponentListener(componentAdapter);
		
		scale = Double.NaN;
	}
	
	public void SetMapModel(MapModel model)
	{
		this.model = model;
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
		
		copy.dropType = this.dropType;
		copy.dropColor = this.dropColor;
		copy.dropAllowed = this.dropAllowed;
		copy.dropHexLoc = this.dropHexLoc;
		copy.dropEdgeLoc = this.dropEdgeLoc;
		copy.dropVertLoc = this.dropVertLoc;
		
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
	
	private ComponentAdapter componentAdapter = new ComponentAdapter()
	{	
		@Override
		public void componentResized(ComponentEvent e)
		{
			super.componentResized(e);
			
			updateScale();
			repaint();
		}
	};
	
	private MouseAdapter mouseAdapter = new MouseAdapter() 
	{	
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
			
			Coordinate closestHexCoordinate = getClosestHexCoordinate(mousePoint);
			if (!model.ContainsHex(closestHexCoordinate))
				return;
			
			Hex closestHex;
			try 
			{
				closestHex = model.GetHex(closestHexCoordinate);
				
				if(dropType == PieceType.ROAD)
				{
					Map<Double, Vertex> possibleEnds = getSortedVerticies(mousePoint, closestHex);
					
					Iterator<Vertex> sortedVerticies = possibleEnds.values().iterator();
					Vertex v1 = sortedVerticies.next();
					Vertex v2 = sortedVerticies.next();
					Coordinate p1 = v1.getPoint();
					Coordinate p2 = v2.getPoint();
					
					dropAllowed = getController().canPlaceRoad(p1, p2, dropColor);
					
					if (dropAllowed)
					{
						try
						{
							dropEdgeLoc = model.GetEdge(p1, p2);
						} 
						catch (MapException e1) {
							e1.printStackTrace();
							dropAllowed = false;
						}
					}
				}
				else if (dropType == PieceType.CITY || dropType == PieceType.SETTLEMENT)
				{
					Map<Double, Vertex> possibleEnds = getSortedVerticies(mousePoint, closestHex);
					
					Iterator<Vertex> sortedVerticies = possibleEnds.values().iterator();
					Vertex v1 = sortedVerticies.next();
					Coordinate p1 = v1.getPoint();
					
					if (dropType == PieceType.CITY)
						dropAllowed = getController().canPlaceCity(p1, dropColor);
					else
						dropAllowed = getController().canPlaceSettlement(p1);
					
					if (dropAllowed)
					{
						try
						{
							dropVertLoc = model.GetVertex(p1);
						} 
						catch (MapException e1) {
							e1.printStackTrace();
							dropAllowed = false;
						}
					}
				}
				else if(dropType == PieceType.ROBBER)
				{
					dropAllowed = getController().canPlaceRobber(closestHexCoordinate);
					
					if (dropAllowed)
					{
						try
						{
							dropHexLoc = model.GetHex(closestHexCoordinate);
						} 
						catch (MapException e1) {
							e1.printStackTrace();
							dropAllowed = false;
						}
					}
				}
				else
				{
					assert false;
				}
				
				repaint();
			} 
			catch (MapException e2) {
				e2.printStackTrace();
			}	
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
							getController().placeRoad(dropEdgeLoc.getStart(), dropEdgeLoc.getEnd(), dropColor);
							break;
						case SETTLEMENT:
							getController().placeSettlement(dropVertLoc.getPoint(), dropColor);
							break;
						case CITY:
							getController().placeCity(dropVertLoc.getPoint(), dropColor);
							break;
						case ROBBER:
							getController().placeRobber(dropHexLoc.getPoint());
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
			return;
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(this.getBackground());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());		

		if (model == null)
			return;

		g2.translate(this.getWidth() / 2, this.getHeight() / 2);
		g2.scale(scale, scale);
		g2.translate(-WORLD_WIDTH / 2, -WORLD_HEIGHT / 2);
		
		drawHexes(g2);
		drawPorts(g2);
		drawNumbers(g2);
		drawRobber(g2);
		drawRoads(g2);
		drawVerticies(g2);
		drawDropShape(g2);
	}
	
	private void drawHexes(Graphics2D g2)
	{
		Iterator<Hex> hexes = model.GetAllHexes();
		
		while (hexes.hasNext())
		{
			Hex hex = hexes.next();
			
			BufferedImage hexImage = ImageHandler.getHexImage(hex.getType());
			
			Point2D hexCenter = getHexCenterPoint(hex);
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
		Iterator<Entry<Integer, List<Hex>>> pips = model.GetPips();
		while(pips.hasNext())
		{
			Entry<Integer, List<Hex>> entry = pips.next();
			
			BufferedImage numImage = ImageHandler.getNumberImage(entry.getKey());
			
			for (Hex hex : entry.getValue())
			{
				Point2D hexCenter = getHexCenterPoint(hex);
				drawImage(g2, numImage, hexCenter);
			}
		}
	}
	
	private void drawRobber(Graphics2D g2)
	{
		//I added this so it doesn't look as if there are multiple robbers.
		if (dropHexLoc != null)
			return;
		
		//In case the robber doesn't exist yet.
		if (!model.IsRobberInitialized())
			return;
		
		try
		{
			Point2D hexPoint = getHexCenterPoint(model.GetRobberPlacement());
			BufferedImage robberImage = ImageHandler.getRobberImage();
			drawImage(g2, robberImage, hexPoint);
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
	}
	
	private void drawRoads(Graphics2D g2)
	{
		Iterator<Edge> edges = model.GetAllEdges();
		while (edges.hasNext())
		{
			Edge edge = edges.next();
			
			if (edge.doesRoadExists())
				drawRoads(g2, edge, edge.getColor());
		}
	}
	
	private void drawRoads(Graphics2D g2, Edge edge, CatanColor color)
	{
		Point2D edgeCenter = getEdgeCenterPoint(edge);
		Double angle = getEdgeAngle(edge);
		
		List<Point2D> rotatedRoad = rotateShape(ROAD, angle);
		List<Point2D> completedRoad = translateShape(rotatedRoad, edgeCenter);
		
		Polygon road = toPolygon(completedRoad);
		drawGamePiece(g2, road, edge.getColor());
	}
	
	private void drawVerticies(Graphics2D g2)
	{
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		
		Iterator<Vertex> verticies = model.GetAllVerticies();
		while (verticies.hasNext())
		{
			Vertex vertex = verticies.next();
			Point2D vertPoint = getVertexPoint(vertex.getPoint());
			
			List<Point2D> settlementShape;
			if (vertex.getType() == PieceType.SETTLEMENT)
				settlementShape = translateShape(SETTLEMENT, vertPoint);
			else if (vertex.getType() == PieceType.CITY)
				settlementShape = translateShape(CITY, vertPoint);
			else
				continue;
			
			Polygon polygon = toPolygon(settlementShape);
			drawGamePiece(g2, polygon, vertex.getColor());
		}
	}
	
	private void drawGamePiece(Graphics2D g2, Polygon polygon, CatanColor color)
	{
		Color baseColor = color.getJavaColor();
		Color darkColor = baseColor.darker();
		
		g2.setColor(baseColor);
		g2.fillPolygon(polygon);
		
		g2.setColor(darkColor);
		g2.setStroke(new BasicStroke(ROAD_HEIGHT / 3));
		g2.drawPolygon(polygon);
	}
	
	private void drawPorts(Graphics2D g2)
	{
		Iterator<Entry<Edge, Hex>> ports = model.GetAllPorts();
		while (ports.hasNext())
		{
			Entry<Edge, Hex> port = ports.next();
			Edge edge = port.getKey();
			Hex hex = port.getValue();
			
			Point2D hexCenter = getHexCenterPoint(hex);
			Point2D edgeCenter = getEdgeCenterPoint(edge);
			
			double angle = getPortRotation(edgeCenter, hexCenter);
			BufferedImage portImage = ImageHandler.getPortImage(hex.getPort());
			drawRotatedImage(g2, portImage, hexCenter, angle);
		}
	}
	
	private void drawDisallowImage(Graphics2D g2, Point2D location)
	{
		drawImage(g2, ImageHandler.getDisallowImage(), location);
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
						drawRoads(g2, dropEdgeLoc, dropColor);
					}
					else
					{
						drawDisallowImage(g2, getEdgeCenterPoint(dropEdgeLoc));
					}
				}
			}
				break;
			case CITY:
			{
				if(dropVertLoc != null)
				{
					Point2D vertPoint = getVertexPoint(dropVertLoc.getPoint());
					
					if(dropAllowed)
					{
						List<Point2D> settlementShape = translateShape(CITY, vertPoint);
						
						Polygon polygon = toPolygon(settlementShape);
						drawGamePiece(g2, polygon, dropColor);
						
						drawGamePiece(g2, polygon, dropColor);
					}
					else
					{
						drawDisallowImage(g2, vertPoint);
					}
				}
			}
				break;
			case SETTLEMENT:
			{
				if(dropVertLoc != null)
				{
					Point2D vertPoint = getVertexPoint(dropVertLoc.getPoint());
					
					if(dropAllowed)
					{
						List<Point2D> settlementShape = translateShape(SETTLEMENT, vertPoint);
						
						Polygon polygon = toPolygon(settlementShape);
						drawGamePiece(g2, polygon, dropColor);
						
						drawGamePiece(g2, polygon, dropColor);
					}
					else
					{
						drawDisallowImage(g2, vertPoint);
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
						Point2D hexPoint = getHexCenterPoint(dropHexLoc);
						BufferedImage robberImage = ImageHandler.getRobberImage();
						drawImage(g2, robberImage, hexPoint);
					}
					else
					{
						drawDisallowImage(g2, getHexCenterPoint(dropHexLoc));
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
	
	private double getPortRotation(Point2D edge, Point2D hex)
	{
		if (Math.abs(edge.getX() - hex.getX()) < 0.1)
		{
			if (edge.getY() > hex.getY())
				return 0;
			else
				return Math.PI;
		}
		else
		{
			double slope = (edge.getY() - hex.getY()) /
					(edge.getX() - hex.getX());
			
			//This ensures things are flipped the right way.
			if (edge.getX() > hex.getX())
				return Math.atan(slope) + Math.PI * 3.0 / 2.0;
			else
				return Math.atan(slope) + Math.PI / 2.0;
		}
	}
	
	private static Point2D getHexCenterPoint(Hex hex)
	{	
		int wCenterY = WORLD_HEIGHT / 2;
		
		double hX = hex.getPoint().getX();
		double hY = hex.getPoint().getY();
		
		int wX = (int)(((3 * hX + 2) * HEX_IMAGE_WIDTH) / 4.0);
		int wY = wCenterY + (int)(hY * HEX_IMAGE_HEIGHT / 2.0);
		
		return new Point2D.Double(wX, wY);
	}
	
	private Coordinate getClosestHexCoordinate(Point2D point)
	{
		int wCenterY = WORLD_HEIGHT / 2;
		
		double wX = point.getX();
		double wY = point.getY();
		
		double hX = ((4.0 * wX / HEX_IMAGE_WIDTH) - 2) / 3.0;
		double hY = (2 * (wY - wCenterY)) / HEX_IMAGE_HEIGHT;
		
		int iX = (int)Math.round(hX);
		int iY = (int)Math.round(hY);
		
		return new Coordinate (iX, iY);
	}
	
	private Map<Double, Vertex> getSortedVerticies(Point2D point, Hex hex)
	{
		Map<Double, Vertex> result = new TreeMap<Double, Vertex>();
		
		Iterator<Vertex> verticies = model.GetVerticies(hex);
		while (verticies.hasNext())
		{
			Vertex vertex = verticies.next();
			Point2D vertexPoint = getVertexPoint(vertex.getPoint());
			
			double distance = vertexPoint.distance(point);
			
			//This is in the (hopefully) off chance of getting the same
			//distance for multiple vertices. The odds of getting dead
			//center or midpoints is kind of slim though (I hope).
			while (result.containsKey(distance))
				distance += .0001;
			
			result.put(distance, vertex);
		}
		
		return result;
	}
	
	private static Point2D getEdgeCenterPoint(Edge edge)
	{
		Coordinate start = edge.getStart();
		Coordinate end = edge.getEnd();
		
		Point2D startPoint = getVertexPoint(start);
		Point2D endPoint = getVertexPoint(end);
		
		return average(startPoint, endPoint);
	}
	
	private static double getEdgeAngle(Edge edge)
	{
		Coordinate start = edge.getStart();
		Coordinate end = edge.getEnd();
		
		Point2D startPoint = getVertexPoint(start);
		Point2D endPoint = getVertexPoint(end);
		
		double slope = (startPoint.getY() - endPoint.getY()) / 
				(startPoint.getX() - endPoint.getX());
		
		return Math.atan(slope);
	}
	
	private static Point2D getVertexPoint(Coordinate point)
	{
		double wCenterY = WORLD_HEIGHT / 2;
		double x = 0;
		double y;
		
		if (point.isRightHandCoordinate())
			x = HEX_IMAGE_WIDTH / 4.0;
		
		x += point.getX() * 0.75 * HEX_IMAGE_WIDTH;
		y = wCenterY + point.getY() * HEX_IMAGE_HEIGHT / 2.0;
		
		return new Point2D.Double(x, y);
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
	
	private static List<Point2D> rotateShape(List<Point2D> points, double radians)
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
	
	private static List<Point2D> translateShape(List<Point2D> points, Point2D delta)
	{
		
		List<Point2D> result = new ArrayList<Point2D>();
		
		for (Point2D pt : points)
		{
			result.add(add(pt, delta));
		}
		
		return result;
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

