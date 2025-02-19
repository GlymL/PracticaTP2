package simulator.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class NewCityRoadEventTest {

	@Test
	void test_1() {
		RoadMap map = new RoadMap();
		//  junctions
		Junction j1 = new Junction("j1", new RoundRobinStrategy(10), new MoveFirstStrategy(), 0, 0);
		Junction j2 = new Junction("j2", new RoundRobinStrategy(10), new MoveFirstStrategy(), 0, 0);

		map.addJunction(j1);
		map.addJunction(j2);

		// add a new vehicle via an event
		Event e = new NewCityRoadEvent(10, "r1", "j1", "j2", 1000, 500, 100, Weather.SUNNY);
		e.execute(map);
		
		// check that the vehicle was added to the map correctly
		List<Road> l = map.get_roadList();
		
		assertEquals(1, l.size());
		
		Road r = l.get(0);
		
		assertEquals("r1", r.getId());
		assertEquals(100, r.get_speedLimit());
		assertEquals(500, r.get_contLimit());
		assertEquals(1000, r.get_length());
		assertEquals(Weather.SUNNY, r.get_weather());

	}

}
