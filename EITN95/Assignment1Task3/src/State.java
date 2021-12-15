import java.util.*;

class State extends GlobalSimulation{

	public int numberInQueue1 = 0, numberInQueue2 = 0, accumulated = 0, noMeasurements = 0, noOfArrivals = 0, noDepartures = 0;

	Random rand = new Random();
	public double arrivalTimeMean = 1.1;
	public double serviceTimeMean = 1;
	public double timeInTotal = 0;
	
	LinkedList<Double> timeOfArrival = new LinkedList<>();

	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_TO_1:
				arrivalTo1();
				break;
			case DEPARTURE_FROM_1:
				departureFrom1();
				break;
			case DEPARTURE_FROM_2:
				departureFrom2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}

	private void arrivalTo1(){
		noOfArrivals++;
		timeOfArrival.addLast(time);
		numberInQueue1++;
		if (numberInQueue1 == 1) {
			insertEvent(DEPARTURE_FROM_1, time + -(Math.log(rand.nextDouble()) / (1.0/serviceTimeMean)));
		}
		insertEvent(ARRIVAL_TO_1, time + -(Math.log(rand.nextDouble()) / (1.0/arrivalTimeMean)));
	}
	
	private void departureFrom1(){
		numberInQueue1--;
		numberInQueue2++;
		if(numberInQueue2 == 1) {
			insertEvent(DEPARTURE_FROM_2, time + -(Math.log(rand.nextDouble()) / (1.0/serviceTimeMean)));
		}
		if (numberInQueue1 > 0) {
			insertEvent(DEPARTURE_FROM_1, time + -(Math.log(rand.nextDouble()) / (1.0/serviceTimeMean)));
		}
	}
	
	private void departureFrom2() {
		noDepartures++;
		numberInQueue2--;
		timeInTotal += time - timeOfArrival.poll();
		if (numberInQueue2 > 0) {
			insertEvent(DEPARTURE_FROM_2, time + -(Math.log(rand.nextDouble()) / (1.0/serviceTimeMean)));
		}
	}
	
	private void measure(){
		accumulated += numberInQueue1 + numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time -(Math.log(rand.nextDouble()) / (1.0/5.0)));
	}
}