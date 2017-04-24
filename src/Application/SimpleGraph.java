package Application;

import java.awt.*;

import framework.*;

public class SimpleGraph extends Graph {
	public Node[] getNodePrototypes() {
		Node[] nodeTypes = { new ChipIcon(1), new ChipIcon(2), new ChipIcon(3), new CircleComponent(new Color(50,205,50), 15, 15, true) };
		return nodeTypes;
	}

	@Override
	public Edge[] getEdgePrototypes() {
		Edge[] edgeTypes = { new LineEdge(Colors.GREEN.getColor(), 1), new LineEdge(Colors.GREEN.getColor(), 4) };
		return edgeTypes;
	}
}
