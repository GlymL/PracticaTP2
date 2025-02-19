package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {
	private static final String _type_tag = "type";
	private static final String _desc = "data";
	
	public MoveFirstStrategyBuilder() {
		super(_type_tag, _desc);
	}

	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		return new MoveFirstStrategy();
	}

}
