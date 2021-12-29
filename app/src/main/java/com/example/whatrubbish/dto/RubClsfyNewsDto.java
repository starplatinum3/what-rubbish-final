package com.example.whatrubbish.dto;

import com.example.whatrubbish.card.CardItem;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

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
public class RubClsfyNewsDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private String  id;


    /**
     * ctime
     */

    private Date ctime;

    /**
     * title
     */

    private String title;

    /**
     * description
     */

    private String description;

    /**
     * source
     */

    private String source;

    /**
     * picurl
     */

    private String picUrl;
    //private String picurl;
    //不知道大小写要紧吗

    /**
     * url
     */

    private String url;

    public RubClsfyNewsDto() {
    }

    public CardItem toCardItem(){
        Gson gson = new Gson();
        //RubClsfyNews rubClsfyNews=   this;
        String s1 = gson.toJson(this);
        //dto to  dbo
        //RubbishInfo rubbishInfo = gson.fromJson(s1, RubbishInfo.class);
        //RubClsfyNews rubClsfyNews = gson.fromJson(s1, RubClsfyNews.class);
        //
        ////TimeUtil.
        ////RubbishInfoDbo rubbishInfoDbo = gson.fromJson(s1, RubbishInfoDbo.class);
        //rubClsfyNews.setId_str(o.getId());
        //rubClsfyNews.setPicUrl(o.getPicUrl());
        //rubbishInfoDbo.setQueryRub(searchWord);
//                o.ge

        CardItem cardItem = gson.fromJson(s1, CardItem.class);
        cardItem.setTime(this.getCtime());

        return cardItem;
    }


}