package Application;

import java.awt.*;
import java.awt.geom.*;

import framework.*;

public class Gates implements Node {

	private double x;
	private double y;
	private double size;
	private Color color;
	private static final int DEFAULT_SIZE = 70;
	private int rHeight = 20;
	private int rWidth = 50;
	private int rows;

	public Gates(Color aColor, int mRows) {
		if(mRows > 8){
			mRows = 8;
		}
		size = DEFAULT_SIZE;
		rows = mRows;
		rHeight = adjustHeight(mRows);
		x = 0;
		y = 0;
		color = aColor;
	}
	
	public int adjustHeight(int r){
		int result = 0;
		if(r < 2){
		result = 20 * r;
		}
		else {
			result = 24 * r;
		}
		return result;
	}
	public String toString() {
		return "[Color: " + this.color.toString();
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
		g2.fill(square);
		g2.draw(square);
		g2.setColor(oldColor);
		for(int i = 0; i<rows; i++){
			Ellipse2D circle = new Ellipse2D.Double(x-15, y+5+25*i, 10, 10);
			g2.setColor(Color.BLACK);
			g2.fill(circle);
			g2.draw(circle);
			
			Ellipse2D circleInside = new Ellipse2D.Double(x-13.5, y+6.25+25*i, 7, 7);
			g2.setColor(Color.GREEN);
			g2.fill(circleInside);
			g2.draw(circleInside);

			Ellipse2D circle2 = new Ellipse2D.Double(x+5+rWidth, y+5+25*i, 10, 10);
			g2.setColor(Color.BLACK);
			g2.fill(circle2);
			g2.draw(circle2);
			
			Ellipse2D circleInside2 = new Ellipse2D.Double(x+6.5+rWidth, y+6.25+25*i, 7, 7);
			g2.setColor(Color.GREEN);
			g2.fill(circleInside2);
			g2.draw(circleInside2);
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
		double dx = aPoint.getX() + rWidth/2 + 10;
		double dy = aPoint.getY();
		return new Point2D.Double(dx, dy);
		
		
	}
}
