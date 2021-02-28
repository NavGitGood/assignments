package com.flight.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utilities {

    private Utilities() { }

    public static Date dateParser(String date) throws ParseException {
        return new SimpleDateFormat("dd-mm-yyyy").parse(date);
    }

    public static String dateFormatter(Date date) {
        return new SimpleDateFormat("dd-mm-yyyy").format(date);
    }
}
