package de.normalisiert.utils.graphs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.algowiki.Node;

public class SCCResult {
	private final List<Node>[] adjList;
	private final Integer lowestNodeId;

	public SCCResult(final List<Node>[] adjList, final Integer lowestNodeId) {
		final Set<Integer> nodeIDsOfSCC = new HashSet<Integer>();
		this.adjList = adjList;
		this.lowestNodeId = lowestNodeId;
		if (this.adjList != null) {
			for (int i = this.lowestNodeId; i < this.adjList.length; i++) {
				if (this.adjList[i].size() > 0) {
					nodeIDsOfSCC.add(Integer.valueOf(i));
				}
			}
		}
	}

	public List<Node>[] getAdjList() {
		return adjList;
	}

	public Integer getLowestNodeId() {
		return lowestNodeId;
	}

}
