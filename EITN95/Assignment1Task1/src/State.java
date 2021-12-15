import java.util.*;

class State extends GlobalSimulation{
	
	public int numberInQueue1 = 0, numberInQueue2 = 0, accumulatedQ2 = 0, noMeasurements = 0, noRejected = 0, noOfArrivals = 0;

	Random rand = new Random();
	int interArrivalTime = 5;
	double q1ServiceTimeMean = 2.1;
	double lambda = 1 / q1ServiceTimeMean;
	int q2ServiceTime = 2;
	
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
		if (numberInQueue1 < 10) {
			numberInQueue1++;
		}
		else {
			noRejected++;
		}
		if (numberInQueue1 == 1) {
			insertEvent(DEPARTURE_FROM_1, time + -(Math.log(rand.nextDouble()) / lambda));
		}
		insertEvent(ARRIVAL_TO_1, time + interArrivalTime);
	}
	
	private void departureFrom1(){
		numberInQueue1--;
		numberInQueue2++;
		if(numberInQueue2 == 1) {
			insertEvent(DEPARTURE_FROM_2, time + q2ServiceTime);
		}
		if (numberInQueue1 > 0) {
			insertEvent(DEPARTURE_FROM_1, time -(Math.log(rand.nextDouble()) / lambda));
		}
	}
	
	private void departureFrom2() {
		numberInQueue2--;
		if (numberInQueue2 > 0) {
			insertEvent(DEPARTURE_FROM_2, time + q2ServiceTime);
		}
	}
	
	private void measure(){
		accumulatedQ2 = accumulatedQ2 + numberInQueue2;
		noMeasurements++;
		insertEvent(MEASURE, time -(Math.log(rand.nextDouble()) / (1.0/5.0)));
	}
}