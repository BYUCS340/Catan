package shared.networking.transport;

import java.util.ArrayList;
import java.util.List;

public class NetLog
{
	List<String> lines;
	
	/**
	 * Default constructor, instantiates new arraylist of
	 * Strings
	 */
	public NetLog(){
		lines = new ArrayList<String>();
	}
	
	/**
	 * Adds a line to the net log entry list
	 * @param line The line to add to the log
	 */
	public void addLine(String line){
		lines.add(line);
	}

	/**
	 * @return the log
	 */
	public List<String> getLines()
	{
		return lines;
	}

	/**
	 * @param the log to set
	 */
	public void setLines(List<String> lines)
	{
		this.lines = lines;
	}
}
