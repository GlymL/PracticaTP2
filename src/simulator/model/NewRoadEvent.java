package simulator.model;

public abstract class NewRoadEvent extends Event {
	Road r;
	String _id;
	String _scrJunc;
	String _DestJunc;
	int _lenght;
	int _co2Limit;
	int _maxSpeed;
	Weather _weather;

	public NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed,
			Weather weather) {
		super(time);
		_id = id;
		_scrJunc = srcJun;
		_DestJunc = destJunc;
		_lenght = length;
		_co2Limit = co2Limit;
		_maxSpeed = maxSpeed;
		_weather = weather;
	}
}
