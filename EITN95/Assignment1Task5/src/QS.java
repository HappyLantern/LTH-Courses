import java.util.*;

class QS extends Proc{
	public int numberInQueue = 0, numberOfReady = 0, accumulated = 0, noMeasurements = 0;
	Random rand = new Random();
	public double timeInTotal = 0;
	LinkedList<Double> timeOfArrival = new LinkedList<>();
	private double serviceTime = 0.5;

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL:{
				numberInQueue++;
				timeOfArrival.addLast(time);
				if (numberInQueue == 1){
					SignalList.SendSignal(READY,this, time - (Math.log(rand.nextDouble())/(1.0/serviceTime)));
				}
			} break;

			case READY:{
				numberOfReady++;
				numberInQueue--;
				timeInTotal += time - timeOfArrival.poll();
				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time - (Math.log(rand.nextDouble())/(1.0/serviceTime)));
				}
			} break;

			case MEASURE:{
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2.0*rand.nextDouble());
			} break;
		}
	}
}