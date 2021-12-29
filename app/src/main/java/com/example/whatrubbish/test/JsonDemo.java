package com.example.whatrubbish.test;

//import android.os.DropBoxManager;

import android.os.DropBoxManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
 
public class JsonDemo {
	public static void main(String[] args) {
		JsonObject msgObj = new JsonObject();
		msgObj.addProperty("test", "123");
		msgObj.addProperty("test1", "1233");
		msgObj.addProperty("test2", "1234");
		msgObj.addProperty("test3", "1235");
		System.out.println(msgObj.toString());
		String msgStr = msgObj.toString();
 
		Gson g = new Gson();
		JsonObject obj = g.fromJson(msgStr, JsonObject.class);
		System.out.println(obj.get("test"));
//		Type 'android.os.DropBoxManager.Entry' does not have type parameters
//		for (DropBoxManager.Entry<String, JsonElement> set : obj.entrySet()) {//通过遍历获取key和value
//			System.out.println(set.getKey() + "_" + set.getValue());
//		}
//		obj.get("")
//		obj.get()
//		obj.getAsBigDecimal().
	}
}