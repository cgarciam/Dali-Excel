package net.algowiki;

import java.util.Set;

import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class AdjacencyList {

	public Set<? extends Node> getSourceNodeSet() {
		return graph.vertexSet();
	}

	public Set<? extends Edge> getAdjacent(Node v) {
		return graph.edgesOf(v);
	}

	public AdjacencyList() {
		graph = new DefaultDirectedGraph<Node, Edge>(Edge.class);
		Node v0 = new Node(0);
		Node v1 = new Node(1);
		Node v2 = new Node(2);

		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);

		graph.addEdge(v0, v1, new Edge(v0, v1));
		graph.addEdge(v1, v0, new Edge(v1, v0));
		graph.addEdge(v1, v2, new Edge(v1, v2));
		// g.addEdge(v2, v1, new Edge(v2, v1));
	}

	public AdjacencyList(AbstractGraph<Node, Edge> iniGraph) {
		this.graph = iniGraph;
	}

	private final AbstractGraph<Node, Edge> graph;
	// private final DirectedGraph<Node, Edge> graph;

}
