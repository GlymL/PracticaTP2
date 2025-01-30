package simulator.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> _itinerary;
	private int _maxSpeed;
	private int _actualSpeed;
	private VehicleStatus _status;
	private Road _road;
	private int _location;
	private int _contaminationClass;
	private int _totalPollution;
	private int _totalDistance;
	
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) { //Constructor
		super(id);
		if (maxSpeed <= 0 || contClass < 0 || contClass > 10 || itinerary.size() <= 2)
			throw new IllegalArgumentException("Invalid type/desc");
		else {
			_maxSpeed = maxSpeed;
			_contaminationClass = contClass;
			_itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		}
	}

	@Override
	void advance(int time) { //actualizador
		// TODO Auto-generated method stub
		if(_status == VehicleStatus.TRAVELING) {
			
		}
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	void moveToNextRoad() {
		
	}
	
	
	
	//Todos los setters
	void setSpeed(int s) { 
		if(s < 0)
			throw new IllegalArgumentException("Invalid type/desc");
		else
			_actualSpeed = Math.min(_maxSpeed, s);
		
	}
	
	void setContaminationClass(int c) {
		if(c < 0 || c > 10)
			throw new IllegalArgumentException("Invalid type/desc");
		else
			_contaminationClass = c;
	}
	
	//Todos los Getters
	public List<Junction> get_itinerary() {
		return _itinerary;
	}

	public int get_maxSpeed() {
		return _maxSpeed;
	}

	public int get_actualSpeed() {
		return _actualSpeed;
	}

	public VehicleStatus get_status() {
		return _status;
	}

	public Road get_road() {
		return _road;
	}

	public int get_location() {
		return _location;
	}

	public int get_contaminationClass() {
		return _contaminationClass;
	}

	public int get_totalPollution() {
		return _totalPollution;
	}

}