package shared.definitions;

import java.awt.Color;

public enum CatanColor
{
	RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN;
	
	private Color color;
	
	static
	{
		RED.color = new Color(227, 66, 52);
		ORANGE.color = new Color(255, 165, 0);
		YELLOW.color = new Color(253, 224, 105);
		BLUE.color = new Color(111, 183, 246);
		GREEN.color = new Color(109, 192, 102);
		PURPLE.color = new Color(157, 140, 212);
		PUCE.color = new Color(204, 136, 153);
		WHITE.color = new Color(223, 223, 223);
		BROWN.color = new Color(161, 143, 112);
	}
	
	public Color getJavaColor()
	{
		return color;
	}
	
	public static String toString(CatanColor type)
	{
		switch(type)
		{
			case RED: return "red";
			case ORANGE: return "orange";
			case YELLOW: return "yellow";
			case BLUE: return "blue";
			case GREEN: return "green";
			case PURPLE: return "purple";
			case PUCE: return "puce";
			case WHITE: return "white";
			case BROWN: return "brown";
		}
		return "unknown";
	}
	
	public static CatanColor fromString(String s)
	{
		switch(s.toLowerCase())
		{
			case "red": return CatanColor.RED;
			case "orange" : return CatanColor.ORANGE;
			case "yellow": return CatanColor.YELLOW;
			case "blue": return CatanColor.BLUE;
			case "green": return CatanColor.GREEN;
			case "purple": return CatanColor.PURPLE;
			case "puce" : return CatanColor.PUCE;
			case "white": return CatanColor.WHITE;
			case "brown": return CatanColor.BROWN;
			
		}
		return null;
	}
}

