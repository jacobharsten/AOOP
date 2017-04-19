package Application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import framework.AbstractEdge;

public class LineEdge extends AbstractEdge {
	
	private Color c;
	
	public LineEdge(Color col) {
		c = col;
	}

	public void draw(Graphics2D g2){
		g2.setColor(c);
		g2.setStroke(new BasicStroke(1));
		g2.draw(getConnectionPoints());
	}

	public boolean contains(Point2D aPoint){
		final double MAX_DIST = 2;
		return getConnectionPoints().ptSegDist(aPoint) < MAX_DIST;
	}
}
