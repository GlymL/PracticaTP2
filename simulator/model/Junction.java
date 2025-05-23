package  simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;


public class Junction extends SimulatedObject{
	
	private List<Road> _roadList;
	private Map<Junction, Road> _mapJunctionRoad;
	private List<List<Vehicle>> _listArrayVehicle;
	private int _currentGreenTraffic;
	private int _lastGreenTraffic;
	private LightSwitchingStrategy _lsStrategy;
	private DequeuingStrategy _dqStrategy;
	private int _xCoor;
	private int _yCoor;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		  super(id);
		  if(lsStrategy == null || dqStrategy == null || xCoor < 0 || yCoor < 0)
			  throw new IllegalArgumentException("Invalid type/desc");
		  _lsStrategy = lsStrategy;
		  _dqStrategy = dqStrategy;
		  _xCoor = xCoor;
		  _yCoor = yCoor;
		  _currentGreenTraffic = -1;
		  _lastGreenTraffic = 0;
		  _roadList = new ArrayList<Road>();
		  _listArrayVehicle = new ArrayList<List<Vehicle>>();
		  _mapJunctionRoad = new HashMap<Junction, Road>();
		}
	@Override
	void advance(int time) {
		if(_currentGreenTraffic != -1) {
		List<Vehicle> removable = _dqStrategy.dequeue(_listArrayVehicle.get(_currentGreenTraffic)); //llama a la funcion con la cola del semaforo actual
		for(Vehicle v : _listArrayVehicle.get(_currentGreenTraffic))
			if (removable.contains(v)) {
				v.moveToNextRoad();
				_listArrayVehicle.get(_currentGreenTraffic).remove(v);
			}
		}
		int newGreenTraffic = _lsStrategy.chooseNextGreen(_roadList, _listArrayVehicle, _currentGreenTraffic, _lastGreenTraffic, time);
		if(newGreenTraffic == _currentGreenTraffic)
			_lastGreenTraffic++;
		else
			_currentGreenTraffic = newGreenTraffic;
	}
	@Override
	public JSONObject report() {
		JSONObject j_junction = new JSONObject();
		j_junction.put("id", _id);
		if(_currentGreenTraffic != -1)
			j_junction.put("green", _roadList.get(_currentGreenTraffic).getId());
		else
			j_junction.put("green", "none");
		List<JSONObject> lv_report = new ArrayList<JSONObject>();
		for(int i = 0; i < _roadList.size(); i++) {
			JSONObject r_report = new JSONObject();
			r_report.put("road", _roadList.get(i).getId());
			List<String> listReport = new ArrayList<String>();
			for(Vehicle v : _listArrayVehicle.get(i))
				listReport.add(v._id);
			r_report.put("vehicles", listReport);
			lv_report.add(r_report);
		}
		j_junction.put("queues", lv_report);
		return j_junction;
	}
	
	void addIncommingRoad(Road r) {
		if( r.get_destJunc() != this) //carretera no es de este cruce
			 throw new IllegalArgumentException("Invalid type/desc");
		_roadList.add(r);
		List<Vehicle> lista = new LinkedList<Vehicle>();
		_listArrayVehicle.add(lista);
	}
	void addOutGoingRoad(Road r) { // TODO not finished
		if( r.get_srcJunc() != this) //carretera no es de este cruce
			 throw new IllegalArgumentException("Invalid type/desc");
		for(int i = 0; i < this._roadList.size(); i++) { //ya existe una carretera con mismo inicio y final
			if(this._roadList.get(i).get_destJunc() == r.get_destJunc())
				throw new IllegalArgumentException("Invalid type/desc");
		}
		_mapJunctionRoad.put(r.get_destJunc(), r);
	}
	void enter(Vehicle v) {
		if (v.get_status() != VehicleStatus.PENDING) {
			Road r = v.get_road();
			int aux = _roadList.indexOf(r); //veo la pos de la carretera
			_listArrayVehicle.get(aux).add(v); //como estan en el mismo orden, a�ado el coche a la cola de la carretera
		}	
	}
	Road roadTo(Junction j) {
		return _mapJunctionRoad.get(j);
	}
	
	
}