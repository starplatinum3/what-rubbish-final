package com.example.whatrubbish.dbo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @description rubbish_info
 * @author mqp
 * @date 2021-12-16
 */
@Data

@AllArgsConstructor
@ToString
public class RubbishInfoDbo implements Serializable {

    private static final long serialVersionUID = 1L;


//    private Integer id;

    /**
     * rub_name
     */

    private String rubName;
    //private String name;

    /**
     * type
     */

    private Integer type;

    /**
     * aipre
     */

    private Integer aipre;

    /**
     * explain_rub
     */

    private String explainRub;
    //private String explain;

    /**
     * contain
     */

    private String contain;

    /**
     * tip
     */

    private String tip;

    /**
     * query_rub
     */

//    private String queryRub;
    private String queryRub;

    public RubbishInfoDbo() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRubName() {
        return rubName;
    }

    public void setRubName(String rubName) {
        this.rubName = rubName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAipre() {
        return aipre;
    }

    public void setAipre(Integer aipre) {
        this.aipre = aipre;
    }

    public String getExplainRub() {
        return explainRub;
    }

    public void setExplainRub(String explainRub) {
        this.explainRub = explainRub;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getQueryRub() {
        return queryRub;
    }

    public void setQueryRub(String queryRub) {
        this.queryRub = queryRub;
    }
}