package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event> {
	private static final String _type_tag = "new_city_road";
	private static final String _desc = "new city road";

	public NewCityRoadEventBuilder() {
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
		return new NewCityRoadEvent(time, id, jSrc, jDesc, length, co2limit, maxspeed, Weather.valueOf(weather));
	}

}
