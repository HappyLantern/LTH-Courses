import java.util.ArrayList;

public class Global{
	public static final int WAKE_UP = 1, ARRIVAL = 2, OUT_OF_RANGE = 3, PROCESS = 4, FAIL = 5, BACK_OFF = 6;
	public static double time = 0;
	public static int strategy;
	public static int lb,ub;
	public static ArrayList<Sensor> sensorsSending = new ArrayList<>();
	public static int radius, ts, tp;
}
