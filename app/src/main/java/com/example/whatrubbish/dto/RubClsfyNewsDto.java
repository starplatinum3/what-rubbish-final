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

//@AllArgsConstructor
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RubClsfyNewsDto(String id, Date ctime, String title, String description, String source, String picUrl, String url) {
        this.id = id;
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.source = source;
        this.picUrl = picUrl;
        this.url = url;
    }
}