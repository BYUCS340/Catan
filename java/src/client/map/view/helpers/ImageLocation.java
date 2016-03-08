package client.map.view.helpers;

import shared.definitions.HexType;
import shared.definitions.PortType;

/**
 * This class stores the disk location of the image files for the map.
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
	 * Gets the port for a specified angle direction.
	 * @param angle The desired angle.
	 * @return The associated image.
	 */
	public static String getPortImageFile(int angle)
	{
		switch (angle)
		{
		case 0:
			return "images/ports/Ports_000.png";
		case 60:
			return "images/ports/Ports_060.png";
		case 120:
			return "images/ports/Ports_120.png";
		case 180:
			return "images/ports/Ports_180.png";
		case 240:
			return "images/ports/Ports_240.png";
		case 300:
			return "images/ports/Ports_300.png";
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
	public static String getPortResourceImageFile(PortType portType)
	{
		
		switch (portType)
		{
			case WOOD:
				return "images/resources/wood.png";
			case BRICK:
				return "images/resources/brick.png";
			case SHEEP:
				return "images/resources/sheep.png";
			case WHEAT:
				return "images/resources/wheat.png";
			case ORE:
				return "images/resources/ore.png";
			case THREE:
				return "images/ports/three.png";
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
		return "images/misc/trogdor_icon.png";
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
