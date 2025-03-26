package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.TrafficSimObserver;
import simulator.misc.Pair;

public class ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoadMap _rm = null;
	private Controller _c;
	int time;
	
	 ChangeCO2ClassDialog(Controller c){
		 super();
		 _c = c;
		 c.addObserver(this);
		 initGUI();
		 time = 0;
	 }


	private void initGUI() {
		// TODO Auto-generated method stub
		setTitle("Change CO2 Class");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		JLabel text = new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now");
		text.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(text);


		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setLayout(new BoxLayout(spinnerPanel, BoxLayout.X_AXIS));
		spinnerPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(spinnerPanel);
		
		spinnerPanel.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.PAGE_START);

		
		JLabel vLabel = new JLabel("Vehicle: ");
		SpinnerModel vSpinModel;
		if(_rm.getVehicles().size() != 0)
			vSpinModel = new SpinnerListModel(_rm.getVehicles());
		else
			vSpinModel = new SpinnerNumberModel();
		JSpinner vSpin = new JSpinner(vSpinModel);
		vSpin.setMaximumSize(new Dimension(200, 20));
		vLabel.add(vSpin);
		spinnerPanel.add(vLabel);
		spinnerPanel.add(vSpin);
		
		
		JLabel CO2Class = new JLabel("CO2Class: ");
		SpinnerNumberModel CO2spinModel = new SpinnerNumberModel(0, 0, 9, 1);
		JSpinner CO2spin = new JSpinner(CO2spinModel);
		CO2spin.setMaximumSize(new Dimension(200, 20));
		CO2Class.add(CO2spin);
		spinnerPanel.add(CO2Class);
		spinnerPanel.add(CO2spin);
		
		
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
			if(_rm == null);
			else {
				ArrayList<Pair<String, Integer>> cs = new ArrayList<Pair<String, Integer>>();
				Pair<String, Integer> p = new Pair<>(vSpinModel.getValue().toString(), tSpinModel.getNumber().intValue());
				cs.add(p);
				SetContClassEvent event = new SetContClassEvent(time + tSpinModel.getNumber().intValue(), cs);
				this.setVisible(false);
				_c.addEvent(event);
			}
		
		
		});
		buttonPanel.add(OKButton);
		
		setBounds(new Rectangle(800, 150));
		setVisible(false);
	}

	public void open() {
		setVisible(true);
	}


	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {time++;_rm = map;}


	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {}


	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {time = 0; _rm = null;}


	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {_rm = map;}

}
