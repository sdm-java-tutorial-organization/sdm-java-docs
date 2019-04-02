package main.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Doc_Timestamp {

    /**
     * `Timestamp`는 `1970-01-01 00:00:00`에서 시작합니다. (Timestamp = 0기준)
     *
     * `Timestamp`에는 2가지 설정이 있습니다.
     *  - `Second` 기준
     *  - `MilliSecond` 기준 - getTime() 메소드는 MilliSecond 기준으로 처리됩니다.
     *
     * */

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {

        final Date SAMPLE_UTC_DATE = formatter.parse("2019-03-26 04:00:00"); // (서버기준-UTC+9)

        // [서버시간기준] Date -> long(MilliSecond)
        long numOfTimestamp = convertDateToLong(SAMPLE_UTC_DATE);
        System.out.println("[Date -> long(MilliSecond)] " + numOfTimestamp); // [GMT] 2019-03-25 19:00:00 (-9 처리)

        // [서버시간기준] Date -> Timestamp(MilliSecond)
        Timestamp timestamp = convertDateToTimeStamp(SAMPLE_UTC_DATE);
        System.out.println("[Date -> Timestamp(MilliSecond) by TimeStamp] " + timestamp); // [KST] 2019-03-26 04:00:00.0
        System.out.println("[Date -> Timestamp(MilliSecond) by long] " + timestamp.getTime()); // [GMT] 2019-03-25 19:00:00 (-9 처리)

        // [서버시간기준] Timestamp(MilliSecond) -> Date(String)
        String strDateByMilliSecond = convertTimestampToDate(timestamp);
        System.out.println("[Timestamp(MilliSecond) -> Date(String)] " + strDateByMilliSecond); // [KST] 2019-03-26 04:00:00 (+9 처리)

        // [서버시간기준] Timestamp(Second) -> Date(String)
        Timestamp timeStampBySecond = new Timestamp(timestamp.getTime() / 1000);
        /*System.out.println("[timeStampBySecond] " + timeStampBySecond.getTime());*/
        String strDateBySecond = convertTimestampToDate(timeStampBySecond);
        System.out.println("[Timestamp(Second) -> Date(String)] " + strDateBySecond); // [KST] 1970-01-19 08:32:20

        // [GMT] Date(UTC) -> Date(GMT:String)
        String strDateFromDateByGMT = convertDateToDateGMT(SAMPLE_UTC_DATE);
        System.out.println("[GMT][Date -> Date(String)] " + strDateFromDateByGMT); // [GMT] 2019-3월-25 19:00:00

        // [GMT] Timestamp(GMT) -> Date(GMT:String)
        String strDateFromTimestampByGMT = convertTimeStampToDateGMT(timestamp);
        System.out.println("[GMT][Timestamp -> Date(String)] " + strDateFromTimestampByGMT); // [GMT] 2019-3월-25 19:00:00

        // Instant
        printInstant();
    }

    // [서버시간기준] Date -> long(mileSecond)
    public static long convertDateToLong(Date date) throws ParseException {
        return date.getTime();
    }

    // [서버시간기준] Date -> Timestamp(mileSecond)
    public static Timestamp convertDateToTimeStamp(Date date) throws ParseException {
        return new Timestamp(convertDateToLong(date));
    }

    // [서버시간기준]  Timestamp -> Date(String)
    public static String convertTimestampToDate(Timestamp ts) {
        return formatter.format(ts);
    }

    // [GMT] Date -> Date
    public static String convertDateToDateGMT(Date date) {
        SimpleDateFormat formatterByGMT = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        formatterByGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatterByGMT.format(date);
    }
    
    // [GMT] Timestamp -> Date(String)
    public static String convertTimeStampToDateGMT(Timestamp ts){
        SimpleDateFormat formatterByGMT = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        formatterByGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatterByGMT.format(ts);
    }

    // Instant
    public static void printInstant() {
        System.out.println("[Instant.now()] " + Instant.now());
        System.out.println("[Instant.now().toString()] " + Instant.now().toString());
    }

}
