package framework;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class AbstractEdge implements Edge {
	
	private Node start;
	private Node end;

	public Object clone(){
		try{
			return super.clone();
		}
		catch (CloneNotSupportedException exception){
			return null;
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Point2D aPoint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connect(Node aStart, Node anEnd) {
		start = aStart;
		end = anEnd;

	}

	@Override
	public Node getStart() {
		return start;
	}

	@Override
	public Node getEnd() {
		return end;
	}

	@Override
	public Line2D getConnectionPoints() {
		Rectangle2D startBounds = start.getBounds();
		Rectangle2D endBounds = end.getBounds();
		
		Point2D startCenter = new Point2D.Double(startBounds.getCenterX(), startBounds.getCenterY());
		Point2D endCenter = new Point2D.Double(endBounds.getCenterX(), endBounds.getCenterY());
		return new Line2D.Double(start.getConnectionPoint(endCenter), end.getConnectionPoint(startCenter));
	}

	@Override
	public Rectangle2D getBounds(Graphics2D g2) {
		Line2D conn = getConnectionPoints();
		Rectangle2D r = new Rectangle2D.Double();
		
		r.setFrameFromDiagonal(conn.getX1(), conn.getY1(), conn.getX2(), conn.getY2());
		
		return r;
	}

}
