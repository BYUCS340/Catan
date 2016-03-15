package shared.networking.parameter;

import java.io.Serializable;

import shared.definitions.CatanColor;

public class PJoinGame implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1732160358083131845L;
	private int id;
	private CatanColor color;
	
	public PJoinGame()
	{
		
	}
	
	/**
	 * @param id
	 * @param color
	 */
	public PJoinGame(int id, CatanColor color)
	{
		super();
		this.id = id;
		this.color = color;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(CatanColor color) {
		this.color = color;
	}
	
	
}
