package name.malchooni.trader.telegram.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 메시지 보내기 vo
 *
 * @author ijyoon
 */
@Data
public class SendMessageReq {
    @JsonProperty("chat_id")
    private String chatId;
    private String text;
}
