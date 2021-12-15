public class GlobalSimulation{	
	public static final int ARRIVAL = 1, A_DEPARTURE = 2, B_ARRIVAL = 3, B_DEPARTURE = 4, MEASURE = 5;
	public static double time = 0;
	public static EventListClass eventList = new EventListClass();
	public static void insertEvent(int type, double TimeOfEvent){ 
		eventList.InsertEvent(type, TimeOfEvent);
	}
}