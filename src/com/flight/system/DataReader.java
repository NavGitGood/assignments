package com.flight.system;

import com.flight.system.util.ConfigurationLoader;
import com.flight.system.util.Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

/**
 * The DataReader class is used to read flight details from given source file into Flights object
 */
public class DataReader implements Runnable {

    private final static String PIPE_DELIMITER = ConfigurationLoader.getPropertyValue("delimiter");
    private final static String fileName = ConfigurationLoader.getPropertyValue("sourceFile");
    private final static String logFile = ConfigurationLoader.getPropertyValue("logFile");
    Flights flights;
    List<List<String>> result;
    Handler fileHandler = null;
    public long lastModifiedTime = Long.parseLong(ConfigurationLoader.getPropertyValue("timeInMs1980"));
    private static final Logger LOGGER = Logger.getLogger(ReadScheduler.class.getName());

    public long getLastModifiedTime() throws IOException {
        BasicFileAttributes attr = Files.readAttributes(Paths.get(fileName), BasicFileAttributes.class);
        return attr.lastModifiedTime().toMillis();
    }

    public void setup() {
        try {
            fileHandler = new FileHandler(logFile, true);
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataReader(Flights flights) {
        this.flights = flights;
        setup();
    }

    public synchronized void readData(Flights flights) throws IOException {
        if (lastModifiedTime < getLastModifiedTime()) {
            LOGGER.info("Reading...");
            lastModifiedTime = getLastModifiedTime();
            result = Files.readAllLines(Paths.get(fileName))
                    .stream()
                    .filter(line -> !line.trim().isEmpty()) // ignoring empty lines
                    .map(line -> Arrays.asList(line.split(PIPE_DELIMITER)))
                    .collect(Collectors.toList());
            makeObject(result, flights);
        }
    }

    public void makeObject(List<List<String>> result, Flights flights) {
        result.remove(0);
        flights.purgeSet();
        result.stream()
                .map(data -> {
                    try {
                        return mapper(data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .forEach(flights::addFlight);
    }

    public Flight mapper(List<String> data) throws ParseException {
        return new Flight(
                data.get(0),
                data.get(1),
                data.get(2),
                Utilities.dateParser(data.get(3)),
                data.get(4),
                Float.parseFloat(data.get(5)),
                Integer.parseInt(data.get(6)),
                data.get(7).equals("Y")
        );
    }

    @Override
    public void run() {
        try {
            readData(flights);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
