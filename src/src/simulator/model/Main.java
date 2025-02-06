package simulator.model;

import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
	Junction j1 = new Junction("j1", new RoundRobinStrategy(1),
		new MoveAllStrategy(),0,0);
	Junction j2 = new Junction("j2", new RoundRobinStrategy(1),
		new MoveAllStrategy(),0,0);
	Road r = new CityRoad("r",j1,j2,120,100,2,Weather.SUNNY);
	Vehicle v = new Vehicle("v",100,3,Arrays.asList(j1,j2));
	System.out.println(j1.report());
	System.out.println(j2.report());
	System.out.println(r.report()); 
	System.out.println(v.report()); //status PENDING
	v.moveToNextRoad();
	System.out.println(j1.report());
	System.out.println(j2.report());
	System.out.println(r.report());
	System.out.println(v.report());//status TRAVELING
	j1.advance(1);
	j2.advance(1);
	r.advance(1);
	System.out.println(j1.report());
	System.out.println(j2.report());
	System.out.println(r.report());
	System.out.println(v.report());//status WAITING
	j1.advance(2);
	j2.advance(2);
	r.advance(2);
	System.out.println(j1.report());
	System.out.println(j2.report());
	System.out.println(r.report());
	System.out.println(v.report());//status ARRIVED
	}

}
