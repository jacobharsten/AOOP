package framework;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;

public interface Node extends Cloneable, Serializable {

	/**
	 Draws the node.
	 @param g2 the graphics context
	 */
	void draw(Graphics2D g2);

	/**
	 * Translates the node in given amount.
	 * @param dx the amount to translate in x-direction
	 * @param dy the amount to translate in y-direction
	 */
	void translate(double dx, double dy);
	
	/**
	 * Tests if the node contains a point.
	 * @param aPoint the point to test
	 * @return true if the node contains aPoint
	 */
	boolean contains(Point2D aPoint);
	
	/**
	 * Gets the connection point of the node.
	 * @param aPoint an point that is to be joined with this node                                                                
	 * @return the recommended connection point
	 */
	Point2D getConnectionPoint(Point2D aPoint);
	
	/**
	 * Gets the bounding rectangle of the shape of this node.
	 * @return the bounding rectangle.
	 */
	Rectangle2D getBounds();

	Object clone();
}
