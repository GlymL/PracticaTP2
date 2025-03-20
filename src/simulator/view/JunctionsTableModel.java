package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{

	
	private static final long serialVersionUID = 1L;

	private List<Junction> _junctions;
	private String[] _colNames = { "ID", "Green", "Queues" };
	private Controller _c;

	public JunctionsTableModel(Controller c) {
		_junctions = new ArrayList<>();
		_c = c;
		_c.addObserver(this);
	}

	public void reset() {
		_junctions.clear();
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
		return _junctions == null ? 0 : _junctions.size();
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
			s = _junctions.get(rowIndex).getId();
			break;
		case 1:
			int i = _junctions.get(rowIndex).getGreenLightIndex();
			if (i == -1)
				s = "NONE";
			else
				s = _junctions.get(rowIndex).getInRoads().get(i).toString();
			break;
		case 2:
			s = "";
			for(Road r : _junctions.get(rowIndex).getInRoads())
			s += r.getId() + ": " + _junctions.get(rowIndex).getQueue(r)+ " ";
			break;
		}
		return s;
	}

	private void loadTable(RoadMap map) {
		for(Junction j : map.getJunctions()) {
			if(_junctions.indexOf(j) == -1)
			_junctions.add(j);
		}
		fireTableDataChanged();
			
	}
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {this.loadTable(map);}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {this.reset();}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {loadTable(map);}

}
