package mx.home.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Search {
	private static final String START = "B";
	private static final String END = "E";
	private static final Logger LOG;

	public static void main(final String[] args) {
		final Graph graph = initGraph();
		final List<String> visited = new LinkedList<String>();
		visited.add(START);
		new Search().breadthFirst(graph, visited);
	}

	private static Graph initGraph() {
		// this graph is directional
		final Graph graph = new Graph();

		graph.addTwoWayVertex("A", "B");
		graph.addTwoWayVertex("A", "C");
		graph.addTwoWayVertex("B", "D");
		graph.addEdge("B", "E"); // this is the only one-way connection
		graph.addTwoWayVertex("B", "F");
		graph.addTwoWayVertex("C", "E");
		graph.addTwoWayVertex("C", "F");
		graph.addTwoWayVertex("E", "F");

		return graph;
	}

	private void breadthFirst(final Graph graph, final List<String> visited) {
		final LinkedList<String> visitedLocal = initVisitedLocal(visited);
		final List<String> nodes = graph.adjacentNodes(visitedLocal.getLast());
		examineAdjacentNodes(visitedLocal, nodes);
		// in breadth-first, recursion needs to come after visiting adjacent
		// nodes
		for (final String node : nodes) {
			if (visitedLocal.contains(node) || node.equals(END)) {
				continue;
			}
			visitedLocal.addLast(node);
			breadthFirst(graph, visitedLocal);
			visitedLocal.removeLast();
		}
	}

	private LinkedList<String> initVisitedLocal(final List<String> visited) {
		LinkedList<String> visitedLocal;
		if (visited instanceof LinkedList) {
			visitedLocal = (LinkedList<String>) visited;
		} else {
			throw new NotImplementedException(
					"FALTA TERMINAR IMPL cuando la lista subyacente no es LinkedList!!!");
		}
		return visitedLocal;
	}

	private void examineAdjacentNodes(final LinkedList<String> visited,
			final List<String> nodes) {
		// examine adjacent nodes
		for (final String node : nodes) {
			if (visited.contains(node)) {
				continue;
			}
			if (node.equals(END)) {
				visited.add(node);
				printPath(visited);
				visited.removeLast();
				break;
			}
		}
	}

	private void printPath(final List<String> visited) {
		final StringBuilder pathStr = new StringBuilder("\n");
		for (final String node : visited) {
			pathStr.append(node);
			pathStr.append(" ");
		}
		LOG.info(pathStr.toString());
	}

	static {
		LOG = LoggerFactory.getLogger(Search.class);
	}

}
