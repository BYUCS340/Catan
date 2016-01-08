package client.points;

import java.awt.*;

import client.base.*;
import client.utils.*;

/**
 * Implementation for the points view, which displays the user's victory points
 */
@SuppressWarnings("serial")
public class PointsView extends ComponentView implements IPointsView
{
	
	private final int TOP_MARGIN = 40;
	private final int IMAGE_SPACING = 5;
	private final float FULL_IMAGE_SCALE = 0.25f;
	private final float EMPTY_IMAGE_SCALE = 0.25f;
	private final int MAX_POINTS = 10;
	
	private Image fullPointImage;
	private Image emptyPointImage;
	
	private int points;
	
	public PointsView()
	{
		
		this.setBackground(Color.white);
		
		this.setPreferredSize(new Dimension(100, 700));
		
		fullPointImage = ImageUtils.loadImage("images/victory_points/full_point.png");
		emptyPointImage = ImageUtils.loadImage("images/victory_points/empty_point.png");
		
		this.setPoints(0);
	}
	
	@Override
	public IPointsController getController()
	{
		return (IPointsController)super.getController();
	}
	
	public void setPoints(int points)
	{
		
		if(0 <= points && points <= MAX_POINTS)
		{
			this.points = points;
			this.repaint();
		}
		else
		{
			throw new IllegalArgumentException("invalid points value");
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(this.getBackground());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		int y = TOP_MARGIN;
		y = drawImages(g2, fullPointImage, FULL_IMAGE_SCALE, points, y);
		y = drawImages(g2, emptyPointImage, EMPTY_IMAGE_SCALE, MAX_POINTS
															   - points, y);
		
	}
	
	private int drawImages(Graphics2D g2, Image image, float scale, int count,
						   int y)
	{
		
		int midX = this.getWidth() / 2;
		
		int scaledImageWidth = (int)(image.getWidth(null) * scale);
		int scaledImageHeight = (int)(image.getHeight(null) * scale);
		
		int x = midX - scaledImageWidth / 2;
		
		for (int i = 0; i < count; ++i)
		{
			
			g2.drawImage(image, x, y, x + scaledImageWidth,
						 y + scaledImageHeight, 0, 0, image.getWidth(null),
						 image.getHeight(null), null);
			
			y += scaledImageHeight + IMAGE_SPACING;
		}
		
		return y;
	}
	
}

