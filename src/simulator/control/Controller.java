package simulator.control;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator _ts;
	private Factory<Event> _ef;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		_ts = sim;
		_ef = eventsFactory;
		}

}
