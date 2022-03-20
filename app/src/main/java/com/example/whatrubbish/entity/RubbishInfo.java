package com.example.whatrubbish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import lombok.ToString;

/**
 * @description rubbish_info
 * @author mqp
 * 这里的名字和 sprib 的数据库不一样应该没事吧
 * @date 2021-12-16
 */
@Data

@AllArgsConstructor
@ToString
public class RubbishInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    /**
     * rub_name
     */

//    private String rubName;
    private String name;

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

//    private String explainRub;
    private String explain;

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

    private String queryRub;
//    private String queryRub;

    public RubbishInfo() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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