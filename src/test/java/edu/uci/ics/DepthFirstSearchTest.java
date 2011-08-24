package edu.uci.ics;

import org.jgrapht.Graph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

public class DepthFirstSearchTest {

	@Test
	public void testExecuteStCmp() {
		// AbstractBaseGraph<Vertex, DefaultEdge> tree =
		new StrongCompsDepthFirstSearch(initGraph()).execute();
	}

	//@Test
	public void testExecute() {
		AbstractBaseGraph<Vertex, DefaultEdge> tree = new DepthFirstSearch(
				initGraph()).execute();
		System.out.println(tree.isAllowingLoops());
		// Un buen rato estuve explorando esta parte del API, sin llegar a nada
		// en concreto (como se usa?)

		// Object arg00 = new Object();
		// ConnectedComponentTraversalEvent arg1 = new
		// ConnectedComponentTraversalEvent(arg00 , 1);
		// TraversalListener<Vertex, DefaultEdge> arg0 = new
		// TraversalListenerAdapter<Vertex, DefaultEdge>();
		// arg0.connectedComponentStarted(arg1);
		// Vertex arg11 = null;
		// for (Vertex v0 : tree.vertexSet()) {
		// arg11 = v0;
		// break;
		// }
		// VertexTraversalEvent<Vertex> vte = new
		// VertexTraversalEvent<Vertex>(arg00, arg11);
		// arg0.vertexFinished(vte );
		// new DepthFirstIterator<Vertex,
		// DefaultEdge>(initGraph()).addTraversalListener(arg0);
		//
	}

	private Graph<Vertex, DefaultEdge> initGraph() {
		final Class<? extends DefaultEdge> clazz = Edge.class;
		final AbstractBaseGraph<Vertex, DefaultEdge> graph = new DefaultDirectedGraph<Vertex, DefaultEdge>(
				clazz);
		final Vertex vert0 = new Vertex(0L);
		final Vertex vert1 = new Vertex(1L);
		final Vertex vert2 = new Vertex(2L);
		final Vertex vert3 = new Vertex(3L);

		// NOTA: The resulting tree depends on the details of vertices ordering:
		graph.addVertex(vert0);
		graph.addVertex(vert1);
		graph.addVertex(vert2);
		graph.addVertex(vert3);

		final DefaultEdge edge01 = new Edge(vert0, vert1);
		final DefaultEdge edge02 = new Edge(vert0, vert2);
		final DefaultEdge edge23 = new Edge(vert2, vert3);
		final DefaultEdge edge21 = new Edge(vert2, vert1);

		graph.addEdge(vert0, vert1, edge01);
		graph.addEdge(vert0, vert2, edge02);
		graph.addEdge(vert2, vert3, edge23);
		graph.addEdge(vert2, vert1, edge21);

		return graph;
	}

}
