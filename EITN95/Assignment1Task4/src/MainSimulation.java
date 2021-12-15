import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		new MainSimulation().run();
	}

	public void run() {

		Event actEvent;
		State actState = new State(); 
		insertEvent(ARRIVAL, 0);
		insertEvent(MEASURE, 1);

		while (actState.noMeasurements < 4000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		actState.f.close();
	}
}