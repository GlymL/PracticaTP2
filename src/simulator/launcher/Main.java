package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.*;
import simulator.view.TestWindow;


public class Main {

	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static int steps = 10;
	private static String _model = "gui";

	private static void parseArgs(String[] args) {
	    // define the valid command line options
	    Options cmdLineOptions = buildOptions();

	    // parse the command line as provided in args
	    CommandLineParser parser = new DefaultParser();
	    try {
	        CommandLine line = parser.parse(cmdLineOptions, args);
	        parseHelpOption(line, cmdLineOptions);
	        parseModeOption(line);
	        parseInFileOption(line);
	        parseOutFileOption(line);
	        parseTicksFileOption(line);

	        // if there are some remaining arguments, then something wrong is
	        // provided in the command line!
	        String[] remaining = line.getArgs();
	        if (remaining.length > 0) {
	            String error = "Illegal arguments:";
	            for (String o : remaining)
	                error += (" " + o);
	            throw new ParseException(error);
	        }

	    } catch (ParseException e) {
	        System.err.println(e.getLocalizedMessage());
	        System.exit(1);
	    }
	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(
				Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator's main loop (default value is 10).").build());
		cmdLineOptions.addOption(
				Option.builder("m").longOpt("mode").hasArg().desc("Where is the program going to execute.").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && _model != "gui") {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if(!_model.equalsIgnoreCase("gui"))
			_outFile = line.getOptionValue("o");
	}
	private static void parseTicksFileOption(CommandLine line) {
	    String ticksValue = line.getOptionValue("t");
	    if (ticksValue != null) {
           	steps = Integer.parseInt(ticksValue);
	    }
	}
	
	private static void parseModeOption(CommandLine line) {
	    String model = line.getOptionValue("m");
	    String cons = "console";
	    if(model != null && model.equalsIgnoreCase(cons))
	    	_model = model;
	}

	private static void initFactories() {
		//	initialize the lss factory
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add( new RoundRobinStrategyBuilder() );
		lsbs.add( new MostCrowdedStrategyBuilder() );
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		//	initialize the events factory
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add( new MoveFirstStrategyBuilder() );
		dqbs.add( new MoveAllStrategyBuilder() );
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		//	initialize the events factory
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add( new NewJunctionEventBuilder(lssFactory,dqsFactory) );
		ebs.add( new NewCityRoadEventBuilder() );
		ebs.add( new NewInterCityRoadEventBuilder() );
		ebs.add(new NewVehicleEventBuilder());
		ebs.add(new SetWeatherEventBuilder());
		ebs.add(new SetContClassEventBuilder());
		
		_eventsFactory = new BuilderBasedFactory<>(ebs);
	}

	private static void startBatchMode() throws IOException {
		InputStream is = new FileInputStream(_inFile);
		
		OutputStream out;
		if(_outFile == null)
			out = System.out;
		else
			out = new FileOutputStream(_outFile);
		
		
		TrafficSimulator ts = new TrafficSimulator();
		
		Controller c = new Controller(ts, _eventsFactory);
	
		
		c.loadEvents(is);
		
		is.close();
		
		c.run(steps, out);
		}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		startMode();
	}
	
	private static void startMode() throws IOException {
		
		if(_model == "gui")
			startGuiMode();
		else if (_model == "console")
			startBatchMode();
		else
			throw new IllegalArgumentException("Mode has an illegal argument");
			
	}
	
	private static void startGuiMode() throws IOException {
		
		TrafficSimulator ts = new TrafficSimulator();
		
		Controller c = new Controller(ts, _eventsFactory);
		
		if(_inFile != null) {
			InputStream is = new FileInputStream(_inFile);
			c.loadEvents(is);		
			is.close();
		}
			
		
		c.run(steps);
		
		SwingUtilities.invokeLater(new Runnable() {	
			@Override
			public void run() {
				new TestWindow(c);
			}
		}); 
		
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}