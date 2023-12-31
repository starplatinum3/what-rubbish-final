package com.example.whatrubbish.jo;

import com.example.whatrubbish.card.CardItem;
import com.example.whatrubbish.dto.RubClsfyNewsDto;
import com.example.whatrubbish.entity.RubClsfyNews;
import com.example.whatrubbish.entity.RubbishInfo;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
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
public class RubClsfyNewsJo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * id_str
     */

    private String idStr;
    //private String id_str;

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

    //private String picUrl;
    private String picurl;
    //不知道大小写要紧吗

    /**
     * url
     */

    private String url;

    public RubClsfyNewsJo() {
    }

    public   RubClsfyNews toRubClsfyNews(){
        Gson gson = new Gson();
        String s1 = gson.toJson(this);
        RubClsfyNews rubClsfyNews = gson.fromJson(s1, RubClsfyNews.class);
        rubClsfyNews.setId_str(idStr);
        //rubClsfyNews.setIdStr(idStr);
        rubClsfyNews.setPicUrl(picurl);
        return rubClsfyNews;
    }

    public CardItem toCardItem() {
        Gson gson = new Gson();
        //RubClsfyNews rubClsfyNews=   this;
        String s1 = gson.toJson(this);


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

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
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

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}