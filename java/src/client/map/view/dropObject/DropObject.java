package client.map.view.dropObject;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import client.map.*;
import client.map.view.helpers.Dimensions;
import shared.definitions.CatanColor;
import shared.model.map.Coordinate;
import shared.model.map.objects.*;

/**
 * This class handles objects that can be dragged and dropped onto the map.
 * @author Jonathan Sadler
 *
 */
public abstract class DropObject
{
	protected IMapController controller;
	
	protected CatanColor color;
	
	protected DropObject(IMapController controller, CatanColor color)
	{
		this.controller = controller;
		this.color = color;
	}
	
	/**
	 * Gets the color of the object being placed.
	 * @return The CatanColor of the object.
	 */
	public CatanColor GetColor()
	{
		return color;
	}
	
	/**
	 * Returns if the location of the object is a valid location.
	 * @return True if valid, else false.
	 */
	public abstract boolean IsValid();
	
	/**
	 * Returns if the object can be placed at the current location.
	 * @return True if yes, else false.
	 */
	public abstract boolean IsAllowed();
	
	/**
	 * Used to handle mouse movement. Gets the associated data with the new
	 * coordinate.
	 * @param point The location of the mouse.
	 */
	public abstract void Handle(Point2D point);
	
	/**
	 * Indicates the mouse button was pressed.
	 */
	public abstract void Click();
	
	protected Coordinate GetClosestHexCoordinate(Point2D point)
	{
		int worldHeight = Dimensions.WORLD_HEIGHT;
		int wCenterY = worldHeight / 2;
		
		double wX = point.getX();
		double wY = point.getY();
		
		double hexWidth = Dimensions.HEX_IMAGE_WIDTH;
		double hexHeight = Dimensions.HEX_IMAGE_HEIGHT;
		
		double hX = (((4.0 * wX) / hexWidth) - 2) / 3.0;
		double hY = (-2.0 * (wY - wCenterY)) / hexHeight;
		
		int iX = (int)Math.round(hX);
		
		//X position is consecutive where Y is not.
		//Whether Y is odd or even depends on its X location.
		int iY = (int)Math.ceil(hY);
		if (iX % 2 != 0 && iY % 2 != 0)
			iY = (int)Math.floor(hY);
		else if (iX % 2 == 0 && iY % 2 == 0)
			iY = (int)Math.floor(hY);
		
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
		
		int hexWidth = Dimensions.HEX_IMAGE_WIDTH;
		int hexHeight = Dimensions.HEX_IMAGE_HEIGHT;
		
		if (point.isRightHandCoordinate())
			x = hexWidth / 4.0;
		
		x += point.getX() * 0.75 * hexWidth;
		y = wCenterY - point.getY() * hexHeight / 2.0;
		
		return new Point2D.Double(x, y);
	}
}
