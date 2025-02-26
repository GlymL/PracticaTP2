package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	@Override
	void reduceTotalContamination() {
		int tc = this.getTotalCO2();
		Weather w = this.getWeather();
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
		if(this.getTotalCO2() > this.getContLimit())
			this.set_speedLimit(this.getMaxSpeed()/2);
		else
			this.set_speedLimit(this.getMaxSpeed());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		if(this.getWeather() == Weather.STORM) 
				return this.getSpeedLimit()*8/10;
		else
				return this.getSpeedLimit();
	}

}
