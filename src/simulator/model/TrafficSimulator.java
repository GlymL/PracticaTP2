package simulator.model;

import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator {
	
	private RoadMap _rm;
	private Queue<Event> _eventQueue;
	private int _time;
	
	TrafficSimulator(){
		_rm = new RoadMap();
		_eventQueue = new PriorityQueue<Event>();
		_time = 0;
	}
	public void addEvent(Event e) {
		_eventQueue.offer(e); //añade el elemento ordenado
	}
	public void advance() {
	   _time++;
	    while (!_eventQueue.isEmpty() && _eventQueue.element().getTime() == _time) {
	        Event e = _eventQueue.poll();
	        e.execute(_rm);
	    }
	    if(_time == 1)
	    	for(Vehicle v : _rm.get_vehicleList())
	    		v.moveToNextRoad();
	    for(Junction j : _rm.get_junctionList())
	    	j.advance(_time);
	    for(Road r : _rm.get_roadList())
	    	r.advance(_time);
	}
	public void reset() {
		_rm.reset();
	}
	
	public JSONObject report() {
		JSONObject j_traffic = new JSONObject();
		j_traffic.put("time", _time);
		j_traffic.put("state", _rm.report());
		return j_traffic;
	}
}
