package com.example.whatrubbish.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
//import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReturnT {
//    private String port;


    private int code;
    private String msg;
    private Object data;
//private static final String code  = 777;

    public static final int codeSuccess = 200;
    public static final int codeError = 400;
//    public static ReturnT build() {
//        return new ReturnT();
//    }


    public static ReturnT success(String msg) {
        return new ReturnT(codeSuccess, msg, null);
//        return success(msg, null);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Code", "1000");
//        jsonObject.put("Msg", msg);
//        jsonObject.put("Data", "");
//        return jsonObject;
//        return new ReturnT("200", msg, null);
    }
//    public static JSONObject success(String msg) {
////        return success(msg, null);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Code", "1000");
//        jsonObject.put("Msg", msg);
//        jsonObject.put("Data", "");
//        return jsonObject;
////        return new ReturnT("200", msg, null);
//    }


    public static ReturnT success() {
        return success("成功");
    }

//    public static JSONObject successWithToken(String msg, String token) {
//        JSONObject jsonObject = ReturnT.success(msg);
//        jsonObject.put("token", token);
////        JSONObject jsonObject = new JSONObject();
////        jsonObject.put("port", "200");
////        jsonObject.put("msg", msg);
//        return jsonObject;
////        return new ReturnT("200", msg, null);
//    }

//    public static JSONObject successWithToken(String msg, Object data, String token) {
//        JSONObject jsonObject = ReturnT.success(msg, data);
//        jsonObject.put("token", token);
//
//        return jsonObject;
//    }


    public static ReturnT success(String msg, Object data) {
        return ReturnT.builder().code(codeSuccess).msg(msg).data(data).build();
    }

    public static ReturnT success(Object data) {
        return ReturnT.success("成功", data);
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("data",data);
//        return  jsonObject;
////        return new ReturnT("200", msg, null);
    }

    public static ReturnT error(String msg) {
       return  new  ReturnT(codeError,msg,null);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Code", "400");
//        jsonObject.put("Msg", msg);
//        jsonObject.put("Data", "");
//
//        return jsonObject;
//        return new ReturnT("200", msg, null);
    }

//    public static JSONObject error(String msg) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Code", "400");
//        jsonObject.put("Msg", msg);
//        jsonObject.put("Data", "");
//
//        return jsonObject;
////        return new ReturnT("200", msg, null);
//    }

    public static ReturnT error(Object data) {
//        JSONObject jsonObject = new JSONObject();
//        JSONObject errorReturn = ReturnT.error("失败");
////数据库改了 会重启
////        jsonObject.put("port", "400");
//        errorReturn.put("Data", data);
//
//        return errorReturn;
//        return new ReturnT("200", msg, null);
        return  new ReturnT(codeError,"失败",data);
    }

//    public static ReturnT error(String msg, Object data) {
//        return new ReturnT("400", msg, data);
//    }

    public static ReturnT error() {
        return error("失败");

    }


//    public static ReturnT error(String msg, Object data) {
////        JSONObject jsonObject=new JSONObject();
////        jsonObject.put("port","200");
////        jsonObject.put("msg",msg);
//        return error()
//        JSONObject jsonObject = ReturnT.error(msg);
//        jsonObject.put("Data", data);
//        return jsonObject;
////        return new ReturnT("200", msg, null);
//    }

    private ReturnT() {
    }


//
//    public Object getdata() {
//        return data;
//    }
//
//    public ReturnT setdata(Object data) {
//        this.data = data;
//        return this;
//    }


    public static void main(String[] args) {
//        JSONObject returnT = ReturnT.success("好耶");
//        Map<String, String> map = new HashMap<>();
//        map.put("名字", "star");
//        JSONObject returnTWithData = ReturnT.error("坏也", map);
//        System.out.println(returnT);
//        System.out.println(returnTWithData);
//
////        System.out.println(returnT);
//        System.out.println(returnTWithData.getData());
    }
}
