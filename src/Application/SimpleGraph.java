package Application;

import java.awt.*;

import framework.*;

public class SimpleGraph extends Graph {
	public Node[] getNodePrototypes() {
		Node[] nodeTypes = { new CircleComponent(Color.RED), new ResistorComp(), new Gates(Color.BLUE, 1), new Gates(Color.PINK, 3) };
		return nodeTypes;
	}

	@Override
	public Edge[] getEdgePrototypes() {
		Edge[] edgeTypes = { new LineEdge(new Color(0,200,0), 1), new LineEdge(Color.RED, 4) };
		return edgeTypes;
	}
}
