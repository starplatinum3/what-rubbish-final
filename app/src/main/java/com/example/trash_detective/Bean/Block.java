package com.example.trash_detective.Bean;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;


public class Block implements Parcelable {
    private Rect rect;
    private int id;
    private String name;
    private String name_CN;
    private double score;
    private double[] location;

    public Block(){
        super();
    }

    protected Block(Parcel in) {
        rect = in.readParcelable(Rect.class.getClassLoader());
        id = in.readInt();
        name = in.readString();
        name_CN = in.readString();
        score = in.readDouble();
        location = in.createDoubleArray();
    }

    public static final Creator<Block> CREATOR = new Creator<Block>() {
        @Override
        public Block createFromParcel(Parcel in) {
            return new Block(in);
        }

        @Override
        public Block[] newArray(int size) {
            return new Block[size];
        }
    };

    public void setObject_id(int id) {
        this.id = id;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setName_CN(String nameCN) {
        this.name_CN = nameCN;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public int getObject_id() {
        return id;
    }

    public Rect getRect() {
        return rect;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public String getName_CN() {
        return name_CN;
    }

    public double[] getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(rect, i);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(name_CN);
        parcel.writeDouble(score);
        parcel.writeDoubleArray(location);
    }
}
