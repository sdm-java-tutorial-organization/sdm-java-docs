package main.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class TimeStamp {

    /**
     * `TimeStamp`에는 2가지 설정이 있습니다.
     * - `Second` 기준
     * - `MileSecond` 기준
     *
     * */
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse("2019-03-26 04:00:00"); // (서버기준-UTC+9)

        // [서버시간기준] Date -> long(mileSecond)
        long timestampA = convertTimestampByGetTime(date);
        System.out.println(timestampA);

        // [서버시간기준] Date -> TimeStamp
        Timestamp timestamp = convertTimestampByTimeStamp(date);
        System.out.println(timestamp.getTime());

        // [서버시간기준] TimeStamp -> Date(String)
        String strDate = convertDateTimeByTimestamp(timestamp);
        System.out.println(strDate);

        // [GMT] TimeStamp -> Date(String)
        String strDateGMT = convertDateTimeByTimestampGMT(timestamp);
        System.out.println(strDateGMT);

        // E (Instant)
        printInstant();
    }

    // [서버시간기준] Date -> long(mileSecond)
    public static long convertTimestampByGetTime(Date date) throws ParseException {
        return date.getTime();
    }

    // [서버시간기준] Date -> TimeStamp
    public static Timestamp convertTimestampByTimeStamp(Date date) throws ParseException {
        return new Timestamp(convertTimestampByGetTime(date));
    }

    // [서버시간기준] TimeStamp -> Date(String)
    public static String convertDateTimeByTimestamp(Timestamp ts) {
        return formatter.format(ts);
    }

    // [GMT] TimeStamp -> Date(String)
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
