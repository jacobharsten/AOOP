package Application;

import java.awt.*;

import framework.*;

public class SimpleGraph extends Graph {
	public Node[] getNodePrototypes() {
		Node[] nodeTypes = { new Gates(Color.GREEN, 1), new Gates(Color.RED, 5), new Gates(Color.BLUE, 6), new Gates(Color.PINK, 12) };
		return nodeTypes;
	}

	@Override
	public Edge[] getEdgePrototypes() {
		Edge[] edgeTypes = { new LineEdge() };
		return edgeTypes;
	}
}
