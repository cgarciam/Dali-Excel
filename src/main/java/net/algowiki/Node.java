package net.algowiki;

public class Node {

	public Integer index;
	public int lowlink;
	private final Integer id;

	public Node(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return id.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			return id.equals(((Node) obj).id);
		} else {
			return false;
		}
	}

}
