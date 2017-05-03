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
	private double size;
	private static final int DEFAULT_SIZE = 70;
	private int rHeight = 16;
	private int rWidth = 48;
	private int rows;
	private String name;
	private Color border_color;
	private Color circle_inside;
	private Color circle_border;
	private boolean[] active_pin_l;
	private boolean[] active_pin_r;

	public ChipIcon(int mRows) {
		if(mRows > 4){
			mRows = 3;
		}
		size = DEFAULT_SIZE;
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


	public int adjustHeight(int r){
		if(r == 1)return 16;
		return 24 * r;
	}
	public String toString() {
		return "Chip with + " + rows*2 + " legs";
	}
	public int getRows(){
		return rows;
	}
	public void setRows(int newRows){
		if(newRows > 4){
			newRows = 3;
		}
		rows = newRows;
		rHeight = adjustHeight(rows);
	}
	public String getName(){
		return name;
	}
	public void setName(String n){
		name = n;
	}

	public Color getBorderColor(){
		return border_color;
	}
	public void setBorderColor(Color new_color){
		border_color = new_color;
	}
	public Color getCircleColor(){
		return circle_inside;
	}
	public void setCircleColor(Color new_color){
		circle_inside = new_color;
	}

	public Color getCircleBorderColor(){
		return circle_border;
	}
	public void setCircleBorderColor(Color new_color){
		circle_border = new_color;
	}

	public double getX(){
		return x;
	}
	public void setX(double newX){
		x = newX;
	}

	public double getY(){
		return y;
	}
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
