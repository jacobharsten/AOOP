package Application;

import java.awt.*;

import framework.*;

public class SimpleGraph extends Graph {
	public Node[] getNodePrototypes() {
		Node[] nodeTypes = { new CircleComponent(Color.GREEN), new CircleComponent(Color.RED), new Gates(Color.BLUE, 1), new Gates(Color.PINK, 3) };
		return nodeTypes;
	}

	@Override
	public Edge[] getEdgePrototypes() {
		Edge[] edgeTypes = { new LineEdge(new Color(0,200,0)), new LineEdge(Color.RED) };
		return edgeTypes;
	}
}
