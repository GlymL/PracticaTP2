package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {
	private static final String _type_tag = "move_all_dqs";
	private static final String _desc = "move all dequeing strategy";

	public MoveAllStrategyBuilder() {
		super(_type_tag, _desc);
	}

	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		return new MoveAllStrategy();
	}

}
