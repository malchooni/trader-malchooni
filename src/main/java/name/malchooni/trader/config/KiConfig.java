package name.malchooni.trader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 한국투자증권 설정 값
 *
 * @author ijyoon
 */
@Configuration
@ConfigurationProperties(prefix = "ki")
@Data
public class KiConfig {
    private String appKey;
    private String appSecret;
    private String accountNo;
}
