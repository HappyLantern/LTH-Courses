import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class MainSimulation extends Global {

	public static void main(String[] args) {
		Random rand = new Random();
		int[] numberOfSensors = { 1000 };
		//int[] numberOfSensors = {2000};
		int[] radiuses = { 7000 };
		//int[] radiuses = {6000, 7000, 8000, 9000, 10000, 11000};
		//strategy = 1;
		strategy = 2;
		lb = 0;
		ub = 500;
		
		ArrayList<ArrayList<Double>> throughputMeasurements = new ArrayList<>();
		ArrayList<ArrayList<Double>> packetLossMeasurements = new ArrayList<>();
		ArrayList<Integer> measurementInfoN = new ArrayList<>();
		ArrayList<Integer> measurementInfoR = new ArrayList<>();
		int measurementIndex;
		int simulations = 0;
		int minimumSimulations = 30;
		boolean cont;

		do {
			simulations++;
			measurementIndex = 0;
			for (int n : numberOfSensors) {
				StringBuilder sb = new StringBuilder("config_");
				sb.append(String.valueOf(n));
				sb.append(".txt");
				for (int r : radiuses) {
					Signal actSignal;
					new SignalList();
					if(measurementIndex == packetLossMeasurements.size()) {
						throughputMeasurements.add(new ArrayList<Double>());
						packetLossMeasurements.add(new ArrayList<Double>());
						measurementInfoN.add(n);
						measurementInfoR.add(r);
					}
					sensorsSending.clear();
					radius = r;
					time = 0;
					ArrayList<Double> currentThroughput = throughputMeasurements.get(measurementIndex);
					ArrayList<Double> currentPacketLoss =  packetLossMeasurements.get(measurementIndex);
					Gateway gateway = new Gateway();
					ArrayList<Sensor> sensors = new ArrayList<>();
					try {
						BufferedReader configFile = new BufferedReader(new FileReader(sb.toString()));
						String string = configFile.readLine();
						String[] split;
						if (string != null) {
							split = string.split(";");
							tp = Integer.parseInt(split[0]);
							ts = Integer.parseInt(split[1]);
						} else {
							configFile.close();
							throw new IOException();
						}
						while ((string = configFile.readLine()) != null) {
							split = string.split(";");
							int x = Integer.parseInt(split[0]);
							int y = Integer.parseInt(split[0]);
							sensors.add(new Sensor(x, y, gateway));
						}
						configFile.close();
					} catch (IOException e) {
						System.out.println("Config file error " + sb.toString());
					}

					for (Sensor s : sensors) {
						SignalList.SendSignal(WAKE_UP, s, s, time - (ts * Math.log(rand.nextDouble())));
					}
					

					while (time < 100000) {
						actSignal = SignalList.FetchSignal();
						time = actSignal.arrivalTime;
						actSignal.destination.TreatSignal(actSignal);
					}

					double throughput = (1.0) * gateway.noProcessed / time;
					double packetLoss = (1.0) * (gateway.noFails + gateway.noOutOfRange) / (gateway.noArrivals + gateway.noOutOfRange);
					currentThroughput.add(throughput);
					currentPacketLoss.add(packetLoss);
					throughputMeasurements.set(measurementIndex, currentThroughput);
					packetLossMeasurements.set(measurementIndex, currentPacketLoss);
					measurementIndex++;
				}
			}
			
			cont = simulations < minimumSimulations;
			System.out.println(simulations);
			if (!cont) {
				cont = !checkConfidenceIntervals(packetLossMeasurements);
			}
		} while (cont);
		StringBuilder sb = new StringBuilder("throughput_");
		sb.append(String.valueOf(strategy));
		sb.append(".m");
		SimpleFileWriter tp = new SimpleFileWriter(sb.toString(), false);
		sb = new StringBuilder("conf_intervals_");
		sb.append(String.valueOf(strategy));
		sb.append(".m");
		SimpleFileWriter cf = new SimpleFileWriter(sb.toString(), false);
		
		for (int i = 0; i < throughputMeasurements.size(); i++) {
			double sum = 0;
			for (double tpN : throughputMeasurements.get(i)) {
				sum += tpN;
			}
			double tpAvg = sum / throughputMeasurements.get(i).size();
			System.out.println(tpAvg);
			tp.println(String.valueOf(tpAvg) + " " + String.valueOf(measurementInfoN.get(i)) + " " + String.valueOf(measurementInfoR.get(i)));
		
			double[] cfi = calculate95ConfidenceInterval(packetLossMeasurements.get(i));
			System.out.println(cfi[0] + " " + cfi[1] + " "  + cfi[2]);
			cf.println(String.valueOf(cfi[0]) + " " + String.valueOf(cfi[1]) + " " + String.valueOf(cfi[2]) + " " + String.valueOf(measurementInfoN.get(i)) + " " + String.valueOf(measurementInfoR.get(i)));
		}
		tp.close();
		cf.close();
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
