package simulator.view;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.Box;
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
	private JLabel eventLabel;

	
	public StatusBar(Controller c) {
		_controller = c;
		initGUI();
		_controller.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(Box.createRigidArea(new Dimension(15, 0)));
		tickLabel = new JLabel("Time: 0");
		this.add(tickLabel);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		JSeparator sep = new JSeparator(JSeparator.VERTICAL);
		sep.setMaximumSize(new Dimension(1, 50));
		this.add(sep);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		eventLabel = new JLabel("Welcome");
		this.add(eventLabel);
		this.setVisible(true);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {tickLabel.setText("Time: " + time); eventLabel.setText("");}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {eventLabel.setText("Event added " + e.toString());}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {tickLabel.setText("Time: 0");}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {}

}
