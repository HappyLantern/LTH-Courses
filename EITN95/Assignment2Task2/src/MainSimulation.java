import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class MainSimulation extends Global {

	public static void main(String[] args) {
		Random rand = new Random();	
		
		int experiments[] = {0,1,2};
		ArrayList<ArrayList<Double>> results = new ArrayList<>();
		ArrayList<int[]> recorded_time = new ArrayList<>();
		int minimumSimulations = 100;
		int simulations = 0;
		boolean cont;
		do {
			simulations++;
			for (int e : experiments) {
				if(results.size() == e) {
					results.add(new ArrayList<Double>());
					recorded_time.add(new int[1]);
				}
				ArrayList<Double> currentResults = results.get(e);
				int[] currentTimes = recorded_time.get(e);
				Signal actSignal;
				new SignalList();
				time = 0;
				roomY = 20;
				roomX = 20;
				noStudents = 20;
				studentsMet = 0;
				room = new int[roomY][roomX];
				students = new Student[noStudents];
				int v;
				switch(e) {
					case 0:
						v = 2;
						talkTime = 60;
						for (int i = 0; i < noStudents; i++) {
							int x = rand.nextInt(roomX);
							int y = rand.nextInt(roomY);
							Student s = new Student(new Point(x,y), v);
							students[i] = s;
							SignalList.SendSignal(UPDATE, s, s, time);
						}
						break;
					case 1:
						v = 4;
						talkTime = 60;
						for (int i = 0; i < noStudents; i++) {
							int x = rand.nextInt(roomX);
							int y = rand.nextInt(roomY);
							Student s = new Student(new Point(x,y), v);
							students[i] = s;
							SignalList.SendSignal(UPDATE, s, s, time);
						}
						break;
					case 2:
						talkTime = 60;
						for (int i = 0; i < noStudents; i++) {
							int x = rand.nextInt(roomX);
							int y = rand.nextInt(roomY);
							Student s = new Student(new Point(x,y), 1+rand.nextInt(7));
							students[i] = s;
							SignalList.SendSignal(UPDATE, s, s, time);
						}
						break;
				}
				
				while(studentsMet < noStudents) {
					actSignal = SignalList.FetchSignal();
					time = actSignal.arrivalTime;
					actSignal.destination.TreatSignal(actSignal);
				}
				currentResults.add(time);
				for (Student s : students) {
					for (int i = 0; i < students.length; i++) {
						if (s != students[i]) {
							if (s.acquaintances[i] >= currentTimes.length) {
								int[] temp = new int[s.acquaintances[i]+1];
								for (int n = 0; n < currentTimes.length; n++) {
									temp[n] = currentTimes[n];
								}
								currentTimes = temp;
							}
							currentTimes[s.acquaintances[i]]++;
						}
					}
				}
				results.set(e, currentResults);
				recorded_time.set(e, currentTimes);
			}
			cont = simulations < minimumSimulations;
			if (!cont) {
				cont = !checkConfidenceIntervals(results);
			}
		} while (cont);
		for (int eNbr : experiments) {
			StringBuilder sb = new StringBuilder("conf_intervals_");
			sb.append(String.valueOf(eNbr));
			sb.append(".m");
			SimpleFileWriter c = new SimpleFileWriter(sb.toString(), false);
			sb = new StringBuilder("times_");
			sb.append(String.valueOf(eNbr));
			sb.append(".m");
			SimpleFileWriter t = new SimpleFileWriter(sb.toString(), false);
			
			double[] cfi = calculate95ConfidenceInterval(results.get(eNbr));
			System.out.println(cfi[0] + " " + cfi[1] + " "  + cfi[2]);
			c.println(String.valueOf(cfi[0]) + " " + String.valueOf(cfi[1]) + " " + String.valueOf(cfi[2]));
			c.close();	
			int sum = 0;
			for (int tm : recorded_time.get(eNbr)) {
				sum += tm;	
			}
			for (int tm : recorded_time.get(eNbr)) {
				t.println(String.valueOf((1.0)*tm/sum));
			}
			
			t.close();
		}
	}

	private static boolean checkConfidenceIntervals(ArrayList<ArrayList<Double>> measurements) {
		ArrayList<double[]> confidenceIntervals = new ArrayList<>();
		for (ArrayList<Double> m : measurements) {
			confidenceIntervals.add(calculate95ConfidenceInterval(m));
		}
		
		for (int i = 0; i < confidenceIntervals.size(); i++) {
			for (int j = 0; j < confidenceIntervals.size(); j++) {
				if(i != j) {
					if((confidenceIntervals.get(j)[2] > confidenceIntervals.get(i)[0] && confidenceIntervals.get(j)[2] < confidenceIntervals.get(i)[2]) || (confidenceIntervals.get(j)[0] < confidenceIntervals.get(i)[2] && confidenceIntervals.get(j)[0] > confidenceIntervals.get(i)[0])) {
						return false;
					}
				}
			}
		}
		return true;
	}
	private static double[] calculate95ConfidenceInterval(ArrayList<Double> data) {
		double sum = 0;
		for (double number : data) {
			sum += number;
		}
		double mean = sum / data.size();

		double squaredDifferenceSum = 0;
		for (double number : data) {
			squaredDifferenceSum += (number - mean) * (number - mean);
		}
		double variance = squaredDifferenceSum / data.size();
		double standardDeviation = Math.sqrt(variance);

		double confidenceLevel = 1.96;
		double temp = confidenceLevel * standardDeviation / Math.sqrt(data.size());
		return new double[] { mean - temp, mean, mean + temp };
	}
}
