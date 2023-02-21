package name.malchooni.trader.telegram.vo;

import lombok.Data;
import name.malchooni.trader.telegram.vo.sendmessage.Result;

/**
 * 보낸 메시지 응답 vo
 *
 * @author ijyoon
 */
@Data
public class SendMessageRes {

    private boolean ok;
    private Result result;

}
