import java.util.*;

class State extends GlobalSimulation{
	
	public int busyServers = 0, noMeasurements = 0, noRejected = 0;

	Random rand = new Random();
	public int n = 100, x = 10, lambda = 4, T = 4;
	SimpleFileWriter f = new SimpleFileWriter("results.m", false);
	
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case DEPARTURE:
				departure();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	private void arrival(){
		if (busyServers < n) {
			busyServers++;
			insertEvent(DEPARTURE, time + x);
		} else {
			noRejected++;
		}
		insertEvent(ARRIVAL, time + -(Math.log(rand.nextDouble()) / lambda));
	}
	
	private void departure(){
		busyServers--;
	}
	
	private void measure(){
		noMeasurements++;
		insertEvent(MEASURE, time + T);
		f.println(String.valueOf(busyServers));
	}
}