package com.flight.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;


public class DataReader implements Runnable {

    private final static String PIPE_DELIMITER = ConfigurationLoader.getPropertyValue("delimiter");
    private final static String fileName = ConfigurationLoader.getPropertyValue("sourceFile");
    Flights flights;
    List<List<String>> result;
    Handler fileHandler = null;
    public long lastModifiedTime = Long.parseLong(ConfigurationLoader.getPropertyValue("timeInMs1980"));//315513000000L; //01-01-1980
    private static final Logger LOGGER = Logger.getLogger(ReadScheduler.class
            .getClass().getName());

    public long getLastModifiedTime() throws IOException {
        BasicFileAttributes attr = Files.readAttributes(Paths.get(fileName), BasicFileAttributes.class);
        return attr.lastModifiedTime().toMillis();
    }

    public void setup() {
        try {
            fileHandler = new FileHandler("logs/logfile.log");
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
        }
    }

    public DataReader(Flights flights) {
        this.flights = flights;
        setup();
    }

    public synchronized void readData(Flights flights) throws IOException {
        if (lastModifiedTime < getLastModifiedTime()) {
            LOGGER.info("Reading...");
            System.out.println("Reading...");
            lastModifiedTime = getLastModifiedTime();
            result = Files.readAllLines(Paths.get(fileName))
                    .stream()
                    .filter(line -> !line.trim().isEmpty()) // ignoring empty lines
                    .map(line -> Arrays.asList(line.split(PIPE_DELIMITER)))
                    .collect(Collectors.toList());
            makeObject(result, flights);
        }
    }

    public synchronized List<List<String>> getResult() {
        return result;
    }

    public void makeObject(List<List<String>> result, Flights flights) {
        result.remove(0);
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
