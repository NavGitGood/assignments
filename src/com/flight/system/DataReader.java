package com.flight.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {

    private final static String PIPE_DELIMITER = "\\|";
    private final static String fileName = "data/FlightsData.csv";
    Flights flights = new Flights();

    public DataReader() throws IOException {
        List<List<String>> result = Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(line -> Arrays.asList(line.split(PIPE_DELIMITER)))
                .collect(Collectors.toList());
        System.out.println(result);
    }

    public void makeObject(List<List<String>> result) {
        result.remove(0);
        result.stream()
                .map(data -> {
                    try {
                        return this.mapper(data);
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
                DateFormat.getDateInstance().parse(data.get(3)),
                data.get(4),
                Float.parseFloat(data.get(5)),
                Integer.parseInt(data.get(6)),
                data.get(7).equals("Y")
        );
    }

    public static void main(String[] args) throws IOException {
        DataReader obj = new DataReader();
    }

}
