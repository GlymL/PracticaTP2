package simulator.model;

import java.util.List;
import simulator.misc.Pair;


public class SetContClassEvent extends Event{
	
	List<Pair<String,Integer>> _cs;
	
	public SetContClassEvent(int time, List<Pair<String,Integer>> cs)  {
		super(time);
		if(cs == null)
			throw new IllegalArgumentException("The argument Contamination Class List cannot be null");
		_cs = cs;
	}


	@Override
	void execute(RoadMap map) {
		for(int i = 0; i < _cs.size(); i++)
			map.getVehicle(_cs.get(i).getFirst()).setContClass((_cs.get(i).getSecond()));
	}
	
	@Override
	public String toString() {
		String string = "Set ContClass [";
		for (int i = 0; i < _cs.size(); i++) {
			Pair <String, Integer> p = _cs.get(i);
			if(i == _cs.size()-1)
				string+="("+ p.getFirst() + ", " + p.getSecond() + ")";
			else
				string+="("+ p.getFirst() + ", " + p.getSecond() + "), ";
		}
		string+= "]";
		return string;
	}
}
