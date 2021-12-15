import java.util.*;

class Sensor extends Proc{
	Random rand = new Random();
	private Gateway gateway;
	public int x, y;
	
	public Sensor(int x, int y, Gateway gateway) {
		this.x = x;
		this.y = y;
		this.gateway = gateway;
	}

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case WAKE_UP:
				if(strategy == 1) {
					if (Math.hypot(gateway.x - this.x, gateway.y - this.y) <= radius) {
						SignalList.SendSignal(ARRIVAL, this, gateway, time);
					} else {
						SignalList.SendSignal(OUT_OF_RANGE, this, gateway, time+tp);
					}
					sensorsSending.add(this);
				} else if (strategy == 2) {
					boolean backOff = false;
					for (Sensor s : sensorsSending) {
						if (Math.hypot(s.x - this.x, s.y - this.y) <= radius) {
							SignalList.SendSignal(BACK_OFF, this, this, time + lb + (ub-lb)*rand.nextDouble());
							backOff = true;
							break;
						} 
					}
					if(!backOff) {
						if (Math.hypot(gateway.x - this.x, gateway.y - this.y) <= radius) {
							SignalList.SendSignal(ARRIVAL, this, gateway, time);
						} else {
							SignalList.SendSignal(OUT_OF_RANGE, this, gateway, time+tp);
						}
						sensorsSending.add(this);
					}
				}
				SignalList.SendSignal(WAKE_UP, this, this, time - (ts)*Math.log(rand.nextDouble()));
				break;

			case BACK_OFF:
				if (Math.hypot(gateway.x - this.x, gateway.y - this.y) <= radius) {
					SignalList.SendSignal(ARRIVAL, this, gateway, time);
				} else {
					SignalList.SendSignal(OUT_OF_RANGE, this, gateway, time+tp);
				}
				sensorsSending.add(this);
				break;

		}
	}
}