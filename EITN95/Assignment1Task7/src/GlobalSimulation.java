public class GlobalSimulation{
	public static final int COMPONENT_1 = 1, COMPONENT_2 = 2, COMPONENT_3 = 3, COMPONENT_4 = 4, COMPONENT_5 = 5, FINISHED = 6;;
	public static double time = 0;
	public static EventListClass eventList = new EventListClass();
	public static void insertEvent(int type, double TimeOfEvent){
		eventList.InsertEvent(type, TimeOfEvent);
	}
}