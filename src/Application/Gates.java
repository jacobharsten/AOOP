package Application;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import framework.*;

public class Gates implements Node {

	private double x;
	private double y;
	private Color color;
	private int rHeight = 20;
	private int rWidth = 48;
	private int rows;
	private String name;

	/**
	 * Constructs a Gate object with rows.
	 * @param mRows the amount of rows.
	 * @postcondition mRows < 10
	 */
	public Gates(int mRows) {
		if(mRows > 10){
			mRows = 10;
		}
		rows = mRows;
		rHeight = adjustHeight(mRows);
		x = 0;
		y = 0;
		color = Colors.GREEN.getColor();
		name = "";

	}
	/**
	 * Set a width to the object.
	 * @param r the new width
	 */
	public void setWidth(int r){
		rWidth = r;
	}
	/**
	 * Gets the width of the object.
	 * @return the width
	 */
	public int getWidth(){
		return rWidth;
	}
	/**
	 * Adjust the height of the object.
	 * @param r amount of rows
	 * @return height size of the object
	 */
	public int adjustHeight(int r){
		return r * 16;
	}

	public String toString() {
		return "name";
	}
	/**
	 * Get the amount of rows.
	 * @return the amount of rows
	 */
	public int getRows(){
		return rows;
	}
	/**
	 * Sets a value of rows.
	 * @param r value of rows
	 * @precondition r < 10
	 */
	public void setRows(int r) {
		if(r > 10){
			r = 10;
		}
		rows = r;
		rHeight = adjustHeight(rows);
	}
	/**
	 * Gets the name of the object.
	 * @return the name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Sets the name of the object
	 * @param n the name
	 */
	public void setName(String n){
		name = n;
	}

	/**
	 * Gets the color of the object.
	 * @return the color
	 */
	public Color getColor(){
		return color;
	}
	/**
	 * Sets the color to the object.
	 * @param new_color the color
	 */
	public void setColor(Color new_color){
		color = new_color;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException exception) {
			return null;
		}
	}

	public void draw(Graphics2D g2) {
		Rectangle2D square = new Rectangle2D.Double(x, y, rWidth, rHeight);
		Color oldColor = g2.getColor();
		g2.setColor(color);
		g2.setStroke(new BasicStroke(2));
		g2.drawString(name, (int)getBounds().getMinX(), (int)getBounds().getCenterY());
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
		return aPoint;
	}
}