package com.example.trash_detective.Bean;

public class Trash {
    private int trash_id;
    private String name;
    private String name_CN;
    private int classification_id;
    private String classification;
    private String description;

    public Trash(){
    }

    public Trash(int trash_id, String name, String name_CN, int classification_id, String classification, String description) {
        this.trash_id = trash_id;
        this.name = name;
        this.name_CN = name_CN;
        this.classification_id = classification_id;
        this.classification = classification;
        this.description = description;
    }

    public int getTrash_id() {
        return trash_id;
    }

    public void setTrash_id(int trash_id) {
        this.trash_id = trash_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_CN() {
        return name_CN;
    }

    public void setName_CN(String name_CN) {
        this.name_CN = name_CN;
    }

    public int getClassification_id() {
        return classification_id;
    }

    public void setClassification_id(int classification_id) {
        this.classification_id = classification_id;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
