package framework;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;



public interface Edge extends Serializable, Cloneable {
	/**
	 * Draws the edge
	 * @param g2 the graphics context
	 */
	void draw(Graphics2D g2);

	/**
	 * Tests if the node contains a point.
	 * @param aPoint the point to test
	 * @return true if the node contains aPoint
	 */
	boolean contains(Point2D aPoint);

	/**
	 * Connects this edge to two nodes.
	 * @param aStart the starting node
	 * @param anEnd the ending node
	 */
	void connect(Node aStart, Node anEnd);
	
	/**
	 * Gets the starting node
	 * @return the starting node
	 */
	Node getStart();
	
	/**
	 * Gets the ending node
	 * @return the ending node
	 */
	Node getEnd();

	/**
	 * Gets the point at which the edge is connected to its nodes.                                                                
	 * @return a line joining two connections points.
	 */
	Line2D getConnectionPoints();

	/**
	 * Gets the rectangle that bounds this edge.
	 * @param g2 the graphics context
	 * @return the bounding rectangle
	 */
	Rectangle2D getBounds(Graphics2D g2);

	Object clone();

}
