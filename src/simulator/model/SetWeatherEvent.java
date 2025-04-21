package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	List<Pair<String, Weather>> _ws;

	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		if (ws == null)
			throw new IllegalArgumentException("The argument Wetaher List cannot be null");
		_ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		for (int i = 0; i < _ws.size(); i++)
			map.getRoad(_ws.get(i).getFirst()).setWeather(_ws.get(i).getSecond());
	}

	@Override
	public String toString() {
		String string = "Set Weather [";
		for (int i = 0; i < _ws.size(); i++) {
			Pair<String, Weather> p = _ws.get(i);
			if (i == _ws.size() - 1)
				string += "(" + p.getFirst() + ", " + p.getSecond().toString() + ")";
			else
				string += "(" + p.getFirst() + ", " + p.getSecond().toString() + "), ";
		}
		string += "]";
		return string;
	}
}
