package com.flight.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ReadScheduler {

    BufferedReader reader;
    Flights flights;
    private final static long refreshPeriod = Long.parseLong(ConfigurationLoader.getPropertyValue("refreshPeriodInSeconds"));

    public ReadScheduler() throws IOException {
        reader = new BufferedReader(new InputStreamReader(System.in));
        flights = new Flights();
    }

    public static void main(String[] args) throws IOException {
        ReadScheduler obj = new ReadScheduler();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        DataReader task1 = new DataReader(obj.flights);

        ScheduledFuture<?> result = executor.scheduleAtFixedRate(task1, 2, refreshPeriod, TimeUnit.SECONDS);

        boolean running = true;
        System.out.println(ConfigurationLoader.getPropertyValue("refreshPeriodInSeconds"));
        System.out.println("What would you like to perform!");
        while (running) {
            System.out.println("1. Search a flight");
            System.out.println("2. Print size");
            System.out.println("3. Exit");
            switch (obj.reader.readLine()) {

                case "1":
                    obj.getQuery();
                    break;

                case "2":
                    obj.printFlights();
                    break;

                case "3":
                    System.out.println("Thank you");
                    running = false;
                    executor.shutdown();
                    break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    public void getQuery() throws IOException {
        System.out.println("Enter Departure Location!");
        String depLoc = this.reader.readLine();
        System.out.println("Enter Arrival Location!");
        String arrLoc = this.reader.readLine();
        System.out.println("Enter Flight Date!");
        String flightDate = this.reader.readLine();
        System.out.println(flights.searchFlights(depLoc, arrLoc, flightDate));
    }

    public void printFlights() {
//        System.out.println(flights.toString());
        System.out.println(flights.getTotalFlights());
    }
}
