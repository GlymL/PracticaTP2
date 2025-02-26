package simulator.model;

import java.util.Comparator;

public class VehicleComparator implements Comparator<Vehicle>{;
	
	VehicleComparator(){	}

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		if(o1.getLocation() < o2.getLocation())
			return 1;
		else if (o1.getLocation() > o2.getLocation())
			return -1;
		else // (if (o1.get_location() == o2.get_location()))
			return 0;
	}

}
