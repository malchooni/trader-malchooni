package name.malchooni.trader.telegram.vo.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * {@link name.malchooni.trader.telegram.vo.getupdates.Message}
 * {@link name.malchooni.trader.telegram.vo.sendmessage.Result}
 *
 * @author ijyoon
 */
@Data
public class Chat {
    private long id;
    @JsonProperty("first_name")
    private String firstName;
    private String type;
}
