package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
			_ts.addEvent(_ef.create_instance(jo));
		}
	}
	public void run(int n, OutputStream out) throws IOException {
	    JSONObject report = new JSONObject();
	    List<JSONObject> out_report = new ArrayList<>();
	    for (int i = 0; i < n; i++) {
	        _ts.advance();
	        out_report.add(_ts.report());
	    }
	    report.put("states", out_report);

	    // Convert the JSON object to a string and write it to the output stream
	    out.write(report.toString().getBytes());
	    out.flush();
	}
	
	public void reset() {
		_ts.reset();
	}

}
