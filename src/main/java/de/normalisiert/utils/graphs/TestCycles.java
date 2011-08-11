package de.normalisiert.utils.graphs;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Testfile for elementary cycle search.
 * 
 * @author Frank Meyer
 * 
 */
public class TestCycles {

	@SuppressWarnings("unused")
	private void firtsAayMatrix() {
		// adjMatrix[0][1] = true;
		// adjMatrix[1][2] = true;
		// adjMatrix[2][0] = true;
		// adjMatrix[2][4] = true;
		// adjMatrix[1][3] = true;
		// adjMatrix[3][6] = true;
		// adjMatrix[6][5] = true;
		// adjMatrix[5][3] = true;
		// adjMatrix[6][7] = true;
		// adjMatrix[7][8] = true;
		// adjMatrix[7][9] = true;
		// adjMatrix[9][6] = true;
	}

	@SuppressWarnings("unused")
	private void secondAayMatrix() {
		// adjMatrix[0][1] = true;
		// adjMatrix[1][2] = true;
		// adjMatrix[2][0] = true;
		// adjMatrix[2][6] = true;
		// adjMatrix[3][4] = true;
		// adjMatrix[4][5] = true;
		// adjMatrix[4][6] = true;
		// adjMatrix[5][3] = true;
		// adjMatrix[6][7] = true;
		// adjMatrix[7][8] = true;
		// adjMatrix[8][6] = true;
		// adjMatrix[6][1] = true;
	}

	private static boolean[][] myAdjMatrix(int numVertices) {
		final boolean[][] adjMatrix = new boolean[numVertices][numVertices];

		adjMatrix[0][3] = true;
		adjMatrix[3][0] = true;
		adjMatrix[1][2] = true;
		adjMatrix[2][1] = true;
		adjMatrix[1][9] = true;
		adjMatrix[9][1] = true;
		adjMatrix[1][14] = true;
		adjMatrix[14][1] = true;
		adjMatrix[3][1] = true;
		adjMatrix[1][3] = true;
		adjMatrix[3][2] = true;
		adjMatrix[2][3] = true;
		adjMatrix[3][9] = true;
		adjMatrix[9][3] = true;
		adjMatrix[3][13] = true;
		adjMatrix[13][3] = true;
		adjMatrix[3][14] = true;
		adjMatrix[14][3] = true;
		adjMatrix[4][6] = true;
		adjMatrix[6][4] = true;
		adjMatrix[4][8] = true;
		adjMatrix[8][4] = true;
		adjMatrix[6][0] = true;
		adjMatrix[0][6] = true;
		adjMatrix[6][5] = true;
		adjMatrix[5][6] = true;
		adjMatrix[6][7] = true;
		adjMatrix[7][6] = true;
		adjMatrix[6][10] = true;
		adjMatrix[10][6] = true;
		adjMatrix[7][9] = true;
		adjMatrix[9][7] = true;
		adjMatrix[9][15] = true;
		adjMatrix[15][9] = true;
		adjMatrix[10][11] = true;
		adjMatrix[11][10] = true;
		adjMatrix[12][0] = true;
		adjMatrix[0][12] = true;
		adjMatrix[12][1] = true;
		adjMatrix[1][12] = true;
		adjMatrix[12][4] = true;
		adjMatrix[4][12] = true;
		adjMatrix[12][6] = true;
		adjMatrix[6][12] = true;
		return adjMatrix;
	}

	public static void main(final String[] args) {
		String nodes[] = new String[numVertices];
		boolean adjMatrix[][] = myAdjMatrix(numVertices);

		for (int i = 0; i < numVertices; i++) {
			// nodes[i] = "Node " + NODE_NAME[i];
			nodes[i] = NODE_NAME[i];
		}

		final ElementaryCyclesSearch ecs = new ElementaryCyclesSearch(
				adjMatrix, nodes);
		// final Collection<Collection<Object>> cycles =
		// ecs.getElementaryCycles();
		final List<List<Object>> cycles = ecs.getElementaryCycles();
		// final Collection<Collection<Object>>
		Collection<? extends Collection<Object>> test = transformCyclesFromListToSet(cycles);
		printCycles(test);
		printCycles(cycles);

	}

	private static Collection<? extends Collection<Object>> transformCyclesFromListToSet(
			final Collection<List<Object>> cycles) { // TODO pasarlo a Coll de Colls de Objs
		Collection<Collection<Object>> cyclesInSet = new HashSet<Collection<Object>>();
		for (final List<Object> cycle : cycles) {
			// for (final List<Object> cycle : test) {
			// System.out.println(cycle.getClass());
			Set<Object> testInner = new HashSet<Object>();
			testInner.addAll(cycle);
			cyclesInSet.add(testInner);
		}
		return cyclesInSet;
	}

	private static void printCycles(
			Collection<? extends Collection<Object>> test) {
		Integer countCycles = 0;
		for (final Collection<Object> cycle : test) {
			/*
			 * Estos ciclos de longitud 2 no son de interes pues son un ciclo
			 * ficticio debido a que se ha generado al hacer simetrica a la
			 * matriz de adjacency
			 */
			if (cycle.size() > 2) {
				countCycles++;
				// System.out.print(cycles.indexOf(cycle) + " ");
				System.out.print(countCycles + "\t");
				for (Object node : cycle) {
					System.out.print(node);
					System.out.print("\t");
				}
				// for (int j = 0; j < cycle.size(); j++) {
				// String node = (String) cycle.get(j);
				// if (j < cycle.size() - 1) {
				// System.out.print(node + " -> ");
				// } else {
				// System.out.print(node);
				// }
				// }
				System.out.print("\n");
			}
		}
	}

	private static final String[] NODE_NAME;
	private static final Integer numVertices = 16;
	static {
		NODE_NAME = new String[] { "BOV", "CC", "CES", "CN", "CUP", "DIV",
				"EMI", "ERA", "ESC", "INST", "INSTR", "MER", "PC", "SEC", "TC",
				"TI" };
		// for (String nodeName : NODE_NAME) {
		// nodeName[0] = "";
		// nodeName[1] = "";
		// nodeName[2] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// nodeName[] = "";
		// }
	}

}
