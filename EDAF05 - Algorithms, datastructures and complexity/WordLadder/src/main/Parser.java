package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
	BufferedReader r;
	File f1;
	File f2;
	
	public Parser(File f1, File f2) {
		this.f1 = f1;
		this.f2 = f2;
	}
	
	
	public void parseWords(ArrayList<Node> nodes) throws IOException {
		r = new BufferedReader(new FileReader(f1));
		String s;
		while ((s = r.readLine()) != null) {
			nodes.add(new Node(s));
		}
	}
	
	public void parsePairs(HashMap<Node, Node> pairs) throws IOException {
		r = new BufferedReader(new FileReader(f2));
		String s;
		while ((s = r.readLine()) != null) {
			String[] pair = s.split(" ");
			pairs.put(new Node(pair[0]), new Node(pair[1]));
		}
	}
}
