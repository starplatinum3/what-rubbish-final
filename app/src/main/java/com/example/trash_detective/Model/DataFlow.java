package com.example.trash_detective.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.trash_detective.Bean.Block;
import com.example.trash_detective.Bean.Trash;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public abstract class DataFlow {
    private static final String Server_URL = "http://192.168.31.64:8080";
    private static final String TAG = "Data_request_response";
    private static HashMap<String,String> HEADER;
    private static int PIC_MAX_HEIGHT = 832;
    private static int PIC_MAX_WIDTH = 832;
    private static float SCALE = 0;
    private static String Message;
    private static Integer Code;
    private static Integer SCREEN_HEIGHT;
    private static Integer SCREEN_WIDTH;

    public void setHeader(HashMap<String,String> h){
        HEADER=h;
    }

    public static String sendData(int id) throws IOException{
        HttpURLConnection conn=null;
        String response_data = null;
        try{
            conn=openConnection("/"+id,"GET");
            response_data = getConnectionResult(conn);
            setCodeAndMsg(response_data);
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
        return response_data;
    }

    public static String sendData(byte[] Image) throws IOException{
        if(HEADER!=null)
            for(Map.Entry<String, String> entry:HEADER.entrySet()) {
                Log.d(TAG, entry.getKey() + ":" + entry.getValue());
            }
        byte[] image_data = resizeImage(Image);
        String response_data = null;
        HttpURLConnection conn=null;
        try {
            JSONObject request_data = new JSONObject();
            conn=openConnection("/","POST");
            String Image_data_string = Base64.encodeToString(image_data,Base64.NO_WRAP);
            request_data.put("content",Image_data_string);
            request_data.put("timestamp", Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
            String jsonString = JSON.toJSONString(request_data);
            Log.d(TAG, "json： "+jsonString);
            OutputStream out=conn.getOutputStream();
            out.write(jsonString.getBytes());
            Log.d(TAG, "send "+jsonString.length());
            out.flush();
            out.close();
            response_data = getConnectionResult(conn);
            setCodeAndMsg(response_data);
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
        return response_data;
    }

    private static void setCodeAndMsg(String response_data){
        if(!response_data.isEmpty()) {
            JSONObject jsonObject = JSON.parseObject(response_data);
            Code = jsonObject.getInteger("code");
            Message = jsonObject.getString("msg");
        }
    }

    private static byte[] compressImage(byte[] imgData,int maxSize){
        Bitmap img= BitmapFactory.decodeByteArray(imgData,0,imgData.length);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;
        img.compress(Bitmap.CompressFormat.JPEG, options, os);
        while (options>10&&os.toByteArray().length / 1024 > maxSize) {
            os.reset();
            options -= 10;
            img.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        return os.toByteArray();
    }

    private static byte[] resizeImage(byte[] imgData){
        Bitmap img= BitmapFactory.decodeByteArray(imgData,0,imgData.length);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int width = img.getWidth();
        int height = img.getHeight();
        if(width<=PIC_MAX_WIDTH||height<=PIC_MAX_HEIGHT){
            SCALE = 1;
        }else {
            if (SCALE == 0) {
                if ((float) PIC_MAX_WIDTH / width > (float) PIC_MAX_HEIGHT / height) {
                    SCALE = ((float) PIC_MAX_WIDTH / width);
                } else {
                    SCALE = ((float) PIC_MAX_HEIGHT / height);
                }
            }
        }
        Matrix matrix = new Matrix();
        matrix.postScale(SCALE, SCALE);
        Bitmap new_img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        new_img.compress(Bitmap.CompressFormat.JPEG, 100, os);
        return os.toByteArray();
    }


    private static HttpURLConnection openConnection(String uri,String method) throws IOException {
        Log.d(TAG, "url "+Server_URL+uri+" method:"+method);
        URL url = new URL(Server_URL+uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestMethod(method);
        conn.setConnectTimeout(6 * 1000);
        conn.setReadTimeout(6 * 1000);
        conn.setUseCaches(false);
        if(method.equals("POST")) {
            conn.setDoOutput(true);
            conn.setDoInput(true);
        }
        return conn;
    }

    private static String getConnectionResult(HttpURLConnection conn) throws IOException{
        String response_data = null;
        BufferedReader bf_reader;

        if (conn.getResponseCode() == 200) {
            InputStream in = conn.getInputStream();
            bf_reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bf_reader.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            bf_reader.close();
            response_data = sb.toString();
            Log.d(TAG, sb.toString());
        } else {
            Log.e(TAG, "Connection Erro:" + conn.getResponseCode() + "," + conn.getResponseMessage());
        }

        return response_data;
    }

    public static ArrayList<Block> parseBlockData(String json){
        ArrayList<Block> blocks=new ArrayList<>();
        JSONObject data_object = JSON.parseObject(json).getJSONObject("data");
        int image_height = data_object.getInteger("img_height");
        int image_width = data_object.getInteger("img_width");
        JSONArray object_array = data_object.getJSONArray("object");
        for(int i=0;i<object_array.size();i++) {
            JSONObject object = object_array.getJSONObject(i);
            Block block = new Block();
            block.setObject_id(object.getInteger("id"));
            block.setName_CN(object.getString("name_CN"));
            block.setScore(object.getDouble("score"));
            JSONArray location_list = object.getJSONArray("location");
            double top = (location_list.getDouble(0)/image_height)*SCREEN_HEIGHT;
            double left = (location_list.getDouble(1)/image_width)*SCREEN_WIDTH;
            double bottom = (location_list.getDouble(2)/image_height)*SCREEN_HEIGHT;
            double right = (location_list.getDouble(3)/image_width)*SCREEN_WIDTH;
            left = left > SCREEN_WIDTH?SCREEN_WIDTH+10:left;
            top = top > SCREEN_HEIGHT?SCREEN_HEIGHT+10:top;
            right = right > SCREEN_WIDTH?SCREEN_WIDTH-10:right;
            bottom = bottom > SCREEN_HEIGHT?SCREEN_HEIGHT-10:bottom;
            block.setLocation(new double[]{left,top,right,bottom});
            block.setRect(new Rect((int)left,(int)top,(int)right,(int)bottom));
            blocks.add(block);
        }
        return blocks;
    }

    public static Trash parseTrashData(String json){
        JSONObject jsonObject_pre = JSON.parseObject(json);
        JSONObject jsonObject = jsonObject_pre.getJSONObject("data");
        Trash trash = new Trash();
        trash.setName(jsonObject.getString("name"));
        trash.setName_CN(jsonObject.getString("name_CN"));
        trash.setDescription(jsonObject.getString("description"));
        trash.setClassification_id(jsonObject.getInteger("classification_id"));
        trash.setClassification(jsonObject.getString("classification"));
        return trash;
    }

    public static String getMessage(){
        return Message;
    }

    public static int getCode(){
        return Code;
    }

    public static void setScreenSize(int height, int width){
        SCREEN_HEIGHT = height;
        SCREEN_WIDTH = width;
    }
}
