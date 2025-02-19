package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent{

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) { // string junctions?
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		Junction jDest = map.getJunction(_DestJunc);
		Junction jScr = map.getJunction(_scrJunc);
		r = new InterCityRoad(_id, jScr, jDest, _maxSpeed, _co2Limit, _lenght, _weather);
		map.addRoad(r);
	}

}
