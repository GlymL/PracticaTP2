package simulator.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		
		_loadFileButton = new JButton(new ImageIcon(loadImage("open.png")));
		_loadFileButton.addActionListener((e) -> {
			try {
				loadFile();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		this.add(_loadFileButton);
		_setContClassButton = new JButton(new ImageIcon(loadImage("co2class.png")));
		this.add(_setContClassButton);
		_setWeatherButton = new JButton(new ImageIcon(loadImage("weather.png")));
		this.add(_setWeatherButton);
		_executeButton = new JButton(new ImageIcon(loadImage("run.png")));
		_executeButton.addActionListener((e) -> _controller.run(1));
		this.add(_executeButton);
		_exitButton = new JButton(new ImageIcon(loadImage("exit.png")));
		_exitButton.addActionListener((e) -> System.exit(0));
		this.add(_exitButton);
	}
	
	private Object loadFile(){
		JFileChooser chooser = new JFileChooser(new File("resources"));
		try {
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JSON file", "json");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION){
	    	File s = chooser.getSelectedFile();
	    	InputStream is = new FileInputStream(s);
	    	_controller.reset();
	    	_controller.loadEvents(is);
	    }
		} catch (Exception e){
			JOptionPane.showConfirmDialog(chooser, e, "Error en la apertura de fichero", JOptionPane.CLOSED_OPTION);
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
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
}
