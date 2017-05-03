package framework;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphPanel extends JComponent {

	public GraphPanel(ToolBar aToolBar,ShopBar aShopBar, Graph aGraph) {
		toolBar = aToolBar;
		shopBar = aShopBar;
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
					pEdit.setIcon(new ImageIcon("src/images/edit_object.gif"));
					pop.addSeparator();
					pop.add(pEdit);
					pEdit.addActionListener(new
							ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							editSelected();
						}
					});
					JMenuItem pBuy = new JMenuItem("Add selected to cart");
					pBuy.setIcon(new ImageIcon("src/images/universal_add.png"));
					pop.addSeparator();
					pop.add(pBuy);
					pBuy.addActionListener(new
							ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							shopBar.add(selected);
						}
					});
					JMenuItem pRem = new JMenuItem("Remove selected from cart");
					pRem.setIcon(new ImageIcon("src/images/universal_delete.png"));
					//pop.addSeparator();
					pop.add(pRem);
					pRem.addActionListener(new
							ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							shopBar.remove(selected);
						}
					});
					JMenuItem pBuyAll = new JMenuItem("Add all to cart");
					pBuyAll.setIcon(new ImageIcon("src/images/addAll.png"));
					pop.addSeparator();
					pop.add(pBuyAll);
					pBuyAll.addActionListener(new
							ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							shopBar.addAll(graph.getNodes());
							// FUnktion för att lägga till allt
						}
					});
					JMenuItem pEmpty = new JMenuItem("Empty Cart");
					pEmpty.setIcon(new ImageIcon("src/images/deleteAll.png"));
					//pop.addSeparator();
					pop.add(pEmpty);
					pEmpty.addActionListener(new
							ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							shopBar.emptyAll();
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
				mouse_follow = false;
			}
		});

		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent event){
				mouse_follow = true;
				mouse_x = event.getX();
				mouse_y = event.getY();
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
			Line2D line = new Line2D.Double(rubberBandStart, lastMousePoint);
			g2.setColor(Colors.DARKGREEN.getColor());
			g2.setStroke(new BasicStroke(1));
			g2.draw(line);
			g2.setColor(oldColor);
			drawCoord(g2, lastMousePoint.getX(), lastMousePoint.getY());
		}
		if(selected instanceof Node){
			Rectangle2D grabberBounds = ((Node) selected).getBounds();
			drawGrabber(g2, grabberBounds.getMinX()-2, grabberBounds.getMinY()-2);
			drawGrabber(g2, grabberBounds.getMinX()-2, grabberBounds.getMaxY()+2);
			drawGrabber(g2, grabberBounds.getMaxX()+2, grabberBounds.getMinY()-2);
			drawGrabber(g2, grabberBounds.getMaxX()+2,grabberBounds.getMaxY()+2);
			if(rubberBandStart == null){
				drawCoord(g2, grabberBounds.getCenterX(), grabberBounds.getCenterY());
			}
		}
		if(selected instanceof Edge){
			Line2D line = ((Edge) selected).getConnectionPoints();
			drawGrabber(g2, line.getX1(), line.getY1());
			drawGrabber(g2, line.getX2(), line.getY2());
			if(rubberBandStart == null){
				drawCoord(g2, line.getX2(), line.getY2());
			}
		}
	}

	public boolean check_inter(Point2D start_point, Point2D end_point){
		Line2D existing_line;
		Line2D inter_line = new Line2D.Double(start_point, end_point);
		for(Edge e : graph.getEdges()){
			existing_line = e.getConnectionPoints();
			if(inter_line.intersectsLine(existing_line)){
				return true;
			}
		}
		return false;
	}


	public static void drawGrabber(Graphics2D g2, double x, double y){
		g2.setColor(new Color(225, 0, 0));
		g2.fill(new Rectangle2D.Double(x-5/2, y-5/2, 5, 5));
	}

	public void drawCoord(Graphics2D g2, double x, double y){
		if(mouse_follow){
			g2.setColor(new Color(225, 0, 0));
			g2.drawString("[X: " + (int)x + " Y: " + (int)y + "]", mouse_x, mouse_y);
		}
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

	public void editSelected()
	{
		if(selected != null){
			PropertySheet sheet = new PropertySheet(selected);
			sheet.addChangeListener(new
					ChangeListener()
			{
				public void stateChanged(ChangeEvent event)
				{
					repaint();
				}
			});
			JOptionPane.showMessageDialog(null, 
					sheet, 
					"Properties", 
					JOptionPane.PLAIN_MESSAGE);        
		}
	}


	private JPopupMenu popup;
	private Graph graph;
	private ToolBar toolBar;
	private ShopBar shopBar;
	private Object selected;

	private int mouse_x;
	private int mouse_y;
	private boolean mouse_follow;

	private Point2D lastMousePoint;
	private Point2D rubberBandStart;
	private Point2D dragStartPoint;
	private Rectangle2D dragStartBounds;
}
