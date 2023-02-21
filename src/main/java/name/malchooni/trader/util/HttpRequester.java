package name.malchooni.trader.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

import jakarta.ws.rs.core.UriBuilder;
import lombok.extern.slf4j.Slf4j;
import name.malchooni.trader.exception.FailedResponseException;
import org.springframework.stereotype.Component;

/**
 * http 요청 유틸
 *
 * @author ijyoon
 */
@Slf4j
@Component
public class HttpRequester {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    /**
     * RequestPaper 요청 정보로 http client 호출
     *
     * @param paper         요청정보
     * @param responseClass response object class name
     * @param <T>           class type
     * @return object class
     */
    public <T> T call(RequestPaper paper, Class<T> responseClass) throws IOException, InterruptedException, FailedResponseException {

        // 요청 로그
        this.requestLog(paper);

        HttpRequest request = this.createHttpRequest(paper);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 응답 로그
        this.responseLog(response);

        if (response.statusCode() != 200) {
            throw new FailedResponseException(response.statusCode() + " " + response.body());
        }

        return HttpRequestHelper.stringToObject(response.body(), responseClass);
    }

    /**
     * POST or GET HttpRequest 생성
     *
     * @param paper 요청 정보
     * @return HttpRequest object
     */
    private HttpRequest createHttpRequest(RequestPaper paper) {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        switch (paper.getMethod()) {
            case "POST":
                builder.POST(paper.getBody()).uri(URI.create(paper.getUri()));
                break;
            case "GET":
                builder.GET().uri(this.createURI(paper));
                break;
            default:
                throw new UnsupportedOperationException(paper.getMethod() + " method is not supported.");
        }
        this.setHeaders(builder, paper);
        return builder.build();
    }

    /**
     * http header 값 입력
     *
     * @param builder HttpRequest.Builder object
     * @param paper   요청 정보
     */
    private void setHeaders(HttpRequest.Builder builder, RequestPaper paper) {
        Map<String, String> headerMap = paper.getHeader();

        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            builder.setHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * URI object 생성
     *
     * @param paper 요청 정보
     * @return URI object
     */
    private URI createURI(RequestPaper paper) {
        UriBuilder uriBuilder = UriBuilder.fromUri(paper.getUri());
        Map<String, String> queryParamMap = paper.getQueryParam();

        for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }

        return uriBuilder.build();
    }

    /**
     * 요청 로그 출력
     *
     * @param paper 요청 정보
     */
    private void requestLog(RequestPaper paper) {
        if (log.isInfoEnabled()) {
            log.info(" ** Request paper Info ** ");
            log.info(paper.toString());
            log.info(" ** Request paper Info end ** ");
        }
    }

    /**
     * 응답 로그 출략
     *
     * @param response 응답 정보
     */
    private void responseLog(HttpResponse<String> response) {
        if (log.isInfoEnabled()) {
            log.info("Http status code : " + response.statusCode());
            HttpHeaders headers = response.headers();
            headers.map().forEach((k, v) -> log.info(k + ":" + v));
            log.info(" ** Response body message ** ");
            log.info(response.body());
            log.info(" ** Response body message end. ** ");
        }
    }
}
