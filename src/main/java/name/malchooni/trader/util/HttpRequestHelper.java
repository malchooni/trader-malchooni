package name.malchooni.trader.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ObjectMapper util
 *
 * @author ijyoon
 */
public class HttpRequestHelper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private HttpRequestHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * object to json string
     *
     * @param object target object
     * @return json string
     */
    public static String objectToString(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * json String to object
     *
     * @param jsonStr json string
     * @param klass   object class
     * @param <T>     return object class
     * @return object
     */
    public static <T> T stringToObject(String jsonStr, Class<T> klass) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(jsonStr, klass);
    }
}
