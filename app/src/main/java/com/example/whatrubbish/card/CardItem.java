package com.example.whatrubbish.card;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CardItem {

    private int mTextResource;
    private int mTitleResource;
//    private String content;
//    private String time;
    private int headImgRes;

    //"id": "464ca70abb3271fef4343a5e7580b928",
    //        "ctime": "2021-02-04 17:45",
    //        "title": "青云谱区：让垃圾分类习惯渗透到工作中（图）",
    //        "description": "对商铺进行宣传教育（危婧怡摄）对于商铺进行宣传教育（危婧怡摄）垃圾分类宣传进机关单位（危婧怡摄）　　大江网/大江新闻客户端讯通讯员危婧怡报道：近日，青云",
    //        "source": "垃圾分类新闻",
    //        "picUrl": "http://n.sinaimg.cn/sinakd202124s/85/w500h385/20210204/543b-kirmait9157267.png",
    //        "url": "http://k.sina.com.cn/article_1767961804_6960f4cc02000rr4l.html"
    String title;
    String description;
    Date time;
    String  picUrl;
    String  url;

    public  CardItem(){}

    public CardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }
    //bind 到第一个东西上去了

    public CardItem(String title, String description, Date time, String picUrl) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.picUrl = picUrl;
    }

    public int getText() {
        return mTextResource;
    }

    //public int getTitle() {
    //    return mTitleResource;
    //}
}
