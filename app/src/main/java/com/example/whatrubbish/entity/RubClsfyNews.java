package com.example.whatrubbish.entity;

import com.example.whatrubbish.card.CardItem;
import com.example.whatrubbish.dto.RubClsfyNewsDto;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import lombok.ToString;

/**
 * @author mqp
 * @description lajifenlei_news
 * @date 2021-12-17
 * 可以直接写数据库后台
 */
@Data

@AllArgsConstructor
@ToString
public class RubClsfyNews implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * id_str
     */

    //private String idStr;
    private String id_str;

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

    public RubClsfyNews() {
    }

    public static RubClsfyNews toMe(RubClsfyNewsDto rubClsfyNewsDto) {
        Gson gson = new Gson();
        //RubClsfyNews rubClsfyNews=   this;
        String s1 = gson.toJson(rubClsfyNewsDto);
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

        RubClsfyNews rubClsfyNews = gson.fromJson(s1, RubClsfyNews.class);
        rubClsfyNews.setId_str(rubClsfyNewsDto.getId());
        //rubClsfyNews.setIdStr(rubClsfyNewsDto.getId());
        rubClsfyNews.setId(null);
        //rubClsfyNews.setCtime(null);
        //rubClsfyNews.se
        //cardItem.setTime(this.getCtime());
        return rubClsfyNews;
        //return cardItem;
    }

    public CardItem toCardItem() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
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
}