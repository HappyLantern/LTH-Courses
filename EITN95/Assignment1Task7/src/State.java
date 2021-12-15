import java.util.*;

class State extends GlobalSimulation{
	
	public int noFinished = 0;
	public double timeInTotal = 0;
	
	Random rand = new Random();
	double minLifeTime = 1, maxLifeTime = 5;
	boolean[] broken = new boolean[5];
	
	public void treatEvent(Event x){
		switch (x.eventType){
			case COMPONENT_1:
				component1();
				break;
			case COMPONENT_2:
				component2();
				break;
			case COMPONENT_3:
				component3();
				break;
			case COMPONENT_4:
				component4();
				break;
			case COMPONENT_5:
				component5();
				break;
			case FINISHED:
				finished();
				break;
		}
	}

	
	public void reset() {
		insertEvent(COMPONENT_1, minLifeTime + (maxLifeTime - minLifeTime)*rand.nextDouble());
		insertEvent(COMPONENT_2, minLifeTime + (maxLifeTime - minLifeTime)*rand.nextDouble());
		insertEvent(COMPONENT_3, minLifeTime + (maxLifeTime - minLifeTime)*rand.nextDouble());
		insertEvent(COMPONENT_4, minLifeTime + (maxLifeTime - minLifeTime)*rand.nextDouble());
		insertEvent(COMPONENT_5, minLifeTime + (maxLifeTime - minLifeTime)*rand.nextDouble());
		for (int i = 0; i < 5; i++) {
			broken[i] = false;
		}
	}
	
	private void component1() {
		broken[0] = true;
		broken[1] = true;
		broken[4] = true;
		checkAllBroken();
	}
	
	private void component2() {
		broken[1] = true;
		checkAllBroken();
	}

	private void component3() {
		broken[2] = true;
		broken[3] = true;
		checkAllBroken();
	}
	
	private void component4() {
		broken[3] = true;
		checkAllBroken();
	}
	
	private void component5() {
		broken[4] = true;
		checkAllBroken();
	}	
	
	private void finished(){
		noFinished++;
		timeInTotal += time;
		reset();
	}
	
	private void checkAllBroken() {
		for (boolean b : broken) {
			if (b == false) {
				return;
			}
		}
		insertEvent(FINISHED, time);
	}

}