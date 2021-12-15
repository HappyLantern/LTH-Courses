package solutionOne;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
	
	
	public static void main(String[] args) {
		
		HashMap<Person, Person> result = null;
		
		LinkedList<Person> men = new LinkedList<Person>();
		LinkedList<Person> women = new LinkedList<Person>();
		
		Parser p = null;
		String file = "data\\sm-bbt-in.txt";
		try {
			p = new Parser(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		p.parse(men, women);
		
		System.out.println(men);
		for (int i = 0; i < men.size(); i++) {
		//	System.out.println(men.get(i).getPrefs());
		}
		System.out.println(women);
		for (int i = 0; i < women.size(); i++) {
		//	System.out.println(women.get(i).getPrefs());
		}
		
		StableMatching sm = new StableMatching(men, women);
		result = sm.match();
		
		System.out.println(result);
//		p.print(result);

		
	
		}

}
