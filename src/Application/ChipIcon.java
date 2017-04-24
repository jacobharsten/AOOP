package Application;

import java.awt.*;
import java.awt.geom.*;

import framework.*;

public class ChipIcon implements Node {

	private double x;
	private double y;
	private double size;
	private static final int DEFAULT_SIZE = 70;
	private int rHeight = 20;
	private int rWidth = 50;
	private int rows;

	private boolean[] active_r_color;
	private boolean[] active_l_color;


	public ChipIcon(int mRows) {
		if(mRows > 4){
			mRows = 3;
		}
		size = DEFAULT_SIZE;
		rows = mRows;
		rHeight = adjustHeight(mRows);
		x = 0;
		y = 0;
	}

	public int adjustHeight(int r){
		int result = 0;
		if(r < 2){
			result = 24 * r;
		}
		else {
			result = 24 * r;
		}
		return result;
	}
	public String toString() {
		return "Chip with + " + rows*2 + " legs";
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
		g2.setColor(Colors.DARKGREEN.getColor());
		g2.setStroke(new BasicStroke(2));
		g2.draw(square);
		g2.setColor(oldColor);
		for(int i = 0; i<rows; i++){
			Ellipse2D circle = new Ellipse2D.Double(x-15, y+5+25*i, 10, 10);
			g2.setColor(Color.BLACK);
			g2.fill(circle);
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Colors.GREEN.getColor());
			g2.draw(circle);

			Ellipse2D circle2 = new Ellipse2D.Double(x+5+rWidth, y+5+25*i, 10, 10);
			g2.setColor(Color.BLACK);
			g2.fill(circle2);
			g2.setColor(new Color(50,205,50));
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
				}
				else if(point_y1 < center_y2){
					new_y = getBounds().getMinY() + 10;
				}
				else  {
					new_y = getBounds().getCenterY();
				}
			}
			else {
				double y1 = getBounds().getCenterY();
				double y2 = aPoint.getY();
				if(y1 < y2){
					new_y = getBounds().getMaxY() - 10;
				}
				else {
					new_y = getBounds().getMinY() + 10;
				}
			}
			return new Point2D.Double(getBounds().getMaxX()+15, new_y);
		}
		else {
			if(rows > 2){
				if(point_y1 > center_y1){
					new_y = getBounds().getMaxY() - 10;
				}
				else if(point_y1 < center_y2){
					new_y = getBounds().getMinY() + 10;
				}
				else {
					new_y = getBounds().getCenterY();
				}
			}
			else{
				double y1 = getBounds().getCenterY();
				double y2 = aPoint.getY();
				if(y1 < y2){
					new_y = getBounds().getMaxY() - 10;
				}
				else {
					new_y = getBounds().getMinY() + 10;
				}
			}
			return new Point2D.Double(getBounds().getMinX()-15, new_y);
		}
	}
}
