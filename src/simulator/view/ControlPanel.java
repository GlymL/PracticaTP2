package simulator.view;

import java.awt.BorderLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	private Controller _controller;
	private JButton _loadFileButton;
	private JButton _setContClassButton;
	//private JButton;
	
	public ControlPanel(Controller c) {
		_controller = c;
		initGUI();
	}
	private void initGUI() {
		_loadFileButton = new JButton("LoadFileButton");
		this.add(_loadFileButton);
		_setContClassButton = new JButton("SetContClass");
		this.add(_setContClassButton);
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}
	
	

}
