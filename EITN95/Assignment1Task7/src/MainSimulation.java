import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		new MainSimulation().run();
	}

	public void run() {

		Event actEvent;
		State actState = new State();
		
		//Start the measurements by calling the reset function in State.java
		actState.reset();


		while (actState.noFinished < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState.treatEvent(actEvent);
		}

		System.out.println(actState.timeInTotal/actState.noFinished);
	}
}