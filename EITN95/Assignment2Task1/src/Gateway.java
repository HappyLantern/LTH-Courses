
public class Gateway extends Proc{

	public int noArrivals = 0, noOutOfRange = 0, noFinished = 0, noProcessed = 0, noFails = 0;
	private boolean busy;
	public final int x = 5000, y = 5000;
	
	public Gateway() {
		busy = false;
	}
	@Override
	public void TreatSignal(Signal x) {
		
		switch (x.signalType) {
		case ARRIVAL:
			noArrivals++;
			if (busy) {
				SignalList.SendSignal(FAIL, x.from, this, time + tp);
			} else {
				busy = true;
				SignalList.SendSignal(PROCESS, x.from, this, time + tp);
			}
			break;
		case OUT_OF_RANGE:
			noOutOfRange++;
			sensorsSending.remove(x.from);
			break;
		case PROCESS:
			noFinished++;
			sensorsSending.remove(x.from);
			if (noArrivals == noFinished) {
				noProcessed++;
				busy = false;
			} else {
				noFails++;
			}
			break;
		case FAIL:
			noFinished++;
			sensorsSending.remove(x.from);
			if (noArrivals == noFinished) {
				busy = false;
			}
			noFails++;
			break;	
		}
	}

}
