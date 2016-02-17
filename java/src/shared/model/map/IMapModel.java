package shared.model.map;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import shared.definitions.*;
import shared.model.map.objects.*;

public interface IMapModel
{
	/**
	 * Returns if the robber is initialized.
	 * @return True if yes, else false.
	 */
	public boolean IsRobberInitialized();
	
	/**
	 * Gets if the longest road exists.
	 * @return True if yes, else false.
	 */
	public boolean LongestRoadExists();
	
	/**
	 * Returns if a edge exists.
	 * @param p1 The start of the edge.
	 * @param p2 The end of the edge.
	 * @return True if yes, else false.
	 */
	public boolean EdgeExists(Coordinate p1, Coordinate p2);
	
	/**
	 * Returns if a vertex exists.
	 * @param point The vertex to check.
	 * @return True if yes, else false.
	 */
	public boolean VertexExists(Coordinate point);
	
	/**
	 * Returns if a hex exists.
	 * @param point The hex to check.
	 * @return True if yes, else false.
	 */
	public boolean HexExists(Coordinate point);
	
	/**
	 * Returns if a road can be placed.
	 * @param p1 The start of the road.
	 * @param p2 The end of the road.
	 * @param color The color of the road.
	 * @return True if yes, else false.
	 */
	public boolean CanPlaceRoad(Coordinate p1, Coordinate p2, CatanColor color);
	
	/**
	 * Returns if a Settlement can be placed at the specified location.
	 * @param point The point of placement.
	 * @return True if yes, else false.
	 */
	public boolean CanPlaceSettlement(Coordinate point);
	
	/**
	 * Returns if a city can be placed.
	 * @param point The coordinate of the city.
	 * @param color The color of the city.
	 * @return True if yes, else false.
	 */
	public boolean CanPlaceCity(Coordinate point, CatanColor color);
	
	/**
	 * Returns if the robber can be placed at the specified location.
	 * @param point The point to place the robber.
	 * @return True if yes, else false.
	 */
	public boolean CanPlaceRobber(Coordinate point);
	
	/**
	 * Returns if a pip can be place on a certain point.
	 * @param point The point to place the pip.
	 * @return True if yes, else false.
	 */
	public boolean CanPlacePip(Coordinate point);
	
	/**
	 * Creates a hex at the specified location.
	 * @param type The resource type associated with the hex.
	 * @param point The coordinate of the hex.
	 * @throws MapException Thrown if there is an issue adding the hex.
	 */
	public void PlaceHex(HexType type, Coordinate point) throws MapException;
	
	/**
	 * Puts a road on the map.
	 * @param p1 The start of the road.
	 * @param p2 The end of the road.
	 * @param color The color of the road.
	 * @throws MapException Thrown if issues occur adding road.
	 */
	public void PlaceRoad(Coordinate p1, Coordinate p2, CatanColor color) throws MapException;
	
	/**
	 * Adds a settlement to the map.
	 * @param point The coordinate of the settlement.
	 * @param color The color of the settlement.
	 * @throws MapException The government vetod your settlement.
	 */
	public void PlaceSettlement(Coordinate point, CatanColor color) throws MapException;
	
	/**
	 * Adds a city to the board.
	 * @param point The coordinate of the city.
	 * @param color The color of the city.
	 * @throws MapException If you pay 15% tithing, this won't happen (just kidding, your
	 * 						city couldn't be added).
	 */
	public void PlaceCity(Coordinate point, CatanColor color) throws MapException;
	
	/**
	 * Sets a vertex as a port
	 * @param type The type of port to set.
	 * @param point The coordinate of the port.
	 * @throws MapException Thrown if the port is added to a vertex that doesn't exist.
	 */
	public void PlacePort(PortType type, Coordinate hexCoordinate, 
			Coordinate edgeStart, Coordinate edgeEnd) throws MapException;
	
	/**
	 * Sets which hex the robber is on.
	 * @param hex The hex to place the robber on.
	 * @throws MapException  Thrown if the hex is invalid.
	 */
	public void PlaceRobber(Coordinate point) throws MapException;
	
	/**
	 * Adds a pip to a hex.
	 * @param value The value of the pip.
	 * @param hex The hex to which it is added.
	 * @throws MapException Thrown if the hex doesn't exist.
	 */
	public void PlacePip(int value, Coordinate point) throws MapException;
	
	/**
	 * Gets a hex at the specified location.
	 * @param point The desired coordinate.
	 * @return The hex.
	 * @throws MapException Thrown if the hex doesn't exist.
	 */
	public Hex GetHex(Coordinate point) throws MapException;
	
	/**
	 * Gets all the hexes.
	 * @return All the hexes.
	 */
	public Iterator<Hex> GetHexes();
	
	/**
	 * Gets the edge associated with the provided end points.
	 * @param p1 The start point.
	 * @param p2 The end point.
	 * @return The associated edge.
	 * @throws MapException Thrown if the edge can't be found.
	 */
	public Edge GetEdge(Coordinate p1, Coordinate p2) throws MapException;
	
	/**
	 * Gets all the edges.
	 * @return All the edges.
	 */
	public Iterator<Edge> GetEdges();
	
	/**
	 * Gets the vertex at a specified location.
	 * @param point The specified location.
	 * @return The vertex.
	 * @throws MapException Thrown if the vertex doesn't exist.
	 */
	public Vertex GetVertex(Coordinate point) throws MapException;
	
	/**
	 * Gets all the vertices.
	 * @return All the vertices.
	 */
	public Iterator<Vertex> GetVertices();
	
	/**
	 * Gets the vertices that are associated with a hex.
	 * @param hex The hex.
	 * @return The associated vertices.
	 */
	public Iterator<Vertex> GetVertices(Hex hex);
	
	/**
	 * Gets the neighbors (surrounding) vertices of a vertex.
	 * @param vertex The vertex which the neighbors are being requested.
	 * @return An iterator the the neighbors.
	 */
	public Iterator<Vertex> GetVertices(Vertex vertex);
	
	/**
	 * Gets all the ports.
	 * @return All the ports.
	 */
	public Iterator<Entry<Edge, Hex>> GetPorts();
	
	/**
	 * Gets the location of the robber.
	 * @return The robber location.
	 */
	public Hex GetRobberLocation();
	
	/**
	 * Gets all the pips.
	 * @return All the pips.
	 */
	public Iterator<Entry<Integer, List<Hex>>> GetPips();
	
	/**
	 * Gets the longest road.
	 * @return The longest road.
	 * @throws MapException Thrown if the road doesn't exist.
	 */
	public CatanColor GetLongestRoadColor() throws MapException;
	
	/**
	 * Gets the transactions associated with a role.
	 * @param role The role value.
	 * @return Transaction objects associated with role.
	 */
	public Iterator<Transaction> GetTransactions(int role);
}
