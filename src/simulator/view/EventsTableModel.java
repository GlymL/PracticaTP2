package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{

	
	private static final long serialVersionUID = 1L;

	private List<Event> _events;
	private String[] _colNames = { "Time", "Desc" };

	public EventsTableModel(Controller c) {
		_events = new ArrayList<>();
		c.addObserver(this);
	}

	public void addEvent(Event e) {
		_events.add(e);
		fireTableDataChanged();
	}

	public void reset() {
		_events.clear();
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _events == null ? 0 : _events.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	private void eventDelete(int time) {
		List <Event>_eventsaux = new ArrayList<>(_events);
		for(Event e : _events)
			if(e.getTime() <= time) {
				_eventsaux.remove(e);
			}
		_events.removeAll(_events);
		_events.addAll(_eventsaux);
		fireTableDataChanged();
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		eventDelete(time);
	}
	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {this.addEvent(e);}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {this.reset();}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		for(Event e : events)
			if(!_events.contains(e))
				this.addEvent(e);
	}
}
