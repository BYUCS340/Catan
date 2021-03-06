package shared.model.map;

import java.io.Serializable;

/**
 * Stores a X, Y coordinate pair.
 * @author Jonathan Sadler
 *
 */
public class Coordinate implements Serializable 
{
	private static final long serialVersionUID = 6157592562349085452L;
	
	private int x;
	private int y;
	
	/**
	 * Creates a coordinate object.
	 * @param x X Coordinate
	 * @param y Y Coordinate
	 */
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the coordinate above the current coordinate.
	 * @return The coordinate.
	 */
	public Coordinate GetNorth()
	{
		return new Coordinate(x, y + 1);
	}
	
	/**
	 * Returns the coordinate above and to the right of the current coordinate.
	 * @return The coordinate.
	 */
	public Coordinate GetNorthEast()
	{
		return new Coordinate(x + 1, y + 1);
	}
	
	/**
	 * Returns the coordinate to the right of the current coordinate.
	 * @return The coordinate.
	 */
	public Coordinate GetEast()
	{
		return new Coordinate(x + 1, y);
	}
	
	/**
	 * Returns the coordinate below and to the right of the current coordinate.
	 * @return The coordinate.
	 */
	public Coordinate GetSouthEast()
	{
		return new Coordinate(x + 1, y - 1);
	}
	
	/**
	 * Returns the coordinate below the current coordinate.
	 * @return The coordinate.
	 */
	public Coordinate GetSouth()
	{
		return new Coordinate(x, y - 1);
	}
	
	/**
	 * Returns the coordinate below and to the left of the current coordinate.
	 * @return The coordinate.
	 */
	public Coordinate GetSouthWest()
	{
		return new Coordinate(x - 1, y - 1);
	}
	
	/**
	 * Returns the coordinate to the left of the current coordinate.
	 * @return The coordinate.
	 */
	public Coordinate GetWest()
	{
		return new Coordinate(x - 1, y);
	}
	
	/**
	 * Returns the coordinate above and to the left of the current coordinate.
	 * @return The coordinate.
	 */
	public Coordinate GetNorthWest()
	{
		return new Coordinate(x - 1, y + 1);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Determines if the coordinate is left handed.
	 * @return True if yes, else false.
	 */
	public boolean isLeftHandCoordinate()
	{
		return Math.abs((x + y) % 2) == 1;
	}
	
	/**
	 * Determines if the coordinate is right handed.
	 * @return True is yes, else false.
	 */
	public boolean isRightHandCoordinate()
	{
		return (x + y) % 2 == 0;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		
		return true;
	}

	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
}
