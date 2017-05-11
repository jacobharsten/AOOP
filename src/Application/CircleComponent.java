package Application;

import java.awt.*;
import java.awt.geom.*;

import framework.*;

public class CircleComponent implements Node {

	private double x;
	private double y;
	private Color color;
	private boolean dot_inside;
	private int rHeight = 5;
	private int rWidth = 5;
	private String name;

	/**
	 * Construct a CircleComponent node with color, width, height and dot.
	 * @param aColor the fill color
	 * @param width the width
	 * @param height the height
	 * @param dot if the circle should have a dot
	 */
	public CircleComponent(Color aColor, int width, int height, boolean dot) {
		rWidth = width;
		rHeight = height;
		dot_inside = dot;
		x = 0;
		y = 0;
		color = aColor;
		name = "";
	}

	/**
	 * Returns the name/text of the node.
	 * @return the name
	 */
	public String getText(){
		return name;
	}
	/**
	 * Give the node a new name/text.
	 * @param new_text sets a new name to the node
	 */
	public void setText(String new_text){
		name = new_text;
	}
	/**
	 * Gets if the CircleComponent have a border.
	 * @return true if the CircleComponent have a border
	 */
	public boolean getBorder(){
		return dot_inside;
	}
	/**
	 * Set if the CricleComponent should have a border.
	 * @param a sets a boolean value, true or false
	 */
	public void setBorder(boolean a){
		dot_inside = a;
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

	/**
	 * Get the height of the CricleComponent.
	 * @return the height of CricleComponent
	 */
	public int getHeight(){
		return rHeight;
	}
	/**
	 * Set a new height of the CircleComponent.
	 * @param new_height is the new height of the CircleComponent
	 */
	public void setHeight(int new_height){
		rHeight = new_height;
	}
	/**
	 * Get the width of the CricleComponent.
	 * @return the width of CricleComponent
	 */
	public int getWidth(){
		return rWidth;
	}
	/**
	 * Set a new width of the CircleComponent.
	 * @param new_width is the new height of the CircleComponent
	 */
	public void setWidth(int new_width){
		rWidth = new_width;
	}

	public String toString() {
		return "Connector";
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
		if(name.length() > 0){
			g2.drawString(name, (int)getBounds().getMinX(), (int)getBounds().getMinY()-2);
		}
		if(dot_inside){
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.GREEN);
		}
		g2.draw(square);
		g2.setColor(oldColor);

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
