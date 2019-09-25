package com.example.baitap01;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Utilities {
    private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss";
    private static DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
    static Date stringToDate(String strDate) {
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            return new Date();
        }

    }
    static String dateToString(Date date) {
        return dateFormat.format(date);
    }

}
