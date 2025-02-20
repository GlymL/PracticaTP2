package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event{
	List<Pair<String,Weather>> _ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if(ws == null)
			throw new IllegalArgumentException("Invalid type/desc");
		_ws = ws;
		}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		for(int i = 0; i < _ws.size(); i++)
			map.getRoad(_ws.get(i).getFirst()).setWeather(_ws.get(i).getSecond());
	}
}
