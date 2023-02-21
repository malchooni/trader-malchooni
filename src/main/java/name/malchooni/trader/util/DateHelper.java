package name.malchooni.trader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 날짜 유틸
 *
 * @author ijyoon
 */
public class DateHelper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();

    private DateHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 문자열 형태를 long 값으로 변환
     *
     * @param format      date format
     * @param dateTimeStr date string
     * @return get time long
     */
    public static synchronized long stringToLong(String format, String dateTimeStr) throws ParseException {
        DATE_FORMAT.applyPattern(format);
        return DATE_FORMAT.parse(dateTimeStr).getTime();
    }

    /**
     * 'yyyyMMddHHmmssSSS' 형태의 현재 시각 반환
     *
     * @return 현재 시각
     */
    public static synchronized String nowStr() {
        DATE_FORMAT.applyPattern("yyyyMMddHHmmssSSS");
        return DATE_FORMAT.format(new Date());
    }
}
