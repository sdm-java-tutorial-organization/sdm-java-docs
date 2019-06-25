package main.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Doc_ISO8601 {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); // Z = +nnnn

    /**
     * ISO 8601
     * 날짜와 시간과 관련된 데이터 교환을 다루는 국제 표준
     * 이 표준은 국제 표준화 기구(ISO)에 의해 공포
     *
     * - 2016-10-27T17:13:40+00:00 (2016-10-27T17:13:40+0000 -> `replace` 이용한 통합가능)
     * - 2016-10-27T17:13:40Z (GMT -> Z)
     * - 20161027T171340Z
     *
     * */

    public static void main(String[] args) throws Exception {

        // == parseStrISO8601ToDate ==
        System.out.println(parseStrISO8601ToDate("2019-03-05T11:48:49Z")); // UTC 통일 (KST - Tue Mar 05 20:48:49 KST 2019 [+9])
        System.out.println(parseStrISO8601ToDate("2019-03-05T11:48:49+0000")); // UTC 통일 (KST - Tue Mar 05 20:48:49 KST 2019 [+9])
        System.out.println(parseStrISO8601ToDate("2019-03-05T11:48:49+09:00")); // UTC 통일 (KST - Tue Mar 05 11:48:49 KST 2019 [0])
        System.out.println(parseStrISO8601ToDate("2019-03-05T11:48:49.8618642+09:00")); // MS 제거 (KST - Tue Mar 05 11:48:49 KST 2019 [0])
        System.out.println(parseStrISO8601ToDate("2019-03-05T11:48:49")); // UTC 통일 (KST - Tue Mar 05 20:48:49 KST 2019 [+9])
        System.out.println(parseStrISO8601ToDate("2019-03-05T11:48:49.123")); // UTC 통일 (KST - Tue Mar 05 20:48:49 KST 2019 [+9])
    }

    /**
     * ISO 통일화 작업 (UTC 형식통일 & MS 제거)
     *
     * @date 2019.04.10
     * @author sdm
     * @param target - any ISO format string
     * */
    public static String unifyISO8601Format(String target) {
        return target
                .replaceAll("\\.([0-9]){1,}", "")
                .replaceAll("\\+([0-2]){1}([0-9]){1}\\:([0-5]){1}([0-9]){1}", "+$1$2$3$4")
                .replaceAll("Z", "+0000");
    }

    /**
     * ISO 검증
     *
     * @date 2019.04.10
     * @author sdm
     * @param target - any ISO format string
     * */
    public static boolean validateISO8601(String target) {
        return (target.contains("T") && (target.contains("Z") || target.contains("+")));
    }

    /**
     * String(ISO8601) -> Date
     *
     * @reference https://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
     * */
    public static Date parseStrISO8601ToDate(String strISO8601) throws Exception {
        if(validateISO8601(strISO8601)) {
            return ISO8601DATEFORMAT.parse(unifyISO8601Format(strISO8601));
        } else {
            return simpleDateFormat.parse(strISO8601);
        }
    }

    /**
     * String(ISO8601) with MiliiSecond -> Date
     * */
    public static Date parseStrISO8601ToDateWithMilliSecond(String strISO8601) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ"); // ms 7자
        return df.parse(strISO8601);
    }


}
