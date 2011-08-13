package de.normalisiert.utils.graphs;

import java.util.List;
import java.util.Vector;

import net.algowiki.AdjacencyList;
import net.algowiki.Edge;
import net.algowiki.Node;

import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a helpclass for the search of all elementary cycles in a graph with
 * the algorithm of Johnson. For this it searches for strong connected
 * components, using the algorithm of Tarjan. The constructor gets an
 * adjacency-list of a graph. Based on this graph, it gets a nodenumber s, for
 * which it calculates the subgraph, containing all nodes {s, s + 1, ..., n},
 * where n is the highest nodenumber in the original graph (e.g. it builds a
 * subgraph with all nodes with higher or same nodenumbers like the given node
 * s). It returns the strong connected component of this subgraph which contains
 * the lowest nodenumber of all nodes in the subgraph.<br>
 * <br>
 * 
 * For a description of the algorithm for calculating the strong connected
 * components see:<br>
 * Robert Tarjan: Depth-first search and linear graph algorithms. In: SIAM
 * Journal on Computing. Volume 1, Nr. 2 (1972), pp. 146-160.<br>
 * For a description of the algorithm for searching all elementary cycles in a
 * directed graph see:<br>
 * Donald B. Johnson: Finding All the Elementary Circuits of a Directed Graph.
 * SIAM Journal on Computing. Volumne 4, Nr. 1 (1975), pp. 77-84.<br>
 * <br>
 * 
 * @author Frank Meyer, web_at_normalisiert_dot_de
 * @version 1.1, 22.03.2009
 * 
 */
public class StrongConnectedComponentsWithJgraphT {

	/** Adjacency-list of original graph */
	private AdjacencyList adjListOriginal;

	/** Adjacency-list of currently viewed subgraph */
	private int[][] adjList = null;

	/** Helpattribute for finding scc's */
	private boolean[] visited = null;

	/** Helpattribute for finding scc's */
	private Vector stack = null;

	/** Helpattribute for finding scc's */
	private int[] lowlink = null;

	/** Helpattribute for finding scc's */
	private int[] number = null;

	/** Helpattribute for finding scc's */
	private int sccCounter = 0;

	/** Helpattribute for finding scc's */
	private Vector currentSCCs = null;

	/**
	 * Constructor.
	 * 
	 * @param adjList
	 *            adjacency-list of the graph
	 */
	public StrongConnectedComponentsWithJgraphT(int[][] adjList) {

	}

	public StrongConnectedComponentsWithJgraphT(AdjacencyList adjList2) {
		this.adjListOriginal = adjList2;
	}

	/**
	 * This method returns the adjacency-structure of the strong connected
	 * component with the least vertex in a subgraph of the original graph
	 * induced by the nodes {s, s + 1, ..., n}, where s is a given node. Note
	 * that trivial strong connected components with just one node will not be
	 * returned.
	 * 
	 * @param vertex
	 *            node s
	 * @return SCCResult with adjacency-structure of the strong connected
	 *         component; null, if no such component exists
	 */
	public SCCResult getAdjacencyList(final Node vertex) {
		this.visited = new boolean[this.adjListOriginal.length];
		this.lowlink = new int[this.adjListOriginal.length];
		this.number = new int[this.adjListOriginal.length];
		this.visited = new boolean[this.adjListOriginal.length];
		this.stack = new Vector();
		this.currentSCCs = new Vector();

		this.makeAdjListSubgraph(vertex);

		for (int i = vertex; i < this.adjListOriginal.length; i++) {
			if (!this.visited[i]) {
				this.getStrongConnectedComponents(i);
				Vector nodes = this.getLowestIdComponent();
				if (nodes != null && !nodes.contains(Integer.valueOf(vertex))
						&& !nodes.contains(Integer.valueOf(vertex + 1))) {
					return this.getAdjacencyList(vertex + 1);
				} else {
					Vector[] adjacencyList = this.getAdjList(nodes);
					if (adjacencyList != null) {
						for (int j = 0; j < this.adjListOriginal.length; j++) {
							if (adjacencyList[j].size() > 0) {
								return new SCCResult(adjacencyList, j);
							}
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Builds the adjacency-list for a subgraph containing just nodes >= a given
	 * index.
	 * 
	 * @param node
	 *            Node with lowest index in the subgraph
	 */
	private void makeAdjListSubgraph(int node) {
		this.adjList = new int[this.adjListOriginal.length][0];

		for (int i = node; i < this.adjList.length; i++) {
			Vector successors = new Vector();
			for (int j = 0; j < this.adjListOriginal[i].length; j++) {
				if (this.adjListOriginal[i][j] >= node) {
					successors.add(Integer.valueOf(this.adjListOriginal[i][j]));
				}
			}
			if (successors.size() > 0) {
				this.adjList[i] = new int[successors.size()];
				for (int j = 0; j < successors.size(); j++) {
					Integer succ = (Integer) successors.get(j);
					this.adjList[i][j] = succ.intValue();
				}
			}
		}
	}

	/**
	 * Calculates the strong connected component out of a set of scc's, that
	 * contains the node with the lowest index.
	 * 
	 * @return Vector::Integer of the scc containing the lowest nodenumber
	 */
	private Vector getLowestIdComponent() {
		int min = this.adjList.length;
		Vector currScc = null;

		for (int i = 0; i < this.currentSCCs.size(); i++) {
			Vector scc = (Vector) this.currentSCCs.get(i);
			for (int j = 0; j < scc.size(); j++) {
				Integer node = (Integer) scc.get(j);
				if (node.intValue() < min) {
					currScc = scc;
					min = node.intValue();
				}
			}
		}

		return currScc;
	}

	/**
	 * @return Vector[]::Integer representing the adjacency-structure of the
	 *         strong connected component with least vertex in the currently
	 *         viewed subgraph
	 */
	private Vector[] getAdjList(Vector nodes) {
		Vector[] lowestIdAdjacencyList = null;

		if (nodes != null) {
			lowestIdAdjacencyList = new Vector[this.adjList.length];
			for (int i = 0; i < lowestIdAdjacencyList.length; i++) {
				lowestIdAdjacencyList[i] = new Vector();
			}
			for (int i = 0; i < nodes.size(); i++) {
				int node = ((Integer) nodes.get(i)).intValue();
				for (int j = 0; j < this.adjList[node].length; j++) {
					int succ = this.adjList[node][j];
					if (nodes.contains(Integer.valueOf(succ))) {
						lowestIdAdjacencyList[node].add(Integer.valueOf(succ));
					}
				}
			}
		}

		return lowestIdAdjacencyList;
	}

	/**
	 * Searchs for strong connected components reachable from a given node.
	 * 
	 * @param root
	 *            node to start from.
	 */
	private void getStrongConnectedComponents(int root) {
		this.sccCounter++;
		this.lowlink[root] = this.sccCounter;
		this.number[root] = this.sccCounter;
		this.visited[root] = true;
		this.stack.add(Integer.valueOf(root));

		for (int i = 0; i < this.adjList[root].length; i++) {
			int w = this.adjList[root][i];
			if (!this.visited[w]) {
				this.getStrongConnectedComponents(w);
				this.lowlink[root] = Math.min(lowlink[root], lowlink[w]);
			} else if (this.number[w] < this.number[root]) {
				if (this.stack.contains(Integer.valueOf(w))) {
					lowlink[root] = Math
							.min(this.lowlink[root], this.number[w]);
				}
			}
		}

		// found scc
		if ((lowlink[root] == number[root]) && (stack.size() > 0)) {
			int next = -1;
			Vector scc = new Vector();

			do {
				next = ((Integer) this.stack.get(stack.size() - 1)).intValue();
				this.stack.remove(stack.size() - 1);
				scc.add(Integer.valueOf(next));
			} while (this.number[next] > this.number[root]);

			// simple scc's with just one node will not be added
			if (scc.size() > 1) {
				this.currentSCCs.add(scc);
			}
		}
	}

	/**
	 * Version del main usando la estructura alterna con API JgraphT.
	 */
	public static void main(String[] args) {

		initGraph();

		final AdjacencyList adjList = new net.algowiki.AdjacencyList(
				initGraph());// .getAdjacent(v)
		final StrongConnectedComponentsWithJgraphT scc = new StrongConnectedComponentsWithJgraphT(
				adjList);
		for (final Node vertex : adjList.getSourceNodeSet()) {
			LOG.debug("vertex_i: " + vertex + "\n");
			SCCResult r = scc.getAdjacencyList(vertex);
			if (r != null) {
				List<Node>[] al = scc.getAdjacencyList(vertex).getAdjList();
				for (List<Node> list : al) {
					
				}
				for (int j = i; j < al.length; j++) {
					if (al[j].size() > 0) {
						LOG.debug("j: " + j);
						for (int k = 0; k < al[j].size(); k++) {
							LOG.debug(" _" + al[j].get(k).toString());
						}
						LOG.debug("\n");
					}
				}
				LOG.debug("\n");
			}
		}
	}

	private static AbstractGraph<Node, Edge> initGraph() {
		// TODO To generalize final AbstractGraph<? extends Object, ? extends
		// Object> graph = new DefaultDirectedGraph<Object, Object>(
		// Edge.class);
		final AbstractGraph<Node, Edge> graph = new DefaultDirectedGraph<Node, Edge>(
				Edge.class);
		Node v0 = new Node(0);
		Node v1 = new Node(1);
		Node v2 = new Node(2);

		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);

		graph.addEdge(v0, v1, new Edge(v0, v1));
		graph.addEdge(v1, v0, new Edge(v1, v0));
		graph.addEdge(v1, v2, new Edge(v1, v2));

		return graph;
	}

	private static final Logger LOG;

	static {
		LOG = LoggerFactory
				.getLogger(StrongConnectedComponentsWithJgraphT.class);
	}

}
