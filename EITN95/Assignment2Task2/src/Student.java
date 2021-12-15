import java.awt.Point;
import java.util.*;

class Student extends Proc{
	Random rand = new Random();
	public Point currentPosition;
	private Point targetPosition;
	private int velocity;
	int dx, dy;
	public int[] acquaintances;
	public int acquaintancesMade;
	public boolean busy;
	public double nextUpdate;
	
	public Student(Point startPosition, int velocity) {
		currentPosition = new Point(startPosition.x, startPosition.y);
		targetPosition = new Point(startPosition.x, startPosition.y);;
		this.velocity = velocity;
		acquaintances = new int[noStudents];
		room[currentPosition.y][currentPosition.x]++;
		acquaintancesMade = 0;
		busy = false;
		dx = 0;
		dy = 0;
	}

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case UPDATE:
				if(busy) {
					break;
				}
				if(currentPosition.equals(targetPosition)) {
					calcWalk();
					SignalList.SendSignal(UPDATE, this, this, time + nextUpdate);
				} else {
					walk();
					if(room[currentPosition.y][currentPosition.x] == 2) {
						for (Student s : students) {
							if (s != this && s.currentPosition.equals(currentPosition)) {
								busy = true;
								s.busy = true;
								SignalList.SendSignal(FINISHED, s, this, time + talkTime);
								SignalList.SendSignal(FINISHED, this, s, time + talkTime);
							}
						}
					} else {
						SignalList.SendSignal(UPDATE, this, this, time + nextUpdate);
					}
				}
				break;
				
			case FINISHED:
				busy = false;
				for (int i = 0; i < students.length; i++) {
					if (x.from == students[i]) {
						if (acquaintances[i] == 0) {
							acquaintancesMade++;
							if(acquaintancesMade == noStudents-1) {
								studentsMet++;
							}
						}
						acquaintances[i]++;
						break;
					}
				}
				SignalList.SendSignal(UPDATE, this, this, time + nextUpdate);
				break;

		}
	}
	
	private void calcWalk() {
		dx = -1 + rand.nextInt(3);
		dy = -1 + rand.nextInt(3);
		while (dx == 0 && dy == 0) {
			dx = -1 + rand.nextInt(3);
			dy = -1 + rand.nextInt(3);
		}
		int distance = 1 + rand.nextInt(10);
		int distanceX = dx * distance;
		int distanceY = dy * distance;
		if (currentPosition.x + distanceX < 0) {
			targetPosition.x = 0;
			distanceY = dy*currentPosition.x;
		} else if (currentPosition.x + distanceX > roomX-1) {
			targetPosition.x = roomX-1;
			distanceY = dy*(roomX-1-currentPosition.x);
		} else {
			targetPosition.x = currentPosition.x + distanceX;
		}
		if (currentPosition.y + distanceY < 0) {
			targetPosition.y = 0;
			distanceX = dx*currentPosition.y;
			targetPosition.x = currentPosition.x + distanceX;
		} else if (currentPosition.y + distanceY > roomY-1) {
			targetPosition.y = roomY-1;
			distanceX = dx*(roomY-1-currentPosition.y);
			targetPosition.x = currentPosition.x + distanceX;
		} else {
			targetPosition.y = currentPosition.y + distanceY;
		}
		nextUpdate = Math.sqrt(Math.abs(dx)+Math.abs(dy)) / velocity;
	}
	
	private void walk() {
		room[currentPosition.y][currentPosition.x]--;
		currentPosition.setLocation(currentPosition.x + dx, currentPosition.y + dy);
		room[currentPosition.y][currentPosition.x]++;
	}
}