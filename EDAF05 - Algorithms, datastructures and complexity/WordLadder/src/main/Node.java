package main;

import java.util.ArrayList;

public class Node {
	private String word;
	private ArrayList<Node> children;
	private Node parent;
	private boolean discovered;
	
	public Node(String word) {
		this.word = word;
		children   = new ArrayList<Node>();
		discovered = false;
	}
	
	public void addEdge(Node n) {
		children.add(n);
	}
	
	
	public String toString() {
		return word;
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	public void setDiscovered() {
		discovered = true;
	}
	
	public boolean getDiscovered() {
		return discovered;
	}
	
	public void setParent(Node p) {
		parent = p;
	}
	
	public Node getParent() {
		return parent;
	}
}
