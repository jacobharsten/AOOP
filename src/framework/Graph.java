package framework;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public abstract class Graph implements Serializable {

	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;

	public abstract Node[] getNodePrototypes();
	public abstract Edge[] getEdgePrototypes();

	public Graph() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
	}

	public boolean add(Node n, Point2D p) {
		Rectangle2D bounds = n.getBounds();
		n.translate(p.getX() - bounds.getX(), p.getY() - bounds.getY());
		nodes.add(n);
		return true;
	}

	public Node findNode(Point2D p){
		for(int i = 0; i<nodes.size(); i++ ){
			Node n = nodes.get(i);
			if(n.contains(p)){
				return n;
			}
		}
		return null;
	}
	
	public Edge findEdge(Point2D p){
		for(int i = 0; i<edges.size(); i++ ){
			Edge n = edges.get(i);
			if(n.contains(p)){
				return n;
			}
		}
		return null;
	}


	public void draw(Graphics2D g2) {
		for (Node n : nodes)
			n.draw(g2);
		for (Edge e : edges)
			e.draw(g2);
	}

	public void removeNode(Node n){
		for(int i = edges.size()-1; i >=0; i-- ){
			Edge e = edges.get(i);
			if(e.getStart() == n || e.getEnd() == n){
				edges.remove(e);
			}
		}
		nodes.remove(n);
	}

	public void removeEdge(Edge n){
		edges.remove(n);
	}

	public Rectangle2D getBounds(Graphics2D g2){
		Rectangle2D r = null;

		for(Node n : nodes){
			Rectangle2D b = n.getBounds();
			if(r == null) r = b;
			else r.add(b);
		}
		for(Edge e : edges){
			r.add(e.getBounds(g2));
		}
		return r == null ? new Rectangle2D.Double() : r;
	}

	public boolean connect(Edge e, Point2D p1, Point2D p2){
		Node n1 = findNode(p1);
		Node n2 = findNode(p2);

		if(n1 != null && n2 != null){
			e.connect(n1, n2);
			edges.add(e);
			return true;
		}
		return false;
	}



	public List<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}
	public List<Edge> getEdges() {
		return Collections.unmodifiableList(edges);
	}



}
