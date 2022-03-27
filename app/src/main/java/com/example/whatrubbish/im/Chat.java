package com.example.whatrubbish.im;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chat {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private String avatar;
    private String unReadCount;
    private String lastMessage;
    //一个群组的上次点击时间应该 不能绑定在群组里面 因为每个人的点击不一样啊
    //现在先不要考虑那么复杂 首先需要发送信息啊
    private String mobile;
    private String email;
    private String type;
    String userId;

}