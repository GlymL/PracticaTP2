package simulator.view;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	private JButton _executeButton;
	private JButton _exitButton;
	
	public ControlPanel(Controller c) {
		_controller = c;
		initGUI();
		_controller.addObserver(this);
	}
	private void initGUI() {
		_loadFileButton = new JButton("LoadFileButton");
		_loadFileButton.addActionListener((e) -> {
			try {
				loadFile();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		this.add(_loadFileButton);
		_setContClassButton = new JButton("SetContClass");
		this.add(_setContClassButton);
		_setWeatherButton = new JButton("Set Weather");
		this.add(_setWeatherButton);
		_executeButton = new JButton("Execute button");
		_executeButton.addActionListener((e) -> _controller.run(1));
		this.add(_executeButton);
		_exitButton = new JButton("Exit Button");
		_exitButton.addActionListener((e) -> System.exit(0));
		this.add(_exitButton);
	}
	
	private Object loadFile() throws Throwable {
		JFileChooser chooser = new JFileChooser();
//		try {
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JSON file", "json");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION){
	    	_controller.reset();
	    	File s = chooser.getSelectedFile();
	    	InputStream is = new FileInputStream(s);
	    	_controller.loadEvents(is);
	    }
//		} catch (Exception e){
//	    	JPanel j = new JPanel();
//	    	j.add(new JLabel("A"));
//	    }
	    
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
