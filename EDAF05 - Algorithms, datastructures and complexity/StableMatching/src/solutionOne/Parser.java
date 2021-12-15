package solutionOne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Parser {
	
	private BufferedReader reader;
	private static int n = -1;
	private String s = "";
	
	public Parser(File file) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(file));
		System.out.println("Read the file!");
	}
	
	
	public void parse(LinkedList<Person> men, LinkedList<Person> women) {
		try {
			while ((s = reader.readLine()) != null) {
				skipComments();
				System.out.println("Parsed through the comments!");
				parsePeople(men, women);
				System.out.println("Parsed the people!");
				parsePreferences(men, women);
				System.out.println("Parsed the preferences!");
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void skipComments() throws IOException {
		while (s.charAt(0) == '#') {
			s = reader.readLine();
		}
	}

	private void parsePeople(LinkedList<Person> men, LinkedList<Person> women) throws IOException {
		String[] split = s.split("=");
		n = Integer.parseInt((split[1]));
		System.out.println(n);
		for (int i = 0; i < 2*n; i++) {
			s = reader.readLine();
			String[] name = s.split(" ");
			if ((i % 2) == 0) {
				men.add(new Man(name[1], i));
			} else {
				women.add(new Woman(name[1], i));
			}
		}
		reader.readLine();
	}
	
	private void parsePreferences(LinkedList<Person> men, LinkedList<Person> women) throws IOException {
		for (int i = 0; i < 2*n; i++) {
			HashMap<Person, Integer> preferences = new HashMap<Person, Integer>();

			s = reader.readLine();
			String[] pref = s.split(" ");
				for (int k = 1; k < pref.length; k++) {		
				//	System.out.println(pref[k]);
					int index = (Integer.parseInt(pref[k])-1)/2;
					if ((i % 2) == 0) {
						preferences.put(women.get(index), n - k);
					} else {
						preferences.put(men.get(index), n - k);
					}
				}
			
			if ((i % 2) == 0) {
				men.get(i/2).setCandidates(women, preferences);
			} else {
				women.get((i-1)/2).setCandidates(men, preferences);
			}
		}
	}
	
	public void print(HashMap<Person, Person> result) {
		
	}
	
	
	
	

}
