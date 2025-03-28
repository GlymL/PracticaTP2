package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;
import simulator.misc.Pair;

public class ChangeWeatherDialog extends JDialog implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> _rIds = new ArrayList<>();
	private Controller _c;
	int _time;
	
	 ChangeWeatherDialog(Controller c){
		 super();
		 _c = c;
		 c.addObserver(this);
		 initGUI();
	 }


	private void initGUI() {
		setTitle("Change Road Weather");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		JLabel text = new JLabel("<html><p>Schedule an event to change the weather of a road after a given number of simulation ticks from now</p></html>");
		text.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(text);


		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setLayout(new BoxLayout(spinnerPanel, BoxLayout.X_AXIS));
		spinnerPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(spinnerPanel);
		
		spinnerPanel.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.PAGE_START);

		
		JLabel vLabel = new JLabel("Road: ");
		Vector<String> vComboModel = new Vector<>();
		if(_rIds != null)
			vComboModel = new Vector<>(_rIds);
		JComboBox<String> vBox = new JComboBox<>(vComboModel);
		vBox.setMaximumSize(new Dimension(200, 20));
		vLabel.add(vBox);
		spinnerPanel.add(vLabel);
		spinnerPanel.add(vBox);
		
		
		JLabel CO2Class = new JLabel("Weather: ");
		Vector<Weather> cComboModel = new Vector<>();
		cComboModel = new Vector<>();
		for(Weather w : Weather.values())
			cComboModel.add(w);
		JComboBox<Weather> cBox = new JComboBox<>(cComboModel);
		cBox.setMaximumSize(new Dimension(200, 20));
		CO2Class.add(cBox);
		spinnerPanel.add(CO2Class);
		spinnerPanel.add(cBox);
		
		
		JLabel tLabel = new JLabel("Ticks: ");
		SpinnerNumberModel tSpinModel = new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1);
		JSpinner tSpin = new JSpinner(tSpinModel);
		tSpin.setMaximumSize(new Dimension(200, 20));
		tSpin.setValue(1);
		tLabel.add(tSpin);
		spinnerPanel.add(tLabel);
		spinnerPanel.add(tSpin);
			
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setAlignmentX(BOTTOM_ALIGNMENT);
		mainPanel.add(buttonPanel);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.addActionListener((e) -> {this.setVisible(false);});
		buttonPanel.add(CancelButton);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		JButton OKButton = new JButton("OK");
		OKButton.addActionListener((e) -> {
			if(_rIds.size() != 0) {
				ArrayList<Pair<String, Weather>> ws = new ArrayList<Pair<String, Weather>>();
				String car = vBox.getSelectedItem().toString();
				Weather w = Weather.valueOf(cBox.getSelectedItem().toString());
				Pair<String, Weather> p = new Pair<>(car, w);
				ws.add(p);
				SetWeatherEvent event = new SetWeatherEvent(_time + tSpinModel.getNumber().intValue(), ws);
				_c.addEvent(event);
			}
			this.setVisible(false);
		});
		buttonPanel.add(OKButton);
		
		setBounds(new Rectangle(800, 150));
		setVisible(false);
	}

	public void open() {
		setVisible(true);
	}


	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {_time = time;
	if(map != null)
		for(Vehicle v: map.getVehicles())
			_rIds.add(v.toString());
	}


	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {}


	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {time = 0; _rIds.clear();}


	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {_time = time;
	if(map != null)
		for(Road r: map.getRoads())
			_rIds.add(r.toString());
	}


}
