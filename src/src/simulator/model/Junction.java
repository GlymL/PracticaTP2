package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{

	
	private List<Road> _listRoad;
	private Map<Junction, Road> _mapRoads;
	private List<List<Vehicle>> _queueList;
	private int _index;
	private int _lastIndex;
	private LightSwitchingStrategy _lsStrategy;
	private DequeuingStrategy _dqStrategy;
	private int _xCoor;
	private int _yCoor;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		  super(id);
		  if(lsStrategy == null || dqStrategy == null || xCoor <0 || yCoor < 0)
			  throw new IllegalArgumentException("Invalid type/desc");
		  _lsStrategy = lsStrategy;
		  _dqStrategy = dqStrategy;
		  _xCoor = xCoor;
		  _yCoor = yCoor;
		}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	void addIncommingRoad(Road r) {
		_listRoad.add(r);
		List<Vehicle> lista = new ArrayList<Vehicle>();
		lista = r.get_vehicleList();
		_queueList.add(lista);
	}
	void addOutGoingRoad(Road r) { // TODO not finished
		Junction j;
		for(int i = 0; i < this._listRoad.size(); i++) {
			if(this._listRoad.get(i).get_srcJunc() == r.get_srcJunc() && this._listRoad.get(i).get_destJunc() == r.get_destJunc())
				return;
		}
		j = r.get_destJunc();
		_mapRoads.put(j, r);
	}
	void enter(Vehicle v) {
		Road r = v.get_road();
		
		
		
	}
	
	
}
