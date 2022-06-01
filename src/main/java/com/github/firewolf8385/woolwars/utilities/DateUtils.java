package com.github.firewolf8385.woolwars.utilities;

import java.time.LocalDate;

public class DateUtils {

    public static String currentDateToString() {
        String dateString = "";

        // Gets the current date.
        LocalDate currentdate = LocalDate.now();

        // Adds the month to the formatted string.
        if(currentdate.getMonthValue() < 10) {
            dateString += "0";
        }
        dateString += currentdate.getMonthValue();
        dateString += "/";

        // Adds the day to the formatted string.
        if(currentdate.getDayOfMonth() < 10) {
            dateString += "0";
        }
        dateString += currentdate.getDayOfMonth();
        dateString += "/";

        // Adds the year to the formatted string.
        String year = currentdate.getYear() + "";
        dateString += year.substring(Math.max(year.length() - 2, 0));

        // Returns the new string.
        return dateString;
    }

}