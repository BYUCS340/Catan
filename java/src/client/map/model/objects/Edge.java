package client.map.model.objects;

import shared.definitions.CatanColor;

public class Edge {

	private boolean roadExists;
	private CatanColor color;
	
	public Edge()
	{
		roadExists = false;
		color = null;
	}
	
	public void ClearRoad()
	{
		roadExists = false;
		color = null;
	}
	
	public void SetRoad(CatanColor color)
	{
		roadExists = true;
		this.color = color;
	}

	/**
	 * @return the roadExists
	 */
	public boolean doesRoadExists() {
		return roadExists;
	}

	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
}
