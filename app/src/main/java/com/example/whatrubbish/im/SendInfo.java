package com.example.whatrubbish.im;

import lombok.Data;

import java.io.Serializable;

/**
 * websocket 通讯的json 封装
 *
 * @author 乐天
 * @since 2018-10-07
 */
@Data
public class SendInfo implements Serializable {



    /**
     * 发送信息的代码
     */
    //private String code;
    public String code;

    /**
     * 信息
     */
    public Message message;


}
