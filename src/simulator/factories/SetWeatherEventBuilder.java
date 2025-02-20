package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder <Event>{
	private static final String _type_tag = "set_weather";
	private static final String _desc = "set weather";
	
	public SetWeatherEventBuilder() {
		super(_type_tag, _desc);
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		
		List<Pair<String,Weather>> ws = new ArrayList<>();
		JSONArray ws_id = data.getJSONArray("info");
		for(int i = 0; i < ws_id.length(); i++) {
			JSONObject pws = ws_id.getJSONObject(i);
			
			Pair<String, Weather> ret = new Pair<String, Weather>(pws.getString("road"),Weather.valueOf(pws.getString("weather")));
			ws.add(ret);
		}
		return new SetWeatherEvent(time, ws);
	}
}