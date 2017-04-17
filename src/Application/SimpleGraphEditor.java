package Application;

import javax.swing.*;

import framework.*;

public class SimpleGraphEditor {
	public static void main(String[] args) {
		JFrame frame = new GraphFrame(new SimpleGraph());
		frame.setVisible(true);
	}
}
