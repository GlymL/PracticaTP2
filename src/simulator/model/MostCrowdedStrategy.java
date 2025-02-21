package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

	private int _timeSlot;
	
	
	public MostCrowdedStrategy (int timeSlot){
		_timeSlot = timeSlot;
		
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) { 
		int maxLength = -1;
		int nextGreen = -1;
		int iterator = (currGreen+1)%qs.size();
		if(roads.isEmpty()) //lista vacia
			return -1;
		else if(currTime-lastSwitchingTime < _timeSlot) //encendido lleva menos tiempo del min	
			return currGreen;
		else {
			for(int i = 0; i < qs.size(); i++) { //para hacer qs.size() iteraciones, usando iterador aparte por claridad
				if(maxLength < qs.get(iterator).size()) {
					maxLength = qs.get(iterator).size();
					nextGreen = iterator;
				}
				iterator = (iterator + 1) % qs.size();
			}
			return nextGreen;
		}
	}

}
