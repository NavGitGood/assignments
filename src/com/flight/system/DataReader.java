package com.flight.system;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
    static Handler fileHandler = null;
    private static final Logger LOGGER = Logger.getLogger(ReadScheduler.class
            .getClass().getName());

    public static void setup() {
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
        LOGGER.info("Reading...");
        result = Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(line -> Arrays.asList(line.split(PIPE_DELIMITER)))
                .collect(Collectors.toList());
        makeObject(result, flights);
    }

    public synchronized List<List<String>> getResult() {
        return result;
    }

    public Date dateParser(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
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
                .forEach(flightRecord -> flights.addFlight(flightRecord));
    }

    public Flight mapper(List<String> data) throws ParseException {
        return new Flight(
                data.get(0),
                data.get(1),
                data.get(2),
                dateParser(data.get(3)),
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
