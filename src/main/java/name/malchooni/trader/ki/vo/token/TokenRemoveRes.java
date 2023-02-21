package name.malchooni.trader.ki.vo.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 토큰 폐기 응답 vo
 *
 * @author ijyoon
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRemoveRes {
    private int code;
    private String message;
}
