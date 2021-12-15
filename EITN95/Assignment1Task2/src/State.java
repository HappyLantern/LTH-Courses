import java.util.*;

class State extends GlobalSimulation{
	
	public int numberOfA = 0, numberOfB = 0, noMeasurements = 0, accumulatedBuffer = 0;

	Random rand = new Random();
	public double lambda = 150, xA = 0.002, xB = 0.004, d = 1;
	
	SimpleFileWriter f = new SimpleFileWriter("results.m", false);

	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case A_DEPARTURE:
				aDeparture();
				break;
			case B_ARRIVAL:
				bArrival();
				break;
			case B_DEPARTURE:
				bDeparture();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	private void arrival(){
		if(numberOfA + numberOfB == 0) {
			insertEvent(A_DEPARTURE, time + xA);
		}
		numberOfA++;
		insertEvent(ARRIVAL, time -(Math.log(rand.nextDouble()) / lambda));
	}
	
	private void aDeparture(){
		numberOfA--;
		insertEvent(B_ARRIVAL, time + d);
		if (numberOfA > 0) {
			insertEvent(A_DEPARTURE, time + xA);
		} else if(numberOfB > 0) {
			insertEvent(B_DEPARTURE, time + xB);
		}
	}
	
	private void bArrival() {
		if(numberOfA + numberOfB == 0) {
			insertEvent(B_DEPARTURE, time + xB);
		}
		numberOfB++;
	}
	
	private void bDeparture() {
		numberOfB--;
		if (numberOfA > 0) {
			insertEvent(A_DEPARTURE, time + xA);
		} else if(numberOfB > 0) {
			insertEvent(B_DEPARTURE, time + xB);
		}
	}
	
	private void measure(){
		noMeasurements++;
		accumulatedBuffer += numberOfA + numberOfB;
		insertEvent(MEASURE, time + 0.1);
		f.println(String.valueOf(numberOfA + numberOfB) + " " + String.valueOf(time));
	}
	
}