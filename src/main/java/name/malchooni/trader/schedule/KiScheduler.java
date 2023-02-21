package name.malchooni.trader.schedule;

import lombok.extern.slf4j.Slf4j;
import name.malchooni.trader.ki.job.AuthenticationManager;
import name.malchooni.trader.ki.job.StockBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

/**
 * 한국투자증권 스케줄러
 *
 * @author ijyoon
 */
@Slf4j
@RestController
@RequestMapping("/eventCall/ki")
public class KiScheduler {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private StockBalance stockBalance;

    /**
     * 한국투자증권 토큰 생성 스케줄러
     */
    @GetMapping("/createToken")
    @Scheduled(cron = "${job.cron.ki.createToken}")
    public void createToken() {
        long startTime = System.currentTimeMillis();
        try {
            this.startedInfoLog("createToken");
            this.authenticationManager.createToken();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            this.finishedInfoLog("createToken", startTime);
        }
    }

    /**
     * 한국투자증권 토큰 제거 스케줄러
     */
    @GetMapping("/removeToken")
    @Scheduled(cron = "${job.cron.ki.removeToken}")
    @PreDestroy
    public void removeToken() {
        long startTime = System.currentTimeMillis();
        try {
            this.startedInfoLog("removeToken");
            this.authenticationManager.removeToken();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            this.finishedInfoLog("removeToken", startTime);
        }
    }

    /**
     * 잔고 확인
     * output => telegram
     */
    @GetMapping("/stockBalance")
    @Scheduled(cron = "${job.cron.ki.stockBalance}")
    public void stockBalance() {
        long startTime = System.currentTimeMillis();
        try {
            this.startedInfoLog("stockBalance");
            this.stockBalance.execute();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            this.finishedInfoLog("stockBalance", startTime);
        }
    }

    /**
     * 시작 로그
     *
     * @param methodName 메소드명
     */
    private void startedInfoLog(String methodName) {
        if (log.isInfoEnabled()) {
            log.info("{} started.", methodName);
        }
    }

    /**
     * 종료 로그
     *
     * @param methodName 메소드명
     * @param startTime  시작 시각
     */
    private void finishedInfoLog(String methodName, long startTime) {
        if (log.isInfoEnabled()) {
            log.info("{} finished. elapsed time : {} ms", methodName, (System.currentTimeMillis() - startTime));
        }
    }
}
