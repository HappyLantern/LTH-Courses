public class GlobalSimulation{
	public static final int ARRIVAL_TO_1 = 1, DEPARTURE_FROM_1 = 2, DEPARTURE_FROM_2 = 3, MEASURE = 4;
	public static double time = 0;
	public static EventListClass eventList = new EventListClass();
	public static void insertEvent(int type, double TimeOfEvent){ 
		eventList.InsertEvent(type, TimeOfEvent);
	}
}