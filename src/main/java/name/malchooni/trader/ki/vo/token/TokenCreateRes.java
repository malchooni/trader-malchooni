package name.malchooni.trader.ki.vo.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 토큰 생성 응답 vo
 *
 * @author ijyoon
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenCreateRes {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_token_token_expired")
    private String expired;
    @JsonProperty("token_type")
    private String type;
    @JsonProperty("expires_in")
    private long expiresIn;
}
