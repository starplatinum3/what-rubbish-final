package com.example.whatrubbish.gridIcons;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IconButton {
    Integer iconId;
    String text;
    Long typeId;

    public IconButton(String text) {
        this.text = text;
    }

    public IconButton(Integer iconId) {
        this.iconId = iconId;
    }

    //    这和之前定义的 Type 数据是一样的
    public IconButton(Integer iconId, String text) {
        this.iconId = iconId;
        this.text = text;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public IconButton() {
    }
}
