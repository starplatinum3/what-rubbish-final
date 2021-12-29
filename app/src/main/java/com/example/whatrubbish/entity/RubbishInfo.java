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

}