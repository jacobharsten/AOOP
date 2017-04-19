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
	private int connected;

	public Gates(Color aColor, int mRows) {
		if(mRows > 8){
			mRows = 8;
		}
		size = DEFAULT_SIZE;
		rows = mRows;
		rHeight = adjustHeight(mRows);
		x = 0;
		y = 0;
		connected = 0;
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
	public int getRows(){
		return rows;
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
		g2.setColor(Color.YELLOW);
		g2.setStroke(new BasicStroke(4));
	//	g2.fill(square);
		g2.draw(square);
		g2.setColor(oldColor);
		for(int i = 0; i<rows; i++){
			Ellipse2D circle = new Ellipse2D.Double(x-15, y+5+25*i, 10, 10);
			g2.setColor(Color.YELLOW);
			g2.fill(circle);
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.RED);
			g2.draw(circle);

			Ellipse2D circle2 = new Ellipse2D.Double(x+5+rWidth, y+5+25*i, 10, 10);
			g2.setColor(Color.YELLOW);
			g2.fill(circle2);
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(2));
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
		double x1 = getBounds().getMaxX();
		double x2 = aPoint.getX();
		if(x1 < x2){
			return new Point2D.Double(getBounds().getMaxX()+10, getBounds().getCenterY());
		}
		else {
			return new Point2D.Double(getBounds().getMinX()-10, getBounds().getCenterY());
		}
	}
}
