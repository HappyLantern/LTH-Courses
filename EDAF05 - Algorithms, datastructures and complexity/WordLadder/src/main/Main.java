package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {

	public static void main(String[] args) {

		ArrayList<Node> nodes = new ArrayList<Node>();
		HashMap<Node, Node> pairs = new HashMap<Node, Node>();

		File f1 = new File("data\\words-10.txt");
		File f2 = new File("data\\words-10-in.txt");
		Parser p = new Parser(f1, f2);

		try {
			p.parseWords(nodes);
			p.parsePairs(pairs);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(nodes);
		System.out.println(pairs);

		WordLadder wLadder = new WordLadder(nodes);
		wLadder.buildGraph();

		Node[] keys = new Node[pairs.size()];

		pairs.keySet().toArray(keys);

		for (int i = 0; i < pairs.size(); i++) {
			wLadder.bfs(keys[i], pairs.get(keys[i]));
		}
		
	}
}
