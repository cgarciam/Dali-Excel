package net.algowiki;

public class Node {

	public Integer index;
	public int lowlink;
	private transient final Integer identifier;

	public Node(final Integer identifier) {
		this.identifier = identifier;
	}

	public Integer getId() {
		return identifier;
	}

	@Override
	public String toString() {
		return identifier.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		Boolean areEqual;
		if (obj instanceof Node) {
			areEqual = identifier.equals(((Node) obj).identifier);
		} else {
			areEqual = Boolean.FALSE;
		}
		return areEqual;
	}

	@Override
	public int hashCode() {
		return identifier;
	}

}
