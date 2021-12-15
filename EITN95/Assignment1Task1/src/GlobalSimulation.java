public class GlobalSimulation{
	public static final int ARRIVAL_TO_1 = 1, DEPARTURE_FROM_1 = 2, DEPARTURE_FROM_2 = 3, MEASURE = 4; // The events, add or remove if needed!
	public static double time = 0; // The global time variable
	public static EventListClass eventList = new EventListClass(); // The event list used in the program
	public static void insertEvent(int type, double TimeOfEvent){  // Just to be able to skip dot notation
		eventList.InsertEvent(type, TimeOfEvent);
	}
}