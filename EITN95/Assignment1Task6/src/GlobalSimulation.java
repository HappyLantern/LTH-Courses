public class GlobalSimulation{
	public static final int ARRIVAL = 1, DEPARTURE = 2, FINISHED = 3;
	public static double time = 0;
	public static EventListClass eventList = new EventListClass();
	public static void insertEvent(int type, double TimeOfEvent){
		eventList.InsertEvent(type, TimeOfEvent);
	}
}