package framework;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphPanel extends JComponent {

	public GraphPanel(ToolBar aToolBar, Graph aGraph) {
		toolBar = aToolBar;
		graph = aGraph;
		setBackground(Color.BLACK);

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
				else if(tool instanceof Node && event.getButton() != MouseEvent.BUTTON3){
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
				if(event.getButton() == MouseEvent.BUTTON3){
					JPopupMenu pop = new JPopupMenu();
					JMenuItem pDelete = new JMenuItem("Delete");
					pDelete.setIcon(new ImageIcon("src/images/delete.gif"));
					pop.add(pDelete);
					pDelete.addActionListener(new
							ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							deleteSelected();
						}
					});
					JMenuItem pEdit = new JMenuItem("Edit");
					pop.addSeparator();
					pop.add(pEdit);
					pEdit.addActionListener(new
							ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							System.out.println("om vi ska ädnra nåtngin");
						}
					});
					pop.show(GraphPanel.this, event.getX(), event.getY());
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
		graph.draw(g2);
		if(rubberBandStart != null){
			Color oldColor = g2.getColor();
			g2.setColor(new Color(0, 205, 0));
			g2.setStroke(new BasicStroke(1));
			g2.draw(new Line2D.Double(rubberBandStart, lastMousePoint));
			g2.setColor(oldColor);
		}
		if(selected instanceof Node){
			Rectangle2D grabberBounds = ((Node) selected).getBounds();
			drawGrabber(g2, grabberBounds.getMinX(), grabberBounds.getMinY());
			drawGrabber(g2, grabberBounds.getMinX(), grabberBounds.getMaxY());
			drawGrabber(g2, grabberBounds.getMaxX(), grabberBounds.getMinY());
			drawGrabber(g2, grabberBounds.getMaxX(), grabberBounds.getMaxY());
		}
		if(selected instanceof Edge){
			Line2D line = ((Edge) selected).getConnectionPoints();
			drawGrabber(g2, line.getX1(), line.getY1());
			drawGrabber(g2, line.getX2(), line.getY2());
		}
	}

	public static void drawGrabber(Graphics2D g2, double x, double y){
		g2.setColor(new Color(50, 0, 0));
		g2.fill(new Rectangle2D.Double(x-5/2, y-5/2, 5, 5));
	}
	public void deleteSelected(){
		if(selected instanceof Node){
			graph.removeNode((Node)selected);
		}
		else if (selected instanceof Edge){
			graph.removeEdge((Edge)selected);
		}
		selected = null;
		repaint();
		
	}
	private JPopupMenu popup;
	private Graph graph;
	private ToolBar toolBar;
	private Object selected;

	private Point2D lastMousePoint;
	private Point2D rubberBandStart;
	private Point2D dragStartPoint;
	private Rectangle2D dragStartBounds;
}
