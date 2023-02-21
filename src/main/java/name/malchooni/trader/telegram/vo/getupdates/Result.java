package name.malchooni.trader.telegram.vo.getupdates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * {@link name.malchooni.trader.telegram.vo.GetUpdatesRes}
 *
 * @author ijyoon
 */
@Data
public class Result {

    @JsonProperty("update_id")
    private long updateId;
    private Message message;
}
