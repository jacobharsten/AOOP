package framework;

import java.awt.*;
import javax.swing.*;

/**
 * This frame shows the toolbar and the graph.
 */
public class GraphFrame extends JFrame {

	public GraphFrame(final Graph graph) {
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);

		this.graph = graph;

		toolBar = new ToolBar(graph);
		panel = new GraphPanel(toolBar, graph);
		scrollPane = new JScrollPane(panel);
		this.add(toolBar, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	private Graph graph;
	private GraphPanel panel;
	private JScrollPane scrollPane;
	private ToolBar toolBar;

	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 400;
}
