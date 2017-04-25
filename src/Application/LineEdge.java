package Application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import framework.AbstractEdge;

public class LineEdge extends AbstractEdge {

	private Color c;
	private int strokeSize;

	public LineEdge(Color col, int size) {
		c = col;
		strokeSize = size;
	}

	public int getStrokeSize(){
		return strokeSize;
	}
	public void setStrokeSize(int s){
		if(s < 10){
			strokeSize = s; 
		}
	}

	public Color getColor(){
		return c;
	}
	public void setColor(Color new_color){
		c = new_color;
	}

	public void draw(Graphics2D g2){
		g2.setColor(c);
		g2.setStroke(new BasicStroke(strokeSize));
		g2.draw(getConnectionPoints());
	}

	public boolean contains(Point2D aPoint){
		final double MAX_DIST = 1;
		return getConnectionPoints().ptSegDist(aPoint) < MAX_DIST;
	}
}
