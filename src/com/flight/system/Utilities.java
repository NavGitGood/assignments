package com.flight.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utilities {

    private Utilities() { }

    public static Date dateParser(String date) throws ParseException {
        return new SimpleDateFormat("dd-mm-yyyy").parse(date);
    }
}
