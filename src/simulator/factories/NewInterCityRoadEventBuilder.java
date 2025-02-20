package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event>{
	private static final String _type_tag = "new_inter_city_road";
	private static final String _desc = "new inter city road";
	
	public NewInterCityRoadEventBuilder() {
		super(_type_tag, _desc);
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String jSrc = data.getString("src");
		String jDesc = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");
		int maxspeed = data.getInt("maxspeed");
		String weather = data.getString("weather");
		return new NewInterCityRoadEvent(time, id, jSrc, jDesc, length, co2limit, maxspeed, Weather.valueOf(weather));
		}

}
