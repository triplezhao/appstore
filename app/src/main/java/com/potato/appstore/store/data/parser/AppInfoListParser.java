package com.potato.appstore.store.data.parser;

import com.potato.appstore.store.data.bean.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 囧图-图册列表数据解析器
 * 
 * @author zhaobingfeng
 * 
 */
public class AppInfoListParser {

	public int code;
	public long minPublicDate ;//最近的时间
//	public long maxPublicDate=System.currentTimeMillis();//最远的时间
	public long maxPublicDate=Long.MAX_VALUE;//最远的时间
	public long total;    //数据总条数
	

	public ArrayList<AppInfo> parseToAlbumList(String jsonStr) {
		if (jsonStr == null) {
			return new ArrayList<AppInfo>();
		}
		ArrayList<AppInfo> list = new ArrayList<AppInfo>();
		try {
			JSONObject obj = new JSONObject(jsonStr);
			code = obj.optInt("Code");
			if (!obj.isNull("Data")) {//Data不为null时
				JSONObject objData = obj.getJSONObject("Data");
				JSONArray array = objData.getJSONArray("List");
				total = objData.optLong("Total");
				if (array != null) {
					int count = array.length();
//					L.e("计算开始：minPublicDate="+minPublicDate+",maxPublicDate="+maxPublicDate);
					for (int i = 0; i < count; i++) {
						JSONObject jsonObj = array.optJSONObject(i);
						if(jsonObj.has("AD")&&jsonObj.getBoolean("AD")){//如果AD字段为true,忽略该图册
							continue;
						}
						AppInfo entity = AppInfo.createFromJSON(jsonObj);
						list.add(entity);
						long publicDate = entity.getPublicDate();
//						L.d("publicDate="+publicDate);
						if(publicDate>minPublicDate){//最近的时间
							minPublicDate = publicDate;
						}
						if(publicDate<maxPublicDate){//最远的时间
							maxPublicDate = publicDate;
						}
					}
//					L.e("计算结束：minPublicDate="+minPublicDate+",maxPublicDate="+maxPublicDate);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<AppInfo> parseAlbumList(String listKey, JSONObject job) {
	    ArrayList<AppInfo> albums = new ArrayList<AppInfo>();
	    try {
            JSONArray array = job.optJSONArray(listKey);
            if (array != null) {
                int length = array.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObj = array.optJSONObject(i);
                    AppInfo entity = AppInfo.createFromJSON(jsonObj);
                    albums.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return albums;
	}
	
	public void reset(){
		minPublicDate = 0l;
		maxPublicDate = Long.MAX_VALUE;
	}
}
