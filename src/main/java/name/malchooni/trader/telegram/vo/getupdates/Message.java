package name.malchooni.trader.telegram.vo.getupdates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import name.malchooni.trader.telegram.vo.common.Chat;

/**
 * {@link Result}
 *
 * @author ijyoon
 */
@Data
public class Message {

    @JsonProperty("message_id")
    private long messageId;
    private From from;
    private Chat chat;
    private long date;
    private String text;
}
