package main;

import java.util.ArrayList;
import java.util.HashMap;

public class WordLadder {
	
	ArrayList<Node> nodes;
	int[] discovered;
	
	public WordLadder(ArrayList<Node> nodes) {
		this.nodes = nodes;
		discovered = new int[nodes.size()];
	}
	
	
	public ArrayList<Node> buildGraph() {
		
		int n = 0;
		for (int i = 0; i < nodes.size() - 1; i++) {
			for (int j = i+1; j < nodes.size(); j++) {
				
				int[] k = check(nodes.get(i), nodes.get(j));
				if (k[0] != -1) 
					nodes.get(i).addEdge(nodes.get(j));
				if (k[1] != -1) 
					nodes.get(j).addEdge(nodes.get(i));
				n++;
			}
		}
		
		return nodes;
	}
	
	
	
	
	
	private int[] check(Node n1, Node n2) {
		int[] k = {-1, -1};
		
		
		String s1 = n1.toString().substring(1, 5);
		String s2 = n2.toString();
		StringBuilder sb = new StringBuilder(n2.toString());
		
		int n = 0;
		
		
		for (int i = 0; i < s1.length(); i++) {
			char c = s1.charAt(i);
			if (s2.contains(c + "")) {
				n++;
				sb.deleteCharAt(s2.indexOf(c));
				s2 = sb.toString();
			}
		}
				
		if (n == 4) {
			k[0] = 1;
		}
		
		n = 0;
		
		s1 = n1.toString();
		s2 = n2.toString().substring(1, 5);
		sb = new StringBuilder(n1.toString());
		
		for (int i = 0; i < s2.length(); i++) {
			char c = s2.charAt(i);
			if (s1.contains(c + "")) {
				n++;
				sb.deleteCharAt(s1.indexOf(c));
				s1 = sb.toString();
			}
		}
		
		if (n == 4) {
			k[1] = 1;
		}

		return k;
	}
	
	public void bfs(Node from, Node to) {
		
		int i = 0;
		Node currRoot = from;
		
		ArrayList<ArrayList<Node>> L = new ArrayList<ArrayList<Node>>();
		L.add(new ArrayList<Node>());
		from.setDiscovered();
		L.get(i).add(from);
		
		
		while (!L.get(i).isEmpty()) {
			L.add(new ArrayList<Node>());
			for (Node n : L.get(i)) {
				for (Node n2 : n.getChildren()) {
					if (!n2.getDiscovered()) {
						n2.setDiscovered();
						L.get(i+1).add(n2);
						currRoot.addEdge(n2);
					}
				}
			}
			i++;
		}
	}
}
