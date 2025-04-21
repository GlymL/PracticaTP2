package simulator.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
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
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.misc.Pair;

public class ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> _vIds = new ArrayList<>();
	private Controller _c;
	private int _time;

	ChangeCO2ClassDialog(Controller c, Frame frame) {
		super(frame, true);
		_c = c;
		c.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setTitle("Change CO2 Class");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		JLabel text = new JLabel(
				"<html><p>Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now</p></html>");
		text.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(text);

		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setLayout(new BoxLayout(spinnerPanel, BoxLayout.X_AXIS));
		spinnerPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(spinnerPanel);

		spinnerPanel.add(Box.createRigidArea(new Dimension(0, 70)), BorderLayout.PAGE_START);

		JLabel vLabel = new JLabel("Vehicle: ");
		Vector<String> vComboModel = new Vector<>();
		if (_vIds != null)
			vComboModel = new Vector<>(_vIds);
		JComboBox<String> vBox = new JComboBox<>(vComboModel);
		vBox.setMaximumSize(new Dimension(200, 20));
		vLabel.add(vBox);
		spinnerPanel.add(vLabel);
		spinnerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		spinnerPanel.add(vBox);

		JLabel CO2Class = new JLabel("CO2Class: ");
		Vector<Integer> cComboModel = new Vector<>();
		cComboModel = new Vector<>();
		for (int i = 0; i < 11; i++) {
			Integer ig = Integer.valueOf(i);
			cComboModel.add(ig);
		}
		JComboBox<Integer> cBox = new JComboBox<>(cComboModel);
		cBox.setMaximumSize(new Dimension(200, 20));
		CO2Class.add(cBox);
		spinnerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		spinnerPanel.add(CO2Class);
		spinnerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		spinnerPanel.add(cBox);

		JLabel tLabel = new JLabel("Ticks: ");
		SpinnerNumberModel tSpinModel = new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1);
		JSpinner tSpin = new JSpinner(tSpinModel);
		tSpin.setMaximumSize(new Dimension(200, 20));
		tSpin.setValue(1);
		tLabel.add(tSpin);
		spinnerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		spinnerPanel.add(tLabel);
		spinnerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		spinnerPanel.add(tSpin);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setAlignmentX(BOTTOM_ALIGNMENT);
		mainPanel.add(buttonPanel);

		JButton CancelButton = new JButton("Cancel");
		CancelButton.addActionListener((e) -> {
			this.setVisible(false);
		});
		buttonPanel.add(CancelButton);

		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		JButton OKButton = new JButton("OK");
		OKButton.addActionListener((e) -> {
			if (_vIds.size() != 0) {
				ArrayList<Pair<String, Integer>> cs = new ArrayList<Pair<String, Integer>>();
				String car = vBox.getSelectedItem().toString();
				Integer contClass = Integer.parseInt(cBox.getSelectedItem().toString());
				Pair<String, Integer> p = new Pair<>(car, contClass);
				cs.add(p);
				SetContClassEvent event = new SetContClassEvent(_time + tSpinModel.getNumber().intValue(), cs);
				_c.addEvent(event);
			}
			this.setVisible(false);
		});
		buttonPanel.add(OKButton);

		setBounds(new Rectangle(800, 170));
		setVisible(false);
	}

	public void open() {
		this.setLocationRelativeTo(getParent());
		setVisible(true);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_time = time;
		if (map != null)
			for (Vehicle v : map.getVehicles())
				_vIds.add(v.toString());
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		time = 0;
		_vIds.clear();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_time = time;
		if (map != null)
			for (Vehicle v : map.getVehicles())
				_vIds.add(v.toString());
	}

}
