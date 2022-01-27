package com.snatik.matches.model;


public class MRubbish {
    int id;
    String  imgUrl;
    String  imgUrlTrashBin;

    public String getImgUrlTrashBin() {
        return imgUrlTrashBin;
    }

    public void setImgUrlTrashBin(String imgUrlTrashBin) {
        this.imgUrlTrashBin = imgUrlTrashBin;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    int typeId;
    //10000+1
    public MRubbish(int id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public MRubbish(int id, String imgUrl, int typeId) {
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
