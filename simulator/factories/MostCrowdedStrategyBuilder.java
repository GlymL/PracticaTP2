package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {
	private static final String _type_tag = "most_crowded_lss";
	private static final String _desc = "most crowded light switching strategy";
	public MostCrowdedStrategyBuilder() {
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
		return new MostCrowdedStrategy(timeslot);
	}

}