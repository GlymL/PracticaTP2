package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		// TODO Auto-generated method stub
		int tc = this.get_contTotal();
		Weather w = this.get_weather();
		int x = 2;
		if (w == Weather.WINDY || w == Weather.STORM)
			x = 10;
			
		this.set_contTotal(Math.max(this.get_contTotal()-x, 0));
		
	}

	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		this.set_speedLimit(get_maxSpeed());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return(((11-v.get_contaminationClass())*this.get_speedLimit())/11);
	}

}
