package edu.uci.ics;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.jgrapht.Graph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrongCompsDepthFirstSearch {

	public StrongCompsDepthFirstSearch(final Graph<Vertex, DefaultEdge> graph) {
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
		// Se extiende al DFS a fin de calcular las componentes fuertemente
		// conexas de la gráfica.
		stack.add(vert);
		vert.setDfsNum(count);
		count++;
		vert.setLow(vert.getDfsNum());
		for (final DefaultEdge edge : graph.outgoingEdgesOf(vert)) {
			LOG.trace("edge: " + edge);
			final Vertex neight = ((Edge) edge).to;
			if (/*!neight.getRemoved() && */!tree.containsVertex(neight)) {
				tree.addVertex(neight);
				tree.addEdge(vert, neight, edge);
				visit(neight);
				vert.setLow(Math.min(vert.getLow(), neight.getLow()));
			} else {
				vert.setLow(Math.min(vert.getLow(), neight.getDfsNum()));
			}
		}
		if (vert.isRootNode()) {
			LOG.info("strongComp : " + getStrongComp(vert));
		}
	}

	private Set<Vertex> getStrongComp(final Vertex vertRoot) {
		final Set<Vertex> strongComp = new HashSet<Vertex>();
		Vertex vertInStComp = null;
		do {
			vertInStComp = stack.pop();
			strongComp.add(vertInStComp);
			// Esto arroja exception, por lo que se sustituye:
			// graph.removeVertex(vertInStComp);
			vertInStComp.setRemoved(Boolean.TRUE);
		} while (!vertRoot.equals(vertInStComp));

		return strongComp;
	}

	private final transient AbstractBaseGraph<Vertex, DefaultEdge> graph;
	private final transient AbstractBaseGraph<Vertex, DefaultEdge> tree;
	private transient Integer count = 0;
	private final transient Stack<Vertex> stack = new Stack<Vertex>();

	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(StrongCompsDepthFirstSearch.class);
	}

}
