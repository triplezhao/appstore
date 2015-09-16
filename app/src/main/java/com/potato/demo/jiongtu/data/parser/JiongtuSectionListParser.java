package com.potato.demo.jiongtu.data.parser;

import com.potato.demo.jiongtu.data.bean.JiongtuSection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 囧图-栏目列表数据解析器
 * 
 * @author zhaobingfeng
 * 
 */
public class JiongtuSectionListParser {

	/** 返回码 */
	public static int code;
	/** 返回Message */
	public static String message;
	/** 返回Total值 */
	public static long total;

	public static ArrayList<JiongtuSection> parseToSectionList(String jsonStr) {
		if (jsonStr == null) {
			return null;
		}
		ArrayList<JiongtuSection> list =null;
		try {
			JSONObject obj = new JSONObject(jsonStr);
			code = obj.optInt("Code");
			message = obj.optString("Message");
			if (!obj.isNull("Data")) {
				JSONObject jo = obj.getJSONObject("Data");
				if (jo != null) {
					list = new ArrayList<JiongtuSection>();
					JSONObject objData = obj.getJSONObject("Data");
					total = objData.optLong("Total");
					JSONArray array = objData.getJSONArray("List");
					int count = array.length();
					for (int i = 0; i < count; i++) {
						JSONObject jsonObj = array.optJSONObject(i);
						JiongtuSection entity = JiongtuSection.createFromJSON(jsonObj);
						list.add(entity);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}