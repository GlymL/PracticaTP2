package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		_stringJunctionMap = new HashMap<>();
		_stringRoadMap = new HashMap<>();
		_stringVehicleMap = new HashMap<>();
		
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
		_stringJunctionMap.put(j._id, j);
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
		_stringRoadMap.put(r._id, r);
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
		_stringVehicleMap.put(v._id, v);
	}
	
	//Getters (aclaracion, el get en mapas devuelve null si el elemento no esta, cumpliendo el parametro de github)
	public Junction getJunction(String id) {
		return _stringJunctionMap.get(id);
	}
	public Road getRoad(String id) {
		return _stringRoadMap.get(id);
	}
	public Vehicle getVehicle(String id) {
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
		_junctionList.clear();
		_roadList.clear();
		_vehicleList.clear();
		_stringJunctionMap.clear();
		_stringRoadMap.clear();
		_stringVehicleMap.clear();
	}
	public JSONObject report() {
		JSONObject j_roadMap= new JSONObject();
		List<JSONObject> report_junction = new ArrayList<JSONObject>();
		for(Junction j : _junctionList)
			report_junction.add(j.report());
		j_roadMap.put("junctions", report_junction);
		List<JSONObject> report_roads = new ArrayList<JSONObject>();
		for(Road r : _roadList)
			report_roads.add(r.report());
		j_roadMap.put("roads", report_roads);
		List<JSONObject> report_vehicles = new ArrayList<JSONObject>();
		for(Vehicle v : _vehicleList)
			report_vehicles.add(v.report());
		j_roadMap.put("vehicles", report_vehicles);
		return j_roadMap;
	}
	
}