package main.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Doc_TimestampUtil {

    // SECOND -> 1973~5000, MILLISECOND -> 1973~.. (https://www.epochconverter.com/ 참고)
    public static final long STANDARD_OF_MILLISECOND = 100000000000L;

    /**
     * TimeStamp MilliSecond 검증
     *
     * @date 2019.04.02
     * @param numOfTimestamp - 전달 받은 `timestamp`값 ( `second`인지 `milliSecond`인지 알 수 없음 )
     * @return `milliSecond`로 변환된 `timeStamp`값
     * @author supermoon
     * */
    public static long parseSecondToMilliSecond(long numOfTimestamp) {
        try {
            if(numOfTimestamp >= STANDARD_OF_MILLISECOND) {
                return numOfTimestamp;
            } else {
                return numOfTimestamp * 1000;
            }
        } catch (Exception e) {
            return numOfTimestamp;
        }
    }

}
