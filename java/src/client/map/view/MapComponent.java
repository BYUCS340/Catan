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
import client.map.IMapController;
import client.map.view.dropObject.*;
import client.map.view.helpers.Dimensions;
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
	public boolean temp = false;
	
	public static final int HEX_IMAGE_WIDTH = Dimensions.HEX_IMAGE_WIDTH;
	public static final int HEX_IMAGE_HEIGHT = Dimensions.HEX_IMAGE_HEIGHT;
	
	public static final int WORLD_WIDTH = Dimensions.WORLD_WIDTH;
	public static final int WORLD_HEIGHT = Dimensions.WORLD_HEIGHT;
	
	private static final int ROAD_HEIGHT = Dimensions.ROAD_HEIGHT;
	private static final int ROAD_WIDTH = Dimensions.ROAD_WIDTH;
	
	private static final int SETTLEMENT_WIDTH = Dimensions.SETTLEMENT_WIDTH;
	private static final int SETTLEMENT_WALL_HEIGHT = Dimensions.SETTLEMENT_WALL_HEIGHT;
	private static final int SETTLEMENT_ROOF_HEIGHT = Dimensions.SETTLEMENT_ROOF_HEIGHT;
	
	private static final int CITY_WIDTH = Dimensions.CITY_WIDTH;
	private static final int CITY_WALL_HEIGHT = Dimensions.CITY_WALL_HEIGHT;
	private static final int CITY_ROOF_HEIGHT = Dimensions.CITY_ROOF_HEIGHT;
	
	private static List<Point2D> SETTLEMENT;
	private static List<Point2D> CITY;
	private static List<Point2D> ROAD;
	
	static
	{
		ROAD = new ArrayList<Point2D>();
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
		Point2D sHighPoint = new Point2D.Double(sMidBase.getX(),
												sMidBase.getY()	- SETTLEMENT_ROOF_HEIGHT);
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
	
	//View details
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
		
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		addComponentListener(componentAdapter);
		
		scale = Double.NaN;
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

			getController().MouseMove(mousePoint);
			
			repaint();	
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			getController().MouseClick();
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
		Iterator<Hex> hexes = controller.GetHexes();
		
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
		Iterator<Entry<Integer, List<Hex>>> pips = controller.GetPips();
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
		if (controller.GetDropObject().getClass() == RobberDropObject.class)
			return;
		
		//In case the robber doesn't exist yet.
		if (!controller.IsRobberInitialized())
			return;
		
		try
		{
			Point2D hexPoint = getHexCenterPoint(controller.GetRobberPlacement());
			BufferedImage robberImage = ImageHandler.getRobberImage();
			drawImage(g2, robberImage, hexPoint, Dimensions.ROBBER_WIDTH, Dimensions.ROBBER_HEIGHT);
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
	}
	
	private void drawRoads(Graphics2D g2)
	{
		Iterator<Edge> edges = controller.GetEdges();
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
		drawGamePiece(g2, road, color);
	}
	
	private void drawVerticies(Graphics2D g2)
	{
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		
		Iterator<Vertex> verticies = controller.GetVertices();
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
		Iterator<Entry<Edge, Hex>> ports = controller.GetPorts();
		while (ports.hasNext())
		{
			Entry<Edge, Hex> port = ports.next();
			Edge edge = port.getKey();
			Hex hex = port.getValue();
			
			Point2D hexCenter = getHexCenterPoint(hex);
			
			int angle = getPortRotation(hex, edge);
			BufferedImage portImage = ImageHandler.getPortImage(angle);
			drawImage(g2, portImage, hexCenter);
			
			BufferedImage portResourceImage = ImageHandler.getPortResourceImage(hex.getPort());
			drawImage(g2, portResourceImage, hexCenter, Dimensions.PORT_WIDTH, Dimensions.PORT_HEIGHT);
		}
	}
	
	private void drawDisallowImage(Graphics2D g2, Point2D location)
	{
		drawImage(g2, ImageHandler.getDisallowImage(), location);
	}
	
	private void drawDropShape(Graphics2D g2)
	{	
		//TODO Come up with better way to do this.
		DropObject dropObject = getController().GetDropObject();
		
		if (!dropObject.IsValid())
			return;
		
		try
		{
			if (dropObject.getClass() == RoadDropObject.class)
			{
				RoadDropObject road = (RoadDropObject)dropObject;
				
				if (road.IsAllowed())
					drawRoads(g2, road.GetDropLocation(), road.GetColor());
				else
					drawDisallowImage(g2, getEdgeCenterPoint(road.GetDropLocation()));
			}
			else if (dropObject.getClass() == SettlementDropObject.class)
			{
				SettlementDropObject settlement = (SettlementDropObject)dropObject;
				Point2D vertPoint = getVertexPoint(settlement.GetDropLocation().getPoint());
				
				if (settlement.IsAllowed())
				{
					List<Point2D> settlementShape = translateShape(SETTLEMENT, vertPoint);
					
					Polygon polygon = toPolygon(settlementShape);
					drawGamePiece(g2, polygon, settlement.GetColor());
				}
				else
				{
					drawDisallowImage(g2, vertPoint);
				}
			}
			else if (dropObject.getClass() == CityDropObject.class)
			{
				CityDropObject city = (CityDropObject)dropObject;
				Point2D vertPoint = getVertexPoint(city.GetDropLocation().getPoint());
				
				if (city.IsAllowed())
				{
					List<Point2D> cityShape = translateShape(CITY, vertPoint);
					
					Polygon polygon = toPolygon(cityShape);
					drawGamePiece(g2, polygon, city.GetColor());
				}
				else
				{
					drawDisallowImage(g2, vertPoint);
				}
			}
			else if (dropObject.getClass() == RobberDropObject.class)
			{
				RobberDropObject robber = (RobberDropObject)dropObject;
				Point2D hexPoint = getHexCenterPoint(robber.GetDropLocation());
				
				if (robber.IsAllowed())
					drawImage(g2, ImageHandler.getRobberImage(), hexPoint, Dimensions.ROBBER_WIDTH, Dimensions.ROBBER_HEIGHT);
				else
					drawDisallowImage(g2, hexPoint);
			}
		}
		catch (MapException e)
		{
			e.printStackTrace();
		}
	}
	
	private void drawImage(Graphics2D g2, BufferedImage image, Point2D location)
	{
		
		int centerX = image.getWidth() / 2;
		int centerY = image.getHeight() / 2;
		
		g2.drawImage(image, (int)location.getX() - centerX,
					 (int)location.getY() - centerY, null);
	}
	
	private void drawImage(Graphics2D g2, BufferedImage image, Point2D location, 
			int width, int height)
	{
		int centerX = width / 2;
		int centerY = height / 2;
		
		int x = (int)location.getX() - centerX;
		int y = (int)location.getY() - centerY;
		
		g2.drawImage(image, x, y, width, height, null);
	}
	
	private int getPortRotation(Hex hex, Edge edge)
	{
		return edge.GetRotation(hex);
	}
	
	private static Point2D getHexCenterPoint(Hex hex)
	{	
		int wCenterY = WORLD_HEIGHT / 2;
		
		double hX = hex.getPoint().getX();
		double hY = hex.getPoint().getY();
		
		int wX = (int)(((3 * hX + 2) * HEX_IMAGE_WIDTH) / 4.0);
		int wY = wCenterY - (int)(hY * HEX_IMAGE_HEIGHT / 2.0);
		
		return new Point2D.Double(wX, wY);
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
	
	private static Point2D getVertexPoint(Coordinate point)
	{
		double wCenterY = WORLD_HEIGHT / 2.0;
		double x = 0;
		double y;
		
		if (point.isRightHandCoordinate())
			x = HEX_IMAGE_WIDTH / 4.0;
		
		x += point.getX() * 0.75 * HEX_IMAGE_WIDTH;
		y = wCenterY - point.getY() * HEX_IMAGE_HEIGHT / 2.0;
		
		return new Point2D.Double(x, y);
	}
}

