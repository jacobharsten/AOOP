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
				Object tool = toolBar.getSelectedTool();
				if (tool != null) {
					Node prototype = (Node) tool;
					Node newNode = (Node) prototype.clone();
					graph.add(newNode, mousePoint);
				}
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		graph.draw(g2);
	}

	private Graph graph;
	private ToolBar toolBar;
}
