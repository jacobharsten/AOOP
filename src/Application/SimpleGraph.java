package Application;

import java.awt.*;

import framework.*;

public class SimpleGraph extends Graph {
	public Node[] getNodePrototypes() {
		Node[] nodeTypes = { new eComponent(Color.BLACK), new eComponent(Color.RED), new eComponent(Color.BLUE) };
		return nodeTypes;
	}

	@Override
	public Edge[] getEdgePrototypes() {
		Edge[] edgeTypes = { new LineEdge() };
		return edgeTypes;
	}
}
