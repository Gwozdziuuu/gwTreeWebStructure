package com.gwozdz.hibernate.data;

public class Tree {

	private long id;
	private String nodeData;
	private long parentId;
	private long nodeValue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNodeData() {
		return nodeData;
	}
	public void setNodeData(String nodeData) {
		this.nodeData = nodeData;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public long getNodeValue() {
		return nodeValue;
	}
	public void setNodeValue(long nodeValue) {
		this.nodeValue = nodeValue;
	}
	
	
}
