package mylib;

import java.sql.Timestamp;
import java.util.logging.Logger;

public class ExpireLoggerUtil {

    private static final int EXPIRE_TIME = 3600 * 1000;
    private static boolean isOpen = false; /* log open 상태 - 자동으로 default 설정 */
    private static Timestamp expireTime = new Timestamp(System.currentTimeMillis());

    public static void log(Logger logger, String message) {
        /* auto */
        if (isOpen && !updateIsOpenByExpireTime()) {
            logger.info(message);
        }
    }

    public static boolean updateIsOpenByExpireTime() {
        /*System.out.println(expireTime.getTime() < new Timestamp(System.currentTimeMillis()).getTime());*/
        if (isOpen && expireTime.getTime() < new Timestamp(System.currentTimeMillis()).getTime()) {
            /*setIsOpen(false); 무한루프*/
            ExpireLoggerUtil.isOpen = false;
            return true;
        } else {
            return false;
        }
    }

    public static boolean getIsOpen() {
        updateIsOpenByExpireTime();
        return ExpireLoggerUtil.isOpen;
    }

    public static void setIsOpen(boolean isOpen) {
        updateIsOpenByExpireTime();
        ExpireLoggerUtil.isOpen = isOpen;
        ExpireLoggerUtil.expireTime = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime() + EXPIRE_TIME);
    }

}
