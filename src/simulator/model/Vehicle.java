package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	private List<Junction> _junctionList;
	private int _maxSpeed;
	private int _actualSpeed;
	private VehicleStatus _status;
	private Road _road;
	private int _location;
	private int _contaminationClass;
	private int _contTotal;
	private int _totalDistance;
	
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) { //Constructor
		super(id);
		if (maxSpeed <= 0)
			throw new IllegalArgumentException("The argument Max Speed must be a positive number");
		if (contClass < 0 || contClass > 10)
			throw new IllegalArgumentException("The argument Contamination Class must be an integer number between 0 and 10");
		if (itinerary.size() < 2)
			throw new IllegalArgumentException("The argument Itinerary must have at least 2 junctions");
		_maxSpeed = maxSpeed;
		_contaminationClass = contClass;
		_actualSpeed = 0;
		_totalDistance = 0;
		_contTotal = 0;
		_junctionList = Collections.unmodifiableList(itinerary);
		_status = VehicleStatus.PENDING;
	}
	@Override
	void advance(int time) { //actualizador
		if(_status == VehicleStatus.TRAVELING) {
			int auxLocation = _location;
			_location = Math.min(_location + _actualSpeed, _road.get_length());
			int distance = _location - auxLocation;
			_totalDistance += distance;
			_road.addContamination(distance*_contaminationClass);
			_contTotal += distance*_contaminationClass;
			if(_location ==_road.get_length()) {
				_actualSpeed = 0;
				_status = VehicleStatus.WAITING;
				_road.get_destJunc().enter(this);
			}
		}
	}
	@Override
	public JSONObject report() {
		JSONObject j_vehicle = new JSONObject();
		j_vehicle.put("id", _id);
		j_vehicle.put("speed", _actualSpeed);
		j_vehicle.put("distance", _totalDistance);
		j_vehicle.put("co2", this._contTotal);
		j_vehicle.put("class", this._contaminationClass);
		j_vehicle.put("status", _status.toString());
		if (!(_status == VehicleStatus.PENDING || _status == VehicleStatus.ARRIVED)) {
			j_vehicle.put("road", _road.getId());
			j_vehicle.put("location", _location);
		}
		return j_vehicle;
	}
	
	
	void moveToNextRoad() {
		if(!(_status == VehicleStatus.PENDING || _status == VehicleStatus.WAITING))
			throw new IllegalArgumentException("Vehicle status must be Pending or waiting");
		Junction currentJunction;
		if(this._status == VehicleStatus.PENDING) { //aun no ha empezado su recorrido
			currentJunction = _junctionList.get(0);
		}
		else {
			//si esta viajando tiene que ir a su cruce destino
			currentJunction = this._road.get_destJunc();
			_road.exit(this);
		}
		int nextIndex = this._junctionList.indexOf(currentJunction) + 1;
		// si no hay mas carreteras en el itinerario el vehiculo ha llegado a su destino
		if(nextIndex >= this._junctionList.size()) { 
			this._road = null;
			this._actualSpeed = 0;
			this._location = 0;
			this._status = VehicleStatus.ARRIVED;
		}else {
			Road nextRoad = currentJunction.roadTo(_junctionList.get(nextIndex)); //obtenemos carretera
			this._road = nextRoad;
			this._actualSpeed = 0;
			this._location = 0;
			this._road.enter(this);
			this._status = VehicleStatus.TRAVELING;
		}

	}
	
	
	
	//Todos los setters
	void setSpeed(int s) { 
		if(s < 0)
			throw new IllegalArgumentException("The argument speed must be a positive number");
		else if(_status == VehicleStatus.TRAVELING)
			_actualSpeed = Math.min(_maxSpeed, s);
		
	}
	
	void setContaminationClass(int c) {
		if(c < 0 || c > 10)
			throw new IllegalArgumentException("The argument Contamination Class must be an integer number between 0 and 10");
		else
			_contaminationClass = c;
	}
	
	//Todos los Getters
	public List<Junction> get_itinerary() {
		return _junctionList;
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
	public int get_contTotal() {
		return _contTotal;
	}
}