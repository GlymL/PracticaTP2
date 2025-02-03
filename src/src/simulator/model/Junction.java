package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{

	
	private List<Road> _listRoad;
	private Map<Junction, Road> _mapRoads;
	private List<List<Vehicle>> _queueList;
	private int _currentGreenTraffic;
	private int _lastGreenTraffic;
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
		  _currentGreenTraffic = -1;
		  _lastGreenTraffic = 0;
		}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		List<Vehicle> removable = _dqStrategy.dequeue(_queueList.get(_currentGreenTraffic)); //llama a la funcion con la cola del semaforo actual
		for(int i= 0; i < removable.size(); i++) {
			removable.get(i).moveToNextRoad();
			
		}
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
		if( r.get_srcJunc() != this) //carretera no es de este cruce
			 throw new IllegalArgumentException("Invalid type/desc");
		for(int i = 0; i < this._listRoad.size(); i++) { //ya existe una carretera con mismo inicio y final
			if(this._listRoad.get(i).get_destJunc() == r.get_destJunc())
				throw new IllegalArgumentException("Invalid type/desc");
		}
		Junction j;
		j = r.get_destJunc();
		_mapRoads.put(j, r);
	}
	void enter(Vehicle v) {
		Road r = v.get_road();
		int aux = _listRoad.indexOf(r); //veo la pos de la carretera
		_queueList.get(aux).add(v); //como estan en el mismo orden, añado el coche a la cola de la carretera
	}
	Road roadTo(Junction j) {
		return _mapRoads.get(j);
	}
	
	
}
