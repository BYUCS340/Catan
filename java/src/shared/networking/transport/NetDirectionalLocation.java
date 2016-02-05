package shared.networking.transport;

import shared.definitions.Direction;

public class NetDirectionalLocation
{
	int x;
	int y;
	Direction direction;
	
	/**
	 * Default constructor, sets x and y to 0 and direction to null
	 */
	public NetDirectionalLocation()
	{
		x = 0;
		y = 0;
		direction = null;
	}

	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection()
	{
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
	
	
}
