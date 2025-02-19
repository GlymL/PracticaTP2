package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{
	
	private static final String _type_tag = "type";
	private static final String _desc = "data";
	private Factory<LightSwitchingStrategy> _lsFactory;
	private Factory<DequeuingStrategy> _dqFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super(_type_tag, _desc);
		_lsFactory = lssFactory;
		_dqFactory = dqsFactory;
	}

	@Override
	protected Event create_instance(JSONObject data) {
//		JSONObject j = data.getJSONObject(_desc);
//		List<Integer> j2 = new ArrayList<>();
		//j2 = j.get("coor");
		
		return new NewJunctionEvent(data.getInt("time"), data.getString("id"),
				_lsFactory.create_instance(data.getJSONObject("ls_strategy")),
				_dqFactory.create_instance(data.getJSONObject("dq_strategy")), 0, 0);
	}
	/*
	int time, 

	String id, 

	LightSwitchingStrategy lsStrategy, 

	DequeuingStrategy dqStrategy, 

	int xCoor, 

	int yCoor
	*/

}
