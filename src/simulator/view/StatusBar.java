package simulator.view;

import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _controller;
	private JLabel tickLabel;
	
	public StatusBar(Controller c) {
		_controller = c;
		initGUI();
		_controller.addObserver(this);
	}

	private void initGUI() {
		JPanel StatusPanel = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		tickLabel = new JLabel("Time: 0");
		StatusPanel.add(tickLabel);
		StatusPanel.add(new JSeparator());
		this.add(StatusPanel);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {tickLabel.setText("Time: " + time);}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {tickLabel.setText("Time: 0");}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {}

}
