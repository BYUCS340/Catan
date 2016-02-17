package client.map.view.dropObject;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import client.map.*;
import client.map.view.helpers.ImageHandler;
import shared.definitions.CatanColor;
import shared.model.map.Coordinate;
import shared.model.map.objects.*;

public abstract class DropObject
{
	protected IMapController controller;
	
	protected CatanColor color;
	protected boolean isAllowed;
	
	protected DropObject(IMapController controller, CatanColor color)
	{
		this.controller = controller;
		this.color = color;
	}
	
	public boolean IsAllowed()
	{
		return isAllowed;
	}
	
	public CatanColor GetColor()
	{
		return color;
	}
	
	public abstract void Handle(Point2D point);
	
	public abstract void Click();
	
	protected Coordinate GetClosestHexCoordinate(Point2D point)
	{
		//TODO Evaluate this logic
		//Likely need to get x, then handle y.
		int worldHeight = Dimensions.WORLD_HEIGHT;
		int wCenterY = worldHeight / 2;
		
		double wX = point.getX();
		double wY = point.getY();
		
		int hexWidth = ImageHandler.GetAverageHexWidth();
		int hexHeight = ImageHandler.GetAverageHexHeight();
		
		double hX = ((4.0 * wX / hexWidth) - 2) / 3.0;
		double hY = (2 * (wY - wCenterY)) / hexHeight;
		
		int iX = (int)Math.round(hX);
		int iY = (int)Math.round(hY);
		
		return new Coordinate (iX, iY);
	}
	
	protected Map<Double, Vertex> GetSortedVerticies(Point2D point, Hex hex)
	{
		Map<Double, Vertex> result = new TreeMap<Double, Vertex>();
		
		Iterator<Vertex> verticies = controller.GetModel().GetVertices(hex);
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
	
	private Point2D getVertexPoint(Coordinate point)
	{
		int worldHeight = Dimensions.WORLD_HEIGHT;
		
		double wCenterY = worldHeight / 2.0;
		double x = 0;
		double y;
		
		int hexWidth = ImageHandler.GetAverageHexWidth();
		int hexHeight = ImageHandler.GetAverageHexHeight();
		
		if (point.isRightHandCoordinate())
			x = hexWidth / 4.0;
		
		x += point.getX() * 0.75 * hexWidth;
		y = wCenterY + point.getY() * hexHeight / 2.0;
		
		return new Point2D.Double(x, y);
	}
}
