package Application;

import java.awt.*;
import java.awt.geom.*;

import framework.*;

public class CircleNode implements Node {

	private double x;
	private double y;
	private double size;
	private Color color;
	private static final int DEFAULT_SIZE = 70;
	private static final int rHeight = 10;
	private static final int rWidth = 10;

	/**
	 * Construct a CircleNode 
	 * @param aColor
	 */
	public CircleNode(Color aColor) {
		size = DEFAULT_SIZE;
		x = 0;
		y = 0;
		color = aColor;
	}
	
	/**
	 * Gets the color of the CircleComponent.
	 * @return Color of the CircleComponent
	 */
	public Color getColor(){
		return color;
	}
	/**
	 * Sets a new color of the CircleComponent.
	 * @param new_color is the new color of the CircleComponent
	 */
	public void setColor(Color new_color){
		color = new_color;
	}
	/**
	 * Returns the value of the x-coordinate.
	 * @return x-coordinate value
	 */
	public double getX(){
		return x;
	}
	/**
	 * Set a new value of x-coordinate.
	 * @param newX the new value in x-coordinate
	 */
	public void setX(double newX){
		x = newX;
	}
	/**
	 * Returns the value of the object in y-coordinate.
	 * @return y-coordinate value
	 */
	public double getY(){
		return y;
	}
	/**
	 * Set a new value of y-coordinate.
	 * @param newX the new value in y-coordinate
	 */
	public void setY(double newY){
		y = newY;
	}
	
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException exception) {
			return null;
		}
	}

	public void draw(Graphics2D g2) {
		Ellipse2D square = new Ellipse2D.Double(x, y, rWidth, rHeight);
		Color oldColor = g2.getColor();
		g2.setColor(color);
		g2.fill(square);
		g2.setColor(oldColor);
		g2.draw(square);
	}

	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}

	public Rectangle2D getBounds() {
		return new Rectangle2D.Double(x, y, rWidth, rHeight);
	}

	@Override
	public boolean contains(Point2D aPoint) {
		Rectangle2D square = new Rectangle2D.Double(x, y, rWidth, rHeight);
		return square.contains(aPoint);
	}

	@Override
	public Point2D getConnectionPoint(Point2D aPoint) {
		double centerX = x + (rWidth / 2);
		double centerY = y + (rHeight / 2);
		double dx = aPoint.getX() - centerX;
		double dy = aPoint.getY() - centerY;
		double distance = Math.sqrt(dx * dx + dy * dy);
		if(distance == 0) return aPoint;
		else return new Point2D.Double(centerX + dx * (rWidth / 2) / distance, centerY + dy * (rHeight / 2) / distance);
	}
}
