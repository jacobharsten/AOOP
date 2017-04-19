package Application;

import java.awt.*;
import java.awt.geom.*;

import framework.*;

public class ResistorComp implements Node {

	private double x;
	private double y;
	private double size;
	private Color color;
	private static final int DEFAULT_SIZE = 70;
	private int rHeight = 15;
	private int rWidth = 40;


	public ResistorComp() {
		x = 0;
		y = 0;
	}

	public String toString() {
		return "Resistor";
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
		g2.drawString("Resistor", (int)square.getMinX(), (int)square.getMinY()-2);
		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(2));
		g2.draw(square);
		g2.setColor(oldColor);
		Ellipse2D circle = new Ellipse2D.Double(square.getMinX()-15, y+2, 10, 10);
		g2.setColor(Color.RED);
		g2.fill(circle);
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.GRAY);
		g2.draw(circle);
		Ellipse2D circle2 = new Ellipse2D.Double(square.getMaxX()+5, y+2, 10, 10);
		g2.setColor(Color.RED);
		g2.fill(circle2);
		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(1));
		g2.draw(circle2);

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
