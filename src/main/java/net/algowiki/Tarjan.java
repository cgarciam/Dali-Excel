package net.algowiki;

import java.util.ArrayList;
import java.util.List;

// Download from http://algowiki.net/wiki/index.php?title=Tarjan%27s_algorithm
public class Tarjan {
	private transient int index = 0;
	private final transient List<Node> stack = new ArrayList<Node>();
	private final transient List<ArrayList<Node>> scc = new ArrayList<ArrayList<Node>>();

	/*
	 * The function tarjan has to be called for every unvisited node of the
	 * graph
	 */
	public List<ArrayList<Node>> executeTarjan(final AdjacencyList graph) {
		scc.clear();
		index = 0;
		stack.clear();
		if (graph != null) {
			final List<Node> nodeList = new ArrayList<Node>(
					graph.getSourceNodeSet());
			if (nodeList != null) {
				for (Node node : nodeList) {
					if (node.index == null) {
						tarjan(node, graph);
					}
				}
			}
		}
		return scc;
	}

	private List<ArrayList<Node>> tarjan(final Node vertex,
			final AdjacencyList adjList) {
		vertex.index = index;
		vertex.lowlink = index;
		index++;
		stack.add(0, vertex);
		System.out.println(stack);
		for (Edge e : adjList.getAdjacent(vertex)) {
			final Node vAdjTo = e.to;
			if (vAdjTo.index == null) {
				tarjan(vAdjTo, adjList);
				vertex.lowlink = Math.min(vertex.lowlink, vAdjTo.lowlink);
			} else if (stack.contains(vAdjTo)) {
				vertex.lowlink = Math.min(vertex.lowlink, vAdjTo.index);
			}
		}
		if (vertex.lowlink == vertex.index) {
			Node vertexInScc;
			final ArrayList<Node> component = new ArrayList<Node>();
			do {
				vertexInScc = stack.remove(0);
				component.add(vertexInScc);
			} while (vertexInScc.equals(vertex));
			scc.add(component);
		}
		return scc;
	}

	public static void main(String[] args) {
		AdjacencyList adjList = new AdjacencyList();
		List<ArrayList<Node>> sccRes = new Tarjan().executeTarjan(adjList);
		System.out.println("SCC - " + sccRes);
	}

}
