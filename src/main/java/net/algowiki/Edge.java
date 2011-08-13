package net.algowiki;

import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {

	public Edge(final Node from, final Node to) {
		this.from = from;
		this.to = to;
	}

	public Node from;
	public Node to;

	private static final long serialVersionUID = 2318831222869023554L;

}
