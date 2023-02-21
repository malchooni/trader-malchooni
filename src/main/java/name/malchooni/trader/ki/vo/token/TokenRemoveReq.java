package name.malchooni.trader.ki.vo.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import name.malchooni.trader.config.KiConfig;

/**
 * 토큰 폐기 요청 vo
 *
 * @author ijyoon
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRemoveReq {
    @JsonProperty("appkey")
    private String appKey;
    @JsonProperty("appsecret")
    private String appSecret;
    private String token;

    public TokenRemoveReq(KiConfig kiConfig) {
        this.appKey = kiConfig.getAppKey();
        this.appSecret = kiConfig.getAppSecret();
    }
}
