package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	private static final String _type_tag = "new_junction";
	private static final String _desc = "new junction";
	private Factory<LightSwitchingStrategy> _lsFactory;
	private Factory<DequeuingStrategy> _dqFactory;

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super(_type_tag, _desc);
		_lsFactory = lssFactory;
		_dqFactory = dqsFactory;
	}

	@Override
	protected Event create_instance(JSONObject data) {
		JSONArray coor = data.getJSONArray("coor");
		int x = coor.getInt(0);
		int y = coor.getInt(1);

		return new NewJunctionEvent(data.getInt("time"), data.getString("id"),
				_lsFactory.create_instance(data.getJSONObject("ls_strategy")),
				_dqFactory.create_instance(data.getJSONObject("dq_strategy")), x, y);
	}
}
