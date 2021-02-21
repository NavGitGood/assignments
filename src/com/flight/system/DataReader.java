package com.flight.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DataReader implements Runnable{

    private final static String PIPE_DELIMITER = "\\|";
    private final static String fileName = "data/FlightsData.csv";
    Flights flights;
    List<List<String>> result;

    public DataReader(Flights flights) {
        this.flights = flights;
    }

    public synchronized void readData(Flights flights) throws IOException {
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
