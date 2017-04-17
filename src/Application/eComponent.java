package Application;

import java.awt.*;
import java.awt.geom.*;

import framework.*;


// HÄR KOMMER MASSA KOMMENTARER
public class eComponent implements Node {
	
	private double x;
	private double y;
	private double size;
	private Color color;
	private static final int DEFAULT_SIZE = 20;
	
	public eComponent(Color aColor) {
		size = DEFAULT_SIZE;
		x = 0;
		y = 0;
		color = aColor;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException exception) {
			return null;
		}
	}

	public void draw(Graphics2D g2) {
		Rectangle2D square = new Rectangle2D.Double(x, y, size, size);
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
		return new Rectangle2D.Double(x, y, size, size);
	}

	
	
	
	@Override
	public boolean contains(Point2D aPoint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point2D getConnectionPoint(Point2D aPoint) {
		// TODO Auto-generated method stub
		return null;
	}
}
