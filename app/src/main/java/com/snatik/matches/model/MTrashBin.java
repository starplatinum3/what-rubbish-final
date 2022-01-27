package com.snatik.matches.model;


public class MTrashBin {
    int id;
    String  imgUrl;

    int typeId;
    //10000+1
    public MTrashBin(int id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public MTrashBin(int id, String imgUrl, int typeId) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
