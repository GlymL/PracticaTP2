package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	private static final Color _ROAD_COLOR = Color.BLACK;
	private static final Color _IMAGE_COLOR = new Color(0, 0, 0, 0); // r g b a (a is alpha, for transparency)

	private RoadMap _map;

	private Image _car;

	MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
		setPreferredSize(new Dimension(300, 200));
	}

	private void initGUI() {
		_car = loadImage("car.png");
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMapByRoad(g);
		}
	}

	private void drawMapByRoad(Graphics g) {
		for (int i = 0; i < _map.getRoads().size(); i++) {
			int x1 = 50;
			int y = (i + 1) * 50;
			int x2 = getWidth() - 100;
			Road r = _map.getRoads().get(i);

			// Calculates the color to the destination junction
			Color destJuncColor = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				destJuncColor = _GREEN_LIGHT_COLOR;
			}

			// Draws the road and the name
			g.setColor(_ROAD_COLOR);
			g.drawLine(x1, y, x2, y);
			g.drawString(r.getId(), 15, y + 6);

			// Draws the junctions names
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().getId(), x1, y - 15);
			g.drawString(r.getDest().getId(), x2, y - 15);

			// Draws the starting junction color
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - 6, y - 6, 10, 10);

			// Draws the cars
			for (Vehicle v : r.getVehicles()) {
				int A = v.getLocation();
				int B = r.getLength();
				int x = x1 + (int) ((x2 - x1) * ((double) A / (double) B));
				g.drawImage(_car, x, y - 10, 16, 16, _IMAGE_COLOR, null);
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				Color c = new Color(0, vLabelColor, 0);
				g.setColor(c);
				g.drawString(v.getId(), x, y - 12);

			}

			// Draws the finishing junction color
			g.setColor(destJuncColor);
			g.fillOval(x2 - 6, y - 6, 10, 10);

			// Draws the climatic conditions
			String w = "";
			switch (r.getWeather().toString()) {
			case "SUNNY":
				w = "sun";
				break;
			case "CLOUDY":
				w = "could";
				break;
			case "RAINY":
				w = "rain";
				break;
			case "WINDY":
				w = "wind";
				break;
			case "STORM":
				w = "storm";
				break;
			}
			Image weather = loadImage(w + ".png");
			g.drawImage(weather, x2 + 13, y - 15, 25, 25, _IMAGE_COLOR, null);

			// Draws the contamination of the road
			String cont = "cont_";
			double A = r.getTotalCO2();
			double B = r.getContLimit();
			cont += (int) Math.floor(Math.min(A / (1.0 + B), 1.0) / 0.19);
			Image contamination = loadImage(cont + ".png");
			g.drawImage(contamination, x2 + 50, y - 15, 25, 25, _IMAGE_COLOR, null);
		}

	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

}
