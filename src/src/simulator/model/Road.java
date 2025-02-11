package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{

	private  Junction _srcJunc;
	private Junction _destJunc;
	private int _maxSpeed;
	private int _speedLimit;
	private int _contLimit;
	private int _contTotal;
	private int _length;
	private Weather _weather;
	private List<Vehicle> _vehicleList;
	
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		  super(id);
		  if (maxSpeed <= 0 || contLimit < 0 || length <= 0 || srcJunc == null || destJunc == null || weather == null)
				throw new IllegalArgumentException("Invalid type/desc");
		  else {
			  _srcJunc = srcJunc;
			  _destJunc = destJunc;
			  _contLimit = contLimit;
			  _maxSpeed = maxSpeed;
			  _length = length;
			  _weather = weather;
			  _contTotal = 0;
			  _vehicleList = new ArrayList<Vehicle>();
			  _srcJunc.addOutGoingRoad(this);
			  _destJunc.addIncommingRoad(this);
		  }
	
	}
	
	void enter(Vehicle v) {
		if(v.get_actualSpeed() != 0 || v.get_location() != 0)
			throw new IllegalArgumentException("Invalid type/desc");
		else
			_vehicleList.add(v);
	}
	
	void exit(Vehicle v) {
		_vehicleList.remove(v);
	}
	
	void addContamination(int c) {
		if(c < 0)
			throw new IllegalArgumentException("Invalid type/desc");
		else
			_contTotal += c;
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v : _vehicleList) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		// TODO _vehicleList.sort(null);
	}

	@Override
	public JSONObject report() { //vehicles ids, array list --> for each añadiendo id vehicle
		
		JSONObject j_road = new JSONObject();
		j_road.put("id", _id);
		j_road.put("speedLimit", _speedLimit);
		j_road.put("Weahter", _weather.toString());
		j_road.put("co2", _contTotal);
		List<String> reportList = new ArrayList<String>();
		for(Vehicle v : _vehicleList)
			reportList.add(v._id);
		j_road.put("vehicles", reportList);
		return j_road;
	}
	
	
	//Setter
	void setWeather(Weather w) {
		if(w == null)
			throw new IllegalArgumentException("Invalid type/desc");
		else
			_weather = w;
	}

	
	//Getter
	public Junction get_srcJunc() {
		return _srcJunc;
	}

	public Junction get_destJunc() {
		return _destJunc;
	}

	public int get_maxSpeed() {
		return _maxSpeed;
	}

	public int get_speedLimit() {
		return _speedLimit;
	}

	public int get_contLimit() {
		return _contLimit;
	}

	public int get_contTotal() {
		return _contTotal;
	}

	public int get_length() {
		return _length;
	}

	public Weather get_weather() {
		return _weather;
	}

	public List<Vehicle> get_vehicleList() {
		return Collections.unmodifiableList(_vehicleList);
	}
	void set_contTotal(int x) {
		_contTotal = x;
	}
	void set_speedLimit(int x) {
		_speedLimit = x;
	}
}
