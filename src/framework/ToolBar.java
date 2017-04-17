package framework;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class ToolBar extends JPanel {

	public ToolBar(Graph graph) {
		group = new ButtonGroup();
		tools = new ArrayList<Object>();

		Node[] nodeTypes = graph.getNodePrototypes();
		Edge[] edgeTypes = graph.getEdgePrototypes();
		for (Node n : nodeTypes)
			add(n);
		for (Edge e : edgeTypes)
			add(e);
	}

	public Object getSelectedTool() {
		int i = 0;
		for (Object o : tools) {
			JToggleButton button = (JToggleButton) getComponent(i++);
			if (button.isSelected())
				return o;
		}
		return null;
	}

	public void add(final Node n) {
		JToggleButton button = new JToggleButton(new Icon() {
			public int getIconHeight() {
				return BUTTON_SIZE;
			}

			public int getIconWidth() {
				return BUTTON_SIZE;
			}

			public void paintIcon(Component c, Graphics g, int x, int y) {
				double width = n.getBounds().getWidth();
				double height = n.getBounds().getHeight();
				double scaleX = (BUTTON_SIZE - OFFSET) / width;
				double scaleY = (BUTTON_SIZE - OFFSET) / height;
				double scale = Math.min(scaleX, scaleY);

				Graphics2D g2 = (Graphics2D) g;
				AffineTransform oldTransform = g2.getTransform();
				g2.translate(x, y);
				g2.scale(scale, scale);
				g2.setColor(Color.black);
				n.draw(g2);
				g2.setTransform(oldTransform);
			}
		});
		group.add(button);
		add(button);
		tools.add(n);
	}

	public void add(final Edge e)
	{
		JToggleButton button = new JToggleButton(new
				Icon()
		{
			public int getIconHeight() { return BUTTON_SIZE; }
			public int getIconWidth() { return BUTTON_SIZE; }
			public void paintIcon(Component c, Graphics g,
					int x, int y)
			{
				Graphics2D g2 = (Graphics2D) g;
				PointNode p = new PointNode();
				p.translate(OFFSET, OFFSET);
				PointNode q = new PointNode();
				q.translate(BUTTON_SIZE - OFFSET, BUTTON_SIZE - OFFSET);
				e.connect(p, q);
				g2.translate(x, y);
				g2.setColor(Color.black);
				e.draw(g2);
				g2.translate(-x, -y);
			}
		});
		group.add(button);
		add(button);
		tools.add(e);
	}

	private ButtonGroup group;
	private ArrayList<Object> tools;

	private static final int BUTTON_SIZE = 20;
	private static final int OFFSET = 4;
}
