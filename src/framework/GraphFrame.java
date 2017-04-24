package framework;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

/**
 * This frame shows the toolbar and the graph.
 */
public class GraphFrame extends JFrame {

	public GraphFrame(final Graph graph) {
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		bg_color = new Color(30,30,30);

		this.graph = graph;

		graph_bars();

		/*
		 * MENUBAR
		 */
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

		mExit.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});

		mSave.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				saveFile();
			}
		});

		mLoad.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				openFile();
			}
		});
		mNew.addActionListener(new
				ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				newFile();
			}
		});

		menuBar.add(mnMenu);
		mnMenu.add(mNew);
		mnMenu.addSeparator();
		mnMenu.add(mSave);
		mnMenu.add(mLoad);
		mnMenu.addSeparator();
		mnMenu.add(mExit);
	}

	private void newFile(){
		try
		{
			File file = new File("src/data/empty");
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			graph = (Graph) in.readObject();
			in.close();
			this.remove(scrollPane);
			this.remove(toolBar);
			graph_bars();
			validate();
			repaint();
		}
		catch (IOException exception)
		{
			JOptionPane.showMessageDialog(null,
					exception);
		}
		catch (ClassNotFoundException exception)
		{
			JOptionPane.showMessageDialog(null,
					exception);
		}
	}

	private void saveFile(){
		JFileChooser c = new JFileChooser();
		if (c.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			try{
				File file = c.getSelectedFile();
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
				out.writeObject(graph);
				out.close();
			}
			catch (IOException exception)
			{
				JOptionPane.showMessageDialog(null,
						exception);
			}
		}
	}

	private void openFile(){
		JFileChooser c = new JFileChooser();
		int r = c.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION){
			try
			{
				File file = c.getSelectedFile();
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				graph = (Graph) in.readObject();
				in.close();
				this.remove(scrollPane);
				this.remove(toolBar);
				graph_bars();
				validate();
				repaint();
			}
			catch (IOException exception)
			{
				JOptionPane.showMessageDialog(null,
						exception);
			}
			catch (ClassNotFoundException exception)
			{
				JOptionPane.showMessageDialog(null,
						exception);
			}
		}
	}
	
	
	public void graph_bars(){
		toolBar = new ToolBar(graph);
		panel = new GraphPanel(toolBar, graph);
		scrollPane = new JScrollPane(panel);
		scrollPane.getViewport().setBackground(bg_color);
		this.add(toolBar, BorderLayout.SOUTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	public int getWidth(){
		return FRAME_WIDTH;
	}
	public int getHeight(){
		return FRAME_HEIGHT;
	}


	private Graph graph;
	private GraphPanel panel;
	private JScrollPane scrollPane;
	private ToolBar toolBar;
	private Color bg_color;

	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGHT = 650;
}
