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
public class TTTC8434RPriceDTO {
    private long dnca_tot_amt;
    private long nxdy_excc_amt;
    private long prvs_rcdl_excc_amt;
    private long cma_evlu_amt;
    private long bfdy_buy_amt;
    private long thdt_buy_amt;
    private long nxdy_auto_rdpt_amt;
    private long bfdy_sll_amt;
    private long thdt_sll_amt;
    private long d2_auto_rdpt_amt;
    private long bfdy_tlex_amt;
    private long thdt_tlex_amt;
    private long tot_loan_amt;
    private long scts_evlu_amt;
    private long tot_evlu_amt;
    private long nass_amt;
    private String fncg_gld_auto_rdpt_yn;
    private long pchs_amt_smtl_amt;
    private long evlu_amt_smtl_amt;
    private long evlu_pfls_smtl_amt;
    private long tot_stln_slng_chgs;
    private long bfdy_tot_asst_evlu_amt;
    private long asst_icdc_amt;
    private double asst_icdc_erng_rt;
}
