package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int tc = this.getTotalCO2();
		Weather w = this.getWeather();
		int x = 2;
		if (w == Weather.WINDY || w == Weather.STORM)
			x = 10;

		this.set_contTotal(Math.max(tc - x, 0));

	}

	@Override
	void updateSpeedLimit() {
		this.set_speedLimit(getMaxSpeed());
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return (((11 - v.getContClass()) * this.getSpeedLimit()) / 11);
	}

}
