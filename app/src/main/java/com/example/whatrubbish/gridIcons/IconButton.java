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

    public IconButton() {
    }
}
