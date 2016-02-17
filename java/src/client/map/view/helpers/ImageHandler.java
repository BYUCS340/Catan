package client.map.view.helpers;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import client.utils.ImageUtils;
import shared.definitions.HexType;
import shared.definitions.PortType;

/**
 * Class for storing images necessary for the map.
 * @author Jonathan Sadler
 *
 */
public class ImageHandler
{	
	private Map<HexType, BufferedImage> HEX_IMAGES;
	private Map<PortType, BufferedImage> PORT_IMAGES;
	private Map<Integer, BufferedImage> NUMBER_IMAGES;
	private BufferedImage ROBBER_IMAGE;
	private BufferedImage DISALLOW_IMAGE;
	
	private int AVERAGE_WIDTH = 0;
	private int AVERAGE_HEIGHT = 0;
	
	private ImageHandler()
	{
		//Load hex images
		HEX_IMAGES = new HashMap<HexType, BufferedImage>();
		
		int count = 0;
		for (HexType hexType : HexType.values())
		{
			BufferedImage hexImage = loadHexImage(hexType);
			HEX_IMAGES.put(hexType, hexImage);
			
			AVERAGE_WIDTH += hexImage.getWidth();
			AVERAGE_HEIGHT += hexImage.getHeight();
		}
		
		if (count != 0)
		{
			AVERAGE_WIDTH /= count;
			AVERAGE_HEIGHT /= count;
		}
		
		//Load ports
		PORT_IMAGES = new HashMap<PortType, BufferedImage>();
		
		for (PortType portType : PortType.values())
		{
			if (portType != PortType.NONE)
				PORT_IMAGES.put(portType, loadPortImage(portType));
		}
		
		//Load numbers
		NUMBER_IMAGES = new HashMap<Integer, BufferedImage>();
		
		for (int i = 2; i <= 12; ++i)
		{
			if(i != 7)
				NUMBER_IMAGES.put(i, loadNumberImage(i));
		}
		
		//Load robber and disallowed
		ROBBER_IMAGE = loadRobberImage();
		DISALLOW_IMAGE = loadDisallowImage();
	}
	
	private static ImageHandler handler;

	private static ImageHandler GetHandler()
	{
		if (handler == null)
			handler = new ImageHandler();
		
		return handler;
	}
	
	public static int GetAverageHexWidth()
	{
		return GetHandler().AVERAGE_WIDTH;
	}
	
	public static int GetAverageHexHeight()
	{
		return GetHandler().AVERAGE_HEIGHT;
	}
	
	/**
	 * Gets the image associated with a hex.
	 * @param hexType The hex type desired.
	 * @return The hex image.
	 */
	public static BufferedImage getHexImage(HexType hexType)
	{
		return GetHandler().HEX_IMAGES.get(hexType);
	}
	
	/**
	 * Gets the image associated with a port.
	 * @param portType The port type desired.
	 * @return The port image.
	 */
	public static BufferedImage getPortImage(PortType portType)
	{
		return GetHandler().PORT_IMAGES.get(portType);
	}
	
	/**
	 * Gets the number image desired.
	 * @param num The number of the image.
	 * @return The number image.
	 */
	public static BufferedImage getNumberImage(int num)
	{	
		return GetHandler().NUMBER_IMAGES.get(num);
	}
	
	/**
	 * Gets the robber image.
	 * @return The robber.
	 */
	public static BufferedImage getRobberImage()
	{
		return GetHandler().ROBBER_IMAGE;
	}
	
	/**
	 * Gets the disallow image.
	 * @return The disallow image.
	 */
	public static BufferedImage getDisallowImage()
	{
		return GetHandler().DISALLOW_IMAGE;
	}
	
	private BufferedImage loadHexImage(HexType hexType)
	{
		String imageFile = ImageLocation.getHexImageFile(hexType);
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private BufferedImage loadPortImage(PortType portType)
	{
		String imageFile = ImageLocation.getPortImageFile(portType);
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private BufferedImage loadNumberImage(int num)
	{
		String imageFile = ImageLocation.getNumberImageFile(num);
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private BufferedImage loadRobberImage()
	{	
		String imageFile = ImageLocation.getRobberImageFile();
		
		return ImageUtils.loadImage(imageFile);
	}
	
	private BufferedImage loadDisallowImage()
	{
		String imageFile = ImageLocation.getDisallowImageFile();
		
		return ImageUtils.loadImage(imageFile);
	}
}
