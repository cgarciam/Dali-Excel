package edu.uci.ics;

public class Vertex {

	private Long identifier;
	private Integer dfsNum;
	private Integer low;
	private Boolean removed = Boolean.FALSE;

	public Vertex(final Long identifier) {
		setIdentifier(identifier);
	}

	public Long getIdentifier() {
		return identifier;
	}

	private void setIdentifier(final Long identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return identifier.toString();
	}

	public Integer getDfsNum() {
		return dfsNum;
	}

	public void setDfsNum(final Integer dfsNum) {
		this.dfsNum = dfsNum;
	}

	public Integer getLow() {
		return low;
	}

	public void setLow(final Integer low) {
		this.low = low;
	}

	public Boolean isRootNode() {
		System.out.println(identifier + " " + low.equals(dfsNum));
		return low.equals(dfsNum);
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(final Boolean removed) {
		this.removed = removed;
	}

	@Override
	public boolean equals(final Object obj) {
		Boolean equals;
		if (obj instanceof Vertex) {
			equals = ((Vertex) obj).identifier.equals(identifier);
		} else {
			equals = Boolean.FALSE;
		}
		return equals;
	}

	@Override
	public int hashCode() {
		return identifier.intValue();
	}

}
