package main.date;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class TimeStamp {

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse("2019-03-26 04:00:00"); // (서버기준-UTC+9)

        // A (long)
        long timestampA = convertTimestampByGetTime(date);
        System.out.println(timestampA);

        // B (TimeStamp)
        Timestamp timestamp = convertTimestampByTimeStamp(date);
        System.out.println(timestamp.getTime());

        // [서버시간] C (ts -> date)
        String strDate = convertDateTimeByTimestamp(timestamp);
        System.out.println(strDate);

        // [GMT] D (ts-> date)
        String strDateGMT = convertDateTimeByTimestampGMT(timestamp);
        System.out.println(strDateGMT);

        // E (Instant)
        printInstant();
    }

    // [서버시간기준] date -> ts(long)
    public static long convertTimestampByGetTime(Date date) throws ParseException {
        return date.getTime();
    }

    // [서버시간기준] date -> ts
    public static Timestamp convertTimestampByTimeStamp(Date date) throws ParseException {
        return new Timestamp(convertTimestampByGetTime(date));
    }

    // [서버시간기준] ts -> date(string)
    public static String convertDateTimeByTimestamp(Timestamp ts) {
        return formatter.format(ts);
    }

    // [GMT 기준] ts -> date(string)
    public static String convertDateTimeByTimestampGMT(Timestamp ts){
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormatGmt.format(ts);
    }

    // Instant
    public static void printInstant() {
        System.out.println("[Instant.now()] " + Instant.now());
        System.out.println("[Instant.now().toString()] " + Instant.now().toString());
    }

}
