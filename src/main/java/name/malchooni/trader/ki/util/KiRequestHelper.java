package name.malchooni.trader.ki.util;

import name.malchooni.trader.util.DateHelper;

import java.util.UUID;

/**
 * 한국투자증권 api 호출 유틸
 */
public class KiRequestHelper {

    private KiRequestHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 고유 아이디 생성
     *
     * @return 고유값
     */
    public static String makeGtUid() {
        return DateHelper.nowStr() + "-" + UUID.randomUUID().toString().substring(22);
    }
}
