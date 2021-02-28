package com.flight.system.util;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * The Validator class provides various predicate functions for validating user input
 */
public class Validator {

    private static Pattern dayOrMonth = Pattern.compile("^\\d{1,2}$");
    private static Pattern year = Pattern.compile("^\\d{4}$");
    private static Pattern threeCharacters = Pattern.compile("^[A-Z]{3}$");
    public static Predicate<String> isThreeLetterCode = i -> threeCharacters.matcher(i).matches();
    private static Predicate<String> hasValidFormat = i -> i.split("-").length == 3;
    private static Predicate<String> isValidDay = i -> dayOrMonth.matcher(i.split("-")[0]).matches() && Integer.parseInt(i.split("-")[0]) <= 31;
    private static Predicate<String> isValidMonth = i -> dayOrMonth.matcher(i.split("-")[1]).matches() && Integer.parseInt(i.split("-")[1]) <= 12;
    private static Predicate<String> isValidYear = i -> year.matcher(i.split("-")[2]).matches() && 2000 <= Integer.parseInt(i.split("-")[2]) && Integer.parseInt(i.split("-")[2]) <= 2500;
    public static Predicate<String> isValidDate = hasValidFormat.and(isValidDay).and(isValidMonth).and(isValidYear);

}
