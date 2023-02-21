package name.malchooni.trader.telegram.vo;

import java.util.List;

import lombok.Data;
import name.malchooni.trader.telegram.vo.getupdates.Result;

/**
 * 봇 메시지창 업데이트 응답 vo
 *
 * @author ijyoon
 */
@Data
public class GetUpdatesRes {
    private boolean ok;
    private List<Result> result;
}
