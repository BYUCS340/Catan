package client.map.view.helpers;

import shared.definitions.HexType;
import shared.definitions.PortType;

/**
 * This class stores the location of the image files for the map.
 * @author Jonathan Sadler
 *
 */
public class ImageLocation
{
	/**
	 * Gets the relative location for hex images.
	 * @param hexType The type of hex desired.
	 * @return The relative file path as a string.
	 */
	public static String getHexImageFile(HexType hexType)
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
	
	/**
	 * Gets the relative location for port file images.
	 * @param portType The type of port desired.
	 * @return The relative file path as a string.
	 */
	public static String getPortImageFile(PortType portType)
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
	
	/**
	 * Gets the relative path of the number image files.
	 * @param num The number image file desired (except 7 ... and anything
	 * 			  that can't be roled with two 6 sided dice).
	 * @return The relative file path as a string.
	 */
	public static String getNumberImageFile(int num)
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
	
	/**
	 * Gets the relative location of the robber file.
	 * @return The relative file path as a string.
	 */
	public static String getRobberImageFile()
	{
		return "images/misc/robber.gif";
	}
	
	/**
	 * Gets the relative location of the disallow image file.
	 * @return The relative file path as a string.
	 */
	public static String getDisallowImageFile()
	{
		return "images/misc/noIcon.png";
	}
}
