package framework;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import javax.swing.*;

import Application.CircleComponent;

public abstract class Graph implements Serializable {

	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	private int board_width = 660;
	private int board_height = 400;

	private String title;

	public abstract Node[] getNodePrototypes();
	public abstract Edge[] getEdgePrototypes();
	/**
	 * Constructs a Graph with no nodes and edges.
	 * Sets the title of the graph.
	 */
	public Graph() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		
		title = "DAVE&COB EZ PCB";
	}
	/**
    Adds a node to the graph so that the top left corner of
    the bounding rectangle is at the given point.
    @param n the node to add
    @param p the desired location
	 */
	public boolean add(Node n, Point2D p) {
		Rectangle2D bounds = n.getBounds();
		n.translate(p.getX() - bounds.getX(), p.getY() - bounds.getY());
		nodes.add(n);
		return true;
	}
	/**
    Finds a node containing the given point.
    @param p a point
    @return a node containing p or null if no nodes contain p
	 */
	public Node findNode(Point2D p){
		for(int i = 0; i<nodes.size(); i++ ){
			Node n = nodes.get(i);
			if(n.contains(p)){
				return n;
			}
		}
		return null;
	}


	/**
    Finds an edge containing the given point.
    @param p a point
    @return an edge containing p or null if no edges contain p
	 */
	public Edge findEdge(Point2D p){
		for(int i = 0; i<edges.size(); i++ ){
			Edge n = edges.get(i);
			if(n.contains(p)){
				return n;
			}
		}
		return null;
	}
	/**
	 * Gets the width of the Graph.
	 * @return the width
	 */
	public int getWidth(){
		return board_width;
	}
	/**
	 * Gets the height of the Graph.
	 * @return the height
	 */
	public int getHeight(){
		return board_height;
	}

	/**
    Draws the graph
    @param g2 the graphics context
	 */
	public void draw(Graphics2D g2) {
		Rectangle2D square = new Rectangle2D.Double(70, 70, board_width, board_height);
		Color oldColor = g2.getColor();
		double rect_size = 16;

		g2.setColor(new Color(211,211,211));
		g2.fill(square);

		for(int i = 0; i<(board_width/rect_size)-1; i++){
			for(int j = 0; j<(board_height/rect_size); j++){
				Rectangle2D in = new Rectangle2D.Double(70+rect_size*i, 70+rect_size*j, rect_size, rect_size);
				g2.setStroke(new BasicStroke(1));
				g2.setColor(new Color(230,230,230));
				g2.draw(in);
			}
		}

		g2.setColor(Color.GRAY);
		g2.drawString(title, (int)square.getMinX(), (int)square.getMinY()-2);
		g2.draw(square);

		if(check_all_edges()){
			oldColor = g2.getColor();
			g2.setColor(Color.RED);
			g2.drawString("ERROR HANDLING: INTERSECTING LINES", 300, 530);
			ImageIcon image = new ImageIcon("src/images/error.gif");
			image.paintIcon(null, g2, 275, 512);
			g2.setColor(oldColor);
		}

		if(check_all_node()){
			oldColor = g2.getColor();
			g2.setColor(Color.RED);
			if(!check_all_edges()){
				g2.drawString("ERROR HANDLING: INTERSECTING NODES", 300, 530);
				ImageIcon image = new ImageIcon("src/images/error.gif");
				image.paintIcon(null, g2, 275, 512);
			}
			else {
				g2.drawString("ERROR HANDLING: INTERSECTING NODES", 300, 510);
			}
			g2.setColor(oldColor);
		}

		for (Edge e : edges){
			e.draw(g2);			
		}

		for (Node n : nodes)
			n.draw(g2);
	}
	/**
    Removes a node and all edges that start or end with that node
    @param n the node to remove
	 */
	public void removeNode(Node n){
		for(int i = edges.size()-1; i >=0; i-- ){
			Edge e = edges.get(i);
			if(e.getStart() == n || e.getEnd() == n){
				edges.remove(e);
			}
		}
		nodes.remove(n);
	}

	/**
    Removes an edge from the graph.
    @param e the edge to remove
	 */
	public void removeEdge(Edge n){
		edges.remove(n);
	}
	/**
    Gets the smallest rectangle enclosing the graph
    @param g2 the graphics context
    @return the bounding rectangle
	 */
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
	/**
    Adds an edge to the graph that joins the nodes containing
    the given points. If the points aren't both inside nodes,
    then no edge is added.
    @param e the edge to add
    @param p1 a point in the starting node
    @param p2 a point in the ending node
	 */

	/**
	 * Adds an edge to the graph that joins the nodes containing
	 * the given points. If p1 is inside a node and p2 aren't containing 
	 * a node, a CircleComponent are created and connected to.
	 * @param e the edge to add
	 * @param p1 a point in the starting node
	 * @param p2 a point in the ending node
	 * @return true if connected
	 */
	public boolean connect(Edge e, Point2D p1, Point2D p2){
		Node n1 = findNode(p1);
		Node n2 = findNode(p2);
		if(n1 != null && n2 != null && !check_inter(p1, p2)){
			e.connect(n1, n2);
			edges.add(e);
			return true;
		}
		if(n1 != null && n2 == null && !check_inter(p1, p2)){
			Node newNode = new CircleComponent(Colors.NAVYGREEN.getColor(), 5, 5, false);
			Point2D new_p = new Point2D.Double(new_c((int)p2.getX()),new_c((int)p2.getY()));		
			this.add(newNode, new_p);
			e.connect(n1, newNode);
			edges.add(e);
			return true;
		}
		return false;
	}

	/**
	 * Adds the edge to the grid by calculating its position.
	 * @param p point where it should add
	 * @return the new position made for the point
	 */
	public int new_c(int p){
		int c = 0;
		int test_value = 0;
		for(int i = 70; i<710; i+=16){
			test_value = Math.abs(i-p);
			if(test_value >= 0 && test_value <= 16){
				c = i;
			}
		}
		return c;
	}

	/**
	 * Check if two points intersect each other.
	 * @param start_point the starting point
	 * @param end_point the ending point
	 * @return true if the lines intersects
	 */
	public boolean check_inter(Point2D start_point, Point2D end_point){
		Line2D existing_line;
		Line2D inter_line = new Line2D.Double(start_point, end_point);
		for(Edge e : getEdges()){
			existing_line = e.getConnectionPoints();
			if(inter_line.intersectsLine(existing_line)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Goes through all edges in the list and check for intersects.
	 * @return true if two or more edge intersects 
	 */
	public boolean check_all_edges(){
		Line2D line_e1;
		Line2D line_e2;
		for(Edge e : getEdges()){
			line_e1 = e.getConnectionPoints();
			for(Edge e2 : getEdges()){
				line_e2 = e2.getConnectionPoints();
				if(line_e1.intersectsLine(line_e2) && line_e1.getX1() != line_e2.getX1()){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Goes through all nodes in the list and check for intersects.
	 * @return true if two or more node intersects 
	 */
	public boolean check_all_node(){
		Rectangle2D line_e1;
		Rectangle2D line_e2;
		for(Node e : getNodes()){
			line_e1 = e.getBounds();
			for(Node e2 : getNodes()){
				line_e2 = e2.getBounds();
				if(line_e1.intersects(line_e2) && line_e1.getX() != line_e2.getX()){
					return true;
				}
			}
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
