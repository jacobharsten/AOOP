package framework;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;

import Application.CircleComponent;

public abstract class Graph implements Serializable {

	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	private int board_width = 650;
	private int board_height = 400;

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

	public int getWidth(){
		return board_width;
	}
	public int getHeight(){
		return board_height;
	}

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
		g2.setStroke(new BasicStroke(4));
		g2.drawString("DAVE&COB EZ PCB", (int)square.getMinX(), (int)square.getMinY()-2);
		g2.draw(square);

		if(check_all_inter()){
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
			if(!check_all_inter()){
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
		if(n1 != null && n2 != null && !check_inter(p1, p2)){
			e.connect(n1, n2);
			edges.add(e);
			return true;
		}
		if(n1 != null && n2 == null && !check_inter(p1, p2)){
			Node newNode = new CircleComponent(new Color(34,139,34), 5, 5, false);
			this.add(newNode, p2);
			e.connect(n1, newNode);
			edges.add(e);
			return true;
		}
		return false;
	}

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

	public boolean check_all_inter(){
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
