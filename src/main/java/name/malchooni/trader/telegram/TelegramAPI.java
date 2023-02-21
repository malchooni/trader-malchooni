package name.malchooni.trader.telegram;

/**
 * telegram api list
 *
 * @author ijyoon
 */
public final class TelegramAPI {

    public static final String TOKEN = "[TOKEN]";
    public static final String GET_UPDATES = "https://api.telegram.org/bot[TOKEN]/getUpdates";
    public static final String SEND_MESSAGE = "https://api.telegram.org/bot[TOKEN]/sendMessage";

    private TelegramAPI() {
        throw new IllegalStateException("Final class");
    }
}