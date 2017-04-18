package framework;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphPanel extends JComponent {

	public GraphPanel(ToolBar aToolBar, Graph aGraph) {
		toolBar = aToolBar;
		graph = aGraph;
		setBackground(Color.WHITE);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				Point2D mousePoint = event.getPoint();
				Node n = graph.findNode(mousePoint);
				Edge e = graph.findEdge(mousePoint);
				Object tool = toolBar.getSelectedTool();
				if (tool == null) {
					if(e != null){
						selected = e;
					}
					else if(n != null){
						selected = n;
						dragStartPoint = mousePoint;
						dragStartBounds = n.getBounds();
					}
					else {
						selected = null;
					}
				}
				else if(tool instanceof Node){
					Node prototype = (Node) tool;
					Node newNode = (Node) prototype.clone();
					boolean added = graph.add(newNode, mousePoint);
					if(added){
						selected = newNode;
						dragStartPoint = mousePoint;
						dragStartBounds = newNode.getBounds();
					}
					else if(n != null){
						selected = n;
						dragStartPoint = mousePoint;
						dragStartBounds = n.getBounds();
					}
				}
				else if(tool instanceof Edge){
					if(n != null) rubberBandStart = mousePoint;
				}
				lastMousePoint = mousePoint;
				repaint();
			}
			public void mouseReleased(MouseEvent event){
				Object tool = toolBar.getSelectedTool();
				if(rubberBandStart != null){
					Point2D mousePoint = event.getPoint();
					Edge prototype = (Edge) tool;
					Edge newEdge = (Edge) prototype.clone();
					if(graph.connect(newEdge, rubberBandStart, mousePoint)) selected = newEdge;
				}

				revalidate();
				repaint();

				lastMousePoint = null;
				dragStartBounds = null;
				rubberBandStart = null;
			}
		});

		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent event){
				Point2D mousePoint = event.getPoint();
				if(dragStartBounds != null){
					if(selected instanceof Node){
						Node n = (Node) selected;
						Rectangle2D bounds = n.getBounds();
						n.translate(dragStartBounds.getX() - bounds.getX() + mousePoint.getX() - dragStartPoint.getX(),
								dragStartBounds.getY() - bounds.getY() + mousePoint.getY() - dragStartPoint.getY());
					}
				}
				lastMousePoint = mousePoint;
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D bounds = getBounds();
		Rectangle2D graphBounds = graph.getBounds(g2);
		graph.draw(g2);
		
		if(selected instanceof Edge){
			Line2D line = ((Edge) selected).getConnectionPoints();
			drawGrabber(g2, line.getX1(), line.getY1());
			drawGrabber(g2, line.getX2(), line.getY2());
		}
		if(selected instanceof Edge){
			Line2D line = ((Edge) selected).getConnectionPoints();
			drawGrabber(g2, line.getX1(), line.getY1());
			drawGrabber(g2, line.getX2(), line.getY2());
		}
		if(rubberBandStart != null){
			g2.setColor(Color.RED);
			g2.draw(new Line2D.Double(rubberBandStart, lastMousePoint));
		}
	}

	public static void drawGrabber(Graphics2D g2, double x, double y){
		g2.fill(new Rectangle2D.Double(x-5/2, y-5/2, 5, 5));
		g2.setColor(Color.RED);

	}

	private Graph graph;
	private ToolBar toolBar;
	private Object selected;

	private Point2D lastMousePoint;
	private Point2D rubberBandStart;
	private Point2D dragStartPoint;
	private Rectangle2D dragStartBounds;
}
