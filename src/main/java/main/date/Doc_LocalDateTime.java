package main.date;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Doc_LocalDateTime {

    /**
     * Java 1.8 기준으로 다음을 사용할 수 있습니다.
     * - LocalDate
     * - LocalTime
     * - LocalDateTime
     *
     * [참고] https://jeong-pro.tistory.com/163
     */

    public static void main(String[] args) {

        LocalDate currentLocalDateByUTC = getLocalDate();
        /*printLocalDate(currentLocalDateByUTC);*/

        LocalDate currentLocalDateByUTCByDetail = getLocalDateByDetail(2019, 4, 2);
        /*printLocalDate(currentLocalDateByUTCByDetail);*/

        LocalDateTime currentLocalDateTimeByUTC = getLocalDateTime();
        /*printLocalDateTime(currentLocalDateTimeByUTC);*/
        /*printByFormat(currentLocalDateTimeByUTC);*/

        // [서버기준] Timestamp -> LocalDateTime
        LocalDateTime localDateTimeByTimeStamp = parseTimestampToLocalDateTime(new Timestamp(1553540400000L)); // [GMT] 1553540400000L - 2019년 March 25일 Monday PM 7:00:00
        /*System.out.println("[localDateTimeByTimeStamp] " + localDateTimeByTimeStamp); // [UTC] 2019-03-26T04:00*/

        // [서버기준] LocalDateTime -> Timestamp
        Timestamp timestampByLocalDateTime = parseLocalDateTimeToTimestamp(localDateTimeByTimeStamp); // [UTC] 2019-03-26T04:00
        /*System.out.println("[timestampByLocalDateTime by long] " + timestampByLocalDateTime.getTime()); // [GMT] 1553540400000L - 2019년 March 25일 Monday PM 7:00:00*/
        /*System.out.println("[timestampByLocalDateTime by Timestamp] " + timestampByLocalDateTime); // [UTC] 2019-03-26 04:00:00.0*/

        ZonedDateTime utcDateTime = getTimeZone("UTC");
        ZonedDateTime seoulDateTime = getTimeZone("Asia/Seoul");

    }

    // ================================== GET ==================================

    // [서버기준] LocalDate
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    // [서버기준] LocalDate by Detail
    public static LocalDate getLocalDateByDetail(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    // [서버기준] LocalDateTime
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    // [서버기준] LocalDateTime by Detail
    public static LocalDateTime getLocalDateTimeByDetail(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond) {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }

    public static ZonedDateTime getTimeZone(String timezone) {
        return ZonedDateTime.now(ZoneId.of(timezone));
    }

    // ================================== SET ==================================

    // Formatting
    public static void printByFormat(LocalDateTime localDateTime) {
        System.out.println("[DateTimeFormatter.BASIC_ISO_DATE] " + localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println("[DateTimeFormatter.ISO_DATE] " + localDateTime.format(DateTimeFormatter.ISO_DATE));
        System.out.println("[DateTimeFormatter.ISO_TIME] " + localDateTime.format(DateTimeFormatter.ISO_TIME));
        System.out.println("[DateTimeFormatter.ISO_DATE_TIME] " + localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println("[DateTimeFormatter.ISO_LOCAL_DATE] " + localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("[DateTimeFormatter.ISO_LOCAL_TIME] " + localDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
        System.out.println("[DateTimeFormatter.ISO_LOCAL_DATE_TIME] " + localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        /*System.out.println("[DateTimeFormatter.ISO_OFFSET_DATE] " + localDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE));*/
        /*System.out.println("[DateTimeFormatter.ISO_OFFSET_TIME] " + localDateTime.format(DateTimeFormatter.ISO_OFFSET_TIME));*/
        /*System.out.println("[DateTimeFormatter.ISO_OFFSET_DATE_TIME] " + localDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));*/
        System.out.println("[DateTimeFormatter.ISO_ORDINAL_DATE] " + localDateTime.format(DateTimeFormatter.ISO_ORDINAL_DATE));
        System.out.println("[DateTimeFormatter.ISO_WEEK_DATE] " + localDateTime.format(DateTimeFormatter.ISO_WEEK_DATE));
        /*System.out.println("[DateTimeFormatter.ISO_ZONED_DATE_TIME] " + localDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));*/
        /*System.out.println("[DateTimeFormatter.RFC_1123_DATE_TIME] " + localDateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME));*/
        /*System.out.println("[DateTimeFormatter.ISO_INSTANT] " + localDateTime.format(DateTimeFormatter.ISO_INSTANT));*/
        System.out.println("[DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")] " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    // LocalDateTime -> java.util.Date
    public static Date parseLocalDateTimeToUtilDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // java.util.Date -> LocalDateTime
    public static LocalDateTime parseUtilDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    // LocalDate -> java.util.Date
    public static java.sql.Date parseLocalDateTimeToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    // java.sql.Date -> LocalDate
    public static LocalDate parseSqlDateToLocalDate(java.sql.Date date) {
        return date.toLocalDate();
    }

    // String -> LocalDate [With Format]
    public static LocalDate parseStringToLocalDate(String strOfLocalDate) {
        return LocalDate.parse(strOfLocalDate);
        /*return LocalDate.parse("20081004", DateTimeFormatter.BASIC_ISO_DATE);*/
    }

    // String -> LocalDateTime [With Format]
    public static LocalDateTime parseStringToLocalDateTime(String strOfLocalDateTime) {
        return LocalDateTime.parse(strOfLocalDateTime);
        /*return LocalDateTime.parse("2010-11-25 12:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));*/
    }

    // [서버기준] java.sql.Timestamp -> LocalDateTime
    public static LocalDateTime parseTimestampToLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    // [서버기준] java.sql.Timestamp -> LocalDateTime
    public static Timestamp parseLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    // LocalDateTime -> LocalDate
    public static LocalDate parseLocalDateTimeToLocalDate(LocalDateTime localDateTime) {
        return LocalDate.from(localDateTime);
    }

    // LocalDate -> LocalDateTime
    public static LocalDateTime parseLocalDateToLocalDateTime(LocalDate localDate, int hour, int minute, int second, int nanoOfSecond) {
        return localDate.atTime(hour, minute, second, nanoOfSecond);
    }

    // ================================== PRINT ==================================

    public static void printLocalDate(LocalDate localDate) {
        System.out.println("[getYear()] " + localDate.getYear()); // int
        System.out.println("[getMonth()] " + localDate.getMonth()); // string (name)
        System.out.println("[getMonthValue()] " + localDate.getMonthValue()); // int (n / 12)
        System.out.println("[getDayOfYear()] " + localDate.getDayOfYear()); // int (n / 365)
        System.out.println("[getDayOfMonth()] " + localDate.getDayOfMonth()); // int (n / 30)
        System.out.println("[getDayOfWeek()] " + localDate.getDayOfWeek()); // string (name)
        System.out.println("[isLeapYear()] " + localDate.isLeapYear()); // boolean (true / false)
    }

    public static void printLocalTime(LocalTime localTime) {
        System.out.println("[localTime.getHour()] " + localTime.getHour());
        System.out.println("[localTime.getMinute()] " + localTime.getMinute());
        System.out.println("[localTime.getSecond()] " + localTime.getSecond());
        System.out.println("[localTime.getNano()] " + localTime.getNano());
    }

    public static void printLocalDateTime(LocalDateTime localDateTime) {
        System.out.println("[getYear()] " + localDateTime.getYear()); // int
        System.out.println("[getMonth()] " + localDateTime.getMonth()); // string (name)
        System.out.println("[getMonthValue()] " + localDateTime.getMonthValue()); // int (n / 12)
        System.out.println("[getDayOfYear()] " + localDateTime.getDayOfYear()); // int (n / 365)
        System.out.println("[getDayOfMonth()] " + localDateTime.getDayOfMonth()); // int (n / 30)
        System.out.println("[getDayOfWeek()] " + localDateTime.getDayOfWeek()); // string (name)
        /*System.out.println("[isLeapYear()] " + localDateTime.isLeapYear()); // boolean (true / false)*/
        System.out.println("[localTime.getHour()] " + localDateTime.getHour());
        System.out.println("[localTime.getMinute()] " + localDateTime.getMinute());
        System.out.println("[localTime.getSecond()] " + localDateTime.getSecond());
        System.out.println("[localTime.getNano()] " + localDateTime.getNano());
    }

}
