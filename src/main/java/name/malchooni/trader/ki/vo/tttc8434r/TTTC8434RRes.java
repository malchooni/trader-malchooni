package name.malchooni.trader.ki.vo.tttc8434r;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Data;

/**
 * 계좌 잔고 확인 응답 vo
 *
 * @author ijyoon
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TTTC8434RRes {
    private String rt_cd;
    private String msg_cd;
    private String msg1;
    private String ctx_area_fk100;
    private String ctx_area_nk100;
    private List<TTTC8434RDetailDTO> output1;
    private List<TTTC8434RPriceDTO> output2;
}
