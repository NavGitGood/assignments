package com.flight.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * The Utilities class provides some re-usable helper methods
 */
public final class Utilities {

    private Utilities() { }

    // to convert string date to Date object in dd-mm-yyyy format
    public static Date dateParser(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    // to convert Date object in dd-mm-yyyy format to string
    public static String dateFormatter(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    // to print given data as a table
    public static void prettyPrint(List<String> columns, List<List<String>> rows) {
        List<List<String>> dataList = new ArrayList<>();
        dataList.add(columns);
        dataList.addAll(rows);
        Map<Integer, Integer> columnLengths = new HashMap<>();
        dataList.stream().forEach(a -> Stream.iterate(0, i -> ++i).limit(dataList.get(0).size()).forEach(i -> {
            columnLengths.putIfAbsent(i,0);
            if (columnLengths.get(i) < a.get(i).length()) {
                columnLengths.put(i, a.get(i).length());
            }
        }));

        final StringBuilder formatString = new StringBuilder();
        columnLengths.entrySet().stream().forEach(e -> formatString.append("| %").append(e.getValue()).append("s "));
        formatString.append("|\n");

        Stream.iterate(0, i -> ++i).limit(dataList.size())
                .forEach(a -> System.out.printf(formatString.toString(), dataList.get(a).toArray(new String[0])));
    }

}
