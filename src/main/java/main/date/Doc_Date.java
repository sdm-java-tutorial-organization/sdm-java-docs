package main.date;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Doc_Date {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /* yyyy-MM-dd HH:mm:ss */
    public static String strStartDateTime = "2019-03-05 10:00:00";
    public static String strEndDateTime = "2019-03-05 12:00:00";

    public static void main(String[] args) throws Exception {

        // String -> Date
        Date myDate = parseDateFormat(strStartDateTime);
        System.out.println("[parse string to date] " + myDate); // Tue Mar 05 10:00:00 KST 2019

        // Date -> String
        String myStringDate = formatDateFormat(myDate);
        System.out.println("[parse date to string] " + myStringDate); // 2019-03-05 10:00:00

        // Date - Date ? (X)
        Date dateA = parseDateFormat(strStartDateTime);
        Date dateB = parseDateFormat(strEndDateTime);
        System.out.println("dateB : " + dateB.getTime());
        System.out.println("dateA : " + dateA.getTime());
        System.out.println("dateB - dateA : " + (dateB.getTime() - dateA.getTime()));
        System.out.println("dateB - dateA : " + ((dateB.getTime() - dateA.getTime()) / 1000));
        System.out.println("dateB - dateA : " + ((dateB.getTime() - dateA.getTime()) / 1000 / 60));

    }

    // String -> Date
    public static Date parseDateFormat(String myDate) throws Exception {
        return simpleDateFormat.parse(myDate);
    }

    // Date -> String
    public static String formatDateFormat(Date myDate) throws Exception {
        return simpleDateFormat.format(myDate);
    }

}