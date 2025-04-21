package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {
	private static final String _type_tag = "set_cont_class";
	private static final String _desc = "set contamintaion class";

	public SetContClassEventBuilder() {
		super(_type_tag, _desc);
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");

		List<Pair<String, Integer>> cs = new ArrayList<>();
		JSONArray cs_id = data.getJSONArray("info");
		for (int i = 0; i < cs_id.length(); i++) {
			JSONObject pws = cs_id.getJSONObject(i);

			Pair<String, Integer> ret = new Pair<String, Integer>(pws.getString("vehicle"), pws.getInt("class"));
			cs.add(ret);
		}
		return new SetContClassEvent(time, cs);
	}
}