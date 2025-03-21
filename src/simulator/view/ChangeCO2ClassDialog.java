package simulator.view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import simulator.model.RoadMap;

public class ChangeCO2ClassDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoadMap _rm = null;
	
	 ChangeCO2ClassDialog(RoadMap rm){
		 super();
		 _rm = rm;
		 initGUI();
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
		spinnerPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(spinnerPanel);
		
		JLabel vLabel = new JLabel("Vehicle: ");
		SpinnerListModel vSpinModel = new SpinnerListModel(_rm.getVehicles());
		JSpinner vSpin = new JSpinner(vSpinModel);
		vSpin.setMaximumSize(new Dimension(2000, 50));
		vLabel.add(vSpin);
		vSpin.setAlignmentX(LEFT_ALIGNMENT);
		vLabel.setAlignmentX(LEFT_ALIGNMENT);
		spinnerPanel.add(vLabel);
		spinnerPanel.add(vSpin);
		
		
		JLabel CO2Class = new JLabel("CO2Class: ");
		SpinnerNumberModel CO2spinModel = new SpinnerNumberModel(0, 0, 9, 1);
		JSpinner CO2spin = new JSpinner(CO2spinModel);
		CO2spin.setMaximumSize(new Dimension(2000, 50));
		CO2Class.add(CO2spin);
		spinnerPanel.add(CO2Class);
		spinnerPanel.add(CO2spin);
		
		
		JLabel tLabel = new JLabel("Ticks: ");
		SpinnerNumberModel tSpinModel = new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1);
		JSpinner tSpin = new JSpinner(tSpinModel);
		tSpin.setMaximumSize(new Dimension(2000, 50));
		tSpin.setValue(1);
		tLabel.add(tSpin);
		spinnerPanel.add(tLabel);
		spinnerPanel.add(tSpin);
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(BOTTOM_ALIGNMENT);
		mainPanel.add(buttonPanel);
		
		JButton CancelButton = new JButton("Cancel");
		CancelButton.addActionListener((e) -> {this.setVisible(false);});
		buttonPanel.add(CancelButton);
		
		JButton OKButton = new JButton("OK");
		OKButton.addActionListener((e) -> {loadEvent();});
		buttonPanel.add(OKButton);

		setVisible(false);
		pack();
	}
	
	private void loadEvent() {
		// TODO Auto-generated method stub
		setVisible(false);
	}


	public void open() {
		setVisible(true);
	}

}
