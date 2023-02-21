package name.malchooni.trader.telegram.vo.sendmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import name.malchooni.trader.telegram.vo.common.Chat;

/**
 * {@link name.malchooni.trader.telegram.vo.SendMessageRes}
 *
 * @author ijyoon
 */
@Data
public class Result {

    @JsonProperty("message_id")
    private long messageId;
    private From from;
    private Chat chat;
    private long date;
    private String text;
}
