import com.flight.system.DataReader;
import com.flight.system.Flight;
import com.flight.system.Flights;
import com.flight.system.ReadScheduler;
import com.flight.system.util.Utilities;
import com.flight.system.util.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Main {

    BufferedReader reader;
    Flights flights;

    public Main() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        flights = new Flights();
    }

    public static void main(String[] args) throws IOException {
        Main obj = new Main();
        DataReader readTask = new DataReader(obj.flights);
        ReadScheduler rs = new ReadScheduler(readTask);

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
                    rs.shutdown();
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
        if (!Validator.isThreeLetterCode.test(depLoc) || !Validator.isThreeLetterCode.test(arrLoc) || !Validator.isValidDate.test(flightDate)) {
            System.out.println("Invalid input!!");
            return;
        }
        List<Flight> result = flights.searchFlights(depLoc, arrLoc, flightDate);
        if (result.isEmpty()) {
            System.out.println("Sorry, no flights available for your query!!\n");
        }
        else {
            printAsTable(result);
        }
    }

    public void printAllFlights() {
        printAsTable(new ArrayList<>(flights.getAllFlights()));
    }

    public void printAsTable(List<Flight> dataToPrint) {
        List<List<String>> dataList = dataToPrint.stream()
                .map(Flight::simpleFlightObject)
                .collect(Collectors.toList());
        Utilities.prettyPrint(Flight.columns(), dataList);
    }

}
