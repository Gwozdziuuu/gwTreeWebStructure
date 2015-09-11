package com.gwozdz.prime.faces.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.gwozdz.hibernate.data.Tree;
import com.gwozdz.spring.service.TreeService;
import com.gwozdz.utils.BasePage;

@ManagedBean
@SessionScoped
public class TreeAction extends BasePage {
	
	private TreeNode rootNode;
	
	private List<Tree> treeList;
	private TreeNode selectedNode;
    private Tree tree;
    private Tree treeAim;
    private List<TreeNode> parentTree;
    private boolean isChild;

    @ManagedProperty("#{treeService}")
    private TreeService treeService;

    public TreeService getTreeService() {
        return treeService;
    }

    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }

    @PostConstruct
    public void init() {
    	tree = new Tree();
    	treeAim = new Tree();
    	treeList = new ArrayList<Tree>();
    	isChild = false;
        initializeTree();
    }
    
    private void initializeTree(){
    	Tree root = getRootFromDatabase();
        rootNode = new DefaultTreeNode("Root", null);
        createTree(root, rootNode);
        treeList = treeService.getAllTrees();
    }
    
    public String addTree() {
    	if(tree.getNodeData() != null && tree.getParentId() >= 0) {
    		treeService.addTree(tree);
    		addMessage("Wêze³ " + tree.getNodeData() + " zosta³ dodany");
    		init();
    	}    	
    	return "";
    }
    
    public String deleteTree(){
    	if(tree.getId() != 1) {
    		treeService.deleteTree(tree);
    		init();
        	addMessage("Wêze³ zosta³ usuniêty");
    	} else {
    		addWarning("Nie mo¿na usun¹æ korzenia");
    	}    	
    	return "";
    }
    
    public String moveTree() {
    	if(checkIfCanMove(tree.getId(), treeAim.getId())) {
    		Tree localTree = treeService.getTree(tree.getId());
	    	localTree.setParentId(treeAim.getId());
	    	treeService.updateTree(localTree);
	    	addMessage("Wêze³ " + localTree.getNodeData() + " zosta³ przeniesiony");
	    	init();
    	}		
    	return "";
    }
    
    public String updateTree() {
    	if(tree.getId() != 1) {
	    	Tree localTree = treeService.getTree(tree.getId());
	    	localTree.setNodeData(tree.getNodeData());
	    	localTree.setNodeValue(tree.getNodeValue());
	    	treeService.updateTree(localTree);
	    	addMessage("Wêze³ " + localTree.getNodeData() + " zosta³ wyedytowany");
	    	init();    	
    	} else {
    		addWarning("Nie mo¿na edytowaæ korzenia");
    	}
    	return "";
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
    	Tree localTree = treeService.getTree((Tree) event.getTreeNode().getData());
    	parentTree = new ArrayList<TreeNode>();
    	getParent(event.getTreeNode());
    	List<Tree> parents = parentTreeList();
    	int parentElements = parents.size();
    	long valuesSummary = countNodeValues(parents);
    	addMessage("Nazwa:" + localTree.getNodeData() + " Wartoœæ: " + localTree.getNodeValue());
    	addMessage("Iloœæ wêz³ów do korzenia: " + parentElements);
    	addMessage("Suma wartoœci w wêz³ach: " + valuesSummary);
    }
    
    private boolean checkIfCanMove(long treeId, long aimId) {
    	if(treeId != 1) {
    		if(aimId != treeId) {
    			checkIfChild(treeId, aimId);
    			if(!isChild) {
    				return true;
    			} else {
    				isChild = false;
    				addWarning("Operacja niedozwolona");
    			}
    		} else {
    			addWarning("Wêz³y musz¹ siê ró¿niæ");
    		}
    	} else {
    		addWarning("Nie mo¿na przenieœæ korzenia");
    	}
    	return false;
    }
    
    private boolean checkIfChild (long id, long parentId) {
    	Tree tree = treeService.getTree(parentId);
    	if(tree != null && !isChild) {
	    	if(tree.getParentId() == id) {
	    		isChild = true;
	    	} else {
	    		checkIfChild(id, tree.getParentId());
	    	}
    	}
    	return true;
    }
    
    private void getParent(TreeNode treeNode) {
    	TreeNode newTreeNode = treeNode.getParent();
    	if(!"root".equals(treeNode.getParent().getRowKey())) {
    		parentTree.add(treeNode);
    		getParent(newTreeNode);
    	}
    }
    
    private long countNodeValues(List<Tree> trees) {
    	long summary = 0;
    	for(Tree tree: trees) {
    		summary += tree.getNodeValue();
    	}
    	return summary;
    }
    
    private List<Tree> parentTreeList() {
    	List<Tree> returnList = new ArrayList<Tree>();
    	for(TreeNode tree: parentTree) {
    		returnList.add(treeService.getTree((Tree) tree.getData()));
    	}
    	
    	return returnList;
    }

    public TreeNode createTree(Tree treeObj, TreeNode rootNode) {
        TreeNode newNode = new DefaultTreeNode(treeObj, rootNode);
        List<Tree> childNodes1 = childNodes(treeObj.getId());
        for (Tree tt : childNodes1) {
            createTree(tt, newNode);
        }
        return newNode;
    }
    
    private List<Tree> childNodes(long id) {
    	List<Tree> trees = treeService.getAllTrees();
    	List<Tree> childNodes = new ArrayList<Tree>();
    	if(trees != null && !trees.isEmpty()) {
    		for(Tree tree: trees) {
    			if(tree.getParentId() == id) {
    				childNodes.add(tree);
    			}
    		}
    	}
    	return childNodes;
    }
    
    private Tree getRootFromDatabase() {
    	List<Tree> trees = treeService.getAllTrees();
    	if(trees != null && !trees.isEmpty()) {
    		for(Tree tree: trees) {
    			if(tree.getParentId() == 0) {
    				return tree;
    			}
    		}
    	}
    	return new Tree();
    }
    
    public void refreshTreeForUpdate() {
    	Tree localTree = treeService.getTree(tree.getId());
    	tree.setNodeData(localTree.getNodeData());
    	tree.setNodeValue(localTree.getNodeValue());
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode node) {
        rootNode = node;
    }

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public List<Tree> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<Tree> treeList) {
		this.treeList = treeList;
	}

	public Tree getTreeAim() {
		return treeAim;
	}

	public void setTreeAim(Tree treeAim) {
		this.treeAim = treeAim;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}
}
