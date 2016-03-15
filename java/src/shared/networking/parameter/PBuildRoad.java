package shared.networking.parameter;

import java.io.Serializable;

import shared.model.map.Coordinate;

public class PBuildRoad implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5960849814196160677L;
	Coordinate start;
	Coordinate end;
	boolean free;

	
	/**
	 * @return the free
	 */
	public boolean isFree() {
		return free;
	}
	/**
	 * @param free the free to set
	 */
	public void setFree(boolean free) {
		this.free = free;
	}
	/**
	 * @return the start
	 */
	public Coordinate getStart()
	{
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(Coordinate start)
	{
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public Coordinate getEnd()
	{
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(Coordinate end)
	{
		this.end = end;
	}
}
