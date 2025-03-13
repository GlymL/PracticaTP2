package simulator.view;

import java.awt.BorderLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	private Controller _controller;
	private JButton _loadFileButton;
	private JButton _setContClassButton;
	private JButton _setWeatherButton;
	private JButton _exitButton;
	
	public ControlPanel(Controller c) {
		_controller = c;
		initGUI();
	}
	private void initGUI() {
		_loadFileButton = new JButton("LoadFileButton");
		_loadFileButton.addActionListener((e) -> loadFile());
		this.add(_loadFileButton);
		_setContClassButton = new JButton("SetContClass");
		this.add(_setContClassButton);
		_setWeatherButton = new JButton("Set Weather");
		this.add(_setWeatherButton);
		_exitButton = new JButton("Exit Button");
		_exitButton.addActionListener((e) -> System.exit(0));
		this.add(_exitButton);
	}
	
	private Object loadFile() {
		JFileChooser chooser = new JFileChooser();
		try {
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JSON files", "json");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) 
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	    throw new UnsupportedOperationException();
		} catch (Exception e){
	    	JPanel j = new JPanel();
	    	j.add(new JLabel("A"));
	    	
	    }
	    
	 return chooser;
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
