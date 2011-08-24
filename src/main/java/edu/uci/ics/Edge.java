package edu.uci.ics;

import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {

	public Edge(final Vertex from, final Vertex to) {
		this.from = from;
		this.to = to;
	}

	public Vertex from;
	public Vertex to;

	private static final long serialVersionUID = 1L;

	// @Override
	// public String toString() {
	// return "Edge";
	// }

}
