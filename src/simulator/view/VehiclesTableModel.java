package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	private List<Vehicle> _vehicles;
	private String[] _colNames = { "ID", "Location", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2",
			"Distance" };
	private Controller _c;

	public VehiclesTableModel(Controller c) {
		_vehicles = new ArrayList<>();
		_c = c;
		_c.addObserver(this);
	}

	public void reset() {
		_vehicles.clear();
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	// si no pongo esto no coge el nombre de las columnas
	//
	// this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// método obligatorio, probad a quitarlo, no compila
	//
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// método obligatorio
	//
	// the number of row, like those in the events list
	public int getRowCount() {
		return _vehicles == null ? 0 : _vehicles.size();
	}

	@Override
	// método obligatorio
	// así es como se va a cargar la tabla desde el ArrayList
	// el índice del arrayList es el número de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell
	// 0: "ID", 1: "Location", 2: "Itinerary", 3: "CO2 Class", 4: "Max. Speed", 5:
	// "Speed", 6: "Total CO2", 7: "Distance"
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _vehicles.get(rowIndex).getId();
			break;
		case 1:
			s = _vehicles.get(rowIndex).getLocation();
			break;
		case 2:
			s = _vehicles.get(rowIndex).getItinerary();
			break;
		case 3:
			s = _vehicles.get(rowIndex).getContClass();
			break;
		case 4:
			s = _vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = _vehicles.get(rowIndex).getSpeed();
			break;
		case 6:
			s = _vehicles.get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = _vehicles.get(rowIndex).getTotalDistance();
			break;
		}
		return s;
	}

	private void loadTable(RoadMap map) {
		for (Vehicle v : map.getVehicles()) {
			if (_vehicles.indexOf(v) == -1)
				_vehicles.add(v);
		}
		fireTableDataChanged();

	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		this.loadTable(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		this.reset();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		loadTable(map);
	}

}
