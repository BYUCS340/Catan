package client.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import client.base.*;
import client.utils.ImageUtils;


/**
 * Implementation for the wait view, which is used to display wait dialogs to the user
 */
@SuppressWarnings({"serial", "unused"})
public class WaitView extends OverlayView implements IWaitView {

	private final int LABEL_TEXT_SIZE = 40;
	private final int BORDER_WIDTH = 10;

	private JLabel label;
	private BufferedImage img;
	private String imageLocation = "images"+File.separator + "misc"+ File.separator +"hourglass.png";

	public WaitView() {

		super();
		
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		label = new JLabel("");
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		label.setBorder(new EmptyBorder(0,20,20,20));
		this.add(label, BorderLayout.NORTH);
		img = ImageUtils.loadImage(imageLocation);
		BufferedImage scaledImage = new BufferedImage(img.getWidth()/2,img.getHeight()/2,BufferedImage.TRANSLUCENT);
		Graphics2D g2 = scaledImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(img,0,0,img.getWidth()/2,img.getHeight()/2,null);
		g2.dispose();
		JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
		imageLabel.setBorder(new EmptyBorder(0,0,20,0));
		this.add(imageLabel,BorderLayout.CENTER);
	}

	@Override
	public void setMessage(String message) {
		label.setText(message);
	}

}

