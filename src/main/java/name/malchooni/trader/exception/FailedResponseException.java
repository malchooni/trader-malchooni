package name.malchooni.trader.exception;

/**
 * http 응답에서 status code 값이 200이 아닐 경우 예외 발생
 */
public class FailedResponseException extends Exception {

    public FailedResponseException(Object object) {
        super(object.toString());
    }

    public FailedResponseException(String message) {
        super(message);
    }
}
