import java.util.*;


public class Dispatcher extends Proc {

	Random rand = new Random();
	//Q1 is in index 0, Q2 in index 1...
	public List<QS> queues = new ArrayList<>();

	public Proc destination;
	
	public int dispatchRule;
	private int count = 0;
	@Override
	public void TreatSignal(Signal x) {
		switch(x.signalType){
		case ARRIVAL:
			
			switch (dispatchRule) {
			case RANDOM:
				destination = queues.get(rand.nextInt(5));
				break;
			case ROUND_ROBIN:
				count = count % queues.size();
				destination = queues.get(count);
				count++;
				break;
			case SHORTEST_QUEUE:
				List<QS> shortestQueues = new ArrayList<>();
				int min = Integer.MAX_VALUE;
				for (QS q : queues) {
					if (min > q.numberInQueue) {
						shortestQueues.clear();
						min = q.numberInQueue;
						shortestQueues.add(q);
					} else if (min == q.numberInQueue) {
						shortestQueues.add(q);
					}
				}
				destination = shortestQueues.get(0);
				//Choose randomly if more than 1 with same shortest value
				if(shortestQueues.size() > 1) {
					destination = shortestQueues.get(rand.nextInt(shortestQueues.size()));
				}

				break;
			}
			
		SignalList.SendSignal(ARRIVAL, destination, time);
		break;
		}				
	}
}
