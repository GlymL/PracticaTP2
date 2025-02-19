package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {
	private static final String _type_tag = "type";
	private static final String _desc = "data";
	
	public RoundRobinStrategyBuilder() {
		super(_type_tag, _desc);
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		int timeslot = 1;
		if (data.has(_desc)) {
			JSONObject j = data.getJSONObject(_desc);
			if(j.has("timeslot"))
				timeslot = j.getInt("timeslot");
		}
		return new RoundRobinStrategy(timeslot);
	}

}
