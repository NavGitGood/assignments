package com.flight.system;

import java.util.*;
import java.util.stream.Collectors;

public class Flights {

    private Set<Flight> flights = new HashSet<Flight>();

    public void purgeSet() {
        flights.clear();
    }

    public boolean addFlight(Flight flight) {
        return flights.add(flight);
    }

    public List<Flight> searchFlights(String dep_loc, String arr_loc, String date) {
        List<Flight> search = flights.stream()
                .filter(flight -> {
                    try {
                        return flight.getDep_loc().equals(dep_loc) &&
                                flight.getArr_loc().equals(arr_loc) &&
//                                flight.isSeat_avl() &&
                                flight.getValid_till().getTime() == Utilities.dateParser(date).getTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .sorted(Comparator.comparing(o -> o.getFare()))
                .collect(Collectors.toList());
        return search;
    }

    public Set<Flight> getAllFlights() {
        return flights;
    }

    @Override
    public String toString() {
        return "Flights:\n"
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

    public Integer getTotalFlights() {
        return flights.size();
    }

}
