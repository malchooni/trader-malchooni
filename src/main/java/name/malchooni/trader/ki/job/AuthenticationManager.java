package name.malchooni.trader.ki.job;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import name.malchooni.trader.config.KiConfig;
import name.malchooni.trader.exception.FailedResponseException;
import name.malchooni.trader.ki.util.KiRequestHelper;
import name.malchooni.trader.ki.vo.token.TokenCreateReq;
import name.malchooni.trader.ki.vo.token.TokenCreateRes;
import name.malchooni.trader.ki.vo.token.TokenRemoveReq;
import name.malchooni.trader.ki.vo.token.TokenRemoveRes;
import name.malchooni.trader.util.DateHelper;
import name.malchooni.trader.util.HttpRequestHelper;
import name.malchooni.trader.util.HttpRequester;
import name.malchooni.trader.util.RequestPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * 한국투자증권 토큰 생성 및 폐기 관리
 *
 * @author ijyoon
 */
@Component
public class AuthenticationManager {

    private final HttpRequester httpRequester;
    private final TokenCreateReq tokenCreateReq;
    private final TokenRemoveReq tokenRemoveReq;
    private TokenCreateRes tokenCreateRes;
    private boolean auth = false;

    @Autowired
    public AuthenticationManager(KiConfig kiConfig) {
        this.httpRequester = new HttpRequester();
        this.tokenCreateReq = new TokenCreateReq(kiConfig);
        this.tokenRemoveReq = new TokenRemoveReq(kiConfig);
    }

    /**
     * 인증 여부
     *
     * @return 인증 결과 값
     */
    public boolean isAuth() {
        return auth;
    }

    public String getToken() {
        if (isAuth() && this.tokenCreateRes != null) {
            return this.tokenCreateRes.getAccessToken();
        } else {
            return null;
        }
    }

    /**
     * 토큰 생성
     */
    public synchronized void createToken() throws ParseException, IOException, InterruptedException, FailedResponseException {
        if (!isAuth() || this.tokenCreateRes == null || DateHelper.stringToLong("yyyy-MM-dd HH:mm:ss", this.tokenCreateRes.getExpired()) < new Date().getTime()) {
            RequestPaper tokenPaper = this.createTokenPaper();
            this.tokenCreateRes = this.httpRequester.call(tokenPaper, TokenCreateRes.class);
            this.auth = true;
        }
    }

    /**
     * 토큰 생성 요청 정보
     *
     * @return 요청 정보
     */
    private RequestPaper createTokenPaper() throws JsonProcessingException {
        RequestPaper requestPaper = new RequestPaper();
        return requestPaper
                .setMethod("POST")
                .setUri("https://openapi.koreainvestment.com:9443/oauth2/tokenP")
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .putHeader("gt_uid", KiRequestHelper.makeGtUid())
                .setBody(HttpRequestHelper.objectToString(this.tokenCreateReq));
    }

    /**
     * 토큰 폐기
     */
    public synchronized void removeToken() throws IOException, InterruptedException, FailedResponseException {
        if (!this.auth) {
            return;
        }

        this.tokenRemoveReq.setToken(this.tokenCreateRes.getAccessToken());
        RequestPaper tokenRemovePaper = this.createRemoveTokenPaper();
        TokenRemoveRes tokenRemoveRes = this.httpRequester.call(tokenRemovePaper, TokenRemoveRes.class);
        if (tokenRemoveRes.getCode() == 200) {
            this.auth = false;
        }
    }

    /**
     * 토큰 폐기 요청 정보
     *
     * @return 요청 정보
     */
    private RequestPaper createRemoveTokenPaper() throws JsonProcessingException {
        RequestPaper requestPaper = new RequestPaper();
        return requestPaper
                .setMethod("POST")
                .setUri("https://openapi.koreainvestment.com:9443/oauth2/revokeP")
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .putHeader("gt_uid", KiRequestHelper.makeGtUid())
                .setBody(HttpRequestHelper.objectToString(this.tokenRemoveReq));
    }
}
