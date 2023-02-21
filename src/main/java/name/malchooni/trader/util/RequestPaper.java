package name.malchooni.trader.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 요청 URI에 대한 정보를 보관하는 클래스
 *
 * @author ijyoon
 */
public class RequestPaper {

    private String method;
    private String uri;
    private final Map<String, String> header;
    private final Map<String, String> queryParam;
    private HttpRequest.BodyPublisher body;
    private String bodyStr;

    public RequestPaper() {
        this.header = new HashMap<>();
        this.queryParam = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    /**
     * http method set.
     *
     * @param method GET, POST, ....
     * @return RequestPaper object
     */
    public RequestPaper setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getUri() {
        return uri;
    }

    /**
     * uri set.
     *
     * @param uri request address
     * @return RequestPaper object
     */
    public RequestPaper setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    /**
     * http header put.
     *
     * @param key   키
     * @param value 값
     * @return RequestPaper object
     */
    public RequestPaper putHeader(String key, String value) {
        this.header.put(key, value);
        return this;
    }

    public Map<String, String> getQueryParam() {
        return queryParam;
    }

    /**
     * query parameter put.
     *
     * @param key   키
     * @param value 값
     * @return RequestPaper object
     */
    public RequestPaper putQueryParam(String key, String value) {
        this.queryParam.put(key, value);
        return this;
    }

    public HttpRequest.BodyPublisher getBody() {
        return body;
    }

    /**
     * http body 값 설정 ( json )
     *
     * @param object json object or json string
     * @return RequestPaper object
     */
    public RequestPaper setBody(Object object) throws JsonProcessingException {
        String jsonStr;
        if (object instanceof String) {
            jsonStr = (String) object;
        } else {
            jsonStr = HttpRequestHelper.objectToString(object);
        }
        this.bodyStr = jsonStr;
        this.body = HttpRequest.BodyPublishers.ofString(jsonStr);
        return this;
    }

    /**
     * 등록된 header, query parameter 출력
     *
     * @return URI 정보
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        this.mapToString("header", this.header, sb);
        this.mapToString("queryParam", this.queryParam, sb);
        sb.append("bodyStr : ").append(this.bodyStr);
        return sb.toString();
    }

    /**
     * map key, value to string
     *
     * @param name map name
     * @param map  target map
     * @param sb   StringBuilder object
     */
    private void mapToString(String name, Map<String, String> map, StringBuilder sb) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(name)
                    .append(" [")
                    .append(entry.getKey())
                    .append("] : ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
    }
}
