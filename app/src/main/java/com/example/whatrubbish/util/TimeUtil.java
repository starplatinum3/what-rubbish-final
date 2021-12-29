package com.example.whatrubbish.util;

//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.jni.Local;
//import zucc.kinect.dto.UsernameAndExceCnt;
//import zucc.kinect.page.ComRedPageTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//@Slf4j
public class TimeUtil {
    public static Timestamp nowSqlTime() {
        return new Timestamp(System.currentTimeMillis());
    }

//    public static Timestamp strToSqlTime(String strTime,String formatStr) {
//        Date date = DateUtil.stringtoDate(strTime, formatStr);
//        return new Timestamp(date.getTime());
//    }


    /*
     * 将时间戳转换为时间
     * https://www.cnblogs.com/qiantao/p/10218921.html
     */
    public static String stampToDate(String timeStampStr) {

        long timeStamp = new Long(timeStampStr);
        return stampToDate(timeStamp);

    }

    void stoD() {

    }

    static void test() throws InterruptedException, ParseException {

//        long l = sqlStampToMillionSeconds(nowSqlTime());
//        System.out.println("l");
//        System.out.println(l);
//
//        String timeStr = toTimeStr(new Date(), "yyyy-MM-dd");
//        System.out.println("timeStr");
//        System.out.println(timeStr);

//        List<ComRedPageTime> list = new ArrayList<>();
//        ComRedPageTime comRedPageTime = new ComRedPageTime(nowSqlTime(), "11");
//
//        list.add(comRedPageTime);
//
//        Thread.sleep(1000);
//        list.add(new ComRedPageTime(nowSqlTime(), "22"));
//
////        list.sort();
//        list.sort(Comparator.comparing(ComRedPageTime::getUpdateTime, Comparator.reverseOrder()));
//        System.out.println(list);

        Timestamp sendDate = TimeUtil.nowSqlTime();
        System.out.println("sendDate");
        System.out.println(sendDate);

//        sqlStampToTimeStr()
//        toTimeStr()

//        String format = new SimpleDateFormat("yyyy-MM-dd").format(sendDate);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sendDate);
        System.out.println("format");
        System.out.println(format);
        Date date = TimeUtil.sqlStampToDate(sendDate);
//        log.info("date");
//        log.info(String.valueOf(date));
        String timeStr = TimeUtil.toTimeStr(date, "yyyy-MM-dd");
        System.out.println("timeStr");
        System.out.println(timeStr);
    }

    public static void main(String[] args) throws ParseException, InterruptedException {
        Date date = sqlStampToDate(nowSqlTime());
        Date date2 = sqlStampToDate(nowSqlTime());
        System.out.println("date");
        System.out.println(date);
        System.out.println(date2.getDay()-date.getDay());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
//        date getday 弃用

    }

    public static long sqlStampToMillionSeconds(Timestamp timestamp) throws ParseException {
        //获取当前时间
//        Timestamp t = new Timestamp(new Date().getTime());
//        System.out.println("当前时间："+t);
        //定义时间格式
        Date date = sqlStampToDate(nowSqlTime());

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
//        String str = dateFormat.format(timestamp);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
//        //此处转换为毫秒数
//        Date parse = sdf.parse(str);
//        System.out.println("parse");
//        System.out.println(parse);
        long millionSeconds = date.getTime();
//        long millionSeconds = sdf.parse(str).getTime();// 毫秒
//        System.out.println("毫秒数：" + millionSeconds);
        return millionSeconds;
    }

    /**
     * 不能用 .. date 会多几天
     * 改了 可以使用了
     * 来自 https://blog.csdn.net/gkx_csdn/article/details/88421994
     *
     * @param timestamp
     * @return
     * @throws
     */
    public static Date sqlStampToDate(Timestamp timestamp)  {
        //获取当前时间
//        Timestamp t = new Timestamp(new Date().getTime());
//        System.out.println("当前时间："+t);
        //定义时间格式
        return new Date(timestamp.getTime());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
//        String str = dateFormat.format(timestamp);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
//        //此处转换为毫秒数
//        Date parse = sdf.parse(str);
////        System.out.println("parse");
////        System.out.println(parse);
//
//        return parse;
//        long millionSeconds = sdf.parse(str).getTime();// 毫秒
//        System.out.println("毫秒数：" + millionSeconds);
//        return millionSeconds;
    }

    public static String sqlStampToTimeStr(Timestamp timestamp, String timePattern) throws ParseException {
        String formatTime = new SimpleDateFormat(timePattern).format(timestamp);
        return formatTime;

    }

//    public static Date longToDate(long timeStamp) {
//        String res;
////        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        long lt = new Long(s);
//        Date date = new Date(timeStamp);
////        res = simpleDateFormat.format(date);
//        return date;
//    }



    public static String stampToDate(long timeStamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long lt = new Long(s);
        Date date = new Date(timeStamp);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static long getUnixTimestamp() {

        return System.currentTimeMillis() / 1000;
    }

    public static long getUnixTimestamp(String timeStr, String timePattern) throws ParseException {
//        "2020-03-25 15:01:17"
//        "yyyy-MM-dd HH:mm:ss"
        Date date = new SimpleDateFormat(timePattern).parse(timeStr);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern);
//        simpleDateFormat.format()
        //        System.out.println(timestamp);
        return date.getTime() / 1000;
    }

    public static String toTimeStr(Date date, String timePattern) {
//        "2020-03-25 15:01:17"
//        "yyyy-MM-dd HH:mm:ss"
        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern);
//        simpleDateFormat.format()
        //        System.out.println(timestamp);

        return new SimpleDateFormat(timePattern).format(date);
    }


}
