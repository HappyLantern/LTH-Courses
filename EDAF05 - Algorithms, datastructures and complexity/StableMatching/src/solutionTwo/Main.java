package solutionTwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	int[] men;
	int[] women;
	int[] next;
	int[][] preferences;
	static int n = -1;
	public static void main(String[] args)   {
		
		
		String f = "F:\\Programming\\School\\EDAF05 - Algorithms, datastructures and complexity\\StableMatching\\data\\sm-bbt-in.txt";
		
		File file = new File(f);
		
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String s = reader.readLine();
			while (s.charAt(0) == '#') {
				s = reader.readLine();
			}
			String[] split = s.split("=");
			n = Integer.parseInt(split[1]);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
	}
}
