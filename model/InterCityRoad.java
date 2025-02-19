package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		int tc = this.get_contTotal();
		Weather w = this.get_weather();
		int x;
		if(w == Weather.SUNNY)
			x = 2;
		else if (w == Weather.CLOUDY)
			x = 3;
		else if (w == Weather.RAINY)
			x = 10;
		else if (w == Weather.WINDY)
			x = 15;
		else // if(w == Weather.STORM)
			x = 20;
			
		this.set_contTotal(((100-x)*tc)/100);
		
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		if(this.get_contTotal() > this.get_contLimit())
			this.set_speedLimit(this.get_maxSpeed()/2);
		else
			this.set_speedLimit(this.get_maxSpeed());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(this.get_weather() == Weather.STORM) 
				return this.get_speedLimit()*8/10;
		else
				return this.get_speedLimit();
	}

}
