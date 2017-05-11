package Application;

import java.awt.*;
import java.awt.geom.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


import framework.*;

public class ChipIcon implements Node, Serializable {

	private double x;
	private double y;
	private int rHeight = 16;
	private int rWidth = 48;
	private int rows;
	private String name;
	private Color border_color;
	private Color circle_inside;
	private Color circle_border;
	private boolean[] active_pin_l;
	private boolean[] active_pin_r;

	/**
	 * Construct a ChipIcon node with given row size. 
	 * @param mRows the row height.
	 * @postcondition mRows < 4
	 */
	public ChipIcon(int mRows) {
		if(mRows > 4){
			mRows = 3;
		}
		rows = mRows;
		rHeight = adjustHeight(mRows);
		x = 0;
		y = 0;

		border_color = Colors.DARKGREEN.getColor();
		circle_inside = Color.BLACK;
		circle_border = Colors.GREEN.getColor();

		name = "";
		active_pin_l = new boolean[3];
		active_pin_r = new boolean[3];
	}

	/**
	 * Adjust the height of the ChipIcon.
	 * @param r amount of rows wanted
	 * @return the row multiplied by 24
	 */
	public int adjustHeight(int r){
		if(r == 1)return 16;
		return 24 * r;
	}

	/**
	 * @return name of the Chip with leg numbers
	 */
	public String toString() {
		return "Chip with + " + rows*2 + " legs";
	}
	/**
	 * Gets the rows.
	 * @return row numbers
	 */
	public int getRows(){
		return rows;
	}
	/**
	 * Set a new value of rows.
	 * @param newRows new value of rows.
	 * @postcondition newRows < 4
	 */
	public void setRows(int newRows){
		if(newRows > 4){
			newRows = 3;
		}
		rows = newRows;
		rHeight = adjustHeight(rows);
	}

	/**
	 * Gets the name of the ChipIcon
	 * @return the name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Set a new name to the ChipIcon
	 * @param n the new name
	 */
	public void setName(String n){
		name = n;
	}
	/**
	 * Gets the color of the border.
	 * @return the color of the border
	 */
	public Color getBorderColor(){
		return border_color;
	}
	/**
	 * Set a new color of the border of the ChipIcon.
	 * @param new_color the new color of the border
	 */
	public void setBorderColor(Color new_color){
		border_color = new_color;
	}

	/**
	 * Get the circle inside of the borders color.
	 * @return circle_inside color
	 */
	public Color getCircleColor(){
		return circle_inside;
	}

	/**
	 * Set a new circle color.
	 * @param new_color is the new color of the circle
	 */
	public void setCircleColor(Color new_color){
		circle_inside = new_color;
	}

	/**
	 * Gets the circle border color.
	 * @return the circle border color
	 */
	public Color getCircleBorderColor(){
		return circle_border;
	}
	/**
	 * Sets a new color of the circle border.
	 * @param new_color new color on the circles border
	 */
	public void setCircleBorderColor(Color new_color){
		circle_border = new_color;
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

	public void draw(Graphics2D g2) {
		Rectangle2D square = new Rectangle2D.Double(x, y, rWidth, rHeight);
		Color oldColor = g2.getColor();
		g2.setColor(border_color);
		g2.setStroke(new BasicStroke(2));
		g2.draw(square);
		g2.setColor(Color.BLACK);
		g2.drawString(name, (int)square.getMinX()+2, (int)square.getCenterY());
		g2.setColor(oldColor);
		for(int i = 0; i<rows; i++){
			Ellipse2D circle = new Ellipse2D.Double(x-15, y+5+25*i, 10, 10);
			if(active_pin_l[i]){
				g2.setColor(Color.GREEN);
				active_pin_l[i] = false;
			}
			else {
				g2.setColor(circle_inside);
			}
			g2.fill(circle);
			g2.setStroke(new BasicStroke(3));
			g2.setColor(circle_border);
			g2.draw(circle);

			Ellipse2D circle2 = new Ellipse2D.Double(x+5+rWidth, y+5+25*i, 10, 10);
			if(active_pin_r[i]){
				g2.setColor(Color.GREEN);
				active_pin_r[i] = false;
			}
			else {
				g2.setColor(circle_inside);
			}
			g2.fill(circle2);
			g2.setColor(circle_border);
			g2.setStroke(new BasicStroke(3));
			g2.draw(circle2);
		}

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
		double x1 = getBounds().getCenterX();
		double x2 = aPoint.getX();
		double center_y1 = getBounds().getCenterY() + 15;
		double center_y2 = getBounds().getCenterY() - 15;
		double point_y1 = aPoint.getY();
		double new_y = getBounds().getCenterY();

		if(x1 < x2){
			if(rows > 2){
				if(point_y1 > center_y1){
					new_y = getBounds().getMaxY() - 10;
					active_pin_r[2] = true;
				}
				else if(point_y1 < center_y2){
					new_y = getBounds().getMinY() + 10;
					active_pin_r[0] = true;
				}
				else  {
					new_y = getBounds().getCenterY();
					active_pin_r[1] = true;
				}
			}
			else {
				double y1 = getBounds().getCenterY();
				double y2 = aPoint.getY();
				if(y1 < y2){
					if(rows < 2) active_pin_r[0] = true;
					new_y = getBounds().getMaxY() - 10;
					active_pin_r[1] = true;
				}
				else {
					new_y = getBounds().getMinY() + 10;
					active_pin_r[0] = true;
				}
			}
			return new Point2D.Double(getBounds().getMaxX()+15, new_y);
		}
		else {
			if(rows > 2){
				if(point_y1 > center_y1){
					new_y = getBounds().getMaxY() - 10;
					active_pin_l[2] = true;
				}
				else if(point_y1 < center_y2){
					new_y = getBounds().getMinY() + 10;
					active_pin_l[0] = true;
				}
				else {
					new_y = getBounds().getCenterY();
					active_pin_l[1] = true;
				}
			}
			else{
				double y1 = getBounds().getCenterY();
				double y2 = aPoint.getY();
				if(y1 < y2){
					if(rows < 2) active_pin_l[0] = true;
					new_y = getBounds().getMaxY() - 10;
					active_pin_l[1] = true;
				}
				else {
					new_y = getBounds().getMinY() + 10;
					active_pin_l[0] = true;
				}
			}
			return new Point2D.Double(getBounds().getMinX()-15, new_y);
		}
	}
	public Object clone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (ChipIcon) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

}
