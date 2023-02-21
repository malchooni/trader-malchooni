package name.malchooni.trader.telegram.vo.getupdates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * {@link Message}
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
    @JsonProperty("language_code")
    private String languageCode;
}
