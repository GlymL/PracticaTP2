package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	
	private static final long serialVersionUID = 1L;

	private List<Road> _roads;
	private String[] _colNames = { "ID", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2" };
	private Controller _c;

	public RoadsTableModel(Controller c) {
		_roads = new ArrayList<>();
		_c = c;
		_c.addObserver(this);
	}

	public void reset() {
		_roads.clear();
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
		return _roads == null ? 0 : _roads.size();
	}

	@Override
	// método obligatorio
	// así es como se va a cargar la tabla desde el ArrayList
	// el índice del arrayList es el número de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell
	 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _roads.get(rowIndex).getId();
			break;
		case 1:
			s = _roads.get(rowIndex).getLength();
			break;
		case 2:
			s = _roads.get(rowIndex).getWeather().toString();
			break;
		case 3:
			s = _roads.get(rowIndex).getMaxSpeed();
			break;
		case 4:
			s = _roads.get(rowIndex).getSpeedLimit();
			break;
		case 5:
			s = _roads.get(rowIndex).getTotalCO2();
			break;
		}
		return s;
	}

	private void loadTable(RoadMap map) {
		for(Road r : map.getRoads()) {
			if(_roads.indexOf(r) == -1)
			_roads.add(r);
		}
		fireTableDataChanged();
			
	}
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {loadTable(map);}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {this.reset();}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {loadTable(map);}

}
