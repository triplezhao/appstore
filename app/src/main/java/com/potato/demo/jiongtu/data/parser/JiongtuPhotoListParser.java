package com.potato.demo.jiongtu.data.parser;

import com.potato.demo.jiongtu.data.bean.JiongtuPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 囧图-图片列表数据解析器
 * 
 * @author zhaobingfeng
 * 
 */
public class JiongtuPhotoListParser {

	public static int code;
	public static long total;
	public static String message;
	public static ArrayList<String> picUrls = new ArrayList<String>();

	public static ArrayList<JiongtuPhoto> parseToPhotoList(String jsonStr) {
		if(jsonStr==null){
			return null;
		}
		ArrayList<JiongtuPhoto> list = new ArrayList<JiongtuPhoto>();
		try {
			picUrls.clear();
			JSONObject obj = new JSONObject(jsonStr);
			code = obj.optInt("Code");
			message = obj.optString("Message");
			JSONObject objData = obj.getJSONObject("Data");
			total = objData.optLong("Total");
			JSONArray array = objData.getJSONArray("List");
			int count = array.length();
			for (int i = 0; i < count; i++) {
				JSONObject jsonObj = array.optJSONObject(i);
				JiongtuPhoto entity = JiongtuPhoto.createFromJSON(jsonObj);
				list.add(entity);
				picUrls.add(entity.getBigUrl());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String parseFirstPhotoPicUrl(String jsonStr) {
		if(jsonStr==null){
			return null;
		}
		try {
			JSONObject obj = new JSONObject(jsonStr);
			JSONObject objData = obj.getJSONObject("Data");
			JSONArray array = objData.getJSONArray("List");
			if(array.length()>0){
				String bu = array.getJSONObject(0).optString("BigUrl");
				return bu==null?array.getJSONObject(0).optString("MiddleUrl"):bu;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}