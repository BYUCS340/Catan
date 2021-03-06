package client.map.view.helpers;

/**
 * This class stores the dimensions used by the map component. It's easier to read and access
 * in a seperate class.
 * @author Jonathan Sadler
 *
 */
public class Dimensions
{
	public static final int HEX_IMAGE_WIDTH = 298;
	public static final int HEX_IMAGE_HEIGHT = 258;
	
	public static final int WORLD_WIDTH = HEX_IMAGE_WIDTH * 5 + HEX_IMAGE_WIDTH / 2;
	public static final int WORLD_HEIGHT = HEX_IMAGE_HEIGHT * 7;
	
	public static final int ROBBER_WIDTH = 200;
	public static final int ROBBER_HEIGHT = 200;
	
	public static final int PORT_WIDTH = 200;
	public static final int PORT_HEIGHT = 200;
	
	public static final int ROAD_HEIGHT = (int)(HEX_IMAGE_WIDTH * 0.11);
	public static final int ROAD_WIDTH = (int)(HEX_IMAGE_WIDTH * 0.4);
	
	public static final int SETTLEMENT_WIDTH = (int)(HEX_IMAGE_WIDTH * 0.2);
	public static final int SETTLEMENT_WALL_HEIGHT = (int)(SETTLEMENT_WIDTH * 0.7);
	public static final int SETTLEMENT_ROOF_HEIGHT = SETTLEMENT_WIDTH;
	
	public static final int CITY_WIDTH = (int)(HEX_IMAGE_WIDTH * 0.25);
	public static final int CITY_WALL_HEIGHT = (int)(CITY_WIDTH * 0.7);
	public static final int CITY_ROOF_HEIGHT = (int)(CITY_WIDTH * 1.0);
}
