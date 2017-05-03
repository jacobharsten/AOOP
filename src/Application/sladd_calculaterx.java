package Application;

import java.awt.*;
import java.awt.geom.*;

import framework.*;

public class sladd_calculaterx {
	private Graph g;
	public sladd_calculaterx(Graph a){
		g = a;
		double total_length = 0;
		Line2D line;
		for(Edge e2 : g.getEdges()){
			line = e2.getConnectionPoints();
			double x = line.getX2() - line.getX1();
			double y = line.getY2() - line.getY1();
			double d = Math.sqrt(x*x + y*y);
			total_length += Math.round(d);
		}
		System.out.println("Total length of SLADDS: " + total_length);
	}
}
