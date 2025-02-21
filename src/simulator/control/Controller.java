package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray j_arrayEvent = jo.getJSONArray("events");
		for(int i = 0; i < j_arrayEvent.length(); i++) {
			_ts.addEvent(_ef.create_instance(j_arrayEvent.getJSONObject(i)));
		}
	}
	public void run(int n, OutputStream out) throws IOException {
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("  \"states\": [");
	    for (int i = 0; i < n; i++) {
	        _ts.advance();
	        if(i != n-1)
	        p.println(_ts.report() + ",");
	        else
	        	p.println(_ts.report());
	    }
	    p.println("]");
	    p.println("}");
	}
	
	public void reset() {
		_ts.reset();
	}

}
