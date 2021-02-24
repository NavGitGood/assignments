package com.flight.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ReadScheduler<T> {

    BufferedReader reader;
    Flights flights;
    private final static long refreshPeriod = Long.parseLong(ConfigurationLoader.getPropertyValue("refreshPeriodInSeconds"));
    private Pattern dayOrMonth = Pattern.compile("^\\d{1,2}$");
    private Pattern year = Pattern.compile("^\\d{4}$");
    private Pattern threeCharacters = Pattern.compile("^[A-Z]{3}$");
    Predicate<String> isThreeLetterCode = i -> threeCharacters.matcher(i).matches();//i.length() == 3;
    Predicate<String> hasValidFormat = i -> i.split("-").length == 3;
    Predicate<String> isValidDay = i -> dayOrMonth.matcher(i.split("-")[0]).matches() && Integer.parseInt(i.split("-")[0]) <= 31;
    Predicate<String> isValidMonth = i -> dayOrMonth.matcher(i.split("-")[1]).matches() && Integer.parseInt(i.split("-")[1]) <= 12;
    Predicate<String> isValidYear = i -> year.matcher(i.split("-")[2]).matches() && 2000 <= Integer.parseInt(i.split("-")[2]) && Integer.parseInt(i.split("-")[2]) <= 2500;
    Predicate<String> isValidDate = hasValidFormat.and(isValidDay).and(isValidMonth).and(isValidYear);

    public ReadScheduler() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        flights = new Flights();
    }

    public static void main(String[] args) throws IOException {
        ReadScheduler obj = new ReadScheduler();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        DataReader readTask = new DataReader(obj.flights);

        executor.scheduleAtFixedRate(readTask, 0, refreshPeriod, TimeUnit.SECONDS);

        boolean running = true;
        System.out.println("What would you like to perform!");
        while (running) {
            System.out.println("1. Search flights");
            System.out.println("2. Print all flights");
            System.out.println("3. Exit");
            switch (obj.reader.readLine()) {

                case "1":
                    obj.getQuery();
                    break;

                case "2":
                    obj.printAllFlights();
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
        String depLoc = this.reader.readLine().toUpperCase(Locale.ROOT);
        System.out.println("Enter Arrival Location!");
        String arrLoc = this.reader.readLine().toUpperCase(Locale.ROOT);
        System.out.println("Enter Flight Date (dd-mm-yyyy)!");
        String flightDate = this.reader.readLine().toUpperCase(Locale.ROOT);
        if (!isThreeLetterCode.test(depLoc) || !isThreeLetterCode.test(arrLoc) || !isValidDate.test(flightDate)) {
            System.out.println("Invalid input!!");
            return;
        }
        List<Flight> result = flights.searchFlights(depLoc, arrLoc, flightDate);
        if (result.isEmpty()) {
            System.out.println("Sorry, no flights available for your query!!\n");
        }
        result.stream()
                .forEach(System.out::println);
    }

    public void printAllFlights() {
        flights.getAllFlights()
                .stream()
                .forEach(System.out::println);
        System.out.println(flights.getTotalFlights());
    }
}
