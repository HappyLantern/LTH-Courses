import java.io.*;

public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {
    	//Set mean time between arrivals
    	double mu = 2;
    	int numberOfQueues = 5;
    	
    	Signal actSignal;
    	new SignalList();
    	
    	Dispatcher dispatcher = new Dispatcher();
    	//Set rule for dispatcher, RANDOM, ROUND_ROBIN, or SHORTEST_QUEUE
    	dispatcher.dispatchRule = SHORTEST_QUEUE;
    	
    	for (int i = 0; i < numberOfQueues; i++) {
    		dispatcher.queues.add(new QS());
    	}

    	Gen Generator = new Gen();
    	Generator.lambda = 1.0/mu; 
    	Generator.sendTo = dispatcher;
    	
    	SignalList.SendSignal(READY, Generator, time);
    	for (QS q : dispatcher.queues) {
    		SignalList.SendSignal(MEASURE, q, time);
    	}

    	while (time < 100000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	double meanJobs = 0, meanTime = 0;
    	for (QS q : dispatcher.queues) {
    		meanJobs += (1.0*q.accumulated) /  (1.0*q.noMeasurements);
    		meanTime += q.timeInTotal / q.numberOfReady;
    	}
    	meanTime = meanTime / dispatcher.queues.size();
    	System.out.println(meanJobs);
    	System.out.println(meanTime);
    }
}