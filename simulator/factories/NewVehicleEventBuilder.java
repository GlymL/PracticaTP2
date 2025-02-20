package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	private static final String _type_tag = "new_vehicle";
	private static final String _desc = "new vehicle";
	
	public NewVehicleEventBuilder() {
		super(_type_tag, _desc);
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxSpeed = data.getInt("maxspeed");
		int c = data.getInt("class");
		
		JSONArray itinerary_id = data.getJSONArray("itinerary");
		List<String> itinerary = new ArrayList<>();
		for(int i = 0; i < itinerary_id.length(); i++)
			itinerary.add(itinerary_id.getString(i));
		return new NewVehicleEvent(time, id, maxSpeed, c, itinerary);
	}

}
