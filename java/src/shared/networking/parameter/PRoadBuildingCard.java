package shared.networking.parameter;

import java.io.Serializable;

import shared.model.map.Coordinate;

/**
 * 
 * @author pbridd
 *
 */
public class PRoadBuildingCard implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2284077430240273139L;
	private Coordinate start1;
	private Coordinate start2;
	private Coordinate end1;
	private Coordinate end2;
	
	public PRoadBuildingCard()
	{
		
	}
	
	/**
	 * @param start1
	 * @param start2
	 * @param end1
	 * @param end2
	 */
	public PRoadBuildingCard(Coordinate start1, Coordinate start2, Coordinate end1, Coordinate end2)
	{
		super();
		this.start1 = start1;
		this.start2 = start2;
		this.end1 = end1;
		this.end2 = end2;
	}
	/**
	 * @return the start1
	 */
	public Coordinate getStart1()
	{
		return start1;
	}
	/**
	 * @param start1 the start1 to set
	 */
	public void setStart1(Coordinate start1)
	{
		this.start1 = start1;
	}
	/**
	 * @return the start2
	 */
	public Coordinate getStart2()
	{
		return start2;
	}
	/**
	 * @param start2 the start2 to set
	 */
	public void setStart2(Coordinate start2)
	{
		this.start2 = start2;
	}
	/**
	 * @return the end1
	 */
	public Coordinate getEnd1()
	{
		return end1;
	}
	/**
	 * @param end1 the end1 to set
	 */
	public void setEnd1(Coordinate end1)
	{
		this.end1 = end1;
	}
	/**
	 * @return the end2
	 */
	public Coordinate getEnd2()
	{
		return end2;
	}
	/**
	 * @param end2 the end2 to set
	 */
	public void setEnd2(Coordinate end2)
	{
		this.end2 = end2;
	}
		
}
