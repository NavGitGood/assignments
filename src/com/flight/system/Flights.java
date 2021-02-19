package com.flight.system;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Flights {

    private List<Flight> flights = new ArrayList<Flight>();

    public boolean addFlight(Flight flight) {
        return flights.add(flight);
    }

    public List<Flight> searchFlights(String dep_loc, String arr_loc, String date) {
        return flights.stream()
                .filter(flight -> {
                    try {
                        return flight.getDep_loc().equals(dep_loc) &&
                                flight.getArr_loc().equals(arr_loc) &&
                                flight.getValid_till().equals(DateFormat.getDateInstance().parse(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Students:\n"
                + flights.stream()
                .map(flight -> "Flight No.: " + flight.getFlight_no() + "\n" +
                                "Departure Location: " + flight.getDep_loc() + "\n" +
                                "Arrival Location: " + flight.getArr_loc() + "\n" +
                                "Valid till: " + flight.getValid_till() + "\n" +
                                "Time: " + flight.getFlight_time() + "\n" +
                                "Duration: " + flight.getFlight_dur() + "\n" +
                                "Fare: " + flight.getFare() + "\n" +
                                "Seat Availability: " + flight.isSeat_avl() + "\n"
                )
                .collect(Collectors.joining());
    }

}
