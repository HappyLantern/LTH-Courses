package solutionOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class Person {

	private String name;
	private int id;
	private boolean free;
	private Person current;
	
	private HashMap<Person, Integer> preferences;
	private LinkedList<Person> candidates;
	private Person[] next;
	private int n = -1;

	public Person(String name, int id) {
		this.name = name;
		this.id = id;
		free = true;
	}
	
	public void setCandidates(LinkedList<Person> candidates, HashMap<Person, Integer> preferences) {
		this.candidates = candidates;
		this.preferences = preferences;
		next = new Person[candidates.size()];
		//n = candidates.size();
		sortCandidates();
		}
	
	private void sortCandidates() {
		for(int i = 0; i < candidates.size(); i++) {
			int pref = preferences.get(candidates.get(i));
			next[candidates.size() - 1 - pref] = candidates.get(i);
		}
		
		//System.out.println(name);
		for(int i = 0; i < next.length; i++) {
		//	System.out.print(next[i] + " ");
		}
		//System.out.println();
	}

	public boolean free() {
		return free;
	}
	
	public void setFree(boolean free, Person current) {
		this.free = free;
		this.current = current;
	}
	
	public Person propose() {
		n++;
		return next[n];
	}
	
	
	public String toString() {
		return name;
	}
	
	public Person getCurrent() {
		return current;
	}
	
	public boolean prefers(Person p) {
		return preferences.get(current) < preferences.get(p);
	}
	
	public HashMap<Person, Integer> getPrefs() {
		return preferences;
	}
	
}
