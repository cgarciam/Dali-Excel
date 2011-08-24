package edu.uci.ics;

import org.jgrapht.Graph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepthFirstSearch {

	public DepthFirstSearch(final Graph<Vertex, DefaultEdge> graph) {
		final Class<? extends DefaultEdge> clazz = Edge.class;
		this.graph = (AbstractBaseGraph<Vertex, DefaultEdge>) graph;
		tree = new DefaultDirectedGraph<Vertex, DefaultEdge>(clazz);
	}

	public AbstractBaseGraph<Vertex, DefaultEdge> execute() {
		// make a new vertex vert with edges vert -> v for all v in V
		final Vertex vert = new Vertex(-1L);
		graph.addVertex(vert);
		linkToAllVertex(vert);
		// build directed tree T, initially a single vertex {vert}
		tree.addVertex(vert);
		visit(vert);
		LOG.debug("Tree: " + tree);
		return tree;
	}

	/**
	 * Generación de links de vértice comodín inicial vert hacia todos los demás
	 * vértices de la gráfica.
	 * 
	 * @param vert
	 *            el vértice comodin añadido a la gráfica que garantiza que se
	 *            ha de visitar a toda la gráfica mediante el añadido de aristas
	 *            desde este hacia todos los vértices originales.
	 */
	private void linkToAllVertex(final Vertex vert) {
		for (final Vertex vertex : graph.vertexSet()) {
			graph.addEdge(vert, vertex, new Edge(vert, vertex)); // NOPMD
		}
	}

	private void visit(final Vertex vert) {
		for (DefaultEdge edge : graph.outgoingEdgesOf(vert)) {
			LOG.trace("edge: " + edge);
			final Vertex neight = ((Edge) edge).to;
			if (!tree.containsVertex(neight)) {
				tree.addVertex(neight);
				tree.addEdge(vert, neight, edge);
				visit(neight);
			}
		}
	}

	private final transient AbstractBaseGraph<Vertex, DefaultEdge> graph;
	private final transient AbstractBaseGraph<Vertex, DefaultEdge> tree;
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(DepthFirstSearch.class);
	}

}
