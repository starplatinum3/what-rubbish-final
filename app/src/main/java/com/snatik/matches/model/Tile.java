package com.snatik.matches.model;

//@Da
public class Tile {
    int id;
    String tileImageUrl;

    public Tile() {
    }

    public Tile(int id, String tileImageUrl) {
        this.id = id;
        this.tileImageUrl = tileImageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTileImageUrl() {
        return tileImageUrl;
    }

    public void setTileImageUrl(String tileImageUrl) {
        this.tileImageUrl = tileImageUrl;
    }
}
