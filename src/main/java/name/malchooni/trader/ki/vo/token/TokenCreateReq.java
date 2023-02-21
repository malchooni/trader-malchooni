package name.malchooni.trader.ki.vo.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import name.malchooni.trader.config.KiConfig;

/**
 * 토큰 생성 요청 vo
 *
 * @author ijyoon
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenCreateReq {
    @JsonProperty("grant_type")
    private String grantType = "client_credentials";
    @JsonProperty("appkey")
    private String appKey;
    @JsonProperty("appsecret")
    private String appSecret;

    public TokenCreateReq(KiConfig kiConfig) {
        this.appKey = kiConfig.getAppKey();
        this.appSecret = kiConfig.getAppSecret();
    }
}
