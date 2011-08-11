package mx.home.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Graph {

	private transient final Map<String, Collection<String>> map;

	public Graph() {
		map = new HashMap<String, Collection<String>>();
	}

	public void addEdge(final String node1, final String node2) {
		Collection<String> adjacent = map.get(node1);
		if (adjacent == null) {
			adjacent = new HashSet<String>();
			map.put(node1, adjacent);
		}
		adjacent.add(node2);
	}

	public void addTwoWayVertex(final String node1, final String node2) {
		addEdge(node1, node2);
		addEdge(node2, node1);
	}

	// public boolean isConnected(final String node1, final String node2) {
	// Boolean isConnected;
	// final Collection<String> adjacent = map.get(node1);
	// if (adjacent == null) {
	// isConnected = Boolean.FALSE;
	// } else {
	// isConnected = adjacent.contains(node2);
	// }
	// return isConnected;
	// }

	public List<String> adjacentNodes(final String last) {
		final Collection<String> adjacent = map.get(last);
		List<String> adjacentNodes;
		if (adjacent == null) {
			adjacentNodes = new LinkedList<String>();
		} else {
			adjacentNodes = new LinkedList<String>(adjacent);
		}
		return adjacentNodes;
	}

	@Override
	public String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);
		for (Entry<String, Collection<String>> entry : map.entrySet()) {
			toStringBuilder.append("\nkey", entry.getKey());
			// toStringBuilder.append(",");
			toStringBuilder.append("value", entry.getValue());
			toStringBuilder.append("\t");
		}
		return toStringBuilder.toString();
	}

}