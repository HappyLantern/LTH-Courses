import java.util.*;

class State extends GlobalSimulation{
	
	public int numberInQueue = 0,noMeasurements = 0, noOfArrivals = 0;
	public double timeFinished = 0, timeInTotal = 0;
	
	
	Random rand = new Random();
	double minServiceTime = 1.0/6, maxServiceTime = 1.0/3;
	double lambda = 4;
	LinkedList<Double> timeOfArrival = new LinkedList<Double>();
	
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case DEPARTURE:
				departure();
				break;
			case FINISHED:
				finished();
				break;
		}
	}

	private void arrival(){
		if(time <= 8) {
			noOfArrivals++;
			timeOfArrival.addLast(time);
			numberInQueue++;
			if(numberInQueue == 1) {
				insertEvent(DEPARTURE, time + minServiceTime + (maxServiceTime - minServiceTime)*rand.nextDouble());
			}
			insertEvent(ARRIVAL, time - (Math.log(rand.nextDouble()) / lambda));
		}
		else {
			if(numberInQueue == 0) {
				insertEvent(FINISHED, time);
			}
		}
	}
	
	private void departure(){
		numberInQueue--;
		timeInTotal += time - timeOfArrival.poll();
		if(numberInQueue > 0) {
			insertEvent(DEPARTURE, time + minServiceTime + (maxServiceTime - minServiceTime)*rand.nextDouble());
		} else if (numberInQueue == 0 && time > 8) {
			insertEvent(FINISHED, time);
		}
	}
	
	private void finished(){
		noMeasurements++;
		timeFinished += time;
		insertEvent(ARRIVAL, 0);
	}
}