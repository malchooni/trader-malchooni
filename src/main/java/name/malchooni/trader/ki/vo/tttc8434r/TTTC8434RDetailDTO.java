package name.malchooni.trader.ki.vo.tttc8434r;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * {@link TTTC8434RRes}
 *
 * @author ijyoon
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TTTC8434RDetailDTO {
    private String pdno;
    private String prdt_name;
    private String trad_dvsn_name;
    private long bfdy_buy_qty;
    private long bfdy_sll_qty;
    private long thdt_buyqty;
    private long thdt_sll_qty;
    private long hldg_qty;
    private long ord_psbl_qty;
    private double pchs_avg_pric;
    private long pchs_amt;
    private long prpr;
    private long evlu_amt;
    private long evlu_pfls_amt;
    private double evlu_pfls_rt;
    private double evlu_erng_rt;
    private String loan_dt;
    private long loan_amt;
    private long stln_slng_chgs;
    private String expd_dt;
    private double fltt_rt;
    private long bfdy_cprs_icdc;
    private String item_mgna_rt_name;
    private String grta_rt_name;
    private long sbst_pric;
    private double stck_loan_unpr;
}
