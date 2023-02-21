package name.malchooni.trader.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import name.malchooni.trader.config.TelegramConfig;
import name.malchooni.trader.exception.FailedResponseException;
import name.malchooni.trader.telegram.vo.GetUpdatesRes;
import name.malchooni.trader.telegram.vo.SendMessageReq;
import name.malchooni.trader.telegram.vo.SendMessageRes;
import name.malchooni.trader.util.HttpRequestHelper;
import name.malchooni.trader.util.HttpRequester;
import name.malchooni.trader.util.RequestPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * telegram http request
 *
 * @author ijyoon
 */
@Component
public class TelegramInvoker {

    @Autowired
    private HttpRequester httpRequester;
    @Autowired
    private TelegramConfig telegramConfig;

    /**
     * 메시지 보내기
     *
     * @param sendMessageReq 보내는 메시지
     * @return 결과 메시지
     */
    public SendMessageRes sendMessage(SendMessageReq sendMessageReq) throws IOException, InterruptedException, FailedResponseException {
        RequestPaper paper = new RequestPaper()
                .setMethod("POST")
                .setUri(TelegramAPI.SEND_MESSAGE.replace(TelegramAPI.TOKEN, this.telegramConfig.getToken()))
                .putHeader("Content-Type", "application/json")
                .setBody(HttpRequestHelper.objectToString(sendMessageReq));

        SendMessageRes response = this.httpRequester.call(paper, SendMessageRes.class);
        if (!response.isOk()) {
            throw new FailedResponseException(response);
        }
        return response;
    }

    /**
     * 채팅 봇 업데이트
     *
     * @return 업데이트 메시지
     */
    public GetUpdatesRes getUpdates() throws IOException, InterruptedException, FailedResponseException {
        RequestPaper paper = new RequestPaper()
                .setMethod("GET")
                .setUri(TelegramAPI.GET_UPDATES.replace(TelegramAPI.TOKEN, this.telegramConfig.getToken()))
                .putHeader("Content-Type", "application/json");

        GetUpdatesRes response = this.httpRequester.call(paper, GetUpdatesRes.class);
        if (!response.isOk()) {
            throw new FailedResponseException(response);
        }
        return response;
    }
}
