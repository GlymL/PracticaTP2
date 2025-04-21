package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	String _id;
	int _maxSpeed;
	int _contClass;
	List<String> _itinerary;

	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		_id = id;
		_maxSpeed = maxSpeed;
		_contClass = contClass;
		_itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		List<Junction> jList = new ArrayList<Junction>();
		for (String s : _itinerary)
			jList.add(map.getJunction(s));
		Vehicle v = new Vehicle(_id, _maxSpeed, _contClass, jList);
		map.addVehicle(v);
	}

	@Override
	public String toString() {
		return "New Vehicle '" + _id + "'";
	}
}
