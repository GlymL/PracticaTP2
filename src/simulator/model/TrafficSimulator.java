package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver>  {
	
	private RoadMap _rm;
	private Queue<Event> _eventQueue;
	private int _time;
	private List<TrafficSimObserver> _ob;
	
	public TrafficSimulator(){
		_rm = new RoadMap();
		_eventQueue = new PriorityQueue<Event>();
		_time = 0;
		_ob = new ArrayList<>();
	}
	public void addEvent(Event e) {
		_eventQueue.offer(e); //añade el elemento ordenado
		for(TrafficSimObserver obs : _ob)
	    	obs.onEventAdded(_rm, _eventQueue, e,  _time);
	}
	public void advance() {
	   _time++;
	    while (!_eventQueue.isEmpty() && _eventQueue.element().getTime() == _time) {
	        Event e = _eventQueue.poll();
	        e.execute(_rm);
	    }
	    if(_time == 1)
	    	for(Vehicle v : _rm.getVehicles())
	    		v.moveToNextRoad();
	    for(Junction j : _rm.getJunctions())
	    	j.advance(_time);
	    for(Road r : _rm.getRoads())
	    	r.advance(_time);
	    for(TrafficSimObserver obs : _ob)
	    	obs.onAdvance(_rm, _eventQueue, _time);
	}
	public void reset() {
		_rm.reset();
		_eventQueue.clear();
		_time = 0;
		for(TrafficSimObserver obs : _ob)
	    	obs.onReset(_rm, _eventQueue, _time);
	}
	
	public JSONObject report() {
		JSONObject j_traffic = new JSONObject();
		j_traffic.put("time", _time);
		j_traffic.put("state", _rm.report());
		return j_traffic;
	}
	@Override
	public void addObserver(TrafficSimObserver o) {
		_ob.add(o);
		for(TrafficSimObserver obs : _ob)
	    	obs.onRegister(_rm, _eventQueue, _time);
		
	}
	@Override
	public void removeObserver(TrafficSimObserver o) {
		_ob.remove(o);
	}
	
	public int getTime() {
		return _time;
	}
}
