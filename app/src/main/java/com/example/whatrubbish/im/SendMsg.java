package com.example.whatrubbish.im;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMsg{
    //private Boolean mine;
    //private String avatar;
    //private String username;
    //private Long timestamp;
    //private String content;
    //private String fromId;
    //private String chatId;
    //private String type;

    public Boolean mine;
    public String avatar;
    public String username;
    public Long timestamp;
    public String content;
    public String fromId;
    public String chatId;
    public String type;

}
