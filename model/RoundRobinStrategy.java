package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{

	private int _timeSlot;
	
	
	RoundRobinStrategy (int timeSlot){
		_timeSlot = timeSlot;
		
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) { // funciona como un reloj (va de uno en uno hasta que cumplent _timeSlot tiempo)
		if(roads.size() == 0) //lista vacia
			return -1;
		else if (currGreen == -1) //todos apagados
			return 0;
		else if((currTime-lastSwitchingTime) < _timeSlot) //encendido lleva menos tiempo del min
			return currGreen;
		else
			return (currGreen+1)%roads.size();
	}

}
