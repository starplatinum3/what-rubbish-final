package com.example.whatrubbish.dto;

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
public class RubbishInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;


//    private Integer id;

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

//    private String queryRub;
//    private String queryRub;

    public RubbishInfoDto() {
    }

}