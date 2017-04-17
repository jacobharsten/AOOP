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
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		JMenuItem mLoad = new JMenuItem("Load");
		JMenuItem mSave = new JMenuItem("Save");
		JMenuItem mNew = new JMenuItem("New");
		JMenuItem mExit = new JMenuItem("Exit");
		
		mNew.setIcon(new ImageIcon("src/images/new.gif"));
		mSave.setIcon(new ImageIcon("src/images/save.gif"));
		mLoad.setIcon(new ImageIcon("src/images/import.gif"));
		
		menuBar.add(mnMenu);
		mnMenu.add(mNew);
		mnMenu.addSeparator();
		mnMenu.add(mSave);
		mnMenu.add(mLoad);
		mnMenu.addSeparator();
		mnMenu.add(mExit);
	}

	private Graph graph;
	private GraphPanel panel;
	private JScrollPane scrollPane;
	private ToolBar toolBar;

	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 400;
}
