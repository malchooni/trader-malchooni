package name.malchooni.trader.ki.job;

import lombok.extern.slf4j.Slf4j;
import name.malchooni.trader.config.KiConfig;
import name.malchooni.trader.config.TelegramConfig;
import name.malchooni.trader.ki.util.KiRequestHelper;
import name.malchooni.trader.ki.vo.tttc8434r.TTTC8434RDetailDTO;
import name.malchooni.trader.ki.vo.tttc8434r.TTTC8434RPriceDTO;
import name.malchooni.trader.ki.vo.tttc8434r.TTTC8434RRes;
import name.malchooni.trader.telegram.TelegramInvoker;
import name.malchooni.trader.telegram.vo.SendMessageReq;
import name.malchooni.trader.util.HttpRequester;
import name.malchooni.trader.util.RequestPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;

/**
 * 한국투자증권 API 요청
 * 잔고 확인
 *
 * @author ijyoon
 */
@Slf4j
@Service
public class StockBalance {

    @Autowired
    private KiConfig kiConfig;
    @Autowired
    private TelegramConfig telegramConfig;
    @Autowired
    private HttpRequester httpRequester;
    @Autowired
    private TelegramInvoker telegramInvoker;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 잔고 확인 실행
     */
    public void execute() throws Exception {

        RequestPaper requestPaper = this.createPaper();
        TTTC8434RRes response = this.httpRequester.call(requestPaper, TTTC8434RRes.class);

        List<TTTC8434RDetailDTO> detailDTOList = response.getOutput1();
        if (detailDTOList == null || detailDTOList.isEmpty()) {
            return;
        }

        String message = this.createMessage(response);
        sendMessageToUser(message);
    }

    /**
     * 텔레그램 메시지 전송
     *
     * @param message 전달 메시지
     */
    private void sendMessageToUser(String message) throws Exception {
        SendMessageReq reqSendMessageReq = new SendMessageReq();
        reqSendMessageReq.setChatId(this.telegramConfig.getChatId());
        reqSendMessageReq.setText(message);

        this.telegramInvoker.sendMessage(reqSendMessageReq);
    }

    /**
     * 요청 값 설정
     * header, query parameter 설정
     *
     * @return RequestPaper object
     */
    private RequestPaper createPaper() {
        String accountNo = this.kiConfig.getAccountNo();
        return new RequestPaper()
                .setMethod("GET")
                .setUri("https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/trading/inquire-balance")
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .putHeader("gt_uid", KiRequestHelper.makeGtUid())
                .putHeader("authorization", "Bearer " + this.authenticationManager.getToken())
                .putHeader("appkey", this.kiConfig.getAppKey())
                .putHeader("appsecret", this.kiConfig.getAppSecret())
                .putHeader("tr_id", "TTTC8434R")
                .putQueryParam("CANO", accountNo.substring(0, 8))
                .putQueryParam("ACNT_PRDT_CD", accountNo.substring(8, 10))
                .putQueryParam("AFHR_FLPR_YN", "N")
                .putQueryParam("OFL_YN", "")
                .putQueryParam("INQR_DVSN", "02")
                .putQueryParam("UNPR_DVSN", "01")
                .putQueryParam("FUND_STTL_ICLD_YN", "N")
                .putQueryParam("FNCG_AMT_AUTO_RDPT_YN", "N")
                .putQueryParam("PRCS_DVSN", "01")
                .putQueryParam("CTX_AREA_FK100", "")
                .putQueryParam("CTX_AREA_NK100", "");
    }

    /**
     * 전달 메시지 생성
     *
     * @param response TTTC8434R 응닫 값
     * @return 메시지
     */
    private String createMessage(TTTC8434RRes response) {
        List<TTTC8434RDetailDTO> detailDTOList = response.getOutput1();

        StringBuilder message = new StringBuilder();
        message.append("한국투자증권").append(System.lineSeparator());
        message.append("계좌 : ").append(this.kiConfig.getAccountNo()).append(System.lineSeparator());

        for (TTTC8434RDetailDTO row : detailDTOList) {
            message.append("=============================").append(System.lineSeparator())
                    .append("종목명 : ").append(row.getPrdt_name()).append(" [").append(row.getPdno()).append("]").append(System.lineSeparator())
                    .append("현재가 : ").append(NumberFormat.getInstance().format(row.getPrpr())).append("원 (").append(NumberFormat.getInstance().format(row.getBfdy_cprs_icdc())).append(")").append(System.lineSeparator())
                    .append("매입평균가격 : ").append(NumberFormat.getInstance().format(row.getPchs_avg_pric())).append("원").append(System.lineSeparator())
                    .append("보유수량 : ").append(NumberFormat.getInstance().format(row.getHldg_qty())).append(" [").append(NumberFormat.getInstance().format(row.getOrd_psbl_qty())).append("]").append(System.lineSeparator())
                    .append("매입금액 : ").append(NumberFormat.getInstance().format(row.getPchs_amt())).append("원").append(System.lineSeparator())
                    .append("손익 : ").append(NumberFormat.getInstance().format(row.getEvlu_pfls_amt())).append("원").append(System.lineSeparator())
                    .append("평가금액 : ").append(NumberFormat.getInstance().format(row.getEvlu_amt())).append("원 ( ").append(row.getEvlu_pfls_rt()).append("% )").append(System.lineSeparator());
        }

        TTTC8434RPriceDTO tttc8434RPriceDTO = response.getOutput2().get(0);
        double rate = (double) tttc8434RPriceDTO.getEvlu_pfls_smtl_amt() / (double) tttc8434RPriceDTO.getPchs_amt_smtl_amt() * 100;

        message.append("=============================").append(System.lineSeparator())
                .append("매입금액 : ").append(NumberFormat.getInstance().format(tttc8434RPriceDTO.getPchs_amt_smtl_amt())).append("원").append(System.lineSeparator())
                .append("평가손익 : ").append(NumberFormat.getInstance().format(tttc8434RPriceDTO.getEvlu_pfls_smtl_amt())).append("원").append(System.lineSeparator())
                .append("평가금액 : ").append(NumberFormat.getInstance().format(tttc8434RPriceDTO.getEvlu_amt_smtl_amt())).append("원").append(System.lineSeparator())
                .append("수익률 : ").append(String.format("%.2f", rate)).append("%").append(System.lineSeparator())
                .append("예수금 : ").append(NumberFormat.getInstance().format(tttc8434RPriceDTO.getDnca_tot_amt())).append("원").append(System.lineSeparator());

        return message.toString();
    }
}
