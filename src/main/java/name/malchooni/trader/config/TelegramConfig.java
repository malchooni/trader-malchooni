package name.malchooni.trader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 텔레그램 api 설정 값
 *
 * @author ijyoon
 */
@Configuration
@ConfigurationProperties(prefix = "telegram")
@Data
public class TelegramConfig {
    private String token;
    private String chatId;
}
