package shared.networking.transport;

import java.util.ArrayList;
import java.util.List;

public class NetChat
{
	List<String> lines;
	
	
	/**
	 * Default constructor, instantiates a new ArrayList
	 * of Strings
	 */
	public NetChat(){
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
