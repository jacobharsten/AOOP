package Application;

import java.awt.*;
import java.awt.geom.*;

import framework.*;

public class CircleComponent implements Node {

	private double x;
	private double y;
	private double size;
	private Color color;
	private boolean dot_inside;
	private static final int DEFAULT_SIZE = 70;
	private int rHeight = 5;
	private int rWidth = 5;
	private String name;

	public CircleComponent(Color aColor, int width, int height, boolean dot) {
		size = DEFAULT_SIZE;
		rWidth = width;
		rHeight = height;
		dot_inside = dot;
		x = 0;
		y = 0;
		color = aColor;
		name = "";
	}
	public String getText(){
		return name;
	}
	public void setText(String txt){
		name = txt;
	}
	public boolean getBorder(){
		return dot_inside;
	}
	public void setBorder(boolean a){
		dot_inside = a;
	}
	public Color getColor(){
		return color;
	}
	public void setColor(Color new_color){
		color = new_color;
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

	public int getHeight(){
		return rHeight;
	}
	public void setHeight(int new_height){
		rHeight = new_height;
	}

	public int getWidth(){
		return rWidth;
	}
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
