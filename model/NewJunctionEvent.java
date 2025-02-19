package simulator.model;

public class NewJunctionEvent extends Event{
	private Junction j;

	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		  super(time);
		  j = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
		}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		map.addJunction(j);
	}
	
}
