package shared.networking.transport;

public class NetHexLocation
{
	int x;
	int y;
	
	/**
	 * Default constructor, sets x and y to 0
	 */
	public NetHexLocation(){
		x = 0;
		y = 0;
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
	
	

}
