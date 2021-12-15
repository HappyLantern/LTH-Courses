package solutionOne;

import java.util.HashMap;
import java.util.LinkedList;

public class StableMatching {
	
	public LinkedList<Person> men, women;
	
	public HashMap<Person, Person> marriedSet = new HashMap<Person, Person>();


	
	public StableMatching(LinkedList<Person> men, LinkedList<Person> women) {
		this.men = men;
		this.women = women;
	}
	
	public HashMap<Person, Person> match() {
		
		while (!men.isEmpty()) {
			Person m = men.removeFirst();
			while (m.free()) {
				Person w = m.propose();
			//	System.out.println(m + " proposes to: " + w);
				if (w.free()) {
					marriedSet.put(m, w);
					m.setFree(false, w);
					w.setFree(false, m);
				} else {
					if (w.prefers(m)) {
					//	System.out.println(w + " leaves " + w.getCurrent() + " for " + m);
						marriedSet.remove(w.getCurrent());
						men.add(w.getCurrent());
						w.getCurrent().setFree(true, null);
						
						marriedSet.put(m, w);
						m.setFree(false, w);
						w.setFree(false, m);
					} else { 
						men.add(m);
					}
				}	
			}
		}
		
		
		return marriedSet;
	}
	
	public void print() {
		
	}
	
	
}
