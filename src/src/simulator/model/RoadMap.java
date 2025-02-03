package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class RoadMap{
	
	private List<Junction> _junctionList;
	private List<Road> _roadList;
	private List<Vehicle> _vehicleList;
	private Map<String,Junction> _stringJunctionMap;
	private Map<String,Road> _stringRoadMap;
	private Map<String,Vehicle> _stringVehicleMap;
	
	RoadMap(){
		_junctionList = new ArrayList<Junction>();
		_roadList = new ArrayList<Road>();
		_vehicleList = new ArrayList<Vehicle>();
	}
	
	void addJunction(Junction j) {
		int i = 0;
		int max = _junctionList.size();
		while(i < max) {
			if(j == _junctionList.get(i))
				throw new IllegalArgumentException("Invalid type/desc");
		i++;
		}
		_junctionList.add(j);
		//_stringJunctionMap.put(, j); preguntar al profesor
	}
	void addRoad(Road r) {
		int i = 0;
		int max = _roadList.size();
		while(i < max) {
			if(r == _roadList.get(i))
				throw new IllegalArgumentException("Invalid type/desc");
		i++;
		}
		_roadList.add(r);
	}
	void addVehicle(Vehicle v) {
		int i = 0;
		int max = _vehicleList.size();
		while(i < max) {
			if(v == _vehicleList.get(i))
				throw new IllegalArgumentException("Invalid type/desc");
		i++;
		}
		_vehicleList.add(v);
	}
	
	//Getters (aclaracion, el get en mapas devuelve null si el elemento no esta, cumpliendo el parametro de github)
	public Junction getJunction(String id) {
		return _stringJunctionMap.get(id);
	}

	public Road getRoad(String id) {
		return _stringRoadMap.get(id);
	}

	public Vehicle get_vehicleList(String id) {
		return _stringVehicleMap.get(id);
	}

	public List<Junction> get_junctionList() {
		return Collections.unmodifiableList(_junctionList);
	}

	public List<Road> get_roadList() {
		return Collections.unmodifiableList(_roadList);
	}

	public List<Vehicle> get_vehicleList() {
		return Collections.unmodifiableList(_vehicleList);
	}
	
	void reset() {
		_junctionList = new ArrayList<Junction>();
		_roadList = new ArrayList<Road>();
		_vehicleList = new ArrayList<Vehicle>();
	}

	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
