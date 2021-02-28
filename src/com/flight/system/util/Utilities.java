package com.flight.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Utilities class provides some re-usable helper methods
 */
public final class Utilities {

    private Utilities() { }

    // to convert string date to Date object in dd-mm-yyyy format
    public static Date dateParser(String date) throws ParseException {
        return new SimpleDateFormat("dd-mm-yyyy").parse(date);
    }

    // to convert Date object in dd-mm-yyyy format to string
    public static String dateFormatter(Date date) {
        return new SimpleDateFormat("dd-mm-yyyy").format(date);
    }
}
