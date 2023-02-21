package name.malchooni.trader.telegram.vo.sendmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * {@link Result}
 *
 * @author ijyoon
 */
@Data
public class From {
    private long id;
    @JsonProperty("is_bot")
    private boolean isBot;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("username")
    private String userName;
}
