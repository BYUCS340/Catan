package shared.networking.transport;

import java.util.ArrayList;
import java.util.List;

public class NetLog
{
	List<NetLine> lines;
	
	
	/**
	 * Default constructor, instantiates a new ArrayList
	 * of Strings
	 */
	public NetLog()
	{
		lines = new ArrayList<NetLine>();
	}
	
	/**
	 * Adds a line to the net log entry list
	 * @param line The line to add to the log
	 */
	public void addLine(NetLine line)
	{
		lines.add(line);
	}

	/**
	 * @return the log
	 */
	public List<NetLine> getLines()
	{
		return lines;
	}
	
	/**
	 * Returns the number of lines
	 * @return
	 */
	public int size()
	{
		return lines.size();
	}

	/**
	 * @param the log to set
	 */
	public void setLines(List<NetLine> lines)
	{
		this.lines = lines;
	}

}
