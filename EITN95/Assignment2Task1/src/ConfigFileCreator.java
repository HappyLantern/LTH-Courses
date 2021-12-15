import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class ConfigFileCreator {
	private Random rand = new Random();
	private ArrayList<Point> sensorPositions;
	private Point gatewayPosition = new Point(5000, 5000);
	private int maxX = 10000, maxY = 10000;
	private int tp = 1, ts = 4000;
	
	public ConfigFileCreator(){
		for (int i = 1000; i <= 10000; i += 1000) {
			if(!check(i)) {
				create(i, tp, ts);
			}
		}
	}
	
	public static void main(String[] args) {
		ConfigFileCreator cfc = new ConfigFileCreator();
	}
	
	//Will create a new config file with the specified Tp and Ts on row 1 in format Tp;Ts
	//Followed by each sensor on one row each formatted as X;Y
	private void create(int n, int tp, int ts) {
		sensorPositions = new ArrayList<>();
		StringBuilder sb = new StringBuilder("config_");
		sb.append(n);
		sb.append(".txt");
		SimpleFileWriter W = new SimpleFileWriter(sb.toString(), false);
		W.println(String.valueOf(tp) + ";" + String.valueOf(ts));
			
		while(sensorPositions.size() < n){
				
			//create a new sensor at a uniformly distributed position
			int x = (int) (maxX*rand.nextDouble());
			int y = (int) (maxY*rand.nextDouble());
			Point sensorPosition = new Point(x,y);
			
			//check so that the sensor position is unique
			if(!sensorPositions.contains(sensorPosition) && !sensorPosition.equals(gatewayPosition)){
				sensorPositions.add(sensorPosition);
				W.println(String.valueOf(x) + ";" + String.valueOf(y));
			}
				
		}
		W.close();
	}
	
	private boolean check(int n) {
		StringBuilder sb = new StringBuilder("config_");
		sb.append(n);
		sb.append(".txt");
		try {
			FileReader checker = new FileReader(sb.toString());
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
		
		
}
