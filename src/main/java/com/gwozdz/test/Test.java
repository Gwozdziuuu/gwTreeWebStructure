package com.gwozdz.test;

import org.junit.Assert;

import com.gwozdz.hibernate.data.Tree;



public class Test {

	@org.junit.Test
	public void testAddTree() {
		Tree tree = new Tree();
		tree.setId(2);
		Assert.assertEquals(true, tree.getId());
		tree.setNodeData("Node2");
		Assert.assertEquals(true, tree.getNodeData());
		tree.setNodeValue(32);
		Assert.assertEquals(true, tree.getNodeValue());
		tree.setParentId(1);
		Assert.assertEquals(true, tree.getParentId());
	}
	
	@org.junit.Test
	public void testCheckIfChild() {
		Tree tree = new Tree();
		tree.setId(2);
		Assert.assertEquals(true, tree.getId());
	}
	
}
