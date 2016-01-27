package shared.networking.transport;

import java.util.List;

public class NetMap
{
	List<NetHex> netHexes;
	List<NetRoad> netRoads;
	List<NetCity> netCities;
	List<NetSettlement> netSettlements;
	List<NetPort> netPorts;
	int radius;
	NetHexLocation robberLocation;
	
	/**
	 * @return the netHexes
	 */
	public List<NetHex> getNetHexes() {
		return netHexes;
	}
	/**
	 * @param netHexes the netHexes to set
	 */
	public void setNetHexes(List<NetHex> netHexes) {
		this.netHexes = netHexes;
	}
	/**
	 * @return the netRoads
	 */
	public List<NetRoad> getNetRoads() {
		return netRoads;
	}
	/**
	 * @param netRoads the netRoads to set
	 */
	public void setNetRoads(List<NetRoad> netRoads) {
		this.netRoads = netRoads;
	}
	/**
	 * @return the netCities
	 */
	public List<NetCity> getNetCities() {
		return netCities;
	}
	/**
	 * @param netCities the netCities to set
	 */
	public void setNetCities(List<NetCity> netCities) {
		this.netCities = netCities;
	}
	/**
	 * @return the netSettlements
	 */
	public List<NetSettlement> getNetSettlements() {
		return netSettlements;
	}
	/**
	 * @param netSettlements the netSettlements to set
	 */
	public void setNetSettlements(List<NetSettlement> netSettlements) {
		this.netSettlements = netSettlements;
	}
	/**
	 * @return the ports
	 */
	public List<NetPort> getNetPorts() {
		return netPorts;
	}
	/**
	 * @param ports the ports to set
	 */
	public void setNetPorts(List<NetPort> ports) {
		this.netPorts = ports;
	}
	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}
	/**
	 * @return the robberLocation
	 */
	public NetHexLocation getRobberLocation() {
		return robberLocation;
	}
	/**
	 * @param robberLocation the robberLocation to set
	 */
	public void setRobberLocation(NetHexLocation robberLocation) {
		this.robberLocation = robberLocation;
	}
	
	
	
}
