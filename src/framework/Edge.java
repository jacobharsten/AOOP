package framework;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;



public interface Edge extends Serializable, Cloneable {
	
	void draw(Graphics2D g2);

	boolean contains(Point2D aPoint);
	
	void connect(Node aStart, Node anEnd);
	
	Node getStart();
	Node getEnd();
	
	Line2D getConnectionPoints();
	
	Rectangle2D getBounds(Graphics2D g2);

	Object clone();
	
}
