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
			map.getVehicle(_cs.get(i).getFirst()).setContaminationClass((_cs.get(i).getSecond()));
	}
}
