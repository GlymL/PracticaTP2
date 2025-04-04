package simulator.model;

import java.util.List;
import simulator.misc.Pair;


public class SetContClassEvent extends Event{
	
	List<Pair<String,Integer>> _cs;
	
	public SetContClassEvent(int time, List<Pair<String,Integer>> cs)  {
		super(time);
		if(cs == null)
			throw new IllegalArgumentException("Invalid type/desc");
		_cs = cs;
	}


	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		for(int i = 0; i < _cs.size(); i++)
			map.getVehicle(_cs.get(i).getFirst()).setContaminationClass((_cs.get(i).getSecond()));
	}
}
