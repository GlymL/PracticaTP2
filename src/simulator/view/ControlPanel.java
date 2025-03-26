package simulator.view;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _controller;
	private JButton _loadFileButton;
	private JButton _setContClassButton;
	private JButton _setWeatherButton;
	private JButton _executeButton;
	private JButton _stopButton;
	private JButton _exitButton;
	private ChangeCO2ClassDialog _CO2Event;
	private JDialog _weatherEvent;
	private Boolean _stopped = false;
	
	public ControlPanel(Controller c) {
		_controller = c;
		_controller.addObserver(this);
		initGUI();
	}
	private void initGUI() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JToolBar bar = new JToolBar();
		
		_loadFileButton = new JButton(new ImageIcon(loadImage("open.png")));
		_loadFileButton.addActionListener((e) -> {loadFile();});
		bar.add(_loadFileButton);
		
		separator(bar);
		
		_setContClassButton = new JButton(new ImageIcon(loadImage("co2class.png")));
		_setContClassButton.addActionListener((e) -> {addCO2ClassEvent();});
		bar.add(_setContClassButton);
		
		_setWeatherButton = new JButton(new ImageIcon(loadImage("weather.png")));
		bar.add(_setWeatherButton);
		
		separator(bar);
		
		_executeButton = new JButton(new ImageIcon(loadImage("run.png")));
		bar.add(_executeButton);
		
		_stopButton = new JButton(new ImageIcon(loadImage("stop.png")));
		_stopButton.addActionListener((e) ->_stopped = true);
		bar.add(_stopButton);
		
		JLabel ticks = new JLabel("Ticks: ");
		SpinnerNumberModel s = new SpinnerNumberModel();
		JSpinner spin = new JSpinner(s);
		spin.setMaximumSize(new Dimension(2000, 50));
		spin.setValue(10);
		_executeButton.addActionListener((e) -> {_stopped = false; advance(s);});
		ticks.add(spin);
		bar.add(ticks);
		bar.add(spin);
		
		
		bar.add(Box.createHorizontalGlue());
		separator(bar);
		_exitButton = new JButton(new ImageIcon(loadImage("exit.png")));
		_exitButton.addActionListener((e) -> close());
		bar.add(_exitButton);
		this.add(bar);
		
		_CO2Event = new ChangeCO2ClassDialog(_controller);
	}
	
	private void advance(SpinnerNumberModel s) {
		runSim (s.getNumber().intValue());
	}
	private void runSim(int n) {
		if (n > 0 && !_stopped) {
			_loadFileButton.setEnabled(false);
			_setContClassButton.setEnabled(false);
			_setWeatherButton.setEnabled(false);
			_executeButton.setEnabled(false);
			_exitButton.setEnabled(false);
			try {
				_controller.run(1);
	         		SwingUtilities.invokeLater(() -> runSim(n - 1));
			} catch (Exception e) {
				JOptionPane.showConfirmDialog(this, e, "Error en la simulacion", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				_stopped = true;
				_loadFileButton.setEnabled(true);
				_setContClassButton.setEnabled(true);
				_setWeatherButton.setEnabled(true);
				_executeButton.setEnabled(true);
				_exitButton.setEnabled(true);
			}
		} else {
			_stopped = true;
			_loadFileButton.setEnabled(true);
			_setContClassButton.setEnabled(true);
			_setWeatherButton.setEnabled(true);
			_executeButton.setEnabled(true);
			_exitButton.setEnabled(true);
		}
	}
	
	
	private void close() {
		int option = JOptionPane.showConfirmDialog
				(this, "Estas seguro de que quieres cerrar el programa?", "Salida del programa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(option == 0)
			System.exit(0);
		
	}
	private Object loadFile(){
		JFileChooser chooser = new JFileChooser(new File("resources/examples"));
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
			JOptionPane.showConfirmDialog(chooser, e, "Error en la apertura de fichero", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
	    }
	   	 return chooser;
	}
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {}
		return i;
	}
	
	private void separator(JToolBar bar) {
		bar.addSeparator();
		JSeparator sep = new JSeparator(JSeparator.VERTICAL);
		sep.setMaximumSize(new Dimension(0, 20));
		bar.add(sep);
		bar.addSeparator();
	}
	
	private void addCO2ClassEvent() {
		// TODO Auto-generated method stub
		_CO2Event.open();
	}
}
